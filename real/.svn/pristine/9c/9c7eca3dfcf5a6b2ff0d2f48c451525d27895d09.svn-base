package com.whut.myMap;

import java.util.HashMap;
import java.util.Map;

import com.whut.myMap.utils.VerificationCode;
import com.whut.net.loginnet;
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

public class ResetPassword extends Activity {
	private EditText vc_phonenum;
	private EditText vc_pass;
	private EditText vc_pass2;
	private EditText vc;
	private Button vc_change;
	private Button vc_register;
	private ImageView vc_img;
	private String code;
	protected final int SHOW_RESPONSE=0;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if(msg.obj!=null){
					Log.i("zai", "enene");
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene3");
						gohead();
						}
					else {
						Log.i("zai", "enene4");
						Log.i("result", msg.obj.toString());
						Toast.makeText(getApplication(), "修改失败,请重新尝试!", 1).show();
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
		setContentView(R.layout.reset_pass);
		getActionBar().setTitle("修改密码");
		vc_img = (ImageView) findViewById(R.id.iv_reg_vc);
		vc_change = (Button) findViewById(R.id.btn_reg_change);
		vc_register = (Button) findViewById(R.id.btn_reg);
		vc_pass = (EditText) findViewById(R.id.et_reg_password);
		vc_pass2 = (EditText) findViewById(R.id.et_reg_password2);
		vc = (EditText) findViewById(R.id.et_reg_vc);
		vc_phonenum=(EditText) findViewById(R.id.et_reg_phone);
		vc_img.setImageBitmap(VerificationCode.getInstance().createBitmap());

		System.out.println("验证码：" + VerificationCode.getInstance().getCode());
		
		code = VerificationCode.getInstance().getCode();
		vc_change.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {

				vc_img.setImageBitmap(VerificationCode.getInstance()
						.createBitmap());
				System.out.println("验证码："
						+ VerificationCode.getInstance().getCode());
				code = VerificationCode.getInstance().getCode();
			}
		});

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
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
			this.overridePendingTransition(R.anim.tran_pre_in,
					R.anim.tran_pre_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	public void register(View view) {
		final String number=vc_phonenum.getText().toString();
		final String password = vc_pass.getText().toString();
		String password2 = vc_pass2.getText().toString();
		String vcode=vc.getText().toString().toUpperCase();
		
		if (TextUtils.isEmpty(password2) || TextUtils.isEmpty(password)||TextUtils.isEmpty(vcode)||TextUtils.isEmpty(number)) {
			// 弹出吐司
			Toast.makeText(this, "请正确输入", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (!password.equals(password2)  ) {
			Toast.makeText(this, "两次密码不一致", 0).show();
			return;
		} 
	if (!(code.toUpperCase()).equals(vcode)) {
			Toast.makeText(this, "验证码错误", 0).show();
			return;
		}
	new Thread(new Runnable() {	
		String result ;
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Map<String, String> param=new HashMap<String, String>();
				param.put("username", number+"");
				param.put("password", password+"");
	            updatepasswordnet updatepasswordnet=new updatepasswordnet();
	            result=updatepasswordnet.updatepassword(param);
				} catch (Exception e) {
				// TODO: handle exception
				result="2";
			}finally{
		Message message=new Message();
		message.what=SHOW_RESPONSE;
		message.obj=result.toString();		
		handler.sendMessage(message);	
			}					
		}
	}).start();
		}
	public void gohead() {
		Toast.makeText(this, "修改成功,请重新登陆！", 0).show();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
		finish();
	}		
}
