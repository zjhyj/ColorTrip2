package com.whut.myMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.whut.myMap.adapter.ListViewAdapter_speak_point;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.getredsbycluidnet;
import android.support.v4.app.FragmentActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
public class Localreds extends Activity {
	private ActionBar mActionBar;
	private static String jsonstring;
	private LinkedList<redsbean> mList;
    private ListViewAdapter_speak_point myAdapter;	
	 static final int MENU_MANUAL_REFRESH = 0;  
	 static final int MENU_DISABLE_SCROLL = 1;  
	 static final int MENU_SET_MODE = 2;  
	 static final int MENU_DEMO = 3;  
	 private static boolean flag;  
	 private static int clusterid;
	 private static ArrayList<redsbean> person;
	 private static int pos;
	 private final int SHOW_RESPONSE=0;
	 private final int SHOW_RESPONSE_FIRST=1;
	 private Handler handler=new Handler(){
			public void handleMessage(Message msg) {
				person=new ArrayList<redsbean>();
				switch (msg.what) {
				case SHOW_RESPONSE:
					Log.i("zai", "enene2");
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene");
							Log.i("zai", "enene3");
							gsonutil gson=new gsonutil();
							Log.i("json",jsonstring);
							person=(ArrayList<redsbean>) gson.fromJsonList(jsonstring, redsbean.class);	
					}else{					
							Toast.makeText(getApplication(), "没有更多了！", 0).show();	
				            mPullRefreshListView.onRefreshComplete();
				            return;
						}
					break;
				case SHOW_RESPONSE_FIRST:
					if(Integer.parseInt((String) msg.obj)==1){
						Log.i("zai", "enene");
							Log.i("zai", "enene3");
							gsonutil gson=new gsonutil();
							Log.i("json",jsonstring);
							person=(ArrayList<redsbean>) gson.fromJsonList(jsonstring, redsbean.class);	
							for(int i=0;i<person.size();i++){
								mList.add(person.get(i));
							}
							 myAdapter.notifyDataSetChanged();  
					         mPullRefreshListView.onRefreshComplete();  
					}else{					
							Toast.makeText(getApplication(), "请检查您的网络！", 0).show();	
				            mPullRefreshListView.onRefreshComplete();  
							person=null;
						}
					break;
			}
			}
		};
	 private PullToRefreshListView mPullRefreshListView;  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pulltorefresh_cotainer);
		Intent intent=getIntent();
		clusterid=intent.getIntExtra("clusterid", 0);
		Log.i("clusterid1", clusterid+"");
		person=new ArrayList<redsbean>();
		mList=new LinkedList<redsbean>();	
		getData();
		if(mList==null){return;}
		myAdapter = new ListViewAdapter_speak_point(mList,this);	
		 mPullRefreshListView = (PullToRefreshListView) findViewById(R.id.pull_refresh_list);  
	        /** 
	         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部 
	         */  
	        mPullRefreshListView.setOnRefreshListener(new OnRefreshListener2<ListView>() {  
	            @Override  
	            public void onPullDownToRefresh( PullToRefreshBase<ListView> refreshView) {  
	                flag=false;
	                Log.i("down", "down");
	                new GetDataTask().execute();  
	            }  
	            @Override  
	            public void onPullUpToRefresh( PullToRefreshBase<ListView> refreshView) {  
	                flag=true;
	                Log.i("up", "up");
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
				pos=position-1;
				Intent intent = new Intent(getApplicationContext(), Speak_MainNET.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("speak_item", mList.get(position-1));
//				intent.putExtra("fragment", 1);
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
			}
		});
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
	private void getData() {
		 person=new ArrayList<redsbean> ();
//		Variable variable=new Variable();
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					int redsid=0;
					Log.i("redsiddown",redsid+"");
            		Map<String,String> param=new HashMap<String, String>();
            		param.put("zan", 999999+"");
            		param.put("clusterid", clusterid+"");
            		param.put("redsid", 0+"");
		            getredsbycluidnet getredsbycluidnet=new getredsbycluidnet();
		           String  jsonstring1=getredsbycluidnet.getredsbycluid(param);
		            if(jsonstring1!=null&&jsonstring1.length()>0){
		            	jsonstring=jsonstring1;	
		            	result="1";
		            }else{
		            	result="0";
		            }
					} catch (Exception e) {
					// TODO: handle exception
					result="0";
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
	@Override
	public void onResume() {
		super.onResume();
		 
	}
	 //模拟网络加载数据的   异步请求类  
	   private class GetDataTask extends AsyncTask<Void, Void, Object> {  
	        //子线程请求数据  
	        @Override  
	        protected ArrayList<redsbean> doInBackground(Void... params) {  
	            	if(flag){       		
	            		new Thread(new Runnable() {
							private String result;
							@Override
							public void run() { 
						try { 
						// TODO Auto-generated method stub
						int zan=mList.get(mList.size()-1).getReds().getZan();
						int redsid=mList.get(mList.size()-1).getReds().getReds_id();
						Log.i("redsiddown",redsid+"");
	            		Map<String,String> param=new HashMap<String, String>();
	            		param.put("zan", zan+"");
	            		param.put("clusterid",clusterid+"");
	            		param.put("redsid",redsid+"");
	            		getredsbycluidnet getredsbycluidnet=new getredsbycluidnet();
	 		           String  jsonstring1=getredsbycluidnet.getredsbycluid(param);
	            		  if(jsonstring1!=null&&jsonstring1.length()>0){
	  		            	jsonstring=jsonstring1;	
	  		            	result=1+"";
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
	            	}else {   
	            	person=null;
	            	}
	            	try {
						Thread.sleep(3000);
						return person;
					} catch (InterruptedException e) {
						e.printStackTrace();
						return null;
					}		
	            }    
	        //主线程更新UI  
	        @Override  
			protected void onPostExecute(Object result) {              
	        	if(result!=null){
				List<redsbean> listredsbean=(ArrayList<redsbean>) result;
			     if(!flag){
			     for(int i=listredsbean.size()-1;i>=0;i--){          
	             mList.addFirst(listredsbean.get(i));
			      }
			     Log.i("hou",123+"");
			     }
			     else {		    	 
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
	        		  mPullRefreshListView.onRefreshComplete();
	        		return;
	        	}
	        }  
	    }
	   public boolean onCreateOptionsMenu(Menu menu) {
			// TODO Auto-generated method stub
			super.onCreateOptionsMenu(menu);
			mActionBar = getActionBar();
		    mActionBar.setTitle(R.string.speak_main);
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
}
