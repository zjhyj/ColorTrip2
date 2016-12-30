package com.whut.myMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.whut.myMap.adapter.ListViewAdapter_speak_route;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.layout.SearchView;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.deleteUtilNet;
import com.whut.net.getmydongtaiRoutenet;
import com.whut.net.getsearchnet;
import com.whut.net.loginnet;

import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;

public class Serch extends Activity implements SearchView.SearchViewListener{
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
	 private static boolean flag=true;
		private final int DELETE_ITEM = 2;
		private int deletePosition;
		private ProgressDialog pd;
		private static String keyword;
		private EditText search;
	 private static int pos;
	  /**
	     * 热搜框列表adapter
	     */
	    private ArrayAdapter<String> hintAdapter;

	    /**
	     * 自动补全列表adapter
	     */
	    private ArrayAdapter<String> autoCompleteAdapter;
	    /**
	     * 热搜版数据
	     */
	    private List<String> hintData;

	    /**
	     * 搜索过程中自动补全数据
	     */
	    private List<String> autoCompleteData;
	    /**
	     * 搜索view
	     */
	    private SearchView searchView;
	    /**
	     * 默认提示框显示项的个数
	     */
	    private static int DEFAULT_HINT_SIZE = 4;

	    /**
	     * 提示框显示项的个数
	     */
	    private static int hintSize = DEFAULT_HINT_SIZE;
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
							Toast.makeText(getApplicationContext(), "没有更多了！", 0).show();	
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
							Toast.makeText(getApplicationContext(), "请检查您的网络！", 0).show();	
							return;
						}
			
					break;
			}
			}
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_serch);
//		handleIntent(getIntent());
//		onSearchRequested();
		initData();
		 searchView = (SearchView) findViewById(R.id.main_search_layout);
		 searchView.setSearchViewListener(this);
	        //设置adapter
	        searchView.setTipsHintAdapter(hintAdapter);
	        searchView.setAutoCompleteAdapter(autoCompleteAdapter);
		person=new ArrayList<trackbean>();
		mList=new LinkedList<trackbean>();
				onSearchRequested();
		if(mList==null){return;}
		 myAdapter = new ListViewAdapter_speak_route(mList,getApplicationContext());
		 mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);  
	        /** 
	         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部 
	         */  
	        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {  
	            @Override  
	            public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView) {  
	                flag=true;
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
				Intent intent = new Intent(getApplicationContext(), Speak_Main_routeNET.class);
				pos=position-1;
				Bundle bundle=new Bundle();
				bundle.putSerializable("speak_item1", mList.get(position-1));
				intent.putExtras(bundle);
				startActivityForResult(intent, 2);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
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
	/**
     * 初始化数据
     */
    private void initData() {
        //初始化热搜版数据
        getHintData();
    }
	private void getData(final String query) {
		 person=new ArrayList<trackbean> ();
//		Variable variable=new Variable();
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Map<String,String> param=new HashMap<String, String>();
            		param.put("trackid", 99999999+"");
            		param.put("keyword", query);
            		getsearchnet g=new getsearchnet();
	            	String jsonstring1=g.getsearch(param);
		            if(jsonstring1!=null){
		            	jsonstring=jsonstring1;	
		            	result="1";
		            }else{
		            	result="2";
		            }
					} catch (Exception e) {
					// TODO: handle exception
					result="2";
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
	 //模拟网络加载数据的   异步请求类  
	 private class GetDataTask extends AsyncTask<Void, Void, Object> {  
		 int trackid;
	        //子线程请求数据  
	        @Override  
	        protected ArrayList<trackbean> doInBackground(Void... params) {  
	            // Simulates a background job.     	 
//	             listreds=new ArrayList<redsbean>();
	            	if(!flag){       		
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
	            		param.put("keyword", keyword);
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
	            	}else if(flag){   
	            		new Thread(new Runnable() {
							private String result;
							@Override
							public void run() { 
						try { 
							if(mList==null||mList.size()<=0){  trackid=0; 
							}else{
						trackid=mList.get(mList.size()-1).getTrack().getTrack_id();
							}
						Log.i("redsidup",trackid+"");
	            		Map<String,String> param=new HashMap<String, String>();
	            		param.put("trackid", trackid+"");
	            		param.put("keyword", keyword);
	            		 getsearchnet getsourcebyhotFirstnet=new getsearchnet();
	  		           String  jsonstring1=getsourcebyhotFirstnet.getLsearch(param);
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
  /**
	     * 点击搜索键时edit text触发的回调
	     *
	     * @param text
	     */
	    @Override
	    public void onSearch(String text) {
		       getData(text); 
		       keyword=text;
		       mList.removeAll(mList);
	        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();


	    }
	    /**
	     * 获取热搜版data 和adapter
	     */
	    private void getHintData() {
	        hintData = new ArrayList<String>(hintSize);
	       
	            hintData.add("武汉");
	            hintData.add("北京");
	            hintData.add("南京");
	            hintData.add("杭州");
	        hintAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, hintData);
	    }
		@Override
		public void onRefreshAutoComplete(String text) {
			// TODO Auto-generated method stub
			
		}

}
