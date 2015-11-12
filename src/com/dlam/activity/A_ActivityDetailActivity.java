package com.dlam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.dlam.adapter.A_WantJoinListAdapter;
import com.dlam.bean.A_DetailBean;
import com.dlam.bean.A_WantJoin;
import com.dlam.bean.A_ac_itemBean;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.bean.infowantlist;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class A_ActivityDetailActivity extends Activity implements OnClickListener,OnCheckedChangeListener{
	//定义控件
	private TextView titleName;
	private CheckBox cb1;
	private CheckBox cb2;
	private CheckBox cb3;
	
	private TextView tv_hold;
	private TextView tv_pub_time;
	private TextView tv_ac_time;
	private TextView tv_place;
	private TextView tv_joinnum;
	private TextView tv_content;
	private TextView tv_wantjoin;
	private ImageView iv_pic;
	private ListScrollView lv_people;
	
	private ScrollView sv;
	
	//定义变量
	private Context mContext;
	private List<infowantlist> datas = new ArrayList<infowantlist>();
	
	private JSONObject requestData;
	private JSONObject RequestData;
	private A_DetailBean responseData2;
	private A_ac_itemBean responseData1;
	private String URL;
	
	private String NET_TAG = "A_ActivityDetailActivity";
	private String myid;
	private String userid;
	
	private A_WantJoinListAdapter adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.a_activitydetail);
		mContext = A_ActivityDetailActivity.this;
		findView();
		init();
		click();
		getData();
	}
	private void findView()
	{
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("活动详情");
		
		cb1 = (CheckBox) findViewById(R.id.cb1);
		cb2 = (CheckBox) findViewById(R.id.cb2);
		cb3 = (CheckBox) findViewById(R.id.cb3);
		
		tv_hold = (TextView) findViewById(R.id.tv_hold);
		tv_pub_time = (TextView) findViewById(R.id.tv_pub_time);
		tv_ac_time = (TextView) findViewById(R.id.tv_ac_time);
		tv_place = (TextView) findViewById(R.id.tv_place);
		tv_joinnum = (TextView) findViewById(R.id.tv_joinnum);
		tv_content = (TextView) findViewById(R.id.tv_content);
		tv_wantjoin = (TextView) findViewById(R.id.tv_wantjoin);
		
		iv_pic = (ImageView) findViewById(R.id.iv_pic);
		lv_people =  (ListScrollView) findViewById(R.id.lv_people);
		
		sv = (ScrollView) findViewById(R.id.sv);
	}
	private void init()
	{
		sv.smoothScrollTo(0, 0);
		Intent intent = getIntent();
		myid = intent.getStringExtra("myid");
	}
	private void click()
	{
		cb1.setOnCheckedChangeListener(this);
		cb2.setOnCheckedChangeListener(this);
		cb3.setOnCheckedChangeListener(this);
	}
	private void getData()
	{
		//获取想参加活动数据
		getAcWantJoin("1", myid, Constant.token, new VolleyDataCallback1() {	
			@Override
			public void onSuccess1(final A_ac_itemBean datas1) {
				datas = datas1.infowantlist;
				tv_wantjoin.setText("想参加：" + datas1.totalcount);
				adapter = new A_WantJoinListAdapter(mContext, datas, R.layout.a_activityitem);
				lv_people.setAdapter(adapter);
				lv_people.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						userid = datas1.infowantlist.get(position).id;
						Intent intent =new Intent();
						intent.putExtra("userid", userid);
						intent.setClass(mContext, PersonPageActivity.class);
						mContext.startActivity(intent);
					}
				});
			}
		});
		//获取活动详情数据
		getAcDetail(myid, new VolleyDataCallback2() {	
			@Override
			public void onSuccess2(A_DetailBean datas2) {
				tv_hold.setText("举办方：" + datas2.getOrganizer());
				tv_pub_time.setText("发布时间：" + datas2.getIssuetime());
				tv_ac_time.setText(datas2.getStarttime());
				tv_place.setText(datas2.getPlace());
				tv_joinnum.setText(datas2.getCapacity());
				tv_content.setText(datas2.getContent());
				LoadNetImage(iv_pic,datas2.getPic1path(),R.drawable.bg_default,R.drawable.bg_error);
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
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_back:
			finish();
			break;

		default:
			break;
		}
	}
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		switch (buttonView.getId()) {
		/**
		 * wantJoin
		 */
		case R.id.cb1:
			
			if(isChecked)
			{
				getWantJoin(1,myid,Constant.token,new CommonVolleyDataCallback<A_WantJoin>() {		
					@Override
					public void onSuccess(A_WantJoin datas) {
						tv_wantjoin.setText("想参加：" + datas.wantcount);
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			else
			{
				getWantJoin(0,myid, Constant.token,new CommonVolleyDataCallback<A_WantJoin>() {		
					@Override
					public void onSuccess(A_WantJoin datas) {
						tv_wantjoin.setText("想参加：" + datas.wantcount);
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			break;
		case R.id.cb2:
			if(isChecked)
			{
				GetRemindRequest(2,myid, Constant.token,new CommonVolleyDataCallback<FeedbackBean>() {		
					@Override
					public void onSuccess(FeedbackBean datas) {
						
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			else
			{
				GetRemindRequest(3,myid,Constant.token, new CommonVolleyDataCallback<FeedbackBean>() {		
					@Override
					public void onSuccess(FeedbackBean datas) {
						
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			break;
		case R.id.cb3:
			if(isChecked)
			{
				GetRemindRequest(1,myid, Constant.token,new CommonVolleyDataCallback<FeedbackBean>() {		
					@Override
					public void onSuccess(FeedbackBean datas) {
						
						ToastUtil.showLongToast(mContext, datas.retmsg);
					}
				});
			}
			else
			{
				GetRemindRequest(0,myid, Constant.token,new CommonVolleyDataCallback<FeedbackBean>() {		
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
	//获取想参加活动数据
	public A_ac_itemBean getAcWantJoin(String currentpage,String infoid,String token,
			final VolleyDataCallback1 callback1)
	{
		URL = Constant.IP + Constant.WantList;
   		requestData = new JSONObject();
   		
   		try {
			requestData.put("currentpage", currentpage);
			requestData.put("infoid", infoid);
			requestData.put("token", token);
		} catch (JSONException e) {
			e.printStackTrace();
		}
   		GsonRequest<A_ac_itemBean> request = new GsonRequest<>(URL, requestData.toString(), 
   				new Response.Listener<A_ac_itemBean>() {

					@Override
					public void onResponse(A_ac_itemBean arg0) {
						responseData1 = arg0;
						callback1.onSuccess1(arg0);
					}
				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_ac_itemBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
		return responseData1;
		
	}
	//获取活动详情数据
	public A_DetailBean getAcDetail(String myid,final VolleyDataCallback2 callback2)
	{
		URL = Constant.IP + Constant.ViewInfo;
   		requestData = new JSONObject();
   		try {
   			requestData.put("id", myid);
   		} catch (JSONException e) {
   			
   			e.printStackTrace();
   		}
   		GsonRequest<A_DetailBean> request = new GsonRequest<A_DetailBean>(URL, requestData.toString(), 
   				new Response.Listener<A_DetailBean>() {
   					@Override
   					public void onResponse(A_DetailBean response) {
   						responseData2 = response;
   						System.out.println("2");
   						System.out.println("RequestData1.toString()----" + requestData.toString());
   						callback2.onSuccess2(response);
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, A_DetailBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
   		return responseData2;	
	}
	
	/**
	 * 提醒网络请求
	 */
	private void GetRemindRequest(int type,String id,String token,final CommonVolleyDataCallback<FeedbackBean> callback)
	{
		switch (type) {
		case 0:
			URL = Constant.IP + Constant.getRemindInfo;
			break;
		case 1:
			URL = Constant.IP + Constant.getCancelRemindInfo;
			break;
		case 2:
			URL = Constant.IP + Constant.getFavorInfo;
			break;
		case 3:
			URL = Constant.IP + Constant.getCancelFavorInfo;
			break;
		default:
			break;
		}
		
		RequestData = new JSONObject();
		try {
			RequestData.put("id", id);
			RequestData.put("token", token);
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
	/**
	 * 
	 * @author chunsoft
	 *wantJoin
	 */
	private void getWantJoin(int type,String id,String token,final CommonVolleyDataCallback<A_WantJoin> callback)
	{
		switch (type) {
		case 0:
			URL = Constant.IP + Constant.getCancelWantInfo;
			break;
		case 1:
			URL = Constant.IP + Constant.getwantInfo;
			break;
		default:
			break;
		}
		
		RequestData = new JSONObject();
		try {
			RequestData.put("id", myid);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<A_WantJoin> request = new GsonRequest<A_WantJoin>(URL,  RequestData.toString(), 
	   				new Response.Listener<A_WantJoin>() {
	   					@Override
	   					public void onResponse(A_WantJoin response) {
	   						callback.onSuccess(response);
	   					}
	   				}, new AbstractVolleyErrorListener(mContext) {
	   					@Override
	   					public void onError() {
	   						Log.e(NET_TAG, "----onError");
	   					}
	   				}, A_WantJoin.class);
	   		MyApplication.getInstance().addToRequestQueue(request);	
		
	}
	//回调想参加活动数据接口
	public interface VolleyDataCallback1
	{
		void onSuccess1(A_ac_itemBean datas1);
	}
	//回调活动详情数据接口
	public interface VolleyDataCallback2
	{
		void onSuccess2(A_DetailBean datas2);
	}
}
