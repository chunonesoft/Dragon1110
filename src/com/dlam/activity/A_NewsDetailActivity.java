package com.dlam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.dlam.adapter.A_ComentListAdapter;
import com.dlam.bean.A_CommentListBean;
import com.dlam.bean.A_DetailBean;
import com.dlam.bean.Constant;
import com.dlam.bean.D_PraiseBean;
import com.dlam.bean.FeedbackBean;
import com.dlam.bean.commentlist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.net.LruImageCache;
import com.dlam.ui.ListScrollView;
import com.dlam.utils.CommonVolleyDataCallback;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class A_NewsDetailActivity extends Activity implements OnCheckedChangeListener{
	//声明控件
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	private ImageButton ib_back;
	private ImageButton ib_share;
	
	private TextView tv_title;
	private TextView tv_time;
	private TextView tv_source;
	private ImageView iv_pic;
	private TextView tv_content;
	private TextView tv_comment;
	private TextView tv_zan;
	private ListScrollView lv_connect;
	private ScrollView sv;
	
	//声明变量
	private List<commentlist> commentDatas = new ArrayList<commentlist>();;
	private A_ComentListAdapter adapter;
	
	private String NET_TAG = "A_NewsDetailActivity";
	
	private String myid;
	private String URL;
	private JSONObject RequestData1; 
	private JSONObject RequestData2;
	private A_DetailBean ResponseData1;
	private A_CommentListBean ResponseData2;
	
	private Context mContext;
	
	private JSONObject RequestData;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a_newsdetail);
		mContext = A_NewsDetailActivity.this;
		
		Intent intent = getIntent();
		myid = intent.getStringExtra("myid");
		Toast.makeText(A_NewsDetailActivity.this, intent.getStringExtra("myid"), Toast.LENGTH_LONG).show();
		System.out.println("myid-----"+myid);
		ib_back = (ImageButton) findViewById(R.id.ib_back);
		ib_share = (ImageButton) findViewById(R.id.ib_share);
		
		lv_connect = (ListScrollView)findViewById(R.id.lv_connect);
		tv_title = (TextView) findViewById(R.id.tv_title);
		
		tv_time = (TextView) findViewById(R.id.tv_time);
		tv_source = (TextView) findViewById(R.id.tv_source);
		iv_pic = (ImageView) findViewById(R.id.iv_pic);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_comment = (TextView) findViewById(R.id.tv_comment);
		tv_zan = (TextView) findViewById(R.id.tv_zan);
		cb1 = (CheckBox) findViewById(R.id.cb1);
		cb2 = (CheckBox) findViewById(R.id.cb2);
		cb3 = (CheckBox) findViewById(R.id.cb3);
		sv = (ScrollView) findViewById(R.id.hs);
		sv.smoothScrollTo(0, 0);
		
		click();
		
		getDetailInfoList(myid, new VolleyDataCallback1() {		
			@Override
			public void onSuccess1(A_DetailBean datas) {
				//标题，时间，资讯来源，资讯内容，评论数目，赞的数目
				tv_title.setText(datas.getTitle().toString());
				System.out.println("tv_title------"+datas.getTitle().toString());
				tv_time.setText("时间：" + datas.getIssuetime().toString());
				System.out.println("tv_time"+datas.getIssuetime().toString());
				tv_source.setText("来源：" + datas.getSource().toString());
				System.out.println("tv_source"+datas.getSource().toString());
				tv_content.setText(datas.getContent().toString());
				System.out.println("tv_content"+datas.getContent().toString());
				tv_comment.setText("评论：" + datas.getCommentcount().toString());
				tv_zan.setText("赞：" + datas.getPraisecount().toString());
				LoadNetImage(iv_pic,datas.getPic1path(),R.drawable.loading,R.drawable.failing);
			}

			private void LoadNetImage(ImageView view, String uri,
					int bgDefault, int bgError) {
				RequestQueue mQueue = MyApplication.getInstance().getRequestQueue();
				ImageLoader imageLoader = new ImageLoader(mQueue, new LruImageCache()); 
				    ImageListener listener = ImageLoader.getImageListener(view,  
				    		bgDefault,bgError); 
				    imageLoader.get(Constant.ImageUri+uri, listener);
			}
		});
		
		getCommentList("1", myid, new VolleyDataCallback2() {
			
			@Override
			public void onSuccess2(A_CommentListBean datas2) {
				//用户图像，用户名，评论时间，评论内容，赞的数目
				commentDatas = datas2.getCommentlist();
				adapter = new A_ComentListAdapter(A_NewsDetailActivity.this, commentDatas, R.layout.a_newsitem);
				lv_connect.setAdapter(adapter);
			}
		});
		ib_back.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		ib_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_SEND);	
				intent.setType("image/*"); 
				intent.putExtra(Intent.EXTRA_SUBJECT, R.drawable.logo); 
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, "蓝胖子，创业者身边的多啦艾萌"); 
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  
				startActivity(Intent.createChooser(intent, getTitle())); 
			}
		});
	}

	private void click()
	{
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
	}
    public A_DetailBean getDetailInfoList
   	(String myid,final VolleyDataCallback1 callback)
   	{

   		URL = Constant.IP + Constant.ViewInfo;
   		RequestData1 = new JSONObject();
   		try {
   			RequestData1.put("id", myid);
   		} catch (JSONException e) {
   			
   			e.printStackTrace();
   		}
   		GsonRequest<A_DetailBean> request = new GsonRequest<A_DetailBean>(URL, RequestData1.toString(), 
   				new Response.Listener<A_DetailBean>() {
   					@Override
   					public void onResponse(A_DetailBean response) {
   						ResponseData1 = response;
   						System.out.println("2");
   						System.out.println("RequestData1.toString()----" + RequestData1.toString());
   						callback.onSuccess1(response);
   					}
   				}, new AbstractVolleyErrorListener(A_NewsDetailActivity.this) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_DetailBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
   		return ResponseData1;	
   	}
    
    /**
     * 
     * @param currentpage
     * @param infoid
     * @param callback
     * @return 返回评论数据
     */
    public A_CommentListBean getCommentList
    (String currentpage,String infoid,final VolleyDataCallback2 callback2)
    {
    	URL = Constant.IP + Constant.CommentList+"/currentpage/"+currentpage+"/infoid/"+infoid;
   		RequestData2 = new JSONObject();
   		try {
   			RequestData2.put("currentpage", currentpage);
   			RequestData2.put("infoid", infoid);
   			
   			System.out.println("RequestData2---" + RequestData2.toString());
   		} catch (JSONException e) {
   			
   			e.printStackTrace();
   		}
   		GsonRequest<A_CommentListBean> request = new GsonRequest<A_CommentListBean>(Method.GET,URL, RequestData2.toString(), 
   				new Response.Listener<A_CommentListBean>() {
   					@Override
   					public void onResponse(A_CommentListBean response) {
   						ResponseData2 = response;
   						System.out.println("getCommentList---" + response.getTotalcount());
   						callback2.onSuccess2(response);
   					}
   				}, new AbstractVolleyErrorListener(A_NewsDetailActivity.this) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_CommentListBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
   		return ResponseData2;	
    }

    public interface VolleyDataCallback1
	{
		void onSuccess1(A_DetailBean datas1);
	}
    public interface VolleyDataCallback2
	{
		void onSuccess2(A_CommentListBean datas2);
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		/**
		 * Comment
		 */
		case R.id.cb1:
			if(isChecked)
			{
				Intent intent = new Intent(mContext,A_CommentNews.class);
				intent.putExtra("myid", myid);
				mContext.startActivity(intent);
			}
			
			break;
			/**
			 * zan
			 */
		case R.id.cb2:
			if(isChecked)
			{
				getPraiseInfo(1, myid, Constant.token,new CommonVolleyDataCallback<D_PraiseBean>() {
					@Override
					public void onSuccess(D_PraiseBean response) {
						if(!response.retcode.toString().equals(""))
   						{
							tv_zan.setText("赞：" + response.praisecount);
   							ToastUtil.showShortToast(mContext, response.retmsg);
   						}
   						else
   						{
   							ToastUtil.showShortToast(mContext, "没有数据！");
   						}
					}
				});
			}
			else
			{
				getPraiseInfo(0, myid, Constant.token, new CommonVolleyDataCallback<D_PraiseBean>() {
					@Override
					public void onSuccess(D_PraiseBean response) {
						if(!response.retcode.toString().equals(""))
   						{
   							ToastUtil.showShortToast(mContext, response.retmsg);
   						}
   						else
   						{
   							ToastUtil.showShortToast(mContext, "没有数据！");
   						}
					}
				});
			}
			break;
			/**
			 * save
			 */
		case R.id.cb3:
			if(isChecked)
			{
				GetFavorRequest(0,myid, new CommonVolleyDataCallback<FeedbackBean>() {		
					@Override
					public void onSuccess(FeedbackBean datas) {
						
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			else
			{
				GetFavorRequest(1,myid, new CommonVolleyDataCallback<FeedbackBean>() {		
					@Override
					public void onSuccess(FeedbackBean datas) {						
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			break;
		default:
			break;
		}
		
	}
	/**
	 * Net data interface for zan info
	 */
	private void getPraiseInfo(int type,String id,String token,final CommonVolleyDataCallback<D_PraiseBean> callback)
	{
		RequestData = new JSONObject();
		if(type == 1)
		{
			URL = Constant.IP + Constant.getPraiseInfo;
		}
		else
		{
			URL = Constant.IP + Constant.getCancelPraiseInfo;
		}
		
		try {
			
			RequestData.put("id", id);
			RequestData.put("token", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		GsonRequest<D_PraiseBean> request = new GsonRequest<D_PraiseBean>(URL, RequestData.toString(), 
   				new Response.Listener<D_PraiseBean>() {
   					@Override
   					public void onResponse(D_PraiseBean response) {

   						callback.onSuccess(response);
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, D_PraiseBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);	
	}
	
	/**
	 * favor
	 */
	private void GetFavorRequest(int type,String id,final CommonVolleyDataCallback<FeedbackBean> callback)
	{
		switch (type) {
		case 0:
			URL = Constant.IP + Constant.getFavorInfo;
			break;
		case 1:
			URL = Constant.IP + Constant.getCancelFavorInfo;
			break;
		default:
			break;
		}
		
		RequestData = new JSONObject();
		try {
			RequestData.put("id", id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<FeedbackBean> request = new GsonRequest<>(URL,  RequestData.toString(), 
	   				new Response.Listener<FeedbackBean>() {
	   					@Override
	   					public void onResponse(FeedbackBean response) {
	   						callback.onSuccess(response);
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
