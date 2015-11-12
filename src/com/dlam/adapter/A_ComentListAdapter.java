package com.dlam.adapter;

import java.util.List;

import android.content.Context;

import com.dlam.bean.commentlist;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class A_ComentListAdapter extends CommonAdapter<commentlist>{

	Context context;
	List<commentlist> datas;
	public A_ComentListAdapter(Context context, List<commentlist> datas, int layoutId) {
		super(context, datas, layoutId);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public void convert(ViewHolder holder, commentlist bean) {
		holder.setText(R.id.tv_name, bean.getIssuer());
		holder.setText(R.id.tv_zannum ,bean.getPraisecount());
		holder.setText(R.id.tv_time, bean.getIssuetime());
		holder.setText(R.id.tv_content, bean.getContent());
		holder.setVolleyImage(R.id.iv_pic, bean.getIconpath(), R.drawable.bg_default, R.drawable.bg_error);
	}

}
