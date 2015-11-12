package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.A_PINBack;
import com.dlam.bean.Constant;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class WjmmActivity extends Activity implements OnClickListener
{
	//控件声明
	private EditText et_name;
	private Button btn_sendcheck;
	private EditText et_checknum;
	private EditText et_pwd;
	private EditText et_apwd;
	private Button btn_check;
	
	//变量声明
	private Context mContext;
	private String phonenum;
	private String pwd;
	private String apwd;
	private String pin;
	private String URL;
	private String NET_TAG = "WjmmActivity";
	private JSONObject RequestData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.findpwd);
		
		mContext = WjmmActivity.this;
		findView();
		setListener();
	}
	

	private void findView()
	{
		et_name = (EditText) this.findViewById(R.id.et_name);
		et_checknum = (EditText) this.findViewById(R.id.et_checknum);
		et_pwd = (EditText) this.findViewById(R.id.et_pwd);
		et_apwd = (EditText) this.findViewById(R.id.et_apwd);
		btn_sendcheck = (Button) this.findViewById(R.id.btn_sendcheck);
		btn_check = (Button) this.findViewById(R.id.btn_check);
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.btn_sendcheck:
			getCheckData();
			break;
		case R.id.btn_check:
			getNewPwd();
			break;
		}
	}
	private void findData()
	{
		phonenum = et_name.getText().toString().trim();
		pin = et_checknum.getText().toString().trim();
		apwd = et_pwd.getText().toString().trim();
		pwd = et_apwd.getText().toString().trim();
	}
	private void setListener()
	{
		btn_check.setOnClickListener(this);
		btn_sendcheck.setOnClickListener(this);
	}

	/**
	 * 找回密码的获取验证码方法
	 */
	private void getCheckData()
	{
		findData();
		URL =  Constant.IP + Constant.RequestPWD;
		RequestData = new JSONObject();
		try {
			RequestData.put("phonenum", phonenum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<A_PINBack> request = new GsonRequest<A_PINBack>(URL, RequestData.toString(), 
				new Response.Listener<A_PINBack>()
				{

					@Override
					public void onResponse(A_PINBack arg0) {
						if(arg0.retcode.equals("1"))
						{
							et_checknum.setText("ABCD");
						}
						
						ToastUtil.showShortToast(mContext, arg0.retmsg);
					}
					
				}, 
				new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_PINBack.class);
		MyApplication.getInstance().addToRequestQueue(request);
	}
	/**
	 * 设置新密码,更新服务端密码，但是未更新openfire
	 * @throws JSONException 
	 */
	private void getNewPwd()
	{
		findData();
		URL = Constant.IP + Constant.SetPassword;
		try {
			RequestData.put("phonenum", phonenum);
			RequestData.put("pin", pin);
			RequestData.put("newpassword", pwd);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<A_PINBack> request = new GsonRequest<A_PINBack>(URL, RequestData.toString(), 
				new Response.Listener<A_PINBack>()
				{
					@Override
					public void onResponse(A_PINBack arg0) {
						if (arg0.equals("1")) {
							WjmmActivity.this.finish();
						}
						ToastUtil.showShortToast(mContext, arg0.retmsg);
					}
					
				}, 
				new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_PINBack.class);
		MyApplication.getInstance().addToRequestQueue(request);
	}
}
