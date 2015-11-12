package com.dlam.ui;

import com.dlam.dragon.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class TitleLayout extends RelativeLayout
{
	private ImageButton titleBackBtn;
	public TitleLayout(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.mytitle, this);
		titleBackBtn = (ImageButton) findViewById(R.id.title_back);
		titleBackBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				//按下返回按钮后调用finish()方法杀死当前的activity-->成功完成返回效果
				((Activity)getContext()).finish();
				//Toast.makeText(getContext(), "click the back button", Toast.LENGTH_SHORT).show();
			}
		});
		//重写按钮被按下和弹起的事件，修改其被按下和弹起时的样式
		titleBackBtn.setOnTouchListener(new OnTouchListener()
		{
			
			@SuppressLint("NewApi")
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//按下
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.backonclick));
				}else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//弹起
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.back));
				}
				return false;
			}
		});
	}

}
