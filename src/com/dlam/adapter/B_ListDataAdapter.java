package com.dlam.adapter;

import java.util.List;

import android.content.Context;
import com.dlam.bean.userresourcelist;
import com.dlam.dragon.R;
import com.dlam.utils.ViewHolder;

public class B_ListDataAdapter extends CommonAdapter<userresourcelist>{

	Context context;
	List<userresourcelist> datas;
	public B_ListDataAdapter(Context context, List<userresourcelist> datas,
			int layoutId) {
		super(context, datas, layoutId);
		this.context = context;
		this.datas = datas;
	}

	@Override
	public void convert(ViewHolder holder, userresourcelist bean) {
		holder.setText(R.id.tv_name,"姓名："+bean.username);
		holder.setText(R.id.tv_id,"身份："+bean.charactername);
		if(bean.certificated == "0")
		{
			holder.setText(R.id.tv_sure,"未认证");
		}
		else
		{
			holder.setText(R.id.tv_sure,"已认证");
		}
	
		holder.setText(R.id.tv_address,"地区："+bean.locationname);
		holder.setText(R.id.tv_project,bean.project);
		holder.setText(R.id.tv_need,bean.demand);
	}

}
