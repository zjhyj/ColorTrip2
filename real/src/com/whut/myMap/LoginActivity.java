package com.whut.myMap;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.navisdk.model.params.MsgDefine;
import com.igexin.sdk.PushManager;
import com.whut.myMap.R;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import com.whut.myMap.utils.LoginUtils;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.addredsnet;
import com.whut.net.loginnet;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.opengl.ETC1;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private static final String TAG = "LoginActivity";
	private EditText etNumber;
	private EditText etPassword;
	private CheckBox cbRemerberPWD;
	private TextView etForget;
	String jsonString;
	private final int SHOW_RESPONSE=0;
	private ProgressDialog pd;
	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if(msg.obj!=null){
					Log.i("zai", "enene");
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene3");
						gsonutil gson=new gsonutil();
						 user temp=(user) gson.getobject(jsonString, user.class);
						 saveUserInfo(temp);
						 pd.dismiss();
						 
						 
						checkAndLog();
						}
					else if(Integer.parseInt((String) msg.obj)==0){
						Log.i("zai", "enene4");
						pd.dismiss();
						Toast.makeText(getApplication(), "���벻�ԣ���¼ʧ�ܣ�", 1).show();
						return;
					}else{
						pd.dismiss();
						Toast.makeText(getApplication(), "�����쳣���������磡��", 1).show();
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		PushManager.getInstance().initialize(getApplicationContext());
		etNumber = (EditText) findViewById(R.id.la_et_name);
		etPassword = (EditText) findViewById(R.id.la_et_password);
		cbRemerberPWD = (CheckBox) findViewById(R.id.la_cb_remerber_pwd);
		etForget=(TextView) findViewById(R.id.la_btn_forget);
		etForget.setOnClickListener(new OnClickListener() {	
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,ResetPassword.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		Map<String, String> userInfoMap = LoginUtils.getUserInfo(this);
		SharedPreferences sPreferences=this.getSharedPreferences("log", MODE_PRIVATE);
		Boolean check=sPreferences.getBoolean("check",false);
		if (check==true) {
			cbRemerberPWD.setChecked(true);
			etNumber.setText(userInfoMap.get("number"));
			etPassword.setText(userInfoMap.get("password"));
		}
	}
	protected void saveUserInfo(user temp) {
		// TODO Auto-generated method stub
//		1����Preferences������Ϊsetting�������������������򴴽��µ�Preferences
		SharedPreferences userPreferences = getSharedPreferences("userInfo", 0);
//		2����setting���ڱ༭״̬
		SharedPreferences.Editor editor = userPreferences.edit();
//		3���������
		try {
			editor.putInt(url.SPUSER_ID,temp.getUser_id());
			editor.putString(url.SPUSER_NAME,temp.getUsername());
			editor.putString(url.SPUSER_IMAGE,temp.getUserimage());
			editor.putString(url.SPUSER_PERSONAL, temp.getPersonal());
			editor.putString(url.SPUSER_CLIENTID, temp.getClientid());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
//		4������ύ
		editor.commit();
		 userPreferences = getSharedPreferences("clientid", 0);
		 editor = userPreferences.edit();
		try {
			editor.putString(url.SPUSER_CLIENTID,temp.getClientid());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		editor.commit();
			}
	public void la_btn_login_click(View v) {
		// ִ�е�¼�Ĳ���
		pd=new ProgressDialog(this);
		pd. setProgressStyle(ProgressDialog.STYLE_SPINNER);
		 pd.setTitle("��¼��");
         //����ProgressDialog ����
         pd.setMessage("���ڵ�¼�����Ե�");
         pd.show();
         
		// 1. ȡ�����������
		final String number = etNumber.getText().toString().trim();
		final String password = etPassword.getText().toString().trim();

		if (TextUtils.isEmpty(number) || TextUtils.isEmpty(password)) {
			// ������˾
			Toast.makeText(this, "����ȷ����", Toast.LENGTH_SHORT).show();
			pd.dismiss();
			return;
		}
		// 2. �жϼ�ס�����Ƿ�ѡ��, �����ѡ��, ������
		if (cbRemerberPWD.isChecked()) {
			// ��ǰ��Ҫ��ס����
			Log.i(TAG, "��ס����: " + number + ", " + password);
			boolean isSuccess = LoginUtils.saveUserInfo(this, number, password,cbRemerberPWD.isChecked());
			if (isSuccess) {
//				Toast.makeText(this, "����ɹ�", 0).show();
			} else {
				Toast.makeText(this, "�˺���Ϣ����ʧ��", 0).show();
			}
		}
		// 3. ��½�ɹ�	
		new Thread(new Runnable() {	
			String result;
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Map<String, String> param=new HashMap<String, String>();
					String cid=PushManager.getInstance().getClientid(getApplicationContext());
					param.put("username", number+"");
					param.put("password", password+"");
					param.put("clientid", cid);
					System.out.println(cid+"dsgfdsgfdsbdsdmfhb");
		            String jsonstring1;
		            jsonstring1=loginnet.login(param);
		            System.out.println(jsonstring1+"����");
		            if(jsonstring1!=null&&jsonstring1.length()>0){
		              	jsonString=jsonstring1;	
		              	result="1";
		              }else{
		              	result="0";
		              	return;
		              }
					} catch (Exception e) {
					// TODO: handle exception
					result="2";
				}finally{
			Log.i("result",result.toString());
			Message message=new Message();
			message.what=SHOW_RESPONSE;
			message.obj=result.toString();		
			handler.sendMessage(message);	
				}				
			}
		}).start();	
	}
	private void checkAndLog()
	{
		
		Intent intent = new Intent(this,HomeActivity.class);
		startActivity(intent);
		finish();
	}	
	public void la_btn_reg_click(View view){
		Intent intent = new Intent(this,RegisterActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}
}