package com.dlam.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response.Listener;

public class JSONObjListener implements Listener<JSONObject>{

	@Override
	public void onResponse(JSONObject arg0) {
//		try {
////			System.out.println("----------getCity"+arg0.getJSONObject("weatherinfo").getString("city"));
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		System.out.println("jsonobj...." + arg0.toString());
	}

}
