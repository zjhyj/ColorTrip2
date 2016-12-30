package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.navisdk.model.params.MsgDefine;
import com.whut.myMap.domain.ImageItem;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.layout.RecycledImageview;
import com.whut.myMap.serverce.Redservice;
import com.whut.myMap.utils.Bimp;
import com.whut.myMap.utils.PublicWay;
import com.whut.myMap.utils.Res;
import com.whut.net.addredsnet;
import com.whut.net.loginnet;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;
import uk.co.senab.photoview.PhotoView;
import android.widget.AdapterView.OnItemClickListener;

import com.whut.myMap.R;
public class TakePhoto extends Activity {
	private EditText content;
//	private Button post;
//	private Button save;
	private  String path = null;
    public static final int SHOW_RESPONSE=0;
    public static final int UPDATE_GRIDVIEW=1;
	static private List<String> paths;
    private int track_id;
    private Intent intent;
	private static  double x;
	private static  double y;
	public LocationClient locationClient = null; 
	private GridView noScrollgridview;
	private GridAdapter adapter;
	private View parentView;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	public static Bitmap bimap;
	private static String filelocation = url.SDPATH;// temp
	private static final int TAKE_PICTURE = 0x000001;
	private static final int CHOOSE_PICTURE = 0x000002;
	protected static final int DOODLE = 3;
	private static Uri imageUri;
	String fileName;
	private String picPath;
	BDLocationListener mylistener=new BDLocationListener() {
		public void onReceiveLocation(BDLocation  location) {
			// TODO Auto-generated method stub
			 if (location == null) {  
	                return;  
	            }  
			 x= location.getLatitude();//weidu
			 y= location.getLongitude();//jingdu
			Log.e("shuju", x+"  "+y);
		}};
		private Handler handler=new Handler(){
			 public void handleMessage(Message msg) {
				 Log.i("zai", "enene1");
				switch (msg.what){
				case SHOW_RESPONSE:
					Log.i("zai", "enene2");
					if(msg.obj!=null){
						Log.i("zai", "enene");
						if(Integer.parseInt((String) msg.obj)==1){
							Log.i("zai", "enene3");
							Toast.makeText(getApplication(), "分享成功!", 1).show();}
						else {
							Log.i("zai", "enene4");
							Toast.makeText(getApplication(), "网络异常，请检查网络!", 1).show();
						}
					}
					break;
				case UPDATE_GRIDVIEW:
					adapter.notifyDataSetChanged();
					break;
				}	
			}
		};			
	@SuppressLint("InflateParams")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.take_photo, null);
		Res.init(this);
		bimap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused);
		PublicWay.activityList.add(this);
		setContentView(parentView);
		Init();
		if (url.externalMemoryAvailable()) {
			 filelocation = Environment.getExternalStorageDirectory() + "/Myimage/";
		}else {
			 filelocation =  Environment.getDataDirectory() + "/Myimage/";
		}
		File file=new File(filelocation);
       if(!file.exists())
        file.mkdirs();
		intent=getIntent();
		track_id=intent.getIntExtra("trackid", 0);
		x=intent.getDoubleExtra("lat",0.0);
		y=intent.getDoubleExtra("lon", 0.0);
		Log.i("ttrackid", track_id+"");
		paths=new ArrayList<String>();
        if(x==0){
        LocationClientOption option=new LocationClientOption();
		locationClient=new LocationClient(this);
		locationClient.registerLocationListener(mylistener);	
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式  
	    option.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02  
	    locationClient.setLocOption(option);
		locationClient.start(); // 开始定位	
		locationClient.requestLocation();
        }
		content=(EditText) findViewById(R.id.edittext);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.takephoto_menu, menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setTitle(R.string.write_dongtai);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in,
					R.anim.tran_pre_out);
			return true;
		case R.id.post:
			if(track_id==0){
			 final String ediString=content.getText().toString();
			for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
					paths.add(Bimp.tempSelectBitmap.get(i).getImagePath());
				}
			 final List<picture> listpicture=new ArrayList<picture>();
			for(String url:paths){
				listpicture.add(new picture(0,0,url));
			}	
			Date date=new Date(); 
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			final String str=format.format(date);
			new Thread(new Runnable() {
				String result;
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Log.e("shuju1", x+"  "+y);
			addredsnet addredsnet=new addredsnet();
		   result=addredsnet.addreds(new redsbean(new reds(0,0,ediString,x,y,0,str,track_id,0,0),listpicture));														
		   Log.e("shuju2", x+"  "+y);	
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
				paths.clear();
			
				finish();
			} else {
				Toast.makeText(getApplication(), "正在记录轨迹，请将坐标保存到本地", 0);
			}
		    break;
		case R.id.save:
			String edi=content.getText().toString();
			for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
				paths.add(Bimp.tempSelectBitmap.get(i).getImagePath());
			}
		 final List<picture> listpicture=new ArrayList<picture>();
		for(String url:paths){
			listpicture.add(new picture(0,0,url));
		}	
			Date date1=new Date(); 
			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str1=format1.format(date1);
			Log.e("shuju", x+"  "+y);
			Redservice redsserverce=new Redservice(getApplication());
			redsserverce.addreds(new redsbean(new reds(0,0,edi,x,y,0,str1,track_id,0,0),listpicture), getApplication());	
			paths=null;
			x=0;
		    y=0;
		    finish();
		   break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return false;

	}
	
	public void Init() {
		pop = new PopupWindow(TakePhoto.this);
		View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
		ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
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
				startActivityForResult(intent1, TAKE_PICTURE);
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
				startActivityForResult(intent,CHOOSE_PICTURE);
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
		
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.tempSelectBitmap.size()) {
					Log.i("ddddddd", "----------");
					ll_popup.startAnimation(
							AnimationUtils.loadAnimation(TakePhoto.this, R.anim.activity_translate_in));
					pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
				} else {
					Intent intent = new Intent(TakePhoto.this, GalleryActivity.class);
					intent.putExtra("position", "1");
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		private int selectedPosition = -1;
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.tempSelectBitmap.size() == 9) {
				return 9;
			}
			return (Bimp.tempSelectBitmap.size() + 1);
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (RecycledImageview) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.tempSelectBitmap.size()) {
				holder.image
						.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				if (position == 9) {
					holder.image.setVisibility(View.GONE);
				}
			} else {
				holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
			}

			return convertView;
		}

		public class ViewHolder {
			public RecycledImageview image;
		}

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.tempSelectBitmap.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							Bimp.max += 1;
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
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


	public class selectPic implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ll_popup.startAnimation(AnimationUtils.loadAnimation(TakePhoto.this, R.anim.activity_translate_in));
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
		}
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				try {
					if(fileName!=null&&fileName.length()>0){
						picPath=filelocation+fileName+".jpg";
						}else{
							picPath=null;
						}
					Bitmap camorabitmap = BitmapFactory.decodeFile(picPath);
					if (null != camorabitmap) {
						Bitmap bm = ThumbnailUtils.extractThumbnail(camorabitmap, 150, 150);
						ImageItem takePhoto = new ImageItem();
						takePhoto.setImagePath(picPath);
						takePhoto.setBitmap(bm);
						Bimp.tempSelectBitmap.add(takePhoto);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case CHOOSE_PICTURE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				Bitmap image = null;
				if (null != data) {
					// 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
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
							image = ThumbnailUtils.extractThumbnail(bitmap, 150, 150);
							if (image != null) {
								ImageItem takePhoto = new ImageItem();
								takePhoto.setImagePath(picPath);
								takePhoto.setBitmap(image);
								Bimp.tempSelectBitmap.add(takePhoto);
							}
				}
			}
			
			break;
		case DOODLE:
			if (Bimp.tempSelectBitmap.size() < 9 && resultCode == RESULT_OK) {
				try {
					String path=data.getStringExtra("path");
					Bitmap camorabitmap = BitmapFactory.decodeFile(path);
					if (null != camorabitmap) {
							Bitmap bm = ThumbnailUtils.extractThumbnail(camorabitmap, 150, 150);
						ImageItem takePhoto = new ImageItem();
						takePhoto.setImagePath(path);
						takePhoto.setBitmap(bm);
						Bimp.tempSelectBitmap.add(takePhoto);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 Bimp.tempSelectBitmap.clear();
			Bimp.max = 0;
			Intent intent = new Intent("data.broadcast.action");  
         sendBroadcast(intent); 
	}

	
}
