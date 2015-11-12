package com.dlam.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.A_CommentListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.D_PraiseBean;
import com.dlam.bean.FeedbackBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.utils.CommonVolleyDataCallback;
import com.dlam.utils.MyUtils;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class A_CommentNews extends Activity implements OnClickListener{
	//控件声明
	private TextView titleName;
	private ImageButton titleRightSend;
	private EditText seedbackEdit;
	
	
	//变量声明
	private Context mContext;
	private String content;
	private String currentpage = "1";
	private String infoid;
	
	
	private JSONObject RequestData;
	private String URL;
	private String NET_TAG = "A_CommentNews";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comment_news);
		mContext = A_CommentNews.this;
		Intent intent = getIntent();
		infoid = intent.getStringExtra("myid");
		
		findView();
	}
	
	private void findView()
	{
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("评论");
		
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
				ToastUtil.showShortToast(mContext, "请输入反馈内容！");
			}
			else 
			{
				SendCommentData();
				finish();
			}
			
			break;
		case R.id.et_set_2nd_inputbox:
			MyUtils.toggleSoftKeyBoard(mContext, seedbackEdit);
			break;
		}
	}
	
	private void SendCommentData()
	{
		RequestData = new JSONObject();
		URL = Constant.IP + Constant.feedback;
		try {
			RequestData.put("currentpage", currentpage);
			RequestData.put("infoid", infoid);
			RequestData.put("content", content);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		GsonRequest<A_CommentListBean> request = new GsonRequest<A_CommentListBean>(URL, RequestData.toString(), 
   				new Response.Listener<A_CommentListBean>() {
   					@Override
   					public void onResponse(A_CommentListBean response) {

   						if(!response.getRetcode().toString().equals(""))
   						{
   							ToastUtil.showLongToast(mContext, response.getRetmsg());
   						}
   						else
   						{
   							ToastUtil.showLongToast(mContext, "没有数据！");
   						}
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_CommentListBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);	
	}	
}
