package com.dlam.ui;

import org.xmlpull.v1.XmlPullParser;

import com.dlam.dragon.R;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SegmentControlView extends LinearLayout {
	
	private TextView textView1 = null;
	private TextView textView2 = null;
	private View verTextView1 = null;//中间的竖线
	private onSegmentControlViewClickListener listener;
	
	public SegmentControlView(Context context) {
		super(context);
		initView();
	}
	
	public SegmentControlView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}
	
	public SegmentControlView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView();
	}

	private void initView() {
		
		textView1 = new TextView(getContext());
		textView2 = new TextView(getContext());
		verTextView1 = new View(getContext());
		
		textView1.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		verTextView1.setLayoutParams(new LayoutParams(1, LayoutParams.MATCH_PARENT));
		textView2.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
		
		setSegmentText(0, getContext().getString(R.string.f3_chat));
		setSegmentText(1, getContext().getString(R.string.f3_people));
	    setSegmentTextSize(16);//设置文字大小
		
		XmlPullParser xrp = getResources().getXml(R.drawable.seg_text_color_selector); 
	    try {  
	        ColorStateList csl = ColorStateList.createFromXml(getResources(), xrp);  
	        textView1.setTextColor(csl);
	        textView2.setTextColor(csl);
	      } catch (Exception e) {  
	    } 
	    textView1.setGravity(Gravity.CENTER);
	    textView2.setGravity(Gravity.CENTER);
	    textView1.setPadding(3, 6, 3, 6);
	    textView2.setPadding(3, 6, 3, 6);
		textView1.setBackgroundResource(R.drawable.seg_left);
		textView2.setBackgroundResource(R.drawable.seg_right);
	    verTextView1.setBackgroundColor(getResources().getColor(R.color.theme_color));
				
		this.removeAllViews();
		this.addView(textView1);
		this.addView(verTextView1);
		this.addView(textView2);
		this.invalidate();
		
		textView1.setSelected(true);
		
		textView1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (textView1.isSelected()) {
					return;
				}
				textView1.setSelected(true);
				textView2.setSelected(false);
				if (listener != null) {
					listener.onSegmentControlViewClick(textView1, 0);
				}
			}
		});
		textView2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (textView2.isSelected()) {
					return;
				}
				textView2.setSelected(true);
				textView1.setSelected(false);
				if (listener != null) {
					listener.onSegmentControlViewClick(textView2, 1);
				}
			}
		});
		
	}
	
	/**
	 * 设置字体大小 单位dip
	 * @param dp
	 */
	public void setSegmentTextSize(int dp) {
		textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
		textView2.setTextSize(TypedValue.COMPLEX_UNIT_DIP, dp);
	}
	
	/**
	 * 设置文字
	 * @param text
	 * @param position
	 */
	public void setSegmentText(int position, CharSequence text) {
		if (position == 0) {//左
			textView1.setText(text);
		}
		if (position == 1) {//中
			textView2.setText(text);
		}
	}
	
	public void setOnSegmentControlViewClickListener(onSegmentControlViewClickListener listener) {
		this.listener = listener;
	}
	
	public static interface onSegmentControlViewClickListener{
		
		/**
		 * @param v
		 * @param position 0-左边 1-中间 2-右边
		 */
		public void onSegmentControlViewClick(View v,int position);
	}
	
	/**
	 * dp与px转化函数
	 * @param context
	 * @param dp
	 * @return
	 */
	private static int dp2Px(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}
}
