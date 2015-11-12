package com.dlam.utils;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.dlam.activity.MyApplication;
import com.dlam.bean.Constant;
import com.dlam.net.LruImageCache;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder {

	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;
	private Context context;
	public ViewHolder(Context context,ViewGroup parent,int layoutId,int position)
	{
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
		mConvertView.setTag(this);
		
	}
	public static ViewHolder get(Context context,View convertView,
			ViewGroup parent,int layoutId,int position)
	{
		if(convertView == null)
		{
			return new ViewHolder(context, parent, layoutId, position);
		}
		else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			return holder;
		}
	}
	/**
	 * ͨ��viewId��ÿؼ�
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId)
	{
		View view = mViews.get(viewId);
		if(view == null)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T)view;
		
	}
	
	public View getConvertView()
	{
		
		return mConvertView;	
	}
	public ViewHolder setText(int viewId,String text)
	{
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}
	public ViewHolder setImageResouce(int viewId,int resId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}
	
	public ViewHolder setImageBitmap(int viewId,Bitmap bitmap)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public ViewHolder setVolleyImage(int viewId,String uri,int default_image,int failed_image)
	{
		RequestQueue mQueue = MyApplication.getInstance().getRequestQueue();
		ImageLoader imageLoader = new ImageLoader(mQueue, new LruImageCache());
		ImageView view = getView(viewId); 
		    ImageListener listener = ImageLoader.getImageListener(view,  
				default_image,failed_image); 
		    imageLoader.get(Constant.ImageUri+uri, listener);  
		return this;
	}
	
	public ViewHolder setImageURI(int viewId,String uri)
	{
		return this;
	}
	public int getPosition() {
		return mPosition;
	}
	public void setPosition(int mPosition) {
		this.mPosition = mPosition;
	}
}
