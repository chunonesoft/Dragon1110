package com.dlam.utils;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.activity.MyApplication;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;

import android.content.Context;
import android.util.Log;


public class CommonFun {
	private boolean flag;
	private JSONObject RequestData;
	private String URL;
	private String NET_TAG = "CommonFun";
	
	/**
	 * 想参加
	 */
	
	public boolean WantJoin(String id)
	{
		
		return flag;
	}
	/**
	 * 收藏
	 */
	public boolean SaveInfo(Context mContext,String id)
	{
		flag = false;
		GetRemindRequest(mContext,id, new CommonVolleyDataCallback<FeedbackBean>() {		
			@Override
			public void onSuccess(FeedbackBean datas) {
				if(datas.retcode.toString().equals("1"))
				{
					flag = true;
				}
			}
		});
		return flag;
	}
	/**
	 * 提醒
	 */
	public boolean RemindUser(Context mContext,String id)
	{
		flag = false;
		GetRemindRequest(mContext,id, new CommonVolleyDataCallback<FeedbackBean>() {		
			@Override
			public void onSuccess(FeedbackBean datas) {
				if(datas.retcode.toString().equals("1"))
				{
					System.out.println("--------" + datas.retcode);
					flag = true;
				}
			}
		});
		return flag;
	}
	
	/**
	 * 提醒网络请求
	 */

	private void GetRemindRequest(Context mContext,String id,final CommonVolleyDataCallback<FeedbackBean> callback)
	{
		URL = Constant.IP + Constant.getRemindInfo;
		RequestData = new JSONObject();
		try {
			RequestData.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<FeedbackBean> request = new GsonRequest<>(URL,  RequestData.toString(), 
	   				new Response.Listener<FeedbackBean>() {
	   					@Override
	   					public void onResponse(FeedbackBean response) {
	   						callback.onSuccess(response);
	   					}
	   				}, new AbstractVolleyErrorListener(mContext) {
	   					@Override
	   					public void onError() {
	   						Log.e(NET_TAG, "----onError");
	   					}
	   				}, FeedbackBean.class);
	   		MyApplication.getInstance().addToRequestQueue(request);	
	}
}
