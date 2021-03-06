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
import com.whut.myMap.Speak_Main_routeNET;
import com.whut.myMap.adapter.ListViewAdapter_me_route_nogood;
import com.whut.myMap.adapter.ListViewAdapter_speak_route;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.deleteUtilNet;
import com.whut.net.getfavordongtairoute;
import com.whut.net.getmydongtaiPointnet;
import com.whut.net.getmydongtaiRoutenet;

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
public class Dongtai_route extends Fragment{
	private View mParent;
	private FragmentActivity mActivity;
	private LinkedList<trackbean> mList;
	 private static ArrayList<trackbean> person;
	 private static String jsonstring;
    private ListViewAdapter_speak_route myAdapter;
	 static final int MENU_MANUAL_REFRESH = 0;  
	 static final int MENU_DISABLE_SCROLL = 1;  
	 static final int MENU_SET_MODE = 2;  
	 static final int MENU_DEMO = 3; 
	 private final int SHOW_RESPONSE=0;
	 private final int SHOW_RESPONSE_FIRST=1;
		private boolean isInit;
		private PullToRefreshListView mPullRefreshListView;
	 private static boolean flag;
		private final int DELETE_ITEM = 2;
		private int deletePosition;
		private ProgressDialog pd;
	 private static int pos;
	 private Handler handler=new Handler(){
			public void handleMessage(Message msg) {
				person=new ArrayList<trackbean>();
				switch (msg.what) {
				case SHOW_RESPONSE:
					Log.i("zai", "enene2");
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene");
							Log.i("zai", "enene3");
							gsonutil gson=new gsonutil();
							Log.i("json",jsonstring);
							person=(ArrayList<trackbean>) gson.fromJsonList(jsonstring, trackbean.class);	
					}else{					
							Toast.makeText(getActivity(), "没有更多了！", 0).show();	
							return;
						}
					break;
				case SHOW_RESPONSE_FIRST:
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene");
							Log.i("zai", "enene3");
							gsonutil gson=new gsonutil();
							Log.i("json",jsonstring);
							try {
								person=(ArrayList<trackbean>) gson.fromJsonList(jsonstring, trackbean.class);	
								for(int i=0;i<person.size();i++){
									mList.add(person.get(i));
								}
								 myAdapter.notifyDataSetChanged();  
								 mPullRefreshListView.onRefreshComplete();
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
					}else{					
							Toast.makeText(getActivity(), "请检查您的网络！", 0).show();	
							return;
						}
					break;
				case DELETE_ITEM:
					if (Integer.parseInt((String) msg.obj) == 1) {
						mList.remove(deletePosition - 1);
						myAdapter.notifyDataSetChanged();
						mPullRefreshListView.onRefreshComplete();
						pd.dismiss();
					} else {
						Toast.makeText(getActivity(), "删除失败，请检查您的网络！", 0).show();
						pd.dismiss();
						return;
					}
					break;
			}
			}
		};     


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.pulltorefresh_cotainer, container, false);
		isInit = true;
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();
		person=new ArrayList<trackbean>();
		mList=new LinkedList<trackbean>();
		getData();
		if(mList==null){return;}
		 myAdapter = new ListViewAdapter_speak_route(mList,mActivity);
		 mPullRefreshListView = (PullToRefreshListView) mParent.findViewById(R.id.pull_refresh_list);  
	        /** 
	         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部 
	         */  
	        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {  
	            @Override  
	            public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView) {  
	                flag=false;
	                new GetDataTask().execute();  
	            }  
	            @Override  
	            public void onPullUpToRefresh( PullToRefreshBase<ListView> refreshView) {  
	                flag=true;
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
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), Speak_Main_routeNET.class);
				pos=position-1;
				Bundle bundle=new Bundle();
				bundle.putSerializable("speak_item1", mList.get(position-1));
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);
				getActivity().overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		actualListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

				new AlertDialog.Builder(getActivity()).setTitle("系统提示")// 设置对话框标题

						.setMessage("确认删除这条游记？")// 设置显示的内容

						.setPositiveButton("确定", new DialogInterface.OnClickListener() {// 添加确定按钮

							@Override

							public void onClick(DialogInterface dialog, int which) {// 确定按钮的响应事件
								deletePosition = position;
								// TODO Auto-generated method stub
								pd = ProgressDialog.show(getActivity(), "标题", "正在删除，请稍等");

								/* 开启一个新线程，在新线程里执行耗时的方法 */
								new Thread(new Runnable() {
									private String result;

									@Override
									public void run() {
										try {
											Map<String, String> param = new HashMap<String, String>();
											int trackid=mList.get(position-1).getTrack().getTrack_id();
											param.put("source_id", trackid+"");
											deleteUtilNet deleteUtilNet = new deleteUtilNet();
											String jsonstring1 = deleteUtilNet.deleteTrack(param);
											System.out.println(jsonstring1 + "jsonstring");
											if (jsonstring1 != null && jsonstring1.length() > 0) {
//												jsonstring = jsonstring1;
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

						}).setNegativeButton("取消", new DialogInterface.OnClickListener() {// 添加返回按钮

							@Override

							public void onClick(DialogInterface dialog, int which) {// 响应事件

								// TODO Auto-generated method stub

							}

						}).show();// 在按键响应事件中显示此对话框
						return false;
					}

				});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 if (resultCode != Activity.RESULT_OK) {// result is not correct
				return;
			}else{
		
			int isupdate=data.getIntExtra("zaned", 2);			
			System.out.println("isupdate"+isupdate);
			if (isupdate!=2) {
				if(isupdate==1){
				mList.get(pos).getTrack().setZan(mList.get(pos).getTrack().getZan()+1);	
				  }else{
				mList.get(pos).getTrack().setZan(mList.get(pos).getTrack().getZan()-1);	 
		         }
				 myAdapter.notifyDataSetChanged();    
		         mPullRefreshListView.onRefreshComplete();
			
		}
			}
	}
	private void getData() {
		if (isInit) {
			isInit = false;
		 person=new ArrayList<trackbean> ();
//		Variable variable=new Variable();
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Map<String,String> param=new HashMap<String, String>();
            		param.put("trackid", 0+"");
            		 getmydongtaiRoutenet g=new getmydongtaiRoutenet();
	            		String jsonstring1=g.getmytrack(param);
		            if(jsonstring1!=null){
		            	jsonstring=jsonstring1;	
		            	result="1";
		            }else{
		            	result="";
		            }
					} catch (Exception e) {
					// TODO: handle exception
					result="";
				}finally{
			Log.i("result",result.toString());
			Message message=new Message();
			message.what=SHOW_RESPONSE_FIRST;
			message.obj=result.toString();		
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

	 //模拟网络加载数据的   异步请求类  
    //  
	 private class GetDataTask extends AsyncTask<Void, Void, Object> {  
		 int trackid;
	        //子线程请求数据  
	        @Override  
	        protected ArrayList<trackbean> doInBackground(Void... params) {  
	            // Simulates a background job.     	 
//	             listreds=new ArrayList<redsbean>();
	            	if(flag==false){       		
	            		new Thread(new Runnable() {
							private String result;
							@Override
							public void run() { 
						try { 
						// TODO Auto-generated method stub
							if(mList==null||mList.size()<=0){  trackid=0; }else{
						 trackid=mList.get(0).getTrack().getTrack_id();
							}
						Log.i("redsiddown",trackid+"");
	            		Map<String,String> param=new HashMap<String, String>();
	            		param.put("trackid", trackid+"");
	            		 getmydongtaiRoutenet g=new getmydongtaiRoutenet();
	            		String jsonstring1=g.getmytrack(param);
	            		Log.i("json",jsonstring1);
	            		  if(jsonstring1!=null&&jsonstring1.length()>0){
	  		            	jsonstring=jsonstring1;	
	  		          	Log.i("json",jsonstring);
	  		            	result=1+"";
	  		            }else{
	  		            	result="0";
	  		            	return;
	  		            }
	  					} catch (Exception e) {
	  					// TODO: handle exception
	  						e.printStackTrace();
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
	            	try {
						Thread.sleep(3000);
						return person;
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
	            	}else if(flag==true){   
	            		new Thread(new Runnable() {
							private String result;
							@Override
							public void run() { 
						try { 
							if(mList==null||mList.size()<=0){  trackid=0; }else{
						// TODO Auto-generated method stub
						trackid=mList.get(mList.size()-1).getTrack().getTrack_id();
							}
						Log.i("redsidup",trackid+"");
	            		Map<String,String> param=new HashMap<String, String>();
	            		param.put("trackid", trackid+"");
	            		 getmydongtaiRoutenet getsourcebyhotFirstnet=new getmydongtaiRoutenet();
	  		           String  jsonstring1=getsourcebyhotFirstnet.getmyLtrack(param);
	            		  if(jsonstring1!=null&&jsonstring1.length()>0){
	  		            	jsonstring=jsonstring1;	
	  		           	Log.i("json",jsonstring);
	  		            	result=1+"";
	  		            }else{
	  		            	result="0";
	  		            	return;
	  		            }
	  					} catch (Exception e) {
	  					// TODO: handle exception
	  						e.printStackTrace();
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
	        //主线程更新UI  
	        @Override  
			protected void onPostExecute(Object result) {              
	        	if(result!=null){
				List<trackbean> listredsbean=(ArrayList<trackbean>) result;
			     if(flag==false){
			     for(int i=listredsbean.size()-1;i>=0;i--){          
	             mList.addFirst(listredsbean.get(i));
			      }
			     Log.i("hou",123+"");
			     }
			     else if(flag==true) {		    	 
			    	 for(int i=0;i<=listredsbean.size()-1;i++){
			             mList.add(listredsbean.get(i));
					     }
			    	 Log.i("hou",321+"");
				}
			    myAdapter.notifyDataSetChanged();    
	            //通知RefreshListView 我们已经更新完成  
	            // Call onRefreshComplete when the list has been refreshed.  
	            mPullRefreshListView.onRefreshComplete();  
	            super.onPostExecute(result);  
	        	}else{
	        		return;
	        	}
        }  
    }  
	 
	 
}
