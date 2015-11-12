package com.dlam.net;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

public abstract class AbstractVolleyErrorListener implements ErrorListener {

	private Context mContext;

	public AbstractVolleyErrorListener(Context context) {
		mContext = context;
	}

	public void onErrorResponse(VolleyError error) {
		onError();
		Toast.makeText(mContext, VolleyErrorHelper.getMessage(error, mContext), Toast.LENGTH_SHORT).show();
	}

	abstract public void onError();
}
