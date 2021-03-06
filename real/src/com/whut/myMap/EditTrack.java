package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;

import com.makeramen.RoundedImageView;
import com.whut.myMap.Savetrack.mybutclick;
import com.whut.myMap.Savetrack.selectPic;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.trackservice;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditTrack extends Activity{
	private ActionBar mActionBar;
	private trackbean track;
	private static Uri imageUri;
	protected final int SHOW_RESPONSE = 0;
	protected int CROP_SMALL_PICTURE = 3;
	protected int CHOOSE_SMALL_PICTURE = 1;
	protected int TAKE_SMALL_PICTURE = 2;
	private PopupWindow pop = null;
	private PopupWindow deletepop=null;
	private LinearLayout ll_popup;
	private LinearLayout ll_popup2;
	private static String filelocation;// temp
	private static String fileName; // file
	private View parentView;
	private String picPath;
	private EditText name;
	private RoundedImageView image;
	private LinearLayout delete;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.edittrack, null);
		setContentView(parentView);
		Init();
		initDelete();
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		track= (trackbean) bundle.getSerializable("speak_item");
		
		if (url.externalMemoryAvailable()) {
			 filelocation = Environment.getExternalStorageDirectory() + "/Myimage/";
		}else {
			 filelocation =  Environment.getDataDirectory() + "/Myimage/";
		}
		File file = new File(filelocation);
		if (!file.exists())
			file.mkdirs();
		delete=(LinearLayout) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_popup2.startAnimation(AnimationUtils.loadAnimation(EditTrack.this, R.anim.activity_translate_in));
				deletepop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			}
		});
		name = (EditText) findViewById(R.id.name);
		name.setText(track.getTrack().getWords());
		image = (RoundedImageView) findViewById(R.id.image);
		Bitmap bmpDefaultPic=BitmapFactory.decodeFile(track.getTrack().getBgurl(),null);
		if (bmpDefaultPic!=null) {
			image.setImageBitmap(bmpDefaultPic);
		} 
		image.setOnClickListener(new selectPic());
	}
	public void initDelete(){
		deletepop = new PopupWindow(EditTrack.this);
		View view = getLayoutInflater().inflate(R.layout.pop_delete_track, null);
		ll_popup2 = (LinearLayout) view.findViewById(R.id.ll_popup);
		deletepop.setWidth(LayoutParams.MATCH_PARENT);
		deletepop.setHeight(LayoutParams.WRAP_CONTENT);
		deletepop.setBackgroundDrawable(new BitmapDrawable());
		deletepop.setFocusable(true);
		deletepop.setOutsideTouchable(true);
		deletepop.setContentView(view);
		RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
		Button bt1 = (Button) view.findViewById(R.id.del);
		Button bt2 = (Button) view.findViewById(R.id.cancel);
		parent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deletepop.dismiss();
				ll_popup2.clearAnimation();
			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 trackservice service=new trackservice(EditTrack.this);
					service.deletetrack(EditTrack.this,track.getTrack().getTrack_id());
					deletepop.dismiss();
					ll_popup2.clearAnimation();
					Intent intent2 = new Intent();
					intent2.putExtra("delete", 1);
					setResult(Activity.RESULT_OK, intent2);
					finish();
					overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			}
		});
		bt2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deletepop.dismiss();
				ll_popup2.clearAnimation();
			}
		});
	}
	public void Init() {
		pop = new PopupWindow(EditTrack.this);
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
//				intent.putExtra("return-data", false);
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
	public class selectPic implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ll_popup.startAnimation(AnimationUtils.loadAnimation(EditTrack.this, R.anim.activity_translate_in));
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
				}
			}
		}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.doodle, menu);
		mActionBar = getActionBar();
		mActionBar.setTitle("");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		showPre();
	}
	public void showPre() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			return true;
		case R.id.save:
//          setResult(RESULT_OK, intent);  
			try {
				trackservice service = new trackservice(getApplication());
				String word=name.getText().toString();
				service.updatetrackwords(word, track.getTrack().getTrack_id(), getApplication());
				if (picPath!=null) {
					service.updatetrackimage(picPath, track.getTrack().getTrack_id(), getApplication());
				}
				Toast.makeText(getApplication(), "保存成功！", Toast.LENGTH_LONG).show();
				fileName=null;
				Intent intent=new Intent();
				intent.putExtra("name", word);
				intent.putExtra("image", picPath);
				setResult(RESULT_OK, intent);
				finish();
				 showPre();
			} catch (Exception e) {
				Toast.makeText(getApplication(), "保存失败，请重试！", Toast.LENGTH_LONG);
			}
          return true; 
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
