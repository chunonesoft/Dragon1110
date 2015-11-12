package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.A_DetailBean;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.CommonVolleyDataCallback;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class A_ServiceDetailActivity extends Activity implements OnCheckedChangeListener,OnClickListener{
	//声明控件
	private Button btn_kind;
	private CheckBox cb_cname;
	private TextView tv_companyname;
	private TextView tv_cbusiness;
	private TextView tv_cadress;
	private TextView tv_cphone;
	
	private RelativeLayout rl_back;
	private TextView tv_title1;
	private RelativeLayout rl_share;
	private ImageButton ib_share;
	//声明变量
	private Context mContext;
	private String NET_TAG = "A_ServiceDetailActivity";
	private String myid;
	private A_DetailBean ResponseData;
	private JSONObject RequestData;
	
	private String URL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a_servicedetail);
		
		findView();
		init();
		click();
		getData();
		}
	private void findView()
	{
	  btn_kind = (Button) findViewById(R.id.btn_kind);
	  cb_cname = (CheckBox) findViewById(R.id.cb_cname);
	  tv_cbusiness = (TextView) findViewById(R.id.tv_cbusiness);
	  tv_companyname = (TextView) findViewById(R.id.tv_companyname);
	  tv_cadress = (TextView) findViewById(R.id.tv_cadress);
	  tv_cphone = (TextView) findViewById(R.id.tv_cphone);
	  
	  rl_back = (RelativeLayout) findViewById(R.id.rl_back);
	  tv_title1 = (TextView) findViewById(R.id.tv_title1);
	  rl_share = (RelativeLayout) findViewById(R.id.rl_share);
	  ib_share = (ImageButton) findViewById(R.id.ib_share);
	}
  
	private void init()
	{
		mContext = A_ServiceDetailActivity.this;
		tv_title1.setText("服务");
		Intent intent = getIntent();
		myid = intent.getStringExtra("myid");
	}
  
	private void click()
	{
		rl_back.setOnClickListener(this);
		cb_cname.setOnCheckedChangeListener(this);
		rl_share.setOnClickListener(this);
		ib_share.setOnClickListener(this);
	}
	
	private void getData()
	{
	  //获取服务详情数据
		getDetailInfoList(myid, new VolleyDataCallback1() {
			@Override
			public void onSuccess1(A_DetailBean datas1) {
				btn_kind.setText(datas1.getType1name());
				tv_companyname.setText(datas1.getCompany());
				tv_cbusiness.setText(datas1.getBusiness());
				tv_cadress.setText(datas1.getAddress());
				tv_cphone.setText(datas1.getPhonenum());
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;
		case R.id.ib_share:
			Intent intent=new Intent(Intent.ACTION_SEND);	
			intent.setType("image/*"); 
			intent.putExtra(Intent.EXTRA_SUBJECT, R.drawable.logo); 
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_TEXT, "蓝胖子，创业者身边的多啦艾萌"); 
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
			startActivity(Intent.createChooser(intent, getTitle())); 
			break;
		default:
			break;
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if(isChecked)
		{
			GetFavorRequest(0,myid, new CommonVolleyDataCallback<FeedbackBean>() {		
				@Override
				public void onSuccess(FeedbackBean datas) {
					ToastUtil.showShortToast(mContext, datas.retmsg);
				}
			});
		}
		else
		{
			GetFavorRequest(1,myid, new CommonVolleyDataCallback<FeedbackBean>() {		
				@Override
				public void onSuccess(FeedbackBean datas) {						
					ToastUtil.showShortToast(mContext, datas.retmsg);
				}
			});
		}
	}
	
    public A_DetailBean getDetailInfoList
   	(String myid,final VolleyDataCallback1 callback)
   	{

   		URL = Constant.IP + Constant.ViewInfo;
   		RequestData = new JSONObject();
   		try {
   			RequestData.put("id", myid);
   		} catch (JSONException e) {
   			
   			e.printStackTrace();
   		}
   		GsonRequest<A_DetailBean> request = new GsonRequest<A_DetailBean>(URL, RequestData.toString(), 
   				new Response.Listener<A_DetailBean>() {
   					@Override
   					public void onResponse(A_DetailBean response) {
   						ResponseData = response;
   						
   						callback.onSuccess1(response);
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_DetailBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
   		return ResponseData;	
   	}
    
	/**
	 * favor
	 */
	private void GetFavorRequest(int type,String id,final CommonVolleyDataCallback<FeedbackBean> callback)
	{
		switch (type) {
		case 0:
			URL = Constant.IP + Constant.getFavorInfo;
			break;
		case 1:
			URL = Constant.IP + Constant.getCancelFavorInfo;
			break;
		default:
			break;
		}
		
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
    public interface VolleyDataCallback1
	{
		void onSuccess1(A_DetailBean datas1);
	}
}
