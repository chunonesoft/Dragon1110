package com.dlam.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPException;

import com.dlam.adapter.AddFriendAdapter;
import com.dlam.bean.Constant;
import com.dlam.bean.Session;
import com.dlam.dragon.R;
import com.dlam.ui.LoadingDialog;
import com.dlam.utils.PreferencesUtils;
import com.dlam.utils.ToastUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


/**
 * 
 * @author chunsoft
 */
public class AddFriendActivity extends Activity implements OnClickListener{
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
	
	}

	@Override
	public void onClick(View v) {
	// TODO Auto-generated method stub
		
	}
	
}
