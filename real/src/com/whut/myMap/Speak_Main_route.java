package com.whut.myMap;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.adapter.Route_Main_ListviewAdapter;
import com.whut.myMap.adapter.ScollListView;
import com.whut.myMap.adapter.SpeakGridViewAdapter;
import com.whut.myMap.adapter.Route_Base_ListviewAdapter;
import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.dao.linktrackdao;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.lbs2;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.Redservice;
import com.whut.myMap.serverce.trackservice;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.IInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;
public class Speak_Main_route extends Activity {
	private static final int ADD_REDS = 0;
	private static final int TRAVEL_SET = 1;
	private static final int ADD_TRACK=2;
	private static final int DELETE_TRACK=3;
	private trackbean trackbean;
	private ImageView imageHead;
	private TextView name;
	private TextView content;
	private ScollListView mListView;
	private ImageView titleImg; 
	private Route_Base_ListviewAdapter mAdapter;
	String image;
	String n; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.speak_route);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		trackbean = (com.whut.myMap.domain.trackbean) bundle.getSerializable("speak_item1");
		SharedPreferences userPreferences = getSharedPreferences("userInfo", 0);
		 image = userPreferences.getString(url.SPUSER_IMAGE, "");
		n = userPreferences.getString(url.SPUSER_NAME, "");
		mListView = (ScollListView) findViewById(R.id.ui_lv);
		ImageView music = (ImageView) findViewById(R.id.music);
		music.setVisibility(View.GONE);
		mAdapter = new Route_Base_ListviewAdapter(Speak_Main_route.this, trackbean);
		initListView();
	}
	private void initListView() {	
		RelativeLayout rela=(RelativeLayout) findViewById(R.id.title);
		imageHead = (ImageView) rela.findViewById(R.id.head);
		name = (TextView) rela.findViewById(R.id.username);
		content = (TextView) rela.findViewById(R.id.text);
		ImageView titleImg = (ImageView)rela.findViewById(R.id.toppic);

			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true) // 内存缓存
					.cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			try {
				ImageLoader.getInstance().displayImage(image, imageHead, options);
			} catch (Exception e) {
				imageHead.setImageResource(R.drawable.default_head);
			}
			if(trackbean.getTrack().getBgurl()!=null&&trackbean.getTrack().getBgurl().length()>0){
				
				Bitmap bm = BitmapFactory.decodeFile(trackbean.getTrack().getBgurl());
				titleImg.setImageBitmap(bm);
			}
			name.setText(n);
			content.setText(trackbean.getTrack().getWords());
			

//			placeHolderView.setBackgroundColor(0xFFFFFFFF);
//			mListView.addHeaderView(placeHolderView);

			mListView.setAdapter(mAdapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.route_base, menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.speak_main_route);
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
			showPre();
			return true;
		case R.id.map:
			Intent intent = new Intent(getApplication(),Trackshow.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("speak_item", trackbean);
			intent.putExtras(bundle);
			startActivity(intent);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.set:
			Intent intent1 = new Intent(getApplication(), EditTrack.class);
			Bundle bundle1 = new Bundle();
			bundle1.putSerializable("speak_item", trackbean);
			intent1.putExtras(bundle1);
			startActivityForResult(intent1, TRAVEL_SET);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.add:
			Intent intent2 = new Intent(getApplication(), ChoosePoint.class);
			Bundle bundle2 = new Bundle();
			intent2.putExtras(bundle2);
			startActivityForResult(intent2, ADD_REDS);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.deletetrack:
			Intent intent4=new Intent(getApplication(),DeleteTrack.class);
			intent4.putExtra("track_id", trackbean.getTrack().getTrack_id());
			startActivityForResult(intent4, DELETE_TRACK);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.addtrack:
			Intent intent3=new Intent(getApplication(),ChooseRoute.class);
			startActivityForResult(intent3, ADD_TRACK);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		case R.id.share:
			Intent intent31=new Intent(getApplication(),PostTravel.class);
			Bundle bundle3=new Bundle();
			bundle3.putSerializable("trackbean", trackbean);
			intent31.putExtras(bundle3);
			startActivity(intent31);
			this.overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			switch (requestCode) {
			case ADD_REDS:
				redsbean testPoint = (redsbean) data.getSerializableExtra("POINT");
				//从数据库获得该动态全部信息，再刷新listview
				testPoint.getReds().setTrack_id(trackbean.getTrack().getTrack_id());
				if (trackbean.getRedsbean()==null||trackbean.getRedsbean().size()==0) {
					List<redsbean> aList=new ArrayList<redsbean>();
					aList.add(testPoint);
					trackbean.setRedsbean(aList);
				} else {
					trackbean.getRedsbean().add(testPoint);
				}
				mAdapter.notifyDataSetChanged();
				try {
					Redservice service = new Redservice(Speak_Main_route.this);
					service.update_trackaddreds(Speak_Main_route.this, trackbean.getTrack().getTrack_id(),
							testPoint);
					List<lbs2> lbs=new ArrayList<lbs2>();
					lbs.add(new lbs2(0, testPoint.getReds().getRedsx(),testPoint.getReds().getRedsy(),trackbean.getTrack().getTrack_id(),2,testPoint.getReds().getDate()));
				    trackservice trackservice=new trackservice(Speak_Main_route.this);
				    trackservice.addlbs(lbs,Speak_Main_route.this); 
				} catch (Exception e) {
					// TODO: handle exception
				}
				break;
			case ADD_TRACK:
				trackbean trackbean1=new trackbean();
				trackbean1=(com.whut.myMap.domain.trackbean) data.getSerializableExtra("ROUTE");
				linktrackdao linktrackdao=new linktrackdao(getApplication());
				//linktrackdao.insertalltrack(trackbean.getTrack().getTrack_id(),trackbean.getTrack().getTrack_id());
				linktrackdao.insertalltrack(trackbean.getTrack().getTrack_id(),trackbean1.getTrack().getTrack_id());
				break;
			case TRAVEL_SET:
				int delete=data.getIntExtra("delete", 0);
				String path=data.getStringExtra("image");
				if (delete==1) {
					finish();
					showPre();
				}
				if (delete!=2){
					try{
					Bitmap bitmap = decodeUriAsBitmap(Uri.fromFile(new File(path)));
					bitmap = ThumbnailUtils.extractThumbnail(bitmap, 600, 400);
					titleImg.setImageBitmap(bitmap);
				}catch (Exception e) {
					// TODO: handle exception
				}
				String nString=data.getStringExtra("name");
				content.setText(nString);
				}
				break;
			default:
				break;
			}
		}
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
}