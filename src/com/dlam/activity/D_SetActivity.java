package com.dlam.activity;

import com.dlam.dragon.R;
import com.dlam.utils.CustomDialog;
import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chatuidemo.DemoHXSDKHelper;
import com.zf.view.UISwitchButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class D_SetActivity extends Activity implements OnClickListener{
	//private TextView tv_title1;
	//第一次尝试在页面中获得自定义控件上的组件
	//结果：成功获取并对其内容进行控制！
	private Context mContext;
	private TextView titleName;
	private ImageButton setGotoBtn;
	private UISwitchButton msgSwitch;
	private Button exitBtn;
	private CustomDialog.Builder iBuilder;
	//modifypwdLayout是为了实现点修改密码那一条跳转到修改密码
	//seedbackLayout是为了实现点反馈那一条跳转到反馈
	private LinearLayout modifypwdLayout,seedbackLayout;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.d_set);
		
		mContext = D_SetActivity.this;
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("设置");
		//经过尝试，可以把title控件中右边的imagebutton的透明度设置为0，使其完全透明达到不可见的目的
		//迭代2.0：将title控件右边的imagebutton的背景设置为null，这样就不需要设置其透明度了
		
		setGotoBtn = (ImageButton) findViewById(R.id.set_goto);
		setGotoBtn.setOnClickListener(this);
		
		seedbackLayout = (LinearLayout) findViewById(R.id.ll2);
		seedbackLayout.setOnClickListener(this);
		
		exitBtn = (Button) findViewById(R.id.btn_exit);
		if(!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())){
			exitBtn.setText(getString(R.string.button_logout) + "(" + EMChatManager.getInstance().getCurrentUser() + ")");
		}
		exitBtn.setOnClickListener(this);
		
//		modifypwdLayout = (LinearLayout) findViewById(R.id.ll1);
		setGotoBtn.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//按下
					setGotoBtn.setImageDrawable(getResources().getDrawable(R.drawable.set_goto_clicked));
				}else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//弹起
					setGotoBtn.setImageDrawable(getResources().getDrawable(R.drawable.set_goto));
				}
				return false;
			}
		});
		//实例化滑动按钮并监听滑动事件
		msgSwitch = (UISwitchButton) findViewById(R.id.msg_switch);
		msgSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
			{
				if(isChecked)
				{
					Toast.makeText(D_SetActivity.this, "打开消息提醒功能！", Toast.LENGTH_SHORT).show();
				}
				else
				{
					Toast.makeText(D_SetActivity.this, "关闭消息提醒功能！", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll2:
			//用土司测试是否能点击layout，结果：成功
			//Toast.makeText(this, "click the seedback node!", Toast.LENGTH_SHORT).show();
			getSeedback();
			break;
		case R.id.set_goto:
			gotoModifyPwd();
			break;
		case R.id.btn_exit:
			exitLogin();
			break;
		}
	}
	void logout() {
		final ProgressDialog pd = new ProgressDialog(mContext);
		String st = getResources().getString(R.string.Are_logged_out);
		pd.setMessage(st);
		pd.setCanceledOnTouchOutside(false);
		pd.show();
		DemoHXSDKHelper.getInstance().logout(true,new EMCallBack() {
			
			@Override
			public void onSuccess() {
				D_SetActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						pd.dismiss();
						// 重新显示登陆页面
						D_SetActivity.this.finish();
						startActivity(new Intent(mContext, LoginActivity.class));
						
					}
				});
			}
			
			@Override
			public void onProgress(int progress, String status) {
				
			}
			
			@Override
			public void onError(int code, String message) {
				D_SetActivity.this.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(mContext, "unbind devicetokens failed", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	/**
	 * 退出登录
	 */
	private void exitLogin()
	{
		//在退出前添加一个询问是否退出的对话框，增加用户交互体验度
		iBuilder = new CustomDialog.Builder(this);
		iBuilder.setTitle(R.string.prompt);
		iBuilder.setMessage(R.string.exit_app);
		
		iBuilder.setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener()
		{
			
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				logout();
				dialog.dismiss();
			}
		});
		iBuilder.setNegativeButton(R.string.cancel, null);
		iBuilder.create().show();
	}
	/**
	 * 跳转到反馈界面
	 */
	private void getSeedback()
	{
		Intent intent = new Intent(D_SetActivity.this,D_Set_Seedback.class);
		startActivity(intent);
	}
	/**
	 * 跳转到修改密码界面
	 */
	private void gotoModifyPwd()
	{
		Intent intent = new Intent(D_SetActivity.this,D_Set_ModifyPwd.class);
		startActivity(intent);
	}

}
