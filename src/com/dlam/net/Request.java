package com.dlam.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

/**
 * @ClassName:  Request   
 * @Description:JsonObjectRequest
 * @Description：JsonArrayRequest 
 * @Description：StringRequest
 * @Description：CoolRequest   
 * @author: chunsoft 
 * @date:   2015-8-3 上午8:35:25
 */
public class Request {
	/**
	 * @Title: jsObjectData   
	 * @Description: GET方式请求JSONObject数据
	 * @param: @param URL
	 * @param: @param jsonRequest
	 * @param: @return      
	 * @return: JSONObject      
	 * @throws
	 */
	JSONObject data = new JSONObject();
	public JSONObject jsObjectData(String URL,JSONObject jsonRequest)
	{
		JsonObjectRequest jsObjectRequest = new JsonObjectRequest
		(URL, jsonRequest, 
				new Listener<JSONObject>() 
				{
					@Override
					public void onResponse(JSONObject response) {
						data = response;
						try {
							VolleyLog.v("Response:%n %s", response.toString(4));
						} catch (JSONException e) {
							e.printStackTrace();
						} 
					}
				}, 
				new ErrorListener() 
				{
					@Override
					public void onErrorResponse(VolleyError error) {
						VolleyLog.e("Error: ", error.getMessage()); 
					}
				}
				);
		return data;
	}
	
	
}
