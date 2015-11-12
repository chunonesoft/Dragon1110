package com.dlam.adapter;

import java.util.List;

import android.content.Context;

import com.dlam.bean.infowantlist;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class A_WantJoinListAdapter extends CommonAdapter<infowantlist>{

	public A_WantJoinListAdapter(Context context, List<infowantlist> datas,
			int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, infowantlist t) {
		holder.setText(R.id.tv_name, t.issuer);
		holder.setVolleyImage(R.id.iv_pic, t.iconpath, R.drawable.bg_default, R.drawable.bg_error);
	}

}
