package com.dlam.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.dlam.bean.B_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.D_ViewUserBean;
import com.dlam.bean.userresourcelist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.net.LruImageCache;
import com.dlam.ui.RoundImageView;
import com.dlam.utils.ToastUtil;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.TabHost.OnTabChangeListener;

public class PersonPageActivity extends Activity{
	//控件声明
	private ImageButton ib_ask;
	private ViewPager pager = null;
	TabHost tabHost = null; 
	
	private TextView tv_name;	//name,job
	private TextView tv_job;
	private TextView tv_mix;	//address,age
	private TextView tv_state;	//state
	private TextView tv_need;	//need
	private RoundImageView ib_user;	//user image
	//变量声明
	Context mContext;
	List<View> listViews;
	LocalActivityManager manager = null;
	private JSONObject RequestData;
	private D_ViewUserBean ResponseData;
	private String token;
	private String userid;
	private String URL;
	private String NET_TAG = "PersonPageActivity";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.personpage);
		mContext = PersonPageActivity.this;
		Intent intent = getIntent();
		userid = intent.getExtras().get("userid").toString();
		
		findView();
		
		listViews = new ArrayList<View>();
		manager = new LocalActivityManager(PersonPageActivity.this, true);
		manager.dispatchCreate(savedInstanceState);
		
		init();
		GetData();
	}
	
	//控件实例化
	private void findView()
	{
		ib_ask = (ImageButton) findViewById(R.id.ib_ask);		
		tv_job =  (TextView) findViewById(R.id.tv_job);
		tv_name = (TextView) findViewById(R.id.tv_name);
		tv_mix = (TextView) findViewById(R.id.tv_mix);
		tv_state = (TextView) findViewById(R.id.tv_state);
		tv_need = (TextView) findViewById(R.id.tv_need);
		ib_user = (RoundImageView) findViewById(R.id.ib_user);
		pager = (ViewPager) findViewById(R.id.viewpager);
	}
	
	//初始化
	private void init()
	{
		Intent i1 = new Intent(mContext, D_T1Activity.class);
		listViews.add(getView("A", i1));
		Intent i2 = new Intent(mContext, D_T2Activity.class);
		listViews.add(getView("B", i2));
		Intent i3 = new Intent(mContext, D_T3Activity.class);
		listViews.add(getView("C", i3));
		
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);
		
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.tabwidget, null);  
		TextView tvTab1 = (TextView)tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("创业档案");
				
		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.tabwidget,null);  
		TextView tvTab2 = (TextView)tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("活动");
		
		RelativeLayout tabIndicator3 = (RelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.tabwidget,null);  
		TextView tvTab3 = (TextView)tabIndicator3.findViewById(R.id.tv_title);
		tvTab3.setText("收藏");
		
		Intent intent = new Intent(mContext,EmptyActivity.class);
		//注意这儿Intent中的activity不能是自身 比如“A”对应的是T1Activity，后面intent就new的T3Activity的。
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(tabIndicator3).setContent(intent));
		
		pager.setAdapter(new MyPageAdapter(listViews));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//当viewPager发生改变时，同时改变tabhost上面的currentTab
				tabHost.setCurrentTab(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		 //点击tabhost中的tab时，要切换下面的viewPager
		 tabHost.setOnTabChangedListener(new OnTabChangeListener() {
	            @Override
	            public void onTabChanged(String tabId) {
	            	
	            	if ("A".equals(tabId)) {
	                    pager.setCurrentItem(0);
	                } 
	                if ("B".equals(tabId)) {
	                	
	                    pager.setCurrentItem(1);
	                } 
	                if ("C".equals(tabId)) {
	                	
	                    pager.setCurrentItem(2);
	                } 
	            }
	        });
	}
	
	private void GetData()
	{
		URL = Constant.IP + Constant.ViewUser;
		RequestData = new JSONObject();
		try {
			RequestData.put("userid", userid);
			Log.e("PersonPage:token----", Constant.token);
			RequestData.put("token", Constant.token.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		GsonRequest<D_ViewUserBean> request = new GsonRequest<D_ViewUserBean>(URL, RequestData.toString(), 
				new Response.Listener<D_ViewUserBean>() {
   					@Override
   					public void onResponse(D_ViewUserBean datas) {
   						if (datas.is_friend.equals("1")) {
							ib_ask.setImageDrawable(getResources().getDrawable(R.drawable.d_chat));
							
						}
   						ResponseData = datas;
   						tv_name.setText("用户名：" + datas.username);
   						tv_job.setText("职业：" + datas.charactername);
   						tv_mix.setText("地区：" + datas.locationname + "  "+"年龄：" + datas.age_rangename);
   						tv_state.setText("状态：" + datas.statusname + "  " + "性别：" + datas.sex);
   						tv_need.setText("需求：" + datas.demand);
   						ib_user.setScaleType(ScaleType.CENTER);
   						LoadNetImage(ib_user, datas.iconpath,R.drawable.loading,R.drawable.failing);	
   					}
   					private void LoadNetImage(ImageView view, String uri,
   							int bgDefault, int bgError) {
   						RequestQueue mQueue = MyApplication.getInstance().getRequestQueue();
   						ImageLoader imageLoader = new ImageLoader(mQueue, new LruImageCache()); 
   						    ImageListener listener = ImageLoader.getImageListener(view,  
   						    		bgDefault,bgError); 
   						    imageLoader.get(Constant.ImageUri+uri, listener);
   					}
   				}, new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, D_ViewUserBean.class);
   		MyApplication.getInstance().addToRequestQueue(request);
		
	}
	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}
	
    private class MyPageAdapter extends PagerAdapter {
		
		private List<View> list;

		private MyPageAdapter(List<View> list) {
			this.list = list;
		}

		@Override
        public void destroyItem(View view, int position, Object arg2) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0){
        	
        }
	}
	
}
