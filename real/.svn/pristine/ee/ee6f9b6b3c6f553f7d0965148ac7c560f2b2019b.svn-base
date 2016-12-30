package com.whut.myMap.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main;
import com.whut.myMap.R.layout;
import com.whut.myMap.Speak_Main_route;
import com.whut.myMap.Speak_Main_routeNET;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.utils.gsonutil;
import com.whut.myMap.adapter.*;
import com.whut.net.getLtrackbyhotnet;
import com.whut.net.getLtrackbytimenet;
import com.whut.net.getredsbytimenet;
import com.whut.net.gettrackbyhotnet;
import com.whut.net.gettrackbytimenet;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SpeakFragment4 extends Fragment {
	private View mParent;
	private FragmentActivity mActivity;
	private LinkedList<trackbean> mList;
	private ListViewAdapter_speak_route myAdapter;
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;

	private static boolean flag;
	private static boolean launched = false;
	private static int pos;
	private int zan;
	private static String jsonstring;
	private static ArrayList<trackbean> person;
	private final int SHOW_RESPONSE = 0;
	private final int SHOW_RESPONSE_FIRST = 1;
	private final int SHOW_RESPONSE_TWO = 2;
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
				} else {
					Toast.makeText(getActivity(), "没有更多了！", 0).show();
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
				} else {
					Toast.makeText(getActivity(), "请检查您的网络！", 0).show();
					return;
				}
				break;
			case SHOW_RESPONSE_TWO:
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
					mPullRefreshListView.onRefreshComplete();
				} else {
					Toast.makeText(getActivity(), "没有更多了！", 0).show();
					return;
				}
				break;
			}
		}
	};

	private PullToRefreshListView mPullRefreshListView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static SpeakFragment4 newInstance(int index) {
		SpeakFragment4 f = new SpeakFragment4();

		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);

		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pulltorefresh_cotainer, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();
		person = new ArrayList<trackbean>();
		mList = new LinkedList<trackbean>();
		// getData();
		if (mList == null) {
			return;
		}
		myAdapter = new ListViewAdapter_speak_route(mList, mActivity);
		mPullRefreshListView = (PullToRefreshListView) mParent.findViewById(R.id.pull_refresh_list);
		/**
		 * 实现 接口 OnRefreshListener2<ListView> 以便与监听 滚动条到顶部和到底部
		 */
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
				flag = false;
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
				flag = true;
				new GetDataTask().execute();
			}
		});
		ListView actualListView = mPullRefreshListView.getRefreshableView();
		// Need to use the Actual ListView when registering for Context Menu
		registerForContextMenu(actualListView);
		// 把view层对象ListView和控制器BaseAdapter关联起来
		actualListView.setAdapter(myAdapter);
		actualListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				pos = position - 1;
				Intent intent = new Intent(getActivity(), Speak_Main_routeNET.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("speak_item1", mList.get(position - 1));
				intent.putExtras(bundle);
				startActivityForResult(intent,5);
				getActivity().overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
	}

	private void getData() {
		new Thread(new Runnable() {
			private String result;

			@Override
			public void run() {
				try {
					zan = 0;
					Log.i("redsidup", zan + "");
					Map<String, String> param = new HashMap<String, String>();
					param.put("zan", zan + "");
					gettrackbyhotnet gettrackbyhotnet = new gettrackbyhotnet();
					String jsonstring1 = gettrackbyhotnet.gettrackbyhot(param);
					if (jsonstring1 != null && jsonstring1.length() > 0) {
						jsonstring = jsonstring1;
						result = 1 + "";
					} else {
						result = "0";
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = "2";
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					message.what = SHOW_RESPONSE_TWO;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	@Override
	public void onResume() {
		super.onResume();
	}

//	@Override
//	public void onHiddenChanged(boolean hidden) {
//		super.onHiddenChanged(hidden);
//		if (!hidden && !launched) {
//			getData();
//			launched = true;
//		} else {
//			Log.i("lazy", "diaoyong2");
//		}
//	}
	@Override
	public void setUserVisibleHint(boolean hidden) {
		if (!hidden && !launched) {
			getData();
			launched = true;
		} else {
			Log.i("lazy", "diaoyong2");
		}
	}
	// 模拟网络加载数据的 异步请求类
	//
	private class GetDataTask extends AsyncTask<Void, Void, Object> {
		int zan;

		// 子线程请求数据
		@Override
		protected ArrayList<trackbean> doInBackground(Void... params) {
			// Simulates a background job.
			// listreds=new ArrayList<redsbean>();
			if (flag == true) {
				new Thread(new Runnable() {
					private String result;

					@Override
					public void run() {
						try {
							// TODO Auto-generated method stub
							if (mList == null || mList.size() <= 0) {
								zan = 0;
							} else {
								zan = mList.get(mList.size() - 1).getTrack().getZan();
								if (zan == 0)
									zan = -1;
							}
							Log.i("redsiddown", zan + "");
							Map<String, String> param = new HashMap<String, String>();
							param.put("zan", zan + "");
							getLtrackbyhotnet getLtrackbyhot = new getLtrackbyhotnet();
							String jsonstring1 = getLtrackbyhot.getLtrackbyhot(param);
							if (jsonstring1 != null && jsonstring1.length() > 0) {
								jsonstring = jsonstring1;
								result = 1 + "";
							} else {
								result = "0";
								return;
							}
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
				try {
					Thread.sleep(3000);
					return person;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			} else if (flag == false) {
				new Thread(new Runnable() {
					private String result;

					@Override
					public void run() {
						try {
							if (mList == null || mList.size() <= 0) {
								zan = 0;
							} else {
								// TODO Auto-generated method stub
								zan = mList.get(0).getTrack().getZan();
							}
							Log.i("redsidup", zan + "");
							Map<String, String> param = new HashMap<String, String>();
							param.put("zan", zan + "");
							gettrackbyhotnet gettrackbyhotnet = new gettrackbyhotnet();
							String jsonstring1 = gettrackbyhotnet.gettrackbyhot(param);
							if (jsonstring1 != null && jsonstring1.length() > 0) {
								jsonstring = jsonstring1;
								result = 1 + "";
							} else {
								result = "0";
								return;
							}
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
				try {
					Thread.sleep(3000);
					return person;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return null;
				}
			}
			return null;
		}

		// 主线程更新UI
		@Override
		protected void onPostExecute(Object result) {
			if (result != null) {
				List<trackbean> listredsbean = (ArrayList<trackbean>) result;
				if (flag == false) {
					for (int i = listredsbean.size() - 1; i >= 0; i--) {
						mList.addFirst(listredsbean.get(i));
					}
					Log.i("hou", 123 + "");
				} else if (flag == true) {
					for (int i = 0; i <= listredsbean.size() - 1; i++) {
						mList.add(listredsbean.get(i));
					}
					Log.i("hou", 321 + "");
				}
				myAdapter.notifyDataSetChanged();
				// 通知RefreshListView 我们已经更新完成
				// Call onRefreshComplete when the list has been refreshed.
				mPullRefreshListView.onRefreshComplete();
				super.onPostExecute(result);
			} else {
				return;
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {

			int isupdate = data.getIntExtra("zaned", 2);
			System.out.println("isupdate" + isupdate);
			if (isupdate != 2) {
				if (isupdate == 1) {
					mList.get(pos).getTrack().setZan(mList.get(pos).getTrack().getZan() + 1);
				} else {
					mList.get(pos).getTrack().setZan(mList.get(pos).getTrack().getZan() - 1);
				}
				myAdapter.notifyDataSetChanged();
				mPullRefreshListView.onRefreshComplete();

			}
		}
	}
}
