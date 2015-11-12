package com.dlam.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.Constant;
import com.dlam.bean.D_ViewUserBean;
import com.dlam.bean.LoginBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.ui.LoadingDialog;
import com.dlam.utils.CommonVolleyDataCallback;
import com.dlam.utils.PreferencesUtils;
import com.easemob.EMCallBack;
import com.easemob.applib.controller.HXSDKHelper;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.easemob.chatuidemo.db.UserDao;
import com.easemob.chatuidemo.domain.User;
import com.easemob.chatuidemo.utils.CommonUtils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnCancelListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private Button btn_login;
	private Button btn_register;
	private Button btn_forget;
	private Intent intents;
	
	
	private EditText et_pwd;
	private EditText et_num;
	private Context mContext;
	private JSONObject jsondata;
	private String NET_TAG = "getLoginInfo";
	private LoadingDialog loadDialog;
	String userName = "";
	String userPwd = "";
	
	private String userid;
	private String token;
	
	private String currentUsername;
	private String currentPassword;
	
	private boolean autoLogin = false;
	private boolean progressShow;
	
	private boolean flag = false;
	
	private LoginBean bean = new LoginBean();

	
	private TextView tv_title;
	
	D_ViewUserBean ResponseData = new D_ViewUserBean();
	

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 如果用户名密码都有，直接进入主页面
		/*if (DemoHXSDKHelper.getInstance().isLogined()) {
			Constant.userid = PreferencesUtils.getSharePreStr(getApplicationContext(), "userid");
			Constant.token = PreferencesUtils.getSharePreStr(getApplicationContext(), "token");
			Constant.phonenum = PreferencesUtils.getSharePreStr(getApplicationContext(), "phonenum");//用户名
			autoLogin = true;
			startActivity(new Intent(LoginActivity.this, MainActivity.class));
			return;
		}*/
		//full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		
		mContext = LoginActivity.this;
		loadDialog = new LoadingDialog(mContext);
		loadDialog.setTitle("正在登录...");
		findView();
		init();
		
		// 如果用户名改变，清空密码
		et_num.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				et_pwd.setText(null);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
		
		if (MyApplication.getInstance().getUserName() != null) {
			et_num.setText(MyApplication.getInstance().getUserName());
		}
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loadDialog.show();
				userName = et_num.getText().toString();//用户名
				userPwd = et_pwd.getText().toString();//密码
				getJsonRequest(userName, userPwd, new VolleyDataCallback() {	
					@Override
					public void onSuccess(LoginBean responce) {
						if(responce.getRetcode().equals("1"))
						{
							flag = true;
							userid = responce.getUserid().toString();
							token = responce.getToken().toString();
							
							/*SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);   
							Editor editor = sharedPreferences.edit();//获取编辑器   
							editor.putString("userid", userid);
							Log.e(NET_TAG, userid);
							editor.putString("password", token);   
							editor.putString("phonenum", et_num.getText().toString());   
							editor.commit();//提交修改 
							*/	
							PreferencesUtils.putSharePre(mContext, "userid", responce.getUserid().toString());
							PreferencesUtils.putSharePre(mContext, "token", responce.getToken().toString());
							PreferencesUtils.putSharePre(mContext, "phonenum", et_num.getText().toString());
							Constant.userid = responce.getUserid().toString();
							Constant.token = responce.getToken().toString();
							Constant.phonenum = et_num.getText().toString();
							initData();
							login();
							loadDialog.cancel();
						}
						else
						{
							flag = false;
							login();
						}
					}
				});
				
			}
		});
		MyApplication.getInstance().addActivity(this);
	};
	
	//对象实例化
	private void findView()
	{
		tv_title = (TextView) findViewById(R.id.tv_title);
		et_num = (EditText) findViewById(R.id.et_phonenum);
		et_pwd = (EditText) findViewById(R.id.et_pwd);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_register = (Button) findViewById(R.id.btn_register);
		btn_forget = (Button) findViewById(R.id.btn_wjmm);
	}
	//界面初始化
	private void init()
	{
		Animation anim=AnimationUtils.loadAnimation(mContext, R.anim.login_anim);
		anim.setFillAfter(true);
		tv_title.startAnimation(anim);
		btn_register.setOnClickListener(this);
		btn_forget.setOnClickListener(this);
	}
	
	
	//公司服务器登陆判断
	public LoginBean getJsonRequest(String phonenum,String password,final VolleyDataCallback callback)
	{
		String url = Constant.IP + Constant.VerifyUser;
		jsondata = new JSONObject();
        try {
        	
        	jsondata.put("phonenum", phonenum);
        	jsondata.put("password", password);
        	jsondata.put("rememberme", "true");
        	
		} catch (JSONException e) {
			e.printStackTrace();
		}
        GsonRequest<LoginBean> request = new GsonRequest<LoginBean>(
				url, jsondata.toString(),  
				new Response.Listener<LoginBean>() {
					@Override
					public void onResponse(LoginBean responce) {
						bean = responce;
						callback.onSuccess(bean);
					}
				},
				new AbstractVolleyErrorListener(LoginActivity.this) {
					
					@Override
					public void onError() {
						Log.e(NET_TAG, "----onError");
					}
				},LoginBean.class
				);
        MyApplication.getInstance().addToRequestQueue(request);
		return bean;
	}
	
	 public interface VolleyDataCallback
	{
		void onSuccess(LoginBean datas);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_register:
			intents = new Intent(LoginActivity.this,RegisterActivity.class);
			startActivity(intents);
			break;
		case R.id.btn_wjmm:
			intents = new Intent(LoginActivity.this,WjmmActivity.class);
			startActivity(intents);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 初始化数据
	 */
	private void initData()
	{
		getViewUserData(userName, new CommonVolleyDataCallback<D_ViewUserBean>() {
			@Override
			public void onSuccess(D_ViewUserBean datas) {
				Constant.userData = datas;
				System.out.println("F4"+Constant.userData.age_range);	
			}
		});
	}
	
	/**
	 * 数据请求
	 */
	private D_ViewUserBean getViewUserData(String phonenum,final CommonVolleyDataCallback<D_ViewUserBean> callback)
	{
		String URL = Constant.IP + Constant.ViewUser;
		JSONObject RequestData = new JSONObject();
		try {
			RequestData.put("token", token);
			RequestData.put("userid", userid);
			Log.e("RequestData", "----"+RequestData.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<D_ViewUserBean> request = new GsonRequest<>(URL, RequestData.toString(), 
				new Response.Listener<D_ViewUserBean>() {
					@Override
					public void onResponse(D_ViewUserBean arg0) {
						ResponseData = arg0;
						Constant.userData = arg0;
						callback.onSuccess(arg0);
						System.out.println(arg0.toString());						
					}
		}	
		, new AbstractVolleyErrorListener(mContext) {
				@Override
				public void onError() {
					Log.e("getViewUserData", "----onError");
				}
			}, D_ViewUserBean.class);
		MyApplication.getInstance().addToRequestQueue(request);		
		return ResponseData;
	}
	
	
	/**
	 * 登录
	 * 
	 * @param view
	 */
	public void login() {
		currentUsername = et_num.getText().toString().trim();
		currentPassword = et_pwd.getText().toString().trim();
		if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
			return;
		}

		if (TextUtils.isEmpty(currentUsername)) {
			Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}

		progressShow = true;
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage(getString(R.string.Is_landing));
		pd.show();

		final long start = System.currentTimeMillis();
		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

			@Override
			public void onSuccess() {
				if (!progressShow) {
					return;
				}
				// 登陆成功，保存用户名密码
				MyApplication.getInstance().setUserName(currentUsername);
				MyApplication.getInstance().setPassword(currentPassword);

				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
				    EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					initializeContacts();
				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							DemoHXSDKHelper.getInstance().logout(true,null);
							Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
						}
					});
					return;
				}
				// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
				boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
						MyApplication.currentUserNick.trim());
				if (!updatenick) {
					Log.e("LoginActivity", "update current user nick fail");
				}
				if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
					pd.dismiss();
				}
				// 进入主页面
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				
				finish();
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {
				if (!progressShow) {
					return;
				}
				runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		/*if (!CommonUtils.isNetWorkConnected(this)) {
			Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
			return;
		}
		currentUsername = et_num.getText().toString().trim();
		currentPassword = et_pwd.getText().toString().trim();

		if (TextUtils.isEmpty(currentUsername)) {
			Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(currentPassword)) {
			Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
			return;
		}

		progressShow = true;
		final ProgressDialog pd = new ProgressDialog(LoginActivity.this);
		pd.setCanceledOnTouchOutside(false);
		pd.setOnCancelListener(new OnCancelListener() {

			@Override
			public void onCancel(DialogInterface dialog) {
				progressShow = false;
			}
		});
		pd.setMessage(getString(R.string.Is_landing));
		pd.show();

		final long start = System.currentTimeMillis();
		// 调用sdk登陆方法登陆聊天服务器
		EMChatManager.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

			@Override
			public void onSuccess() {
				if (!progressShow) {
					return;
				}
				// 登陆成功，保存用户名密码
				MyApplication.getInstance().setUserName(currentUsername);
				MyApplication.getInstance().setPassword(currentPassword);

				try {
					// ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
					// ** manually load all local groups and
				    EMGroupManager.getInstance().loadAllGroups();
					EMChatManager.getInstance().loadAllConversations();
					// 处理好友和群组
					initializeContacts();
				} catch (Exception e) {
					e.printStackTrace();
					// 取好友或者群聊失败，不让进入主页面
					runOnUiThread(new Runnable() {
						public void run() {
							pd.dismiss();
							DemoHXSDKHelper.getInstance().logout(true,null);
							Toast.makeText(getApplicationContext(), R.string.login_failure_failed, 1).show();
						}
					});
					return;
				}
				// 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
				boolean updatenick = EMChatManager.getInstance().updateCurrentUserNick(
						MyApplication.currentUserNick.trim());
				if (!updatenick) {
					Log.e("LoginActivity", "update current user nick fail");
				}
				if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
					pd.dismiss();
				}
				// 进入主页面
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				
				finish();
			}

			@Override
			public void onProgress(int progress, String status) {
			}

			@Override
			public void onError(final int code, final String message) {
				if (!progressShow) {
					return;
				}
				runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
								Toast.LENGTH_SHORT).show();
					}
				});
			}
		});*/
	}
	
	private void initializeContacts() {
		Map<String, User> userlist = new HashMap<String, User>();
		// 添加user"申请与通知"
		User newFriends = new User();
		newFriends.setUsername(Constant.NEW_FRIENDS_USERNAME);
		String strChat = getResources().getString(
				R.string.Application_and_notify);
		newFriends.setNick(strChat);

		userlist.put(Constant.NEW_FRIENDS_USERNAME, newFriends);
		// 添加"群聊"
		User groupUser = new User();
		String strGroup = getResources().getString(R.string.group_chat);
		groupUser.setUsername(Constant.GROUP_USERNAME);
		groupUser.setNick(strGroup);
		groupUser.setHeader("");
		userlist.put(Constant.GROUP_USERNAME, groupUser);
		
		// 添加"Robot"
		User robotUser = new User();
		String strRobot = getResources().getString(R.string.robot_chat);
		robotUser.setUsername(Constant.CHAT_ROBOT);
		robotUser.setNick(strRobot);
		robotUser.setHeader("");
		userlist.put(Constant.CHAT_ROBOT, robotUser);
		
		// 存入内存
		((DemoHXSDKHelper)HXSDKHelper.getInstance()).setContactList(userlist);
		// 存入db
		UserDao dao = new UserDao(LoginActivity.this);
		List<User> users = new ArrayList<User>(userlist.values());
		dao.saveContactList(users);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		/*SharedPreferences preferences = getSharedPreferences("userInfo",   
				Activity.MODE_PRIVATE); 
		Constant.userid = preferences.getString("userid", "");
		Log.e(NET_TAG+"1", Constant.userid);

		Constant.token = preferences.getString("token", "");
		Constant.phonenum = preferences.getString("phonenum", "");*/
		if (autoLogin) {
			return;
		}
	}
}
