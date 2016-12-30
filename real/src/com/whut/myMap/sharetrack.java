package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.makeramen.RoundedImageView;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.trackservice;
import com.whut.net.addtracknet;

import android.support.v4.content.CursorLoader;
import android.text.Editable;
import android.util.Log;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class sharetrack extends Activity {
	private ActionBar mActionBar;
	String str;
	EditText edit;
	LinearLayout music;
	private LinearLayout theme;
	private TextView musicname;
	private TextView themename;
	private static int themetype = 0;
	RoundedImageView image;
	private static trackbean trackbean;
	private static Uri imageUri;
	protected final int SHOW_RESPONSE = 0;
	protected int CROP_SMALL_PICTURE = 3;
	protected int CHOOSE_SMALL_PICTURE = 1;
	protected int TAKE_SMALL_PICTURE = 2;
	protected int CHOOSE_MUSIC = 4;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private PopupWindow pop_theme = null;
	private LinearLayout ll_popup_theme;
	private static String filelocation;//temp
	static String fileName=null;																												// file
	private View parentView;
	static String imageurl=null;
	private static String musicString=null;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i("zai", "enene1");
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (msg.obj != null) {
					Log.i("zai", "enene");
					if (Integer.parseInt((String) msg.obj) == 1) {
						Log.i("zai", "enene3");
						Toast.makeText(getApplication(), "分享成功!", 1).show();
						fileName=null;
						musicString=null;
					} else {
						Log.i("zai", "enene4");
						Toast.makeText(getApplication(), "分享失败!", 1).show();
					}
				}
			}
		}
	};
	private String picPath;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.activity_savetrack, null);
		setContentView(parentView);
		Init();
		if (url.externalMemoryAvailable()) {
			 filelocation = Environment.getExternalStorageDirectory() + "/Myimage/";
		}else {
			 filelocation =  Environment.getDataDirectory() + "/Myimage/";
		}
		File file = new File(filelocation);
		if (!file.exists()) 	file.mkdirs();
		edit = (EditText) findViewById(R.id.edit);
		image = (RoundedImageView) findViewById(R.id.image);
		Button but = (Button) findViewById(R.id.baocun);
		music=(LinearLayout) findViewById(R.id.music);
		musicname = (TextView) findViewById(R.id.musicname);
		theme = (LinearLayout) findViewById(R.id.fwd_btn_theme);
		themename = (TextView) findViewById(R.id.theme_tv);
		InitTheme();
		but.setText("分享");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		trackbean = (com.whut.myMap.domain.trackbean) bundle.getSerializable("trackbean");
		but.setOnClickListener(new mybutclick());
		image.setOnClickListener(new selectPic());
		music.setOnClickListener(new mymusicclick());
		theme.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_popup_theme.startAnimation(AnimationUtils.loadAnimation(sharetrack.this, R.anim.activity_translate_in));
				pop_theme.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
	}
	public class mymusicclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent;
//			intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
//			intent.setType("audio/*"); //选择音频
//			intent.putExtra("return-data",false);
			startActivityForResult(intent, CHOOSE_MUSIC);
		}
	}
	public class mybutclick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			try {
				if (trackbean.getTrack().getTrack_id() != 0) {
					str = edit.getText().toString();
					Log.i("trackidsave", trackbean.getTrack().getTrack_id() + "");
				}
				new Thread(new Runnable() {
					String result;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							String imageurl=null;
							addtracknet addtracksnet = new addtracknet();
							trackbean.getTrack().setWords(str);
							trackbean.getTrack().setBgurl(picPath);
							trackbean.getTrack().setTheme(themetype);
//							trackbean.getTrack().setMusicstring(musicString);
							if(musicString!=null&&musicString.length()>0){
								trackbean.getTrack().setMusicstring(musicString);
								}else{
									trackbean.getTrack().setMusicstring(null);
								}
							System.out.println(trackbean.getTrack().getBgurl());
							result = addtracksnet.addtrackbean(trackbean, getApplication());
						} catch (Exception e) {
							// TODO: handle exception
							result = "2";
						} finally {
							Log.i("result", result.toString());
							Message message = new Message();
							message.what = SHOW_RESPONSE;
							message.obj = result.toString();
							handler.sendMessage(message);
						}
					}
				}).start();
				finish();
			} catch (Exception e) {
				Toast.makeText(getApplication(), "分享失败，请重试！",0);
			}
		}
	}
	public void Init() {
		pop = new PopupWindow(sharetrack.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup=(LinearLayout) view.findViewById(R.id.ll_popup);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setBackgroundDrawable(new BitmapDrawable());
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
		Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
		Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
		
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fileName = String.valueOf(System.currentTimeMillis());
				imageUri = Uri.fromFile(new File(filelocation, fileName + ".jpg"));
				Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// action
																				// is
																				// capture
				intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(intent1, TAKE_SMALL_PICTURE);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fileName = String.valueOf(System.currentTimeMillis());
				imageUri = Uri.fromFile(new File(filelocation, fileName + ".jpg"));
				Intent intent;
				intent = new Intent(Intent.ACTION_PICK,
				          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				intent.setType("image/*");
//				intent.putExtra("crop", "true");
//				intent.putExtra("aspectX", 3);
//				intent.putExtra("aspectY", 2);
//				intent.putExtra("outputX", 600);
//				intent.putExtra("outputY", 400);
//				intent.putExtra("scale", true);
//				intent.putExtra("return-data",true);
//				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//				intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//				intent.putExtra("noFaceDetection", true); // no face detection
				startActivityForResult(intent,CHOOSE_SMALL_PICTURE);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
	}
	public void InitTheme() {
		pop_theme = new PopupWindow(sharetrack.this);
		View view = getLayoutInflater().inflate(R.layout.choosetheme, null);
		ll_popup_theme = (LinearLayout) view.findViewById(R.id.ll_popup);
		pop_theme.setWidth(LayoutParams.MATCH_PARENT);
		pop_theme.setHeight(LayoutParams.WRAP_CONTENT);
		pop_theme.setBackgroundDrawable(new BitmapDrawable());
		pop_theme.setFocusable(true);
		pop_theme.setOutsideTouchable(true);
		pop_theme.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		ImageView r1 = (ImageView) view.findViewById(R.id.yellow);
		ImageView r2 = (ImageView) view.findViewById(R.id.pink);
		ImageView r3 = (ImageView) view.findViewById(R.id.blue);
		ImageView r4 = (ImageView) view.findViewById(R.id.green);
		SharedPreferences settings = getSharedPreferences("medal", 0);
		// 2、取出数据
		int medal = settings.getInt(url.SPUSER_MEDAL, 0);
		if (medal<=3) {
			r4.setVisibility(View.GONE);
		}
		if (medal<=2) {
			r3.setVisibility(View.GONE);
		}
		if (medal<=1) {
			r2.setVisibility(View.GONE);
		}
		
		parent.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				pop_theme.dismiss();
				ll_popup_theme.clearAnimation();
			}
		});
		r1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				themetype = 0;
				themename.setText("简约黄白");
				pop_theme.dismiss();
				ll_popup_theme.clearAnimation();
			}
		});
		r2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				themetype = 1;
				themename.setText("甜蜜粉红");
				pop_theme.dismiss();
				ll_popup_theme.clearAnimation();
			}
		});
		r3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				themetype = 3;
				themename.setText("清新淡蓝");
				pop_theme.dismiss();
				ll_popup_theme.clearAnimation();
			}
		});
		r4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				themetype = 2;
				themename.setText("古典墨绿");
				pop_theme.dismiss();
				ll_popup_theme.clearAnimation();
			}
		});

	}

	public class selectPic implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ll_popup.startAnimation(AnimationUtils.loadAnimation(sharetrack.this, R.anim.activity_translate_in));
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
		}
	}
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 2);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			switch (requestCode) {
			case 1:
				if(null!=data){
					System.out.println("data!=nul22l");
					Uri uri=data.getData();
					String[] pojo = {MediaStore.Images.Media.DATA};  
					CursorLoader cursorLoader = new CursorLoader(this, uri, pojo, null,null, null);
				    Cursor cursor = cursorLoader.loadInBackground(); 
			        if(cursor != null )  
			        {  
			        	cursor.moveToFirst();
			            int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);  
			            picPath = cursor.getString(columnIndex);  
			            System.out.println("picpathav sd v"+picPath);
			            cursor.close();  
			        }  
					Bitmap bitmap = decodeUriAsBitmap(uri);
					bitmap = ThumbnailUtils.extractThumbnail(bitmap, 600, 400);
					image.setImageBitmap(bitmap);
			}
					break;
			case 2:
				if(fileName!=null&&fileName.length()>0){
					picPath=filelocation+fileName+".jpg";
					}else{
						picPath=null;
					}
				cropImageUri(imageUri, 600, 400, CROP_SMALL_PICTURE);
				break;
			case 3:
				if (imageUri != null) {
						Bitmap bitmap = decodeUriAsBitmap(imageUri);
						bitmap = ThumbnailUtils.extractThumbnail(bitmap, 600, 400);
						image.setImageBitmap(bitmap);
					}
				break;
			case 4:
				if(null!=data){
					System.out.println("data!=nul22l");
					Uri uri=data.getData();
					Cursor cursor = getContentResolver().query(uri, null, null,null, null);  
				    cursor.moveToFirst();  
				    musicString= cursor.getString(1); // 文件路径  
				    String imgName = cursor.getString(2); // 文件名  
				    musicname.setText(imgName);
				    Log.e("uri", uri.toString()+" "+imgName+" "+musicString);  
				}
				break;
				}
			}
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		mActionBar = getActionBar();
		mActionBar.setTitle(R.string.sharetrack);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	}

