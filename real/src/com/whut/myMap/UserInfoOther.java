package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.getusernet;
import com.whut.net.updateheadnet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserInfoOther extends Activity{
	protected static final int UPDATE_DATA = 0;
	private ActionBar mActionBar;
    private ImageView head;
    private TextView name;
    private TextView name1;
    private TextView gender;
    private TextView age;
    private TextView email;
    private TextView personal;
    private TextView personal1;
	protected static String jsonstring;
    private static user user;
	private View parentView;
    private int userid;
    private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case  UPDATE_DATA:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					user = gson.getobject(jsonstring, user.class);
					show(user);
				} else {
					Toast.makeText(UserInfoOther.this, "��ȡ����ʧ�ܣ������³��ԣ�", 1).show();
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
		Intent intent=getIntent();
		userid=intent.getIntExtra("userid", 0);
		head=(ImageView) findViewById(R.id.fs_head);
		name=(TextView) findViewById(R.id.fs_username);
		name1=(TextView) findViewById(R.id.name);
		gender=(TextView) findViewById(R.id.gender);
		age=(TextView) findViewById(R.id.age);
		email=(TextView) findViewById(R.id.email);
		personal=(TextView) findViewById(R.id.fs_personal);
		personal1=(TextView) findViewById(R.id.personal);
		RelativeLayout rl=(RelativeLayout) findViewById(R.id.zhanghaorl);
		rl.setVisibility(View.GONE);
		getData();
	}	
	private void getData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Map<String, String> param = new HashMap<String, String>();
					param.put("userid", userid+"");
					getusernet getusernet = new getusernet();
					String jsonstring1 = getusernet.getuserother(param);
					if (jsonstring1 != null) {
						Log.i("resultjson", jsonstring1);
						jsonstring = jsonstring1;
						result = 1 + "";
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = null;
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					message.what = UPDATE_DATA;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}


	public void show(user user){
		try{
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.drawable.default_head) // ��������ʾ��Ĭ��ͼƬ
			.showImageOnFail(R.drawable.default_head) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
			.cacheInMemory(true) // �ڴ滺��
			.bitmapConfig(Config.RGB_565)// �����������
			.build();
	         ImageLoader.getInstance().displayImage(user.getUserimage(),
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
				gender.setText(user.getGender());
				age.setText(user.getAge()+"");
				email.setText(user.getEmial());
				personal.setText(user.getPersonal());
				personal1.setText(user.getPersonal());
			} catch (Exception e) {
				name.setText("");
				name1.setText("");
				gender.setText("");
				age.setText("");
				email.setText("");
				personal.setText("");
				personal1.setText("");
			}
	}

         


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		mActionBar = getActionBar();
		mActionBar.setTitle(R.string.userinfo2);
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



}
