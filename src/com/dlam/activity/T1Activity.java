/**
 * Program  : T1Activity.java
 * Author   : chunsoft
 * Create   : 2015-6-31 下午4:24:32
 *
 * Copyright 2015 by newyulong Technologies Ltd.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of dlam Technologies Ltd.("Confidential Information").  
 * You shall not disclose such Confidential Information and shall 
 * use it only in accordance with the terms of the license agreement 
 * you entered into with dlam Technologies Ltd.
 *
 */

package com.dlam.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.dlam.adapter.A_ListDataAdapter;
import com.dlam.bean.A_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.infolist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.pullrefresh.PullToRefreshBase;
import com.dlam.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.dlam.pullrefresh.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author   chunsoft
 * @version  1.0.0
 * @2015-6-31 下午4:24:32
 */
public class T1Activity extends Activity{
	private ListView listview;
	
	private String URL;
	private JSONObject RequestData;
	private String NET_TAG = "getInfoList";
	
	private A_ListDataAdapter adapter;
	private infolist infolistbean;

	
	private A_ListBean responseData = new A_ListBean();
	
	private List<infolist> datas = new ArrayList<infolist>();
	//LsitView 
    private ListView mListView;
    
    private PullToRefreshListView mPullListView;
    private A_ListDataAdapter mAdapter;
    private int curPage = 1;
    private int toaPage;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String time;
    private boolean hasMoreData = true;
    private String myid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		//在实际用的过程中，如果想要在子activity中得到context，需要把这个context = getParent();
		//在我的项目中是这样做的，不过我现在测试，不需要父activity中的context好像也行，如果你遇到错，注意一下这方面。
		//Context context = this.getParent();
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		 mPullListView = new PullToRefreshListView(this);
	        setContentView(mPullListView);
	        
	        mPullListView.setPullLoadEnabled(true);
	        mPullListView.setScrollLoadEnabled(true);
	        time = formatDateTime(System.currentTimeMillis());
	        getListgetInfoList(Integer.toString(curPage), "0", "0", time, new VolleyDataCallback() {
	        	@Override
	    		public void onSuccess(A_ListBean bean) {
	        		curPage++;
	        		toaPage = Integer.parseInt(bean.getTotalpage());
	        		datas = bean.getInfolist();
	        		
	        		mAdapter = new A_ListDataAdapter(T1Activity.this,datas,R.layout.listitem);
	        		mListView = mPullListView.getRefreshableView();
	        	    mListView.setAdapter(mAdapter);
	        	    mListView.setOnItemClickListener(new OnItemClickListener() {

	        			@Override
	        			public void onItemClick(AdapterView<?> parent, View view,
	        					int position, long id) {
	        				myid = datas.get(position).getId().toString();
	        				Intent intent = new Intent();
	        				switch (datas.get(position).getType1id()) {
							case "1":
								intent.putExtra("myid", myid);
								intent.setClass(T1Activity.this, A_NewsDetailActivity.class);
								T1Activity.this.startActivity(intent);
								break;

							case "2":
								intent.putExtra("myid", myid);
								intent.setClass(T1Activity.this, A_ActivityDetailActivity.class);
								T1Activity.this.startActivity(intent);
								break;
								
							case "3":
								intent.putExtra("myid", myid);
								intent.setClass(T1Activity.this, A_ServiceDetailActivity.class);
								T1Activity.this.startActivity(intent);
								break;
							default:
								break;
							}
	        			}
	        		});
	    			}
	    		});
	        	        
	        mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	                //mIsStart = true;
	            	getUpdateData();
	            }
	            
	           @Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	                //mIsStart = false;
	                getMoreData();
	            }
	        }); 
	        setLastUpdateTime();
	     
	        
	       // mPullListView.doPullRefreshing(true, 5);
	        
	    }
	    
	    private void setLastUpdateTime() {
	        String text = formatDateTime(System.currentTimeMillis());
	        mPullListView.setLastUpdatedLabel(text);
	    }

	    private String formatDateTime(long time) {
	        if (0 == time) {
	            return "";
	        }
	        
	        return mDateFormat.format(new Date(time));
	    }
	    
	   
	    public void getMoreData()
	    {
	    	 time  = formatDateTime(System.currentTimeMillis());
	    	 

	    	 if(curPage <= toaPage)
	    	 {
	    		 hasMoreData = true; 	

	    	 getListgetInfoList(String.valueOf(curPage), "0", "0", "0", new VolleyDataCallback() {
	         	@Override
	     		public void onSuccess(A_ListBean bean) {       
	         		List<infolist> dataBean = new ArrayList<infolist>();
	         		dataBean = bean.getInfolist();
	         		for(int i = 0;i < dataBean.size();i++)
	         		{
	         			datas.add(dataBean.get(i));        			
	         		}
	         		
	         		mAdapter.notifyDataSetChanged();
	                mPullListView.onPullDownRefreshComplete();
	                mPullListView.onPullUpRefreshComplete();
	                mPullListView.setHasMoreData(hasMoreData);
	                setLastUpdateTime();
	                curPage++;
	     			}
	         	
	    	 }
	  
	    );
	    	 }
	    	 else{
	    		 hasMoreData = false;
	    		 mPullListView.setHasMoreData(hasMoreData);
	    	 }
	    }

	    public void getUpdateData()
	    {
	    	String time = formatDateTime(System.currentTimeMillis());
	    	getListgetInfoList(Integer.toString(curPage), "0", "0", time, new VolleyDataCallback() {
	         	@Override
	     		public void onSuccess(A_ListBean bean) {
	         		String curPage = bean.getCurrentpage();
	         		String toaPage = bean.getTotalpage();
	         		boolean hasMoreData = true;
	         		if(curPage == toaPage)
	         		{
	         			hasMoreData = false;
	         		}
	         		List<infolist> dataBean = bean.getInfolist();
	         		for(int i = 0;i < dataBean.size();i++)
	         		{
	         			datas.add(dataBean.get(i));
	         		}
	         		
	         		mAdapter.notifyDataSetChanged();
	                mPullListView.onPullDownRefreshComplete();
	                mPullListView.onPullUpRefreshComplete();
	                mPullListView.setHasMoreData(hasMoreData);
	                setLastUpdateTime();
	     			}
	     		});
	    }
	    public A_ListBean getListgetInfoList
	   	(String currentpage,String type1id,
	   			String type2id,String issuetime,
	   			final VolleyDataCallback callback)
	   	{

	   		URL = Constant.IP + Constant.GetInfoList +"/currentpage/"+
	   		currentpage + "/type1id/" + type1id+ "/type2id/" + type2id +
	   		"/issuetime/" + "0";
	   		RequestData = new JSONObject();
	   		try {
	   			RequestData.put("currentpage", currentpage);
	   			RequestData.put("type1id", type1id);
	   			RequestData.put("type2id", type2id);
	   			RequestData.put("issuetime", issuetime);
	   		} catch (JSONException e) {
	   			
	   			e.printStackTrace();
	   		}
	   		GsonRequest<A_ListBean> request = new GsonRequest<A_ListBean>(Method.GET,URL, RequestData.toString(), 
	   				new Response.Listener<A_ListBean>() {
	   					@Override
	   					public void onResponse(A_ListBean response) {
	   						responseData = response;
	   						
	   						callback.onSuccess(response);
	   					}
	   				}, new AbstractVolleyErrorListener(T1Activity.this) {
	   					@Override
	   					public void onError() {
	   						Log.e(NET_TAG, "----onError");
	   					}
	   				}, A_ListBean.class);
	   		MyApplication.getInstance().addToRequestQueue(request);
	   		return responseData;	
	   	}
	    public interface VolleyDataCallback
		{
			void onSuccess(A_ListBean datas);
		}
}

