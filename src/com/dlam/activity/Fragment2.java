package com.dlam.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.adapter.B_ListDataAdapter;
import com.dlam.bean.B_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.userresourcelist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.pullrefresh.PullToRefreshBase;
import com.dlam.pullrefresh.PullToRefreshListView;
import com.dlam.pullrefresh.PullToRefreshBase.OnRefreshListener;
import com.dlam.utils.ToastUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

/**
 * @author chunsoft
 */
public class Fragment2 extends Fragment implements OnItemSelectedListener{
	//控件初始化
	private PullToRefreshListView mPullListView;
	private ListView mListView;
	
	private Spinner sp1;
	private Spinner sp2;
	private Spinner sp3;
	private Spinner sp4;
	
	private ImageButton ib_search;
	//变量初始化 
	private JSONObject RequestData;
	private B_ListBean ResponseData;
	private String URL;
	private String NET_TAG  = "Fragment2";
	private Context mContext;
	
	private int toaPage;
	private SimpleDateFormat mDateFormat;
	private String myid;
	private B_ListDataAdapter mAdapter;
	private List<userresourcelist> datas;
	private boolean hasMoreData = true;
	
	private int currentpage = 1;
	private String locationname;
	private String demand;
	private String statusname;
	private String charactername;
	
	private String userid;
	private String phonenum;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.fragment2, container, false);
		mContext = getActivity();
		/*控件初始化*/
		ib_search = (ImageButton) parentView.findViewById(R.id.ib_search);
		sp1 = (Spinner) parentView.findViewById(R.id.sp_1);
		sp2 = (Spinner) parentView.findViewById(R.id.sp_2);
		sp3 = (Spinner) parentView.findViewById(R.id.sp_3);
		sp4 = (Spinner) parentView.findViewById(R.id.sp_4);
		mPullListView = (PullToRefreshListView) parentView.findViewById(R.id.lv_b);
		
		
		init();	
		click();
		getData();
		
		return parentView;
		}
	
	private void init()
	{
		mDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		mPullListView.setPullLoadEnabled(true);
	    mPullListView.setScrollLoadEnabled(true);
	}
	
	/*数据初始化*/
	public void getData()
	{
		currentpage = 1;
		getSelect();
		GetInfoList(Integer.toString(currentpage), locationname, demand, statusname,charactername, new VolleyDataCallback() {
        	@Override
    		public void onSuccess(B_ListBean bean) {
        		currentpage++;
        		toaPage = Integer.parseInt(bean.totalpage);
        		datas = bean.userresourcelist;
        		
        		mAdapter = new B_ListDataAdapter(mContext,datas,R.layout.b_listviewitem);
        		mListView = mPullListView.getRefreshableView();
        	    mListView.setAdapter(mAdapter);
        	    mListView.setOnItemClickListener(new OnItemClickListener() {
        			@Override
        			public void onItemClick(AdapterView<?> parent, View view,
        					int position, long id) {
        				userid = datas.get(position).id.toString();
        				phonenum = datas.get(position).phonenum.toString();

        				Intent intent = new Intent();
        				intent.putExtra("userid", userid);
						intent.setClass(mContext, PersonPageActivity.class);
						mContext.startActivity(intent);
					}
        		});
    			}
    		});
		 mPullListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
	            @Override
	            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
	                //mIsStart = true;
	            	currentpage++;
	            	getMoreData();
	            }
	            
	           @Override
	            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
	                //mIsStart = false;
	        	   currentpage++;
	                getMoreData();
	            }
	        }); 
	        setLastUpdateTime();
	}

	private void setLastUpdateTime()
	{
	       
		String text = formatDateTime(System.currentTimeMillis());  
		mPullListView.setLastUpdatedLabel(text);
	}

	private String formatDateTime(long time) 
	{
		if (0 == time) 
		{
			return "";   
		}  
	    return mDateFormat.format(new Date(time));
	}
	
    public void getMoreData()
    {
    	
    	 if(currentpage <= toaPage)
    	 {
    		 hasMoreData = true;
 
    	 GetInfoList(String.valueOf(currentpage), locationname, demand, statusname, charactername,new VolleyDataCallback() {
         	@Override
     		public void onSuccess(B_ListBean bean) {
         		System.out.println("网络当前页数"+bean.currentpage);
         		List<userresourcelist> dataBean = new ArrayList<userresourcelist>();
         		dataBean = bean.userresourcelist;
         		for(int i = 0;i < dataBean.size();i++)
         		{
         			datas.add(dataBean.get(i));
         		}
         		
         		mAdapter.notifyDataSetChanged();
                mPullListView.onPullDownRefreshComplete();
                mPullListView.onPullUpRefreshComplete();
                mPullListView.setHasMoreData(hasMoreData);
                setLastUpdateTime();
                currentpage++;
     			}
    	 }
  
    );
    	 }
    	 else{
    		 hasMoreData = false;
    		 mPullListView.setHasMoreData(hasMoreData);
    	 }
    }
	/*Send JSONRequestData then get ResponseData*/    
	public B_ListBean GetInfoList
	(String currentpage,String locationname,
	   			String demand,String statusname,
	   			String charactername,final VolleyDataCallback callback)
	{
	   	URL = Constant.IP + Constant. getUserResourceList;
	   	System.out.println("URL------------" + URL);
	   	RequestData = new JSONObject();
	   	try {
	   		RequestData.put("currentpage", currentpage);
	   		if(!locationname.equals("全部"))
			{
				RequestData.put("locationname", locationname);				
			}
			if(!demand.equals("全部"))
			{
				RequestData.put("demand", demand);	
			}
			if(!statusname.equals("全部"))
			{
				RequestData.put("statusname", statusname);				
			}
			if (!charactername.equals("全部")) {
				RequestData.put("charactername", charactername);
			}
	   	} catch (JSONException e) {		
	   			e.printStackTrace();
	   	}
	   	GsonRequest<B_ListBean> request = new GsonRequest<B_ListBean>(URL, RequestData.toString(), 
	   				new Response.Listener<B_ListBean>() {
	   					@Override
	   					public void onResponse(B_ListBean response) {
	   						ResponseData = response;
	   						callback.onSuccess(response);
	   					}
	   				}, new AbstractVolleyErrorListener(mContext) {
	   					@Override
	   					public void onError() {
	   						Log.e(NET_TAG, "----onError");
	   					}
	   				}, B_ListBean.class);
	   		MyApplication.getInstance().addToRequestQueue(request);
	   		return ResponseData;	
	   	}
		 
	//数据回调接口
	public interface VolleyDataCallback
	{
		void onSuccess(B_ListBean datas);
	}
	
	private void click()
	{
		ib_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),A_Search.class);
				startActivity(intent);
			}
		});
		sp1.setOnItemSelectedListener(this);
		sp2.setOnItemSelectedListener(this);
		sp3.setOnItemSelectedListener(this);
		sp4.setOnItemSelectedListener(this);
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		
		case R.id.sp_1:
			ToastUtil.showShortToast(mContext, sp1.getSelectedItem().toString());
			getData();
			break;
		case R.id.sp_2:
			getData();
			break;
		case R.id.sp_3:
			getData();
			break;
		case R.id.sp_4:
			getData();
			break;

		default:
			break;
		}
	}

	/*Get currentData of Spinner*/
	private void getSelect()
	{
		locationname = sp1.getSelectedItem().toString();
		demand = sp2.getSelectedItem().toString();
		statusname = sp3.getSelectedItem().toString();
		charactername = sp4.getSelectedItem().toString();
	}
	
	
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}
}
