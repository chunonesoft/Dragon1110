package com.dlam.activity;

import java.util.List;

import com.dlam.adapter.RecentViewAdapter;
import com.dlam.bean.Constant;
import com.dlam.bean.D_UserVisitorListBean;
import com.dlam.bean.uservisitorlist;
import com.dlam.dragon.R;
import com.dlam.utils.CommonVolleyDataCallback;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class D_MoreRecentView extends Activity{
	//控件声明
	private TextView titleName;
	D_T1Activity d_T1Activity ;
	private ListView lv_morevisitor;
	
	//变量声明
	private String  userid;
	private List<uservisitorlist> mDatas;
	private RecentViewAdapter rv_adapter;
	private Context mContext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.d_morerecentview);
		mContext = D_MoreRecentView.this;
		findView();
		initData();
	}
	
	private void findView()
	{
		titleName = (TextView) findViewById(R.id.title_name);
		titleName.setText("最近来访");
		lv_morevisitor =  (ListView) findViewById(R.id.lv_morevisitor);
	}
	private void initData()
	{
		d_T1Activity = new D_T1Activity();
		d_T1Activity.getUserVisitorList(Constant.userData.userid, new CommonVolleyDataCallback<D_UserVisitorListBean>() {
			
			@Override
			public void onSuccess(D_UserVisitorListBean datas) {
				mDatas = datas.uservisitorlist;
				rv_adapter = new RecentViewAdapter(mContext, mDatas, R.layout.a_activityitem);
				lv_morevisitor.setAdapter(rv_adapter);
			}
		});
	}
}
