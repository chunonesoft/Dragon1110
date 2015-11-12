package com.dlam.adapter;

import java.util.List;

import android.content.Context;

import com.dlam.bean.D_labelBean;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class D_LabelAdapter extends CommonAdapter<D_labelBean>{

	public D_LabelAdapter(Context context, List<D_labelBean> datas, int layoutId) {
		super(context, datas, layoutId);
	}

	@Override
	public void convert(ViewHolder holder, D_labelBean t) {
		holder.setText(R.id.tv_label, t.tags);
	}

}
