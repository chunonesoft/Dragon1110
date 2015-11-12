package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.B_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.MyUtils;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
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

public class D_Set_Seedback extends Activity implements OnClickListener
{
	//控件声明
	private TextView titleName;
	private ImageButton titleRightSend;
	private EditText seedbackEdit;
	
	//变量声明
	private String content;
	private JSONObject RequestData;
	private String URL;
	private String NET_TAG = "D_Set_Seedback";
	private FeedbackBean ResponseData;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.seedback);
		
		findView();
	}
	
	private void findView()
	{
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("意见反馈");
		
		seedbackEdit = (EditText) findViewById(R.id.et_set_2nd_inputbox);
		
		
		titleRightSend = (ImageButton) findViewById(R.id.title_right_btn);
		titleRightSend.setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_send));
		titleRightSend.setOnClickListener(this);
		titleRightSend.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//按下
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_send_clicked));
				}else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//弹起
					((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_send));
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
			content = seedbackEdit.getText().toString().trim();
			if(content.equals(""))
			{
				ToastUtil.showLongToast(D_Set_Seedback.this, "请输入反馈内容！");
			}
			else 
			{
				sendBackData();
			}
			
			break;
		case R.id.et_set_2nd_inputbox:
			MyUtils.toggleSoftKeyBoard(D_Set_Seedback.this, seedbackEdit);
			break;
		}
	}
	
	private void sendBackData()
	{
		
		RequestData = new JSONObject();
		URL = Constant.IP + Constant.feedback;
		try {
			RequestData.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		GsonRequest<FeedbackBean> request = new GsonRequest<FeedbackBean>(URL, RequestData.toString(), 
   				new Response.Listener<FeedbackBean>() {
   					@Override
   					public void onResponse(FeedbackBean response) {
   						ResponseData = response;
   						if(response.retcode == "1")
   						{
   							ToastUtil.showLongToast(D_Set_Seedback.this, "感谢您的反馈，我们将不断改进！");
   						}
   						else
   						{
   							ToastUtil.showLongToast(D_Set_Seedback.this, response.retmsg);
   						}
   					}
   				}, new AbstractVolleyErrorListener(D_Set_Seedback.this) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, FeedbackBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);	
	}
}
