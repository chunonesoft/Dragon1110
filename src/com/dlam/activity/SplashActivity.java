package com.dlam.activity;

import com.dlam.dragon.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class SplashActivity extends Activity{
	private final int TIME_UP = 1;
	private Handler handler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			if(msg.what == TIME_UP)
			{
				Intent intent = new Intent();
				intent.setClass(SplashActivity.this, LoginActivity.class);
				startActivity(intent);
				SplashActivity.this.finish();
			}
		}
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		
		new Thread()
		{
			@Override
			public void run() {
				try {
					
					Thread.sleep(3000);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("Ïß³Ì´íÎó");
				}
				Message msg = new Message();
				msg.what = TIME_UP;
				handler.sendMessage(msg);
			}
		}.start();
		
		MyApplication.getInstance().addActivity(this);
	}
}
