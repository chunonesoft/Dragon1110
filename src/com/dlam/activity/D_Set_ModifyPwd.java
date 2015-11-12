package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class D_Set_ModifyPwd extends Activity implements OnClickListener
{
	//控件声明
	private ImageButton titleRightSure;
	private TextView titleName;
	private EditText oldpwd,newpwd,repwd;
	
	//变量声明
	private Context mContext;
	private JSONObject RequestData;
	private String URL;
	private String NET_TAG = "D_Set_ModifyPwd";
	private FeedbackBean ResponseData;
	private String phonenum;
	private String oldpassword;
	private String newpassword;
	private String repassword;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.modifypwd);
		mContext = D_Set_ModifyPwd.this;
		findView();
	}
	
	private void findView()
	{
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("修改密码");
		
		oldpwd = (EditText) findViewById(R.id.tv_set_2nd_input_oldpwd);
		newpwd = (EditText) findViewById(R.id.tv_set_2nd_input_newpwd);
		repwd = (EditText) findViewById(R.id.tv_set_2nd_input_twice_pl);
		
		titleRightSure = (ImageButton) findViewById(R.id.title_right_btn);
		titleRightSure.setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure));
		titleRightSure.setOnClickListener(this);
		titleRightSure.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//按下
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure_clicked));
				}else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//弹起
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure));
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId())
		{
			case R.id.title_right_btn:
				modifyPwd();
				break;
		}
	}
	
	private void modifyPwd()
	{
		newpassword = newpwd.getText().toString().trim();
		oldpassword = oldpwd.getText().toString().trim();
		repassword = repwd.getText().toString().trim();
		phonenum = Constant.phonenum;
		
		if(oldpassword.length() < 6)
		{
			ToastUtil.showShortToast(mContext, "请输入正确的旧密码!");
			oldpwd.setText("");
		}
		else if(newpassword.length() < 6)
		{
			ToastUtil.showShortToast(mContext, "请输入6~12位的新密码！");
			
		}
		else if(newpassword.equals(repassword))
		{
			ToastUtil.showShortToast(mContext, "两次输入密码不一致，请重新输入！");
			repwd.setText("");
		}
		else
		{
			RequestData = new JSONObject();
			URL = Constant.IP + Constant.ChangePasswd;
			try {
				RequestData.put("phonenum", phonenum);
				RequestData.put("oldpassword", oldpassword);
				RequestData.put("newpassword", newpassword);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			GsonRequest<FeedbackBean> request = new GsonRequest<FeedbackBean>(URL, RequestData.toString(), 
	   				new Response.Listener<FeedbackBean>() {
	   					@Override
	   					public void onResponse(FeedbackBean response) {
	   						ResponseData = response;
	   						if(response.retcode.equals("1"))
	   						{
	   							ToastUtil.showLongToast(mContext, response.retmsg);
	   							Intent intent = new Intent(mContext,LoginActivity.class);
	   							startActivity(intent);
	   						}
	   						else
	   						{
	   							ToastUtil.showLongToast(mContext, response.retmsg);
	   							oldpwd.setText("");
	   						}
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
}