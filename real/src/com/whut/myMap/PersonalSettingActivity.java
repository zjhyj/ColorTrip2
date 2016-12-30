package com.whut.myMap;

import com.google.gson.Gson;
import com.whut.myMap.domain.personal_message;
import com.whut.myMap.entites.user;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.whut.myMap.R;
import com.whut.net.loginnet;
import com.whut.net.updateheadnet;
import com.whut.net.updateusernet;

public class PersonalSettingActivity extends Activity {
	static private int openfileDialogId = 0;
	private user pMessage;
	private Button postButton;
	private EditText email;
	private EditText username;
	private static final String[] sex = { "男", "女", "不填" };
	private Spinner spinner_sex;
	private ArrayAdapter<String> adapter;
	private String spinner_choose;
	private String filepath;
	private EditText age;
	private ActionBar mActionBar;
	private EditText introdce;
	private final int SHOW_RESPONSE = 0;
	private static String path;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (msg.obj != null) {
					Log.i("zai", "enene");
					if (Integer.parseInt((String) msg.obj) == 1) {
						Log.i("zai", "enene3");
						Toast.makeText(getApplication(), "修改成功！", 1).show();
					} else {
						Log.i("zai", "enene4");
						Toast.makeText(getApplication(), "修改失败！", 1).show();
						return;
					}
				}
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personal_setting);
		pMessage = new user();
		user user = (com.whut.myMap.entites.user) getIntent().getSerializableExtra("user");
		postButton = (Button) findViewById(R.id.aps_post);
		email = (EditText) findViewById(R.id.aps_email);
		username = (EditText) findViewById(R.id.aps_name);
		age = (EditText) findViewById(R.id.aps_age);
		introdce = (EditText) findViewById(R.id.aps_introduce);
		if (user != null) {
			username.setText(user.getUsername());
			email.setText(user.getEmial());
			age.setText(user.getAge() + "");
			introdce.setText(user.getPersonal());
		}
		postButton.setOnClickListener(new OnClickListener() {
			/**
			 * 用户邮箱 昵称 性别 头像 年龄
			 * 
			 */
			@Override
			public void onClick(View v) {
				if(username.getText().toString()!=null&&username.getText().toString()!=""){
				pMessage.setEmial(email.getText().toString());
				pMessage.setGender(spinner_choose);
				pMessage.setUsername(username.getText().toString());
				pMessage.setAge(Integer.parseInt(age.getText().toString()));
				pMessage.setPersonal(introdce.getText().toString());
				new Thread(new Runnable() {
					String result;

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							updateusernet updateusernet = new updateusernet();
							result = updateusernet.updateuser(pMessage);
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
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", pMessage);
				intent.putExtras(bundle);
				setResult(Activity.RESULT_OK, intent);
				finish();
				// Toast.makeText(getApplication(), gString, 1).show();
			}else {
				Toast.makeText(getApplication(), "请输入昵称", 0);
			}
			}
		});
		spinner_sex = (Spinner) findViewById(R.id.aps_sex);
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sex);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner_sex.setAdapter(adapter);
		if (user.getGender() == null || user.getGender().equals("男")) {
			spinner_sex.setSelection(0);
		} else {
			spinner_sex.setSelection(1);
		}
		// 使用数组形式操作
		class SpinnerSelectedListener implements OnItemSelectedListener {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				spinner_choose = (String) spinner_sex.getSelectedItem();
				if (spinner_choose.equals("不填")) {
					spinner_choose = "";
				}
			}

			public void onNothingSelected(AdapterView<?> arg0) {
			}
		}
		// 添加事件Spinner事件监听
		spinner_sex.setOnItemSelectedListener(new SpinnerSelectedListener());
		// 设置默认值
		spinner_sex.setVisibility(View.VISIBLE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		mActionBar = getActionBar();
		mActionBar.setTitle("信息修改");
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
