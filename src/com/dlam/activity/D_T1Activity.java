package com.dlam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.adapter.D_LabelAdapter;
import com.dlam.adapter.RecentViewAdapter;
import com.dlam.bean.Constant;
import com.dlam.bean.D_UserVisitorListBean;
import com.dlam.bean.D_labelBean;
import com.dlam.bean.uservisitorlist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.CommonVolleyDataCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageButton;
/**
 * 
 * @author chunsoft
 *		创业档案
 */
public class D_T1Activity extends Activity implements OnClickListener{

	//控件声明
	private ImageButton iv_more;
	private GridView gv_people;
	private GridView gv_label;
	private GridView gv_major;
	
	//变量声明
	private Intent intent;
	
	private JSONObject RequestData;
	private D_UserVisitorListBean ResponseData;
	private List<uservisitorlist> mDatas;
	private String URL;
	private String NET_TAG = "D_T1Activity";
	private Context mContext;
	private RecentViewAdapter rv_adapter;
	private String tags;
	private String tag[];
	private List<D_labelBean> label_datas;
	private D_labelBean bean;
	
	private D_LabelAdapter label_adapter;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.d_tab1);
		mContext = D_T1Activity.this;
		
		findView();
		click();
		initData();
	}
	
	private void findView()
	{
		iv_more = (ImageButton) findViewById(R.id.iv_more);
		gv_people = (GridView) findViewById(R.id.gv_people);
		gv_label = (GridView) findViewById(R.id.gv_label);
		gv_major = (GridView) findViewById(R.id.gv_major);
	}
	
	private void initData()
	{
		/**
		 * 获取最近来访的人
		 */
		getUserVisitorList(Constant.userData.userid, new CommonVolleyDataCallback<D_UserVisitorListBean>() {
			@Override
			public void onSuccess(D_UserVisitorListBean datas) {
				if(datas.uservisitorlist.size() != 0)
				{
					if(datas.uservisitorlist.size() > 10)
					{
						for(int i = 0;i < 10;i++)
						{
							mDatas.add(datas.uservisitorlist.get(i));
						} 
					}
					else
					{
						mDatas = datas.uservisitorlist;
					}
					rv_adapter = new RecentViewAdapter(mContext, mDatas, R.layout.d_view_visitor);
					gv_people.setAdapter(rv_adapter);
				}	
			}
		});
		/**
		 * 获取用户标签
		 */
		//tags = Constant.userData.tags;
		tags = Constant.userData.tags;
		if(true)
		{
			tags = tags + " " + "标签1";
			tag = tags.split(" ");
			label_datas = new ArrayList<D_labelBean>();
			System.out.println("tag-----" + tag[0] + tags);
			for(int i = 0;i < tag.length;i ++)
			{
				bean = new D_labelBean();
				bean.tags = tag[i];
				label_datas.add(bean);
			}
		}
		else
		{
			bean = new D_labelBean();
			label_datas = new ArrayList<D_labelBean>();
			bean.tags = "暂时无标签";
			label_datas.add(bean);
		}
		
		
		label_adapter = new D_LabelAdapter(mContext, label_datas, R.layout.d_labelitem);
		gv_label.setAdapter(label_adapter);
		
		/**
		 * 获取用户关注领域
		 */
		tags = Constant.userData.focusarea;
		if (true) {
			tags = tags + " " + "关注1";
			tag = tags.split(" ");
			label_datas = new ArrayList<D_labelBean>();
			for(int i = 0;i < tag.length;i ++)
			{
				bean = new D_labelBean();
				bean.tags = tag[i];
				label_datas.add(bean);
			}
		}
		else
		{
			bean = new D_labelBean();
			label_datas = new ArrayList<D_labelBean>();
			bean.tags = "暂时无关注领域";
			label_datas.add(bean);
			
		}
		
		label_adapter = new D_LabelAdapter(mContext, label_datas, R.layout.d_majoritem);
		gv_major.setAdapter(label_adapter);
	}
	/**
	 * 获取最近来访的人
	 */
	D_UserVisitorListBean getUserVisitorList(String userid,final CommonVolleyDataCallback<D_UserVisitorListBean> callback)
	{
		RequestData = new JSONObject();
		URL = Constant.IP + Constant.getUserVisitorList;
		try {
			RequestData.put("userid", userid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<D_UserVisitorListBean> request = new GsonRequest<D_UserVisitorListBean>(URL, RequestData.toString(), 
   				new Response.Listener<D_UserVisitorListBean>() {
   					@Override
   					public void onResponse(D_UserVisitorListBean response) {
   						ResponseData = response;
   						callback.onSuccess(response);
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, D_UserVisitorListBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
		return ResponseData;	
	}
	private void click()
	{
		iv_more.setOnClickListener(this);
	}
/**
 * 单击事件
 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_more:
			intent = new Intent(mContext,D_MoreRecentView.class);
			mContext.startActivity(intent);
			break;
		default:
			break;
		}
	}
	
}
