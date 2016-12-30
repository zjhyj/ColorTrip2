package com.whut.myMap.fragment;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main;
import com.whut.myMap.Speak_MainNET;
import com.whut.myMap.Speak_MainNET_ST;
import com.whut.myMap.adapter.ListViewAdapter_me_point_nogood;
import com.whut.myMap.adapter.ListViewAdapter_speak_point;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.serverce.findshoucang;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.deleteUtilNet;
import com.whut.net.getLredsbytimenet;
import com.whut.net.getPredsbytimenet;
import com.whut.net.getfavordongtaipointnet;
import com.whut.net.getredsbytimenet;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Rate_point extends Fragment {
	private View mParent;
	private static String jsonstring;
	private FragmentActivity mActivity;
	private LinkedList<redsbean> mList;
	private ListViewAdapter_speak_point myAdapter;
	static final int MENU_MANUAL_REFRESH = 0;
	static final int MENU_DISABLE_SCROLL = 1;
	static final int MENU_SET_MODE = 2;
	static final int MENU_DEMO = 3;
	private static boolean flag;
	private boolean isInit;
	private final int DELETE_ITEM = 2;
	private int deletePosition;
	private ProgressDialog pd;
	 private static int pos;
	private static ArrayList<redsbean> person;
	private PullToRefreshListView mPullRefreshListView;
	private final int SHOW_RESPONSE = 0;
	private final int SHOW_RESPONSE_FIRST = 1;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			person = new ArrayList<redsbean>();
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					person = (ArrayList<redsbean>) gson.fromJsonList(jsonstring, redsbean.class);
				} else {
					Toast.makeText(getActivity(), "û�и����ˣ�", 0).show();
					return;
				}
				break;
			case SHOW_RESPONSE_FIRST:
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					person = (ArrayList<redsbean>) gson.fromJsonList(jsonstring, redsbean.class);
					for (int i = 0; i < person.size(); i++) {
						mList.add(person.get(i));
					}
					myAdapter.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
				} 
				else if(Integer.parseInt((String) msg.obj) == 0){
					Toast.makeText(getActivity(), "û�и����ˣ�", 0).show();
					return;
				}else {
					Toast.makeText(getActivity(), "�����������磡", 0).show();
					return;
				}
				break;
			case DELETE_ITEM:
				if (Integer.parseInt((String) msg.obj) == 1) {
					new findshoucang(getActivity()).deleteshoucanged(mList.get(deletePosition-1).getReds().getReds_id(),0,getActivity());
					mList.remove(deletePosition - 1);
					myAdapter.notifyDataSetChanged();
					mPullRefreshListView.onRefreshComplete();
					pd.dismiss();
				} else {
					Toast.makeText(getActivity(), "ɾ��ʧ�ܣ������������磡", 0).show();
					pd.dismiss();
					return;
				}
				break;
			}
		}
	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pulltorefresh_cotainer, container, false);
		isInit = true;
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();
		person = new ArrayList<redsbean>();
		mList = new LinkedList<redsbean>();
		getData();
		if (mList == null) {
			return;
		}
		myAdapter = new ListViewAdapter_speak_point(mList, mActivity);
		mPullRefreshListView = (PullToRefreshListView) mParent.findViewById(R.id.pull_refresh_list);
		/**
		 * ʵ�� �ӿ� OnRefreshListener2<ListView> �Ա������ �������������͵��ײ�
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
		// ��view�����ListView�Ϳ�����BaseAdapter��������
		actualListView.setAdapter(myAdapter);
		actualListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				pos=position-1;
				Intent intent = new Intent(getActivity(), Speak_MainNET.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("speak_item", mList.get(position - 1));
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
				getActivity().overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				new AlertDialog.Builder(getActivity()).setTitle("ϵͳ��ʾ")// ���öԻ������

						.setMessage("ȷ��ȡ���ղأ�")// ������ʾ������

						.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {// ���ȷ����ť

					@Override

					public void onClick(DialogInterface dialog, int which) {// ȷ����ť����Ӧ�¼�
						deletePosition = position;
						// TODO Auto-generated method stub
						pd = ProgressDialog.show(getActivity(), "����", "����ɾ�������Ե�");
						new Thread(new Runnable() {
							private String result;

							@Override
							public void run() {
								try {
									Map<String, String> param = new HashMap<String, String>();
									int redsid=mList.get(position-1).getReds().getReds_id();
									param.put("source_id", redsid+"");
									param.put("type", 0+"");
									deleteUtilNet deleteUtilNet = new deleteUtilNet();
									String jsonstring1 = deleteUtilNet.deleteShoucang(param);
									System.out.println(jsonstring1 + "jsonstring");
									if (jsonstring1 != null && jsonstring1.length() > 0) {
//										jsonstring = jsonstring1;
										result = jsonstring1;
									} else {
										result = "0";
										return;
									}
								} catch (Exception e) {
									// TODO: handle exception
									result = "0";
								} finally {
									Log.i("result", result.toString());
									Message message = new Message();
									message.what = DELETE_ITEM;
									message.obj = result.toString();
									handler.sendMessage(message);
								}
							}
						}).start();

					}

				}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {// ��ӷ��ذ�ť

					@Override

					public void onClick(DialogInterface dialog, int which) {// ��Ӧ�¼�

						// TODO Auto-generated method stub

					}

				}).show();// �ڰ�����Ӧ�¼�����ʾ�˶Ի���
				return false;
			}

		});
	}

	private void getData() {
		if (isInit) {
			isInit = false;
			person = new ArrayList<redsbean>();
			// Variable variable=new Variable();
			new Thread(new Runnable() {
				String result;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Map<String,String> param=new HashMap<String, String>();
	            		param.put("redsid", 0+"");
						getfavordongtaipointnet getfavordongtaipointnet = new getfavordongtaipointnet();
						String jsonstring1 = getfavordongtaipointnet.getfavorpoint(param);
						if (jsonstring1 != null && jsonstring1.length() > 0) {
							jsonstring = jsonstring1;
							result = "1";
						} else {
							result = "0";
						}
					} catch (Exception e) {
						// TODO: handle exception
						result = "0";
					} finally {
						Log.i("result", result.toString());
						Message message = new Message();
						message.what = SHOW_RESPONSE_FIRST;
						message.obj = result.toString();
						handler.sendMessage(message);
					}
				}
			}).start();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (getUserVisibleHint()) {
			getData();
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
		}
	}

	// ģ������������ݵ� �첽������
	//
	private class GetDataTask extends AsyncTask<Void, Void, Object> {
		// ���߳���������
		int redsid;
		@Override
		protected ArrayList<redsbean> doInBackground(Void... params) {
			// Simulates a background job.
			// listreds=new ArrayList<redsbean>();
			if (flag == false) {
				new Thread(new Runnable() {
					private String result;
					@Override
					public void run() {
						try {
							// TODO Auto-generated method stub
		
							if(mList==null||mList.size()<=0){  redsid=0; }else{
								redsid = mList.get(0).getReds().getReds_id();
									}
							Log.i("redsiddown", redsid + "");
							Map<String, String> param = new HashMap<String, String>();
							param.put("redsid", redsid + "");
							getfavordongtaipointnet getfavordongtaipointnet = new getfavordongtaipointnet();
							String jsonstring1 = getfavordongtaipointnet.getfavorpoint(param);
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
			} else if (flag == true) {
				new Thread(new Runnable() {
					private String result;

					@Override
					public void run() {
						try {
							// TODO Auto-generated method stub
							if(mList==null||mList.size()<=0){  redsid=0; }else{
								// TODO Auto-generated method stub
								redsid = mList.get(mList.size() - 1).getReds().getReds_id();
									}
							Log.i("redsidup", redsid + "");
							Map<String, String> param = new HashMap<String, String>();
							param.put("redsid", redsid + "");
							param.put("flag", 1 + "");
							getfavordongtaipointnet getfavordongtaipointnet = new getfavordongtaipointnet();
							String jsonstring1 = getfavordongtaipointnet.getfavorLPoint(param);
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

			}
			try {
				Thread.sleep(3000);
				return person;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		
		}

		// ���̸߳���UI
		@Override
		protected void onPostExecute(Object result) {
			if (result != null) {
				List<redsbean> listredsbean = (ArrayList<redsbean>) result;
				System.out.println("redsbean"+listredsbean.size());
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
				// ֪ͨRefreshListView �����Ѿ��������
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
			}else{
		switch (requestCode) {
		case 1:
			int isupdate=data.getIntExtra("zaned", 2);			
			System.out.println("isupdate"+isupdate);
			if (isupdate!=2) {
				if(isupdate==1){
				mList.get(pos).getReds().setZan(mList.get(pos).getReds().getZan()+1);	
				  }else{
				mList.get(pos).getReds().setZan(mList.get(pos).getReds().getZan()-1);	 
		         }
				 myAdapter.notifyDataSetChanged();    
		         mPullRefreshListView.onRefreshComplete();
				break;	
			}
		}
			}
	}
	
	
}
