package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.adapter.ScollListView;
import com.whut.myMap.domain.TestRoute;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.getusernet;
import com.whut.net.loginnet;
import com.whut.net.updateheadnet;
import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.renderscript.Mesh;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * List<String> 
 * "�˺�", "�ǳ�", "�Ա�", "����", "qq��"
 * @author xxr
 *
 */
public class UserInfo extends Activity {
	private ActionBar mActionBar;
    private ImageView head;
    private TextView name;
    private TextView name1;
    private TextView gender;
    private TextView age;
    private TextView id;
    private TextView email;
    private TextView personal;
    private TextView personal1;
    private static Uri imageUri;
	protected static String jsonstring;
	
    private final int SHOW_RESPONSE=0;
    protected final int UPDATE_HEAD=4;    
	protected int CHOOSE_SMALL_PICTURE=1;
	protected int TAKE_SMALL_PICTURE=2;
	protected int CROP_SMALL_PICTURE=3;
	private String picPath;
	private static  String filelocation = url.SDPATH;//temp file	
    private static com.whut.myMap.entites.user user;
	private PopupWindow pop = null;
	private LinearLayout ll_popup;
	private View parentView;
	static String fileName=null;	
    private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case  UPDATE_HEAD:
				if(Integer.parseInt((String) msg.obj)==1){
					Log.i("zai", "enene");
						Log.i("zai", "enene3");
						Toast.makeText(getApplication(), "���ĳɹ���", 1).show();	
					}else{					
						Toast.makeText(getApplication(), "����ʧ�ܣ������³��ԣ�", 1).show();	
						return;
					}
				break;
				}
		}	
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.userinfo, null);
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
		head=(ImageView) findViewById(R.id.fs_head);
		name=(TextView) findViewById(R.id.fs_username);
		name1=(TextView) findViewById(R.id.name);
		gender=(TextView) findViewById(R.id.gender);
		age=(TextView) findViewById(R.id.age);
		email=(TextView) findViewById(R.id.email);
		personal=(TextView) findViewById(R.id.fs_personal);
		personal1=(TextView) findViewById(R.id.personal);
		id=(TextView) findViewById(R.id.id);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		user=(com.whut.myMap.entites.user) bundle.getSerializable("user");
		show(user);
		head.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Log.i("ddddddd", "----------");
				ll_popup.startAnimation(
						AnimationUtils.loadAnimation(UserInfo.this, R.anim.activity_translate_in));
				pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
		
	}
	
	public void Init() {

		pop = new PopupWindow(UserInfo.this);

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
				startActivityForResult(intent1, TAKE_SMALL_PICTURE);
				pop.dismiss();
				ll_popup.clearAnimation();
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fileName = String.valueOf(System.currentTimeMillis());
				imageUri = Uri.fromFile(new File(filelocation, fileName + ".jpg"));
				Intent intent = new Intent(Intent.ACTION_PICK,
				          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
	            	intent.setType("image/*");
	            	intent.putExtra("crop", "true");
	            	intent.putExtra("aspectX", 2);
	            	intent.putExtra("aspectY", 2);
	            	intent.putExtra("outputX", 600);
	            	intent.putExtra("outputY", 600);
	            	intent.putExtra("scale", true);
	            	intent.putExtra("return-data", false);
	            	intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	            	intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
	            	intent.putExtra("noFaceDetection", true); // no face detection
	            	startActivityForResult(intent, CROP_SMALL_PICTURE);
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
	public void show(user user){
		try{
			SharedPreferences userPreferences = getSharedPreferences("userInfo", 0);
			String image=userPreferences.getString(url.SPUSER_IMAGE, "");
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.drawable.default_head) // ��������ʾ��Ĭ��ͼƬ
			.showImageOnFail(R.drawable.default_head) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
			.cacheInMemory(true) // �ڴ滺��
			.bitmapConfig(Config.RGB_565)// �����������
			.build();
	         ImageLoader.getInstance().displayImage(image,
			head, options);
			}catch (Exception e){
				DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.default_head)
				.cacheInMemory(true) // �ڴ滺��
			.bitmapConfig(Config.RGB_565)// �����������
			.build();
				ImageLoader.getInstance().displayImage(null,
						head, options);
			}
			try {
				name.setText(user.getUsername());
				name1.setText(user.getUsername());
				id.setText(user.getPhone());
				gender.setText(user.getGender());
				age.setText(user.getAge()+"");
				email.setText(user.getEmial());
				personal.setText(user.getPersonal());
				personal1.setText(user.getPersonal());
			} catch (Exception e) {
				name.setText("");
				name1.setText("");
				id.setText("");
				gender.setText("");
				age.setText("");
				email.setText("");
				personal.setText("");
				personal1.setText("");
			}
	}

         

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		}
    else {	
    switch(requestCode){
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
			bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
			head.setImageBitmap(bitmap);
	}
        break; 
    case 2:	
    	if(fileName!=null&&fileName.length()>0){
			picPath=filelocation+fileName+".jpg";
			}else{
				picPath=null;
			}
		cropImageUri(imageUri, 300, 300, CROP_SMALL_PICTURE);
		
    	break;
    case 3:
    	 if(imageUri != null){
    		 Bitmap bitmap = decodeUriAsBitmap(imageUri);
				bitmap = ThumbnailUtils.extractThumbnail(bitmap, 300, 300);
    	        head.setImageBitmap(bitmap);
    	        Intent intent=new Intent();
    	        intent.putExtra("isupdate", true);
    	        intent.putExtra("imgurl", imageUri.toString());
    	        setResult(RESULT_OK, intent);
    	        }
    	 new Thread(new Runnable() {		
    		 String result="0";
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					updateheadnet updateheadnet=new updateheadnet();
					result=updateheadnet.updatehead(new File(new URI(imageUri.toString())));
					System.out.println(result+"bef");
//					1����Preferences������Ϊsetting�������������������򴴽��µ�Preferences
					SharedPreferences userPreferences = getSharedPreferences("userInfo", 0);
					int user_id=userPreferences.getInt(url.SPUSER_ID, 0);
					String username=userPreferences.getString(url.SPUSER_NAME, "");
//					2����setting���ڱ༭״̬
					SharedPreferences.Editor editor = userPreferences.edit();
//					3���������

					editor.putInt(url.SPUSER_ID,user_id);
					editor.putString(url.SPUSER_NAME,username);
					editor.putString(url.SPUSER_IMAGE,imageUri.toString());
//					4������ύ
					editor.commit();
					if(result==null){
						result=1+"";
					}	
					}catch (Exception e) {
						// TODO: handle exception
						result=2+"";	
					}finally{
						Log.i("result",result.toString());
						Message message=new Message();
						message.what=UPDATE_HEAD;
						message.obj=result.toString();		
						handler.sendMessage(message);	
							}		
			}
		}).start();      
    	 break;
    	 case 4:
    		 user u=(com.whut.myMap.entites.user) data.getSerializableExtra("user");
    		 name.setText(user.getUsername());
				name1.setText(user.getUsername());
				id.setText(user.getPhone());
				gender.setText(user.getGender());
				age.setText(user.getAge()+"");
				email.setText(user.getEmial());
				personal.setText(user.getPersonal());
				personal1.setText(user.getPersonal());
    			break;
    default  :
    	break;
    	
    }
		}	
    };
    private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 2);
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
    private Bitmap decodeUriAsBitmap(Uri uri){
        Bitmap bitmap = null;
        try {
        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
            } catch (FileNotFoundException e) {
        e.printStackTrace();
            return null;
            }
        return bitmap;
        } 
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.user_info_menu, menu);
		mActionBar = getActionBar();
		mActionBar.setTitle(R.string.userinfo);
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
		case R.id.reset:
			Intent intent=new Intent(UserInfo.this, PersonalSettingActivity.class);	
			Bundle bundle=new Bundle();
			bundle.putSerializable("user", user);
			intent.putExtras(bundle);
			startActivityForResult(intent,4);
			showNext();
		default:
			return super.onOptionsItemSelected(item);
		}

	}	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("������back��   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	
	public void showNext() {
		// TODO Auto-generated method stub
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	
//	public class UserInfoAdapter extends BaseAdapter {
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return at.length;
//		}
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return null;
//		}
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			ViewHolder holder = null;
//			if (convertView == null) {
//				holder = new ViewHolder();
//				LayoutInflater mInflater = LayoutInflater.from(UserInfo.this);
//				convertView = mInflater.inflate(R.layout.user_info_item, null);
//				holder.attribute = (TextView) convertView
//						.findViewById(R.id.attribute);
//				holder.specific = (TextView) convertView
//						.findViewById(R.id.specific);
//				convertView.setTag(holder);
//			} else {
//				holder = (ViewHolder) convertView.getTag();
//			}
//
//			if (holder.attribute != null) {
//				holder.attribute.setText(at[position]);
//			}
//			if (holder.specific != null) {
//				holder.specific.setText(sp.get(position));
//			}
//			return convertView;
//		}
//	}
//	public final class ViewHolder {
//		public TextView attribute;
//		public TextView specific;
//	}
}