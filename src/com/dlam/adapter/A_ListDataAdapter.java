package com.dlam.adapter;
import java.util.List;

import com.dlam.bean.infolist;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

import android.content.Context;


public class A_ListDataAdapter extends CommonAdapter<infolist>{

	Context context;
	List<infolist> datas;
	public A_ListDataAdapter(Context context, List<infolist> responseData, int layoutId) {
		super(context, responseData, layoutId);
		this.context = context;
		this.datas = responseData;
	}

	@Override
	public void convert(ViewHolder holder, infolist bean) {
		holder.setText(R.id.tv_title, bean.getTitle());
		holder.setText(R.id.tv_source, bean.getSource());
		holder.setText(R.id.tv_time, bean.getIssuetime());
		holder.setText(R.id.tv_num, bean.getViewcount());
		holder.setText(R.id.tv_address, bean.getPlace());
		holder.setText(R.id.tv_wantjoin, bean.getWantcount());
		holder.setText(R.id.tv_comment, bean.getCommentcount());
		holder.setVolleyImage(R.id.iv_pic, bean.getPic1path(), R.drawable.bg_default, R.drawable.itempic);
		holder.setVolleyImage(R.id.iv_mark, bean.getType1icon(), R.drawable.bg_default, R.drawable.mark1);
	}

}
