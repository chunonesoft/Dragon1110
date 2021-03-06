package com.dlam.activity;

import com.dlam.dragon.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

/**
 * @author chunsoft
 *	Activity
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Response;
import com.dlam.adapter.A_ListDataAdapter;
import com.dlam.bean.A_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.infolist;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.pullrefresh.PullToRefreshBase;
import com.dlam.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.dlam.pullrefresh.PullToRefreshListView;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * @author   chunsoft
 * @version  1.0.0
 * @2015-6-31 下午4:24:32
 */
public class D_T2Activity extends Activity{
	
	//控件声明
	private ListView mListView;
    private PullToRefreshListView mPullListView;
   
    //变量声明
    private Context mContext;
    private A_ListDataAdapter mAdapter;
   	private String URL;
   	private JSONObject RequestData;
   	private String NET_TAG = "D_T2Activity";
   	
	private A_ListBean responseData = new A_ListBean();
	private List<infolist> datas = new ArrayList<infolist>();
    
    private int curPage = 1;
    private int toaPage;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private boolean hasMoreData = true;
    private String myid;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		mPullListView = new PullToRefreshListView(this);
	    setContentView(mPullListView);
	    mContext = D_T2Activity.this;    
	    mPullListView.setPullLoadEnabled(true);
	    mPullListView.setScrollLoadEnabled(true);
	        getListgetInfoList(Integer.toString(curPage),Constant.token, new VolleyDataCallback() {
	        	@Override
	    		public void onSuccess(A_ListBean bean) {
	        		curPage++;
	        		toaPage = Integer.parseInt(bean.getTotalpage());
	        		datas = bean.getInfolist();
	        		
	        		mAdapter = new A_ListDataAdapter(mContext,datas,R.layout.listitem);
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
								intent.setClass(mContext, A_NewsDetailActivity.class);
								mContext.startActivity(intent);
								break;

							case "2":
								intent.putExtra("myid", myid);
								intent.setClass(mContext, A_ActivityDetailActivity.class);
								mContext.startActivity(intent);
								break;
								
							case "3":
								intent.putExtra("myid", myid);
								intent.setClass(mContext, A_ServiceDetailActivity.class);
								mContext.startActivity(intent);
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
	    	 if(curPage <= toaPage)
	    	 {
	    		 hasMoreData = true;

	    	 getListgetInfoList(String.valueOf(curPage),Constant.token ,new VolleyDataCallback() {
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
	    	getListgetInfoList(Integer.toString(curPage),Constant.token , new VolleyDataCallback() {
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
	   	(String currentpage,String token,final VolleyDataCallback callback)
	   	{

	   		URL = Constant.IP + Constant.getMyFavoriteList;
	   		RequestData = new JSONObject();
	   		try {
	   			RequestData.put("currentpage", currentpage);
	   			RequestData.put("token", token);
	   		} catch (JSONException e) {
	   			
	   			e.printStackTrace();
	   		}
	   		GsonRequest<A_ListBean> request = new GsonRequest<A_ListBean>(URL, RequestData.toString(), 
	   				new Response.Listener<A_ListBean>() {
	   					@Override
	   					public void onResponse(A_ListBean response) {
	   						responseData = response;
	   						
	   						callback.onSuccess(response);
	   					}
	   				}, new AbstractVolleyErrorListener(mContext) {
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


