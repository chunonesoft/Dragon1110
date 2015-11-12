package com.dlam.net;

import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.dlam.activity.MyApplication;
import com.dlam.bean.Constant;

public class LoadNetImage {

	public LoadNetImage(ImageView view, String uri, int default_image,
			int failed_image) {
		RequestQueue mQueue = MyApplication.getInstance().getRequestQueue();
		ImageLoader imageLoader = new ImageLoader(mQueue, new LruImageCache()); 
		    ImageListener listener = ImageLoader.getImageListener(view,  
				default_image,failed_image); 
		    imageLoader.get(Constant.ImageUri+uri, listener);
	}
	
}
