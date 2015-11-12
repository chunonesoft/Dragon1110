package com.dlam.activity;



import com.dlam.adapter.ConstactAdapter;
import com.dlam.bean.Child;
import com.dlam.bean.Constant;
import com.dlam.bean.Group;
import com.dlam.dragon.R;
import com.dlam.ui.IphoneTreeView;
import com.dlam.ui.TitleBarView;
import com.dlam.utils.ToastUtil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class ConstactFragment extends Fragment {
	private Context mContext;
	private View mBaseView;
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		mContext = getActivity();
		mBaseView = inflater.inflate(R.layout.fragment_constact_father, null);
		return mBaseView;
	}
}
