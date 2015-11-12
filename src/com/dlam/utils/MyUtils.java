package com.dlam.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;


public class MyUtils
{
	/**
	 * Try to return the absolute file path from the given Uri
	 * 4.4之前的版本适用
	 *
	 * @param context
	 * @param uri
	 * @return the file path or null
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
	    if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}
	
	
	
	/** 弹出键盘
	 * @param context 上下文
	 * @param view 获得焦点后需要弹出键盘的view
	 */
	public static void showSoftKeyBoard(Context context,View view)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInputFromInputMethod(view.getWindowToken(), 0);
	}
	
	/** 隐藏键盘
	 * @param context 上下文
	 * @param view 获得焦点后需要弹出键盘的view
	 */
	public static void hideSoftKeyBoard(Context context,View view)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
	/** 反转显示或隐藏键盘
	 * @param context 上下文
	 * @param view 获得焦点后需要弹出键盘的view
	 */
	public static void toggleSoftKeyBoard(Context context,View view)
	{
		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.toggleSoftInputFromWindow(view.getWindowToken(), 0, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	//将map转为json对象
	public static JSONObject parseToJSONObject(Map<String, Object> map)
	{
		JSONObject jsonObject = new JSONObject();
		for(Entry<String, Object> entry:map.entrySet() )
		{
			try
			{
				jsonObject.put(entry.getKey(), entry.getValue());
			} catch (JSONException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonObject;
	}
	
	/**
	 * 获得对象的所有成员变量
	 * 
	 * @param classTye
	 *            对象类型
	 * @return 成员变量list
	 */
	public static List<Field> getDeclaredFields(Class<?> classTye) {
		// 子类成员变量
		Field[] subFiled = classTye.getDeclaredFields();
		// 父类成员变量
		Field[] superField = classTye.getSuperclass().getDeclaredFields();
		// 合并后的成员变量
		List<Object> list = MyUtils.combineArrays(subFiled, superField);
		List<Field> fields = new ArrayList<Field>();
		for (int i = 0; i < list.size(); i++) {
			fields.add((Field) list.get(i));
		}
		return fields;
	}
	
	/**
	 * 一个或多个数组合并后添加到list中
	 * 
	 * @param objects
	 *            Object类型的可变数组参数
	 * @return 合并后的list
	 */
	public static List<Object> combineArrays(Object[]... objects) {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < objects.length; i++) {
			list.addAll(Arrays.asList(objects[i]));
		}
		return list;
	}
	
	/**
	 * 对象转map
	 * 
	 * @param object
	 *            Object类型对象
	 * @return map
	 */
	public static HashMap<String, Object> toMap(Object object)
	{
		HashMap<String, Object> map = new HashMap<String, Object>();
		if (object == null) {
			return map;
		}

		try {
			// 对象类型
			Class<?> classTye = object.getClass();
			// 获得对象的所有成员变量
			List<Field> list = MyUtils.getDeclaredFields(classTye);
			for (int i = 0; i < list.size(); i++) {
				Field field = (Field) list.get(i);
				String fieldName = field.getName();
				field.setAccessible(true);
				Object o = field.get(object);
				if (o != null) {
					map.put(fieldName, o.toString());
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return map;
	}
	
	public final static int PHONENUM = 1,
					        EMAIL = 2,
					        PWD = 3;
	/**
	 * 判断是否为某种类型的数据（手机号/邮箱）
	 * @param phonenum
	 * @param type
	 * @return flag
	 */
	public static boolean isThisType(String str,int type)
	{
		boolean flag = false;
		Pattern p;
		Matcher m;
		if(TextUtils.isEmpty(str))
		{
			return flag;
		}
		else
		{
			switch (type)
			{
			case PHONENUM:
				p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");  
				m = p.matcher(str);
				flag = m.matches();
				break;
			case EMAIL:
				//简单匹配  
			  //Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}");
				//复杂匹配  
			    p = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
			    m = p.matcher(str);
			    flag = m.matches();
				break;
			case PWD:
				p = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
				break;
			}
		}
		return flag;
	}
	
	public static View getRootView(Activity context)
	{
		return ((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
	}
}
