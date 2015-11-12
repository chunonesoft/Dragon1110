package com.dlam.adapter;

import java.util.ArrayList;

import android.content.Context;

import com.dlam.bean.C_ListBean;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class C_ListDataAdapter extends CommonAdapter<C_ListBean>{

	Context context;
	ArrayList<C_ListBean> datas;
	public C_ListDataAdapter(Context context, ArrayList<C_ListBean> datas,
			int layoutId) {
		super(context, datas, layoutId);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public void convert(ViewHolder holder, C_ListBean bean) {
		holder.setText(R.id.tv_name, bean.name);
		holder.setText(R.id.tv_content, bean.content);
		holder.setText(R.id.tv_time, bean.time);
		holder.setImageResouce(R.id.iv_person, bean.picpath);
	}

}
