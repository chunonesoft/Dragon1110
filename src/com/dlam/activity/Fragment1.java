package com.dlam.activity;

import java.util.ArrayList;
import java.util.List;

import com.dlam.dragon.R;

import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;

/**
 * 
 * @author chunsoft
 *
 */
public class Fragment1 extends Fragment implements OnClickListener{
	
	List<View> listViews;
	Context context = null;
	LocalActivityManager manager = null;
	TabHost tabHost = null;
	private ViewPager pager = null;
	private ImageView iv_refresh;
	private ImageButton ib_search;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View parentView = inflater.inflate(R.layout.fragment1, container, false);
		context = getActivity();
		pager = (ViewPager) parentView.findViewById(R.id.viewpager);
		
		iv_refresh = (ImageView) parentView.findViewById(R.id.iv_refresh);
		
		ib_search = (ImageButton) parentView.findViewById(R.id.ib_search);
		//定放一个放view的list，用于存放viewPager用到的view
		listViews = new ArrayList<View>();
		
		ib_search.setVisibility(View.INVISIBLE);
		iv_refresh.setOnClickListener(this);
		//ib_search.setOnClickListener(this);
		manager = new LocalActivityManager(getActivity(), true);
		manager.dispatchCreate(savedInstanceState);
		
		Intent i1 = new Intent(context, T1Activity.class);
		listViews.add(getView("A", i1));
		Intent i2 = new Intent(context, T2Activity.class);
		listViews.add(getView("B", i2));
		Intent i3 = new Intent(context, T3Activity.class);
		listViews.add(getView("C", i3));
		Intent i4 = new Intent(context, T4Activity.class);
		listViews.add(getView("D", i4));
		
		tabHost = (TabHost) parentView.findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);
		
		//这儿主要是自定义一下tabhost中的tab的样式
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tabwidget, null);  
		TextView tvTab1 = (TextView)tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("全部");
				
		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tabwidget,null);  
		TextView tvTab2 = (TextView)tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("资讯");
				
		RelativeLayout tabIndicator3 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tabwidget,null);  
		TextView tvTab3 = (TextView)tabIndicator3.findViewById(R.id.tv_title);
		tvTab3.setText("活动");
		
		RelativeLayout tabIndicator4 = (RelativeLayout) LayoutInflater.from(getActivity()).inflate(R.layout.tabwidget,null);  
		TextView tvTab4 = (TextView)tabIndicator4.findViewById(R.id.tv_title);
		tvTab4.setText("服务");
				
		Intent intent = new Intent(context,EmptyActivity.class);
		//注意这儿Intent中的activity不能是自身 比如“A”对应的是T1Activity，后面intent就new的T3Activity的。
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(tabIndicator3).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("D").setIndicator(tabIndicator4).setContent(intent));
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
	                if ("D".equals(tabId)) {
	                    pager.setCurrentItem(3);
	                } 
	            }
	        });
		
		
		return parentView;
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_refresh:
			  Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.refreshanim);       
		      LinearInterpolator lir = new LinearInterpolator();  
		      anim.setInterpolator(lir);  
		      iv_refresh.startAnimation(anim); 
			break;

		case R.id.ib_search:
			Intent intent = new Intent(getActivity(),A_Search.class);
			startActivity(intent);
			break;
		default:
			break;
		}
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
        public void startUpdate(View arg0) {
        }

	}

}
