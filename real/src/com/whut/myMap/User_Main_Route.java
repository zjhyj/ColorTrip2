package com.whut.myMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.adapter.ListViewAdapter_speak_point;
import com.whut.myMap.adapter.ListViewAdapter_speak_route;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.layout.PullToRefreshLayout;
import com.whut.myMap.layout.PullToRefreshLayout.OnRefreshListener;
import com.whut.myMap.layout.PullableListView;
import com.whut.myMap.layout.PullableListView.OnLoadListener;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.getuserdongtainet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class User_Main_Route extends Activity implements OnLoadListener, OnRefreshListener{
	private ActionBar mActionBar;
	private static final int SHOW_RESPONSE = 0;
	private static final int SHOW_RESPONSE_FIRST = 1;
	private static String jsonstring;
	private static ArrayList<trackbean> person;
	private LinkedList<trackbean> mList;
	private ListViewAdapter_speak_route myAdapter;
	private PullableListView listView;
	private PullToRefreshLayout mPullToRefreshLayout;
	private int user_id;
	private static boolean isInit = true;
	public static final boolean NEEDS_PROXY = Integer.valueOf(Build.VERSION.SDK_INT).intValue() < 11;
	protected static final int SHOW_RESPONSE_XIALA = 2;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			person = new ArrayList<trackbean>();
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					person = (ArrayList<trackbean>) gson.fromJsonList(jsonstring, trackbean.class);
					for (int i = 0; i < person.size(); i++) {
						mList.add(person.get(i));
					}
					myAdapter.notifyDataSetChanged();
					listView.finishLoading();
				} else {
					Toast.makeText(User_Main_Route.this, "没有更多了！", 0).show();
					listView.finishLoading();
					return;
				}
				break;
			case SHOW_RESPONSE_XIALA:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					person = (ArrayList<trackbean>) gson.fromJsonList(jsonstring, trackbean.class);
					for (int i = person.size()-1; i >=0 ; i--) {
						mList.addFirst(person.get(i));
					}
					myAdapter.notifyDataSetChanged();
					mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				} else {
					Toast.makeText(User_Main_Route.this, "没有更多了！", 0).show();
					mPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
					return;
				}
				break;
			case SHOW_RESPONSE_FIRST:
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					person = (ArrayList<trackbean>) gson.fromJsonList(jsonstring, trackbean.class);
					for (int i = 0; i < person.size(); i++) {
						mList.add(person.get(i));
					}
					myAdapter.notifyDataSetChanged();
					listView.finishLoading();
				} else {
					Toast.makeText(User_Main_Route.this, "请检查您的网络！", 0).show();
					listView.finishLoading();
					return;
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_main);
		Intent intent = getIntent();
		user_id = intent.getIntExtra("userid", 0);
		person = new ArrayList<trackbean>();
		mList = new LinkedList<trackbean>();
		mPullToRefreshLayout=((PullToRefreshLayout) findViewById(R.id.refresh_view));
		mPullToRefreshLayout.setOnRefreshListener(this);
		listView = (PullableListView) findViewById(R.id.content_view);
		mPullToRefreshLayout=(PullToRefreshLayout) findViewById(R.id.refresh_view);
		initListView();
		listView.setOnLoadListener(this);

	}

	/**
	 * ListView初始化方法
	 */
	private void initListView() {
		LayoutInflater inflater = LayoutInflater.from(User_Main_Route.this);
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			items.add("这里是item " + i);
		}
		myAdapter = new ListViewAdapter_speak_route(mList, User_Main_Route.this);
		listView.setAdapter(myAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(User_Main_Route.this, " Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onLoad(final PullableListView pullableListView) {

		person = new ArrayList<trackbean>();
		// Variable variable=new Variable();
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int trackid;
					try {
						trackid = mList.getLast().getTrack().getTrack_id();
					} catch (Exception e) {
						// TODO: handle exception
						trackid = 0;
					}
					Map<String, String> param = new HashMap<String, String>();
					param.put("userid", user_id + "");
					param.put("trackid", trackid + "");
					param.put("flag", 0 + "");
					getuserdongtainet getmydongtaiPointnet = new getuserdongtainet();
					String jsonstring1 = getmydongtaiPointnet.getflaguserdongtaiRoute(param);
					System.out.println(jsonstring1);
					if (jsonstring1 != null && jsonstring1.length() > 0) {
						jsonstring = jsonstring1;
						result = "1";
					} else {
						result = "0";
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = "2";
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					if (isInit) {
						message.what = SHOW_RESPONSE_FIRST;
						isInit = false;
					} else {
						message.what = SHOW_RESPONSE;
					}

					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		mActionBar = getActionBar();
		mActionBar.setTitle("");
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
		System.out.println("按下了back键   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// 下拉刷新操作
	person = new ArrayList<trackbean>();
	// Variable variable=new Variable();
	new Thread(new Runnable() {
		String result;

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				int trackid;
				try {
					trackid = mList.getFirst().getTrack().getTrack_id();
				} catch (Exception e) {
					// TODO: handle exception
					trackid = 0;
				}
				Map<String, String> param = new HashMap<String, String>();
				param.put("userid", user_id + "");
				param.put("trackid", trackid + "");
				param.put("flag", 1 + "");
				getuserdongtainet getmydongtaiPointnet = new getuserdongtainet();
				String jsonstring1 = getmydongtaiPointnet.getflaguserdongtaiRoute(param);
				System.out.println(jsonstring1);
				if (jsonstring1 != null && jsonstring1.length() > 0) {
					jsonstring = jsonstring1;
					result = "1";
				} else {
					result = "0";
				}
			} catch (Exception e) {
				// TODO: handle exception
				result = "2";
			} finally {
				Log.i("result", result.toString());
				Message message = new Message();
				message.what = SHOW_RESPONSE_XIALA;
				message.obj = result.toString();
				handler.sendMessage(message);
			}
		}
	}).start();
	}

}
