package com.dlam.activity;

import java.io.File;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.dlam.bean.Constant;
import com.dlam.bean.FeedbackBean;
import com.dlam.dragon.R;
import com.dlam.net.AbstractVolleyErrorListener;
import com.dlam.net.GsonRequest;
import com.dlam.ui.RoundImageView;
import com.dlam.utils.CommonVolleyDataCallback;
import com.dlam.utils.CustomDialog;
import com.dlam.utils.MyUtils;
import com.dlam.utils.ToastUtil;
import com.gghl.view.wheelcity.AddressData;
import com.gghl.view.wheelcity.OnWheelChangedListener;
import com.gghl.view.wheelcity.WheelView;
import com.gghl.view.wheelcity.adapters.AbstractWheelTextAdapter;
import com.gghl.view.wheelcity.adapters.ArrayWheelAdapter;
import com.zf.iosdialog.widget.MyAlertDialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class D_CompleteActivity extends Activity implements android.view.View.OnClickListener,OnItemSelectedListener{
	//控件声明
	private TextView titleName;
	private ImageButton titleRightSure;
	private RoundImageView logoRIV;
	private RadioGroup sexGroup;
	private RadioButton checkedBtn;
	private EditText realnameEt,tagEt,focusareaEt,projectEt,personalintroEt;
	private Spinner stateSP,locateSP,focusSP,ageSP,chooseAreaSP;
	//变量声明
    private static final int FROMGARREY = 0; // 从图库中选择图片
    private static final int TAKE_PHOTO = 1; // 用相机拍摄照片
    public static final int CROP_PHOTO = 2;//剪切照片
    private Uri imageUri;
    private int mainW,mainH;
    private String newState,newLocate,newFocus,sex,realname,cityTxt,age,
    			   tag,focusArea,project,personalIntro;
    private JSONObject RequestData;
    private String URL;
    private String NET_TAG;
    private Context mContext;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.d_complete);
		mContext = D_CompleteActivity.this;
		findView();
		getSelected();
		init();
		click();
	}
	/**
	 * 初始化事件
	 */
	private void init()
	{
		mainH = 45;
		mainW = 45;
		titleName.setText("完善个人资料");
		titleRightSure.setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure));
	}
	/**
	 * 单击事件
	 */
	private void click()
	{
		logoRIV.setOnClickListener(this);
		titleRightSure.setOnClickListener(this);
		titleRightSure.setOnTouchListener(new OnTouchListener()
		{
			
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//按下
					titleRightSure.setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure_clicked));
				}else if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//弹起
					titleRightSure.setImageDrawable(getResources().getDrawable(R.drawable.set_2nd_sure));
				}
				return false;
			}
		});
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.title_right_btn:
			submitNewUserInfo();
			break;
		case R.id.ib_logo:
			showChoosePhotoDialog();
			break;
		/*case R.id.et_address:
			chooseMyArea();
			break;*/
		}
	}
	/**
	 * 弹出选择地区的对话框
	 */
	private void chooseMyArea()
	{
		View view = dialogm();
		final MyAlertDialog dialog1 = new MyAlertDialog(D_CompleteActivity.this)
				.builder()
				.setTitle("选择地区")
				.setView(view)
				.setNegativeButton("取消", new android.view.View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
		dialog1.setPositiveButton("保存", new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), cityTxt, 1).show();
			}
		});
		dialog1.show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(resultCode == RESULT_OK)
		{
			switch (requestCode)
			{
			case FROMGARREY:
				if(data != null)
				{
					imageUri= data.getData();//图片的uri
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(imageUri, "image/*");
					intent.putExtra("scale", true);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, CROP_PHOTO);
				}
				break;
			case TAKE_PHOTO:
				if (resultCode == RESULT_OK) {
					Intent intent = new Intent("com.android.camera.action.CROP");
					intent.setDataAndType(imageUri, "image/*");
					intent.putExtra("scale", true);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, CROP_PHOTO);
				}
				break;
			case CROP_PHOTO:
				if (resultCode == RESULT_OK) {
					try {
//						Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
//								.openInputStream(imageUri));
						String url = MyUtils.getRealFilePath(D_CompleteActivity.this,imageUri);
						   BitmapFactory.Options opts = new Options();
							opts.inJustDecodeBounds = true;
							Bitmap bm = BitmapFactory.decodeFile(url, opts);
							int imageH = opts.outHeight;
							int imageW = opts.outWidth;
							int scale = 1;
							int scaleX = imageW/mainW;
							int scaleY = imageH/mainH;
							if(imageH > imageW && imageW >=1)
							{
								scale = scaleY;
							}
							if(imageW > imageH && imageH >=1)
							{
								scale = scaleX;
							}
							opts.inJustDecodeBounds = false;
							opts.inSampleSize = scale;
							Bitmap bitmap = BitmapFactory.decodeFile(url, opts);
						logoRIV.setImageBitmap(bitmap);
						//在这里获取到图片的file
						
						//imageUri = null;
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				break;
			default:
				break;
			}
		}
	}
	//控件实例化
	private void findView()
	{
		//显示并用于选择头像的RoundImageView
		logoRIV = (RoundImageView) findViewById(R.id.ib_logo);
				
		titleRightSure = (ImageButton) findViewById(R.id.title_right_btn);		
		titleName = (TextView) findViewById(R.id.title_name);
		//真实姓名
		realnameEt = (EditText) findViewById(R.id.et_name);
		//标签
		tagEt = (EditText) findViewById(R.id.d_com_et_tag);
		//关注领域
		focusareaEt = (EditText) findViewById(R.id.d_com_et_focusarea);
		//项目
		projectEt = (EditText) findViewById(R.id.d_com_et_project);
		//个人简介
		personalintroEt = (EditText) findViewById(R.id.d_com_et_personalintro);
		//选择性别的RadioGroup
		sexGroup = (RadioGroup) findViewById(R.id.rg);
		
		locateSP = (Spinner) findViewById(R.id.sp_locate);
		chooseAreaSP =  (Spinner) findViewById(R.id.sp_area);
		focusSP = (Spinner) findViewById(R.id.sp_focus);
		stateSP = (Spinner) findViewById(R.id.sp_state);
		ageSP = (Spinner) findViewById(R.id.sp_age);

	}
	//getSelected
	private void getSelected()
	{
		newState = stateSP.getSelectedItem().toString();
		newLocate = locateSP.getSelectedItem().toString();
		newFocus = focusSP.getSelectedItem().toString();
		realname = realnameEt.getText().toString();
		switch (sexGroup.getCheckedRadioButtonId()) {
		case R.id.rb1:
			sex = "男";
			break;
		case R.id.rb2:
			sex = "女";
			break;
		default:
			break;
		}
		cityTxt = chooseAreaSP.getSelectedItem().toString();
		age = ageSP.getSelectedItem().toString();
		tag = tagEt.getText().toString();
		focusArea = focusareaEt.getText().toString();
		project = projectEt.getText().toString();
		personalIntro = personalintroEt.getText().toString();
		
	}
	private void showChoosePhotoDialog()
	{
		 CharSequence[] items = { "相册", "相机" };
	        
	     CustomDialog.Builder builder = new CustomDialog.Builder(this);
	     builder.setTitle("选择图片来源");
	        builder.setMessage("");
	        builder.setPositiveButton("相机", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					
					File outputImage = new File(Environment.getExternalStorageDirectory(),
							"output_image.jpg");
					try {
						if (outputImage.exists()) {
							outputImage.delete();
						}
						outputImage.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
					imageUri = Uri.fromFile(outputImage);
					Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					startActivityForResult(intent, TAKE_PHOTO);
					dialog.dismiss();
				}
			});
	        builder.setNegativeButton("相册", new OnClickListener()
			{
				
				@Override
				public void onClick(DialogInterface dialog, int which)
				{
					dialog.dismiss();
					Intent intent = new Intent(Intent.ACTION_PICK);
					intent.setType("image/*");
					startActivityForResult(intent, FROMGARREY);
				}
			});
	        builder.create().show();
	        
		
	}
	private void submitNewUserInfo()
	{/*
		//优先判断各个信息是否为空
		realname = realnameEt.getText().toString().trim();
		if(imageUri != null)
		{
			
		}*/
		//if(!TextUtils.isEmpty(newState) && !TextUtils.isEmpty(newFocus) && !TextUtils.isEmpty(newLocate) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(age))
		
		
			SendData(new CommonVolleyDataCallback<FeedbackBean>() {

				@Override
				public void onSuccess(FeedbackBean datas) {
					if(datas.retcode.equals("1"))
					{
						ToastUtil.showShortToast(mContext, datas.retmsg);
						finish();
					}
					else
					{
						ToastUtil.showShortToast(mContext, datas.retmsg);	
					}
				}
			});
			//Toast.makeText(D_CompleteActivity.this, "当前状态："+newState+"/个人定位："+newLocate+"/关注领域："+newFocus+"/性别："+sex+"/年龄："+age+"/标签："+tag+"/关注领域："+focusArea+"/项目："+project+"/个人简介："+personalIntro+"/真实姓名："+realname+"/地区："+cityTxt, Toast.LENGTH_SHORT).show();
	}
	
	
	//以下的code是用来显示仿ios的地区选择框，现在简化后用一个spinner来代替
	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView
				.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

		final String cities[][] = AddressData.CITIES;
		final String ccities[][][] = AddressData.COUNTIES;
		final WheelView city = (WheelView) contentView
				.findViewById(R.id.wheelcity_city);
		city.setVisibleItems(0);

		// 地区选择
		final WheelView ccity = (WheelView) contentView
				.findViewById(R.id.wheelcity_ccity);
		ccity.setVisibleItems(0);// 不限城市

		country.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateCities(city, cities, newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		city.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updatecCities(ccity, ccities, country.getCurrentItem(),
						newValue);
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		ccity.addChangingListener(new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				cityTxt = AddressData.PROVINCES[country.getCurrentItem()]
						+ " | "
						+ AddressData.CITIES[country.getCurrentItem()][city
								.getCurrentItem()]
						+ " | "
						+ AddressData.COUNTIES[country.getCurrentItem()][city
								.getCurrentItem()][ccity.getCurrentItem()];
			}
		});

		country.setCurrentItem(1);// 设置北京
		city.setCurrentItem(1);
		ccity.setCurrentItem(1);
		return contentView;
	}

	/**
	 * Updates the city wheel
	 */
	private void updateCities(WheelView city, String cities[][], int index) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Updates the ccity wheel
	 */
	private void updatecCities(WheelView city, String ccities[][][], int index,
			int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this,
				ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	/**
	 * Adapter for countries
	 */
	private class CountryAdapter extends AbstractWheelTextAdapter {
		// Countries names
		private String countries[] = AddressData.PROVINCES;

		/**
		 * Constructor
		 */
		protected CountryAdapter(Context context) {
			super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
			setItemTextResource(R.id.wheelcity_country_name);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return countries.length;
		}

		@Override
		protected CharSequence getItemText(int index) {
			return countries[index];
		}
	}

	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch (parent.getId()) {
		case R.id.sp_state:
			
			break;
		case R.id.sp_locate:
			
			break;
		case R.id.sp_area:
			
			break;
		case R.id.sp_focus:
			
			break;
		case R.id.sp_age:
			
			break;
		
		default:
			break;
		}
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		
	}

	/**
	 * 打包JSON数据
	 */
	private void RequstJSONData()
	{
		getSelected();
		RequestData = new JSONObject();
		try {
			RequestData.put("username", realname);
			RequestData.put("phonenum", "18018228801");
			RequestData.put("locationid", "1");
			RequestData.put("locationname", cityTxt);
			RequestData.put("age_range", age);
			RequestData.put("age_rangename", age);
			RequestData.put("charactername", realname);
			RequestData.put("status", "创业中");
			RequestData.put("statusname", newState);
			RequestData.put("demand", "合作伙伴");
			RequestData.put("project", project);
			RequestData.put("iconpath", " ");
			RequestData.put("realname", realname);
			RequestData.put("career", "软件工程师");
			RequestData.put("sex", sex);
			RequestData.put("tags", tag);
			RequestData.put("focusarea", focusArea);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
	}
	private void SendData(final CommonVolleyDataCallback<FeedbackBean> callback)
	{
		URL = Constant.IP + Constant.ModifyUser;
		RequstJSONData();
		GsonRequest<FeedbackBean> request = new GsonRequest<FeedbackBean>(URL, RequestData.toString(),new Response.Listener<FeedbackBean>() {
				@Override
				public void onResponse(FeedbackBean response) {
					callback.onSuccess(response);
				}
			}, new AbstractVolleyErrorListener(mContext) {
				@Override
				public void onError() {
					Log.e(NET_TAG, "----onError");
				}
			}, FeedbackBean.class);
	MyApplication.getInstance().addToRequestQueue(request);
	}
}
