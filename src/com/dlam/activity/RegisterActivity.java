package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.bean.RequestPINBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.ui.LoadingDialog;
import com.dlam.utils.ToastUtil;
import com.easemob.EMError;
import com.easemob.chat.EMChatManager;
import com.easemob.exceptions.EaseMobException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterActivity extends Activity implements OnClickListener{
	//控件声明
	private RelativeLayout rl_back;
	private TextView tv_title;
	private Button btn_sendcheck;
	private Button register_complete;
	private EditText et_name;
	private EditText et_pwd;
	private EditText et_apwd;
	private EditText et_checknum;
	private LoadingDialog loadDialog;
	//变量声明
	private String userName;
	private String userPwd;
	private Context mContext;
	private String URL;
	private JSONObject RequestData;
	private String NET_TAG = "RegisterActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		mContext = RegisterActivity.this;
		loadDialog = new LoadingDialog(this);
		loadDialog.setTitle("正在注册...");
		findView();
		init();
		click();
	}
	
	private void findView()
	{ 
		et_name = (EditText) findViewById(R.id.et_name);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		et_apwd = (EditText) findViewById(R.id.et_apwd);
		et_checknum = (EditText) findViewById(R.id.et_checknum);
		rl_back = (RelativeLayout) findViewById(R.id.rl_back);
		tv_title = (TextView) findViewById(R.id.tv_title1);
		btn_sendcheck = (Button) findViewById(R.id.btn_sendcheck);
		register_complete = (Button) findViewById(R.id.register_complete);
	}

	private void init()
	{
		mContext = this;
		tv_title.setText("注册");
	}

	private void click()
	{
		rl_back.setOnClickListener(this);
		register_complete.setOnClickListener(this);
		btn_sendcheck.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		case R.id.btn_sendcheck:
			RequestPIN();
			break;
		case R.id.register_complete:
			if(et_checknum.getText().toString().equals(""))
			{
				ToastUtil.showShortToast(mContext, "请输入正确的验证码");
			}
			else
			{
				registerCompany(et_name.getText().toString(), et_pwd.getText().toString());
			}
			break;
		default:
			break;
		}
	}
	
	/**
	 * 请求PIN
	 */
	public void RequestPIN()
	{
		userName = et_name.getText().toString().trim();
		URL = Constant.IP + Constant.RequestPIN;
		RequestData = new JSONObject();
		if (!TextUtils.isEmpty(userName)) {
			try {
				RequestData.put("phonenum", userName);
			} catch (JSONException e) {	
				e.printStackTrace();
			}
			GsonRequest<RequestPINBean> request = new GsonRequest<RequestPINBean>(URL, RequestData.toString(), 
					new Response.Listener<RequestPINBean>() {
						@Override
						public void onResponse(RequestPINBean arg0) {
							if(arg0.retcode.equals("1"))
							{
								ToastUtil.showShortToast(mContext, arg0.retmsg);
							}
							else
							{
								ToastUtil.showShortToast(mContext, arg0.retmsg);
							}
						}
					}, new AbstractVolleyErrorListener(mContext) {
						
						@Override
						public void onError() {
							Log.e(NET_TAG, "----onError");
						}
					}, RequestPINBean.class);
			 MyApplication.getInstance().addToRequestQueue(request);
		}
		else
		{
			ToastUtil.showShortToast(mContext, "请输入手机号");
		}
		
	}
	/**
	 * 公司服务器注册
	 */
	public void registerCompany(String phonenum,String password)
	{
		loadDialog.show();
		URL = Constant.IP + Constant.CreateUser;
		RequestData = new JSONObject();
		try {
			RequestData.put("phonenum",phonenum);
			RequestData.put("password",password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		GsonRequest<FeedbackBean> request = new GsonRequest<FeedbackBean>(URL, RequestData.toString(), 
   				new Response.Listener<FeedbackBean>() {
   					@Override
   					public void onResponse(FeedbackBean response) {
   						if(response.retcode.equals("1"))
   						{
   							register();
   							loadDialog.cancel();
   						}
   						else
   						{
   							ToastUtil.showShortToast(mContext, response.retmsg);
   							loadDialog.cancel();
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
	
	
	
	/**
	 * 聊天服务器注册
	 */
	/**
	 * 注册
	 * 
	 * @param view
	 */
	public void register() {
		final String username = et_name.getText().toString().trim();
		final String pwd = et_pwd.getText().toString().trim();
		String confirm_pwd = et_apwd.getText().toString().trim();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(this, getResources().getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT).show();
			et_name.requestFocus();
			return;
		} else if (TextUtils.isEmpty(pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			et_pwd.requestFocus();
			return;
		} else if (TextUtils.isEmpty(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Confirm_password_cannot_be_empty), Toast.LENGTH_SHORT).show();
			et_apwd.requestFocus();
			return;
		} else if (!pwd.equals(confirm_pwd)) {
			Toast.makeText(this, getResources().getString(R.string.Two_input_password), Toast.LENGTH_SHORT).show();
			return;
		}

		if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(pwd)) {
			final ProgressDialog pd = new ProgressDialog(this);
			pd.setMessage(getResources().getString(R.string.Is_the_registered));
			pd.show();

			new Thread(new Runnable() {
				public void run() {
					try {
						// 调用sdk注册方法
						EMChatManager.getInstance().createAccountOnServer(username, pwd);
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								// 保存用户名
								MyApplication.getInstance().setUserName(username);
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registered_successfully), 0).show();
								finish();
							}
						});
					} catch (final EaseMobException e) {
						runOnUiThread(new Runnable() {
							public void run() {
								if (!RegisterActivity.this.isFinishing())
									pd.dismiss();
								int errorCode=e.getErrorCode();
								if(errorCode==EMError.NONETWORK_ERROR){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.USER_ALREADY_EXISTS){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.UNAUTHORIZED){
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
								}else if(errorCode == EMError.ILLEGAL_USER_NAME){
								    Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name),Toast.LENGTH_SHORT).show();
								}else{
									Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed) + e.getMessage(), Toast.LENGTH_SHORT).show();
								}
							}
						});
					}
				}
			}).start();

		}
	}

}
