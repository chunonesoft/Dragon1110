package com.dlam.net;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GsonRequest<T> extends JsonRequest<T> {

	private final Listener<T> mListener;
	private Gson mGson;
	private Class<T> mClazz;
	

	

	public GsonRequest(int method, String url, String requestBody,
			Listener<T> listener, ErrorListener errorListener,Class<T> clazz) {
		super(Method.GET, url, requestBody, listener, errorListener);
		this.mListener = listener;
		mGson = new Gson();
		mClazz=clazz; 
	}

	public GsonRequest(String url, String requestBody, Listener<T> listener, ErrorListener errorListener, Class<T> clazz) {
		super(Method.POST, url, requestBody, listener, errorListener);
		this.mListener = listener;
		mGson = new Gson();
		mClazz=clazz;
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		try {
			String jsonString = new String(response.data,"utf-8");
			return Response.success(mGson.fromJson(jsonString, mClazz),
					HttpHeaderParser.parseCacheHeaders(response));
		} catch (UnsupportedEncodingException e) {
			return Response.error(new ParseError(e));
		}
	}

	@Override
	protected void deliverResponse(T response) {
		mListener.onResponse(response);
	}
	
}
