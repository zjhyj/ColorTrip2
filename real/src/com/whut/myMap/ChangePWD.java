package com.whut.myMap;

import java.util.HashMap;
import java.util.Map;

import com.whut.myMap.utils.VerificationCode;
import com.whut.net.updatepasswordnet;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePWD extends Activity {
	private EditText vc_old;
	private EditText vc_new;
	private EditText vc_new2;
	private Button vc_change;
	protected final int SHOW_RESPONSE = 0;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (msg.obj != null) {
					Log.i("zai", "enene");
					if (Integer.parseInt((String) msg.obj) == 1) {
						Log.i("zai", "enene3");
						gohead();
					}else if (Integer.parseInt((String) msg.obj) == 0) {
						Toast.makeText(getApplication(), "原密码错误,请重新尝试!", 0).show();
					} 
					else {
						Log.i("zai", "enene4");
						Log.i("result", msg.obj.toString());
						Toast.makeText(getApplication(), "修改失败,请重新尝试!", 0).show();
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
		setContentView(R.layout.change_pwd);
		getActionBar().setTitle("修改密码");
		vc_change = (Button) findViewById(R.id.btn_confirm);
		vc_new = (EditText) findViewById(R.id.et_new);
		vc_new2 = (EditText) findViewById(R.id.et_new2);
		vc_old = (EditText) findViewById(R.id.et_old);

		vc_change.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				register();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.changepwd);
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

	public void register() {
		final String old = vc_old.getText().toString();
		System.out.println(old+"old");
		final String password = vc_new.getText().toString();
		String password2 = vc_new2.getText().toString();

		if (TextUtils.isEmpty(password2) || TextUtils.isEmpty(password) || TextUtils.isEmpty(old)) {
			// 弹出吐司
			Toast.makeText(this, "请正确输入", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!password.equals(password2)) {
			Toast.makeText(this, "新密码两次输入不一致", 0).show();
			return;
		}
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Map<String, String> param = new HashMap<String, String>();
					param.put("oldpassword", old + "");
					param.put("password", password + "");
					updatepasswordnet updatepasswordnet = new updatepasswordnet();
					result = updatepasswordnet.changepassword(param);
				} catch (Exception e) {
					// TODO: handle exception
					result = "2";
				} finally {
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	public void gohead() {
		Toast.makeText(this, "修改成功！", 0).show();
		finish();
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}
