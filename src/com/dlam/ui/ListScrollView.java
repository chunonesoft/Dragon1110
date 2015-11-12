package com.dlam.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ListScrollView extends ListView{

	public ListScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
			        MeasureSpec.AT_MOST);
				       super.onMeasure(widthMeasureSpec, expandSpec);

	}
	
	
}
