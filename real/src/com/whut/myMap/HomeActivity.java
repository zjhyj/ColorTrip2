package com.whut.myMap;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Looper;
import android.provider.CalendarContract.Instances;

import com.baidu.mapapi.map.MapView;
import com.whut.myMap.R;
import com.whut.myMap.adapter.SpinnerArrayAdapter;
import com.whut.myMap.fragment.FragmentIndicator;
import com.whut.myMap.fragment.FragmentIndicator.OnIndicateListener;
import com.whut.myMap.serverce.trackservice;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
public class HomeActivity extends FragmentActivity {
	static final int SPEAK_FRAGMENT = 1;
	static final int MAP_FRAGMENT = 2;
	static final int SPEAK_FRAGMENT_2 = 3;
	static final int SPEAK_FRAGMENT_3 = 4;
	static final int SPEAK_FRAGMENT_4 = 5;
	static final int SPEAK_FRAGMENT_5 = 6;
	static final int SHOW_CONCERN_TRAVEL = 7;
	public static Fragment[] mFragments;
	private RelativeLayout speak;
	private ImageButton add_speak_btn;
	private ImageButton   serch;
	MapView mMapView = null;
	private long exitTime = 0;
	private Spinner speak_spinner;
	private List<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	public static FragmentIndicator mIndicator;
	public static HomeActivity instance = null;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		 ConnectivityManager connectMgr = (ConnectivityManager) this
		 .getSystemService(Context.CONNECTIVITY_SERVICE);
		 NetworkInfo info = connectMgr.getActiveNetworkInfo();
		 instance = this;
		 if(info==null){
		 Toast.makeText(this, "��ⲻ�����磬������磡", 0).show();
		 }
         //else if(info.getType()==ConnectivityManager.TYPE_WIFI){
		// Toast.makeText(this, "����ʹ��wifi���磡", 0).show();
		// }else {
		// Toast.makeText(this, "����ʹ���ƶ����磡", 0).show();
		// }
		// trackservice service=new trackservice(getApplication());
		// service.deletetrack(getApplication());
//		speak_spinner = (Spinner) findViewById(R.id.speak_spinner);
//   String[] data_list={"���µص�","����·��","���ŵص�","����·��","���㵼��"};
//		// ������
////		arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
//		arr_adapter=new SpinnerArrayAdapter(this, data_list);
//		// ������ʽ
//		arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// ����������
//		speak_spinner.setAdapter(arr_adapter);
//		speak_spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
//			/**
//			 * 
//			 * spinner ������ز�ͬ��fragment������ switch���
//			 */
//			@Override
//			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//				switch (position) {
//				case 0:
//					setFragmentIndicator(1);
//					break;
//				case 1:
//					setFragmentIndicator(3);
//					break;
//				case 2:
//					setFragmentIndicator(4);
//					break;
//				case 3:
//					setFragmentIndicator(5);
//					break;
//				case 4:
//					setFragmentIndicator(6);
//				default:
//					break;
//				}
//
//			}
//			@Override
//			public void onNothingSelected(AdapterView<?> parent) {
//				// TODO Auto-generated method stub
//
//			}
//		});

		setFragmentIndicator(0);

		speak = (RelativeLayout) findViewById(R.id.speak);
		serch=(ImageButton) findViewById(R.id.serch);
		// map_search = (Button) findViewById(R.id.map_search_btn);
		add_speak_btn = (ImageButton) findViewById(R.id.add_speak_btn);
		// map.setVisibility(View.VISIBLE);
		speak.setVisibility(View.GONE);
		add_speak_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, WriteDongtai.class);
				startActivity(intent);

			}
		});
		serch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, Serch.class);
				startActivity(intent);
			}
		});
//		   if (PushReceiver.payloadData != null) {
////	            tLogView.append(PushDemoReceiver.payloadData);
//			   FragmentIndicator.changetoPresonal1();
//	        }
	}

	private void initTitle() {
		// map.setVisibility(View.GONE);
		speak.setVisibility(View.GONE);
	}

	/**
	 * ��ʼ��fragment
	 */
	private void setFragmentIndicator(int whichIsDefault) {
		mFragments = new Fragment[3];
		mFragments[0] = getSupportFragmentManager().findFragmentById(R.id.fragment_map);
		mFragments[1] = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
		mFragments[2] = getSupportFragmentManager().findFragmentById(R.id.fragment_settings);
//		mFragments[3] = getSupportFragmentManager().findFragmentById(R.id.fragment_speak_2);
//		mFragments[4] = getSupportFragmentManager().findFragmentById(R.id.fragment_speak_3);
//		mFragments[5] = getSupportFragmentManager().findFragmentById(R.id.fragment_speak_4);
//		mFragments[6] = getSupportFragmentManager().findFragmentById(R.id.fragment_speak_5);
		getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2])
				.show(mFragments[whichIsDefault]).commit();
		
		 mIndicator = (FragmentIndicator) findViewById(R.id.indicator);
		FragmentIndicator.setIndicator(whichIsDefault);
		mIndicator.setOnIndicateListener(new OnIndicateListener() {
			@Override
			public void onIndicate(View v, int which) {
				getSupportFragmentManager().beginTransaction().hide(mFragments[0]).hide(mFragments[1])
						.hide(mFragments[2]).show(mFragments[which]).commit();
				switch (which) {
				case 0:
					initTitle();
					// map.setVisibility(View.VISIBLE);
					break;
				case 1:
					initTitle();
					speak.setVisibility(View.VISIBLE);
					break;
				case 2:
					initTitle();
					break;
				default:
					break;
				}
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			Fragment f;
			switch (requestCode) {
			case SPEAK_FRAGMENT:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case MAP_FRAGMENT:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_map);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case SHOW_CONCERN_TRAVEL:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_map);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case SPEAK_FRAGMENT_2:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case SPEAK_FRAGMENT_3:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case SPEAK_FRAGMENT_4:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			case SPEAK_FRAGMENT_5:
				/* ���������ͨ����Ƭ�������е�Tag������ÿ����Ƭ�����ƣ�����ȡ��Ӧ��fragment */
				 f = getSupportFragmentManager().findFragmentById(R.id.fragment_speak);
				/* Ȼ������Ƭ�е�����д��onActivityResult���� */
				f.onActivityResult(requestCode, resultCode, data);
				break;
			}
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	/**
	 * �ٰ�һ���˳�����
	 * 
	 */

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
}