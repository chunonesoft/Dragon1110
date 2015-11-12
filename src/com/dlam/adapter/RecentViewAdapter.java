package com.dlam.adapter;

import java.util.List;

import android.content.Context;

import com.dlam.bean.uservisitorlist;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class RecentViewAdapter extends CommonAdapter<uservisitorlist>{

	public RecentViewAdapter(Context context, List<uservisitorlist> datas,
			int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, uservisitorlist t) {
		holder.setText(R.id.tv_name, t.visitor);
		holder.setVolleyImage(R.id.iv_pic, t.visitor_iconpath, R.drawable.bg_default, R.drawable.bg_error);
	}

}
