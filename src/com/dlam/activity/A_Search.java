package com.dlam.activity;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.dlam.adapter.B_ListDataAdapter;
import com.dlam.bean.B_ListBean;
import com.dlam.bean.Constant;
import com.dlam.bean.userresourcelist;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class A_Search extends Activity implements OnClickListener{
	//控件声明
	private EditText et_input;
	private Button btn_search;
	private ListView mListView;
	
	//变量声明
	private B_ListDataAdapter mAdapter;
	private int currentpage = 1;
	private String keywords;
	private String URL;
	private int toaPage;
	private String userid;
	private JSONObject RequestData;
	
	private String NET_TAG = "A_Search";
	private Context mContext;
	
	private List<userresourcelist> mDatas;
 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.a_search);
		
		mContext = A_Search.this;
		findView();
		click();
	}
	//控件实例化
	private void findView()
	{
		et_input = (EditText) findViewById(R.id.et_searchbox);
		btn_search = (Button) findViewById(R.id.btn_search);
		mListView = (ListView) findViewById(R.id.lv_content);
	}
	
	//获取数据
	private void GetData()
	{
		currentpage = 1;
		
		GetListData(Integer.toString(currentpage), keywords, new CommonVolleyDataCallback<B_ListBean>() {

			@Override
			public void onSuccess(final B_ListBean datas) {
				currentpage++;
        		toaPage = Integer.parseInt(datas.totalpage);
        		mDatas = datas.userresourcelist;        		
        		mAdapter = new B_ListDataAdapter(mContext,mDatas,R.layout.b_listviewitem);   
        	    mListView.setAdapter(mAdapter);
        	    mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						userid = datas.userresourcelist.get(position).id;
						Log.e("A_Search id", userid);
						Intent intent = new Intent();
						intent.putExtra("userid", userid);
						intent.setClass(mContext, PersonPageActivity.class);
						mContext.startActivity(intent);
					}
				});
			}
		});
	}
	private void click()
	{
		btn_search.setOnClickListener(this);
	}
	//common class
	private void GetListData(String currentpage,String keywords,final CommonVolleyDataCallback<B_ListBean> callback)
	{
		
		RequestData = new JSONObject();
		try {
			//RequestData.put("currentpage", currentpage);
			RequestData.put("keywords", keywords);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		URL = Constant.IP + Constant.SearchUser + "/keywords/" + keywords;
		GsonRequest<B_ListBean> request = new GsonRequest<B_ListBean>(Method.GET,URL,RequestData.toString(),
				new Response.Listener<B_ListBean>() {

					@Override
					public void onResponse(B_ListBean arg0) {
						callback.onSuccess(arg0);
					}
				},new AbstractVolleyErrorListener(mContext) {
   					@Override
   					public void onError() {
   						Log.e(NET_TAG, "----onError");
   					}
   				}, B_ListBean.class);
		MyApplication.getInstance().addToRequestQueue(request);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			keywords =  et_input.getText().toString();
			
			if(keywords.equals(""))
			{
				ToastUtil.showShortToast(mContext, "请输入搜索关键词");
			}
			else
			{
				GetData();
			}
			break;

		default:
			break;
		}
	}
}
