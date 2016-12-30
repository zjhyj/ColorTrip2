package com.whut.myMap;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.adapter.BaseGridViewAdapter;
import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.dao.lbsdao;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.Redservice;
import com.whut.myMap.serverce.trackservice;
import com.whut.net.addredsnet;
import com.whut.net.addtracknet;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
/**
 * list为评论列表 其余从上级传 坐标不知道
 * @author xxr
 *
 */
public class Speak_Main extends Activity {
	private static final int GONE = 8;
	private static final int VISIBLE = 0;
	private static final int REDS_SET = 4;
	protected static final int SHOW_RESPONSE = 0;
	private redsbean redsbean;
	private ImageView imageHead;
	private TextView name;
	private TextView date;
	private TextView content;
	private Button map_show;
	private int isSelected[]={0,0};
	private SpeakGridView speakGridView;
private int dongtai_id;
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
					Toast.makeText(getApplication(), "分享成功!", 0).show();
					finish();
				} else {
					Log.i("zai", "enene4");
					Toast.makeText(getApplication(), "网络异常，请检查网络！", 0).show();
				}
			}
		}
	}
};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speak_base);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		redsbean= (com.whut.myMap.domain.redsbean) bundle.getSerializable("speak_item");
		imageHead=(ImageView) findViewById(R.id.imgHead);
		name=(TextView) findViewById(R.id.tvName);
		date=(TextView) findViewById(R.id.tvDate);
		content=(TextView) findViewById(R.id.tvContent);
        map_show=(Button) findViewById(R.id.map_show);
        map_show.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Speak_Main.this, Redslocation.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("speak_item", redsbean);
				intent.putExtras(bundle);
				startActivity(intent);	
			}
		});
        SharedPreferences userPreferences = getSharedPreferences("userInfo", 0);
		String image=userPreferences.getString(url.SPUSER_IMAGE, "");
		String n=userPreferences.getString(url.SPUSER_NAME, "");
		if (image!=null) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
			.showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
			.cacheInMemory(true) // 内存缓存
			.cacheOnDisk(true) // sdcard缓存
			.bitmapConfig(Config.RGB_565)// 设置最低配置
			.build();//
	ImageLoader.getInstance().displayImage(image,
			imageHead, options);
		}
		else {
			imageHead.setImageResource(R.drawable.default_head);
		}
		name.setText(n);
		date.setText(redsbean.getReds().getDate().substring(0, 16));
		content.setText(redsbean.getReds().getWords());
		speakGridView=(SpeakGridView) findViewById(R.id.tvGridview);		
		if(redsbean.getListmap()==null){
			speakGridView.setVisibility(GONE);
		}
		else {
			speakGridView.setVisibility(VISIBLE);
			ArrayList<String> transPicture=new ArrayList<String>();
			for (int i = 0; i < redsbean.getListmap().size(); i++) {
				
				transPicture.add("file://"+redsbean.getListmap().get(i).getImgurl());  
			}
			speakGridView.setAdapter(new BaseGridViewAdapter(this, transPicture));
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.speak_base, menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.footprint);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			showPre();
			return true;
		case R.id.set:
			Intent intent1 = new Intent(getApplication(), EditReds.class);
			Bundle bundle1 = new Bundle();
			intent1.putExtra("type", 1);
			bundle1.putSerializable("redsbean", redsbean);
			intent1.putExtras(bundle1);
			startActivityForResult(intent1, REDS_SET);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.share:
			AlertDialog.Builder builder = new Builder(Speak_Main.this);
			builder.setMessage("确定分享？");
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			});
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					post(redsbean);
				}
			});
			builder.create().show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}	
	private void post(final Object textObject) {
			new Thread(new Runnable() {
				String result;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						redsbean reds=(redsbean) textObject;
						reds.getReds().setTrack_id(0);
						addredsnet addredsnet = new addredsnet();
						String ret = addredsnet.addreds(reds);
						if (ret != null && ret.length() > 0) {
							result = ret;
						} else {
							result = "2";
						}
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			switch (requestCode) {
			case REDS_SET:
				//1 finish
				int del=data.getIntExtra("delete", 2);
				String change=data.getStringExtra("change");
				System.out.println();
				if (del==1) {
					showPre();
				}else if (change!=null) {
					content.setText(change);
				}
				break;
			
			default:
				break;
			}
		}
	}
	public void showPre() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	
}