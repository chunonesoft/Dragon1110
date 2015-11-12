package com.dlam.activity;

import com.dlam.dragon.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class T1ActivityItem extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a_activitydetail);
	}
}
