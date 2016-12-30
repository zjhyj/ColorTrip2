package com.whut.myMap;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.logic.task.StatisticsTask;
import com.loopj.android.image.BitmapImage;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.Redslocation.ViewHolder;
import com.whut.myMap.dao.lbsdao;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.picture;
import com.whut.myMap.serverce.trackservice;
import com.whut.myMap.utils.pictureutil;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class Trackshow extends Activity {
	 public MapView mapView = null;  
     public BaiduMap baiduMap = null; 
     InfoWindow mInfoWindow = null;  
 	 private View Mymarker; 
 	 private  static boolean ISFIR=true;
 	List <LatLng>  lat=new ArrayList<LatLng>();
 	List <LatLng>  latnow=new ArrayList<LatLng>();
 	private ImageButton local;
 	private ImageButton red;
 	LatLng lat1;
 	LatLng latlng;
 	List<lbs> listlbs=new ArrayList<lbs>();
 	LatLng lat2;
 	private LatLng lastLatLng;
 	private LocationClient locationclient;
 	private BitmapDescriptor  mIconLocation;
	protected static boolean isFirstLoc=true;
	protected static boolean isrequest=false;
	/* (non-Javadoc)
	 * @see android.support.v7.app.ActionBarActivity#onCreate(android.os.Bundle)
	 */
	BDLocationListener mylistener=new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation  location) {
			// TODO Auto-generated method stub
			 if (location == null) {  
	                return;   }  
			MyLocationData locationdata=new MyLocationData.Builder().
					accuracy(location.getRadius()).
					direction(location.getDerect()).
					latitude(location.getLatitude()).
					longitude(location.getLongitude()).build();
			baiduMap.setMyLocationData(locationdata);
			MyLocationConfiguration config=new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true,mIconLocation);
			    baiduMap.setMyLocationConfigeration(config);
				if (isFirstLoc || isrequest) {
					LatLng latreq = new LatLng(location.getLatitude(), location.getLongitude());
					MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latreq);
					baiduMap.animateMapStatus(u);
					isFirstLoc = false;
					isrequest = false;
				}
		}
 	};
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_redslocation);
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.follow);
		mapView=(MapView) findViewById(R.id.bmapView);
		baiduMap=mapView.getMap();
		baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);
		baiduMap.setMyLocationEnabled(true);
		 LocationClientOption optionlocation=new LocationClientOption();
			locationclient=new LocationClient(this);
			locationclient.registerLocationListener(mylistener);	
			optionlocation.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式  
			optionlocation.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02  
		    locationclient.setLocOption(optionlocation);
			locationclient.start(); // 开始定位	
		local=(ImageButton) findViewById(R.id.dingwei);
		local.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (locationclient != null && locationclient.isStarted()) {
					isrequest = true;
					locationclient.requestLocation();
				}
			 }
		});
		red=(ImageButton) findViewById(R.id.red);
		red.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(lat1!=null){
				MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(lat1);
				baiduMap.animateMapStatus(u);}else if(latlng!=null){
					 MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(latlng);
	    				baiduMap.animateMapStatus(u);
				}
			}
		});
		 baiduMap.setOnMapClickListener(new OnMapClickListener()  
	        {  
	            @Override  
	            public boolean onMapPoiClick(MapPoi arg0)  
	            {  
	                return false;  
	            }  
	            @Override  
	            public void onMapClick(LatLng arg0)  
	            {  
	            	 Mymarker.setVisibility(View.VISIBLE);  
	                baiduMap.hideInfoWindow();  
	            }  
	        });  
		
		Mymarker=findViewById(R.id.markerinfo);
		 baiduMap.setOnMapClickListener(new OnMapClickListener()  
	        {  
	            @Override  
	            public boolean onMapPoiClick(MapPoi arg0)  
	            {  
	                return false;  
	            }  
	            @Override  
	            public void onMapClick(LatLng arg0)  
	            {  
	                baiduMap.hideInfoWindow();  
	            }  
	        });  
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		trackbean tra= (com.whut.myMap.domain.trackbean) bundle.getSerializable("speak_item");
//		listlbs=new lbsdao(getApplication()).getlbsbytrackid(tra.getTrack().getTrack_id());
//		List<lbs>    list2=new ArrayList<lbs>();
//		list2=new lbsdao(getApplication()).getallbs(tra.getTrack().getTrack_id()); 
//		listlbs.addAll(list2);
		listlbs=new lbsdao(getApplication()).getallbs(tra.getTrack().getTrack_id()); 
		try {
		lat1 =new LatLng(listlbs.get(0).getLbsx(), listlbs.get(0).getLbsy());
	    lat2 =new LatLng(listlbs.get(listlbs.size()-1).getLbsx(), listlbs.get(listlbs.size()-1).getLbsy());
		BitmapDescriptor bitmapstart = BitmapDescriptorFactory  
 	            .fromResource(R.drawable.icon_track_navi_end);  
 	        //构建MarkerOption，用于在地图上添加Marker  
 	        OverlayOptions option1 = new MarkerOptions()  
 	            .position(lat1)  
 	            .icon(bitmapstart);  
 	       BitmapDescriptor bitmapend = BitmapDescriptorFactory  
 	 	            .fromResource(R.drawable.icon_track_navi_start);  
 	 	        //构建MarkerOption，用于在地图上添加Marker  
 	 	        OverlayOptions option2 = new MarkerOptions()  
 	 	            .position(lat2)  
 	 	            .icon(bitmapend);  
 	        //在地图上添加Marker，并显示
 	    baiduMap.addOverlay(option1);
		baiduMap.addOverlay(option2);
			for(int i=0;i<listlbs.size();i++){
				Log.i("lbs", listlbs.size()+" "+i);
				lbs lb=listlbs.get(i);			
				LatLng L=new LatLng(lb.getLbsx(), lb.getLbsy());
				lat.add(L);	
				latnow.add(L);
	            if(i>=1){
	            	double length=0;
	            	lbs lbfor=listlbs.get(i-1);
	            	List <LatLng>  latloc=new ArrayList<LatLng>();
	            	if(latnow.size()>1){
	            		length=DistanceUtil.getDistance(latnow.get(latnow.size()-1), latnow.get(latnow.size()-2));
	            	}else {
						length=0;
					}
	            	latloc.add(latnow.get(latnow.size()-1));
	            	latloc.add(latnow.get(latnow.size()-2));
	            	if(lb.getIscontain()==2||length>200){
	            		Log.i("lbs", lb.getLbsx()+"  "+lb.getLbsy()+"  "+i+" "+lb.getIscontain());
	            		lat.remove(lat.size()-1);
	            		if(lat.size()>1){
	            			OverlayOptions polygonOption1 = new PolylineOptions().color(Color.RED).width(3)
	                    			.points(lat); 
	        			   baiduMap.addOverlay(polygonOption1);
	            		}
	            		OverlayOptions polygonOption = new PolylineOptions().color(Color.BLUE).width(3)
	    	    			.points(latloc).dottedLine(true);  	
	            			 baiduMap.addOverlay(polygonOption);
	            			 lat=new ArrayList<LatLng>();	
	            	}
	            	else if(lbfor.getIscontain()==2)		
	            	{
	            		OverlayOptions polygonOption = new PolylineOptions().color(Color.BLUE).width(3)
		    	    			.points(latloc).dottedLine(true);  	
		            			 baiduMap.addOverlay(polygonOption);
	            	}
	            	if(i==listlbs.size()-1){
	            		OverlayOptions polygonOption1 = new PolylineOptions().color(Color.RED).width(3)
                    			.points(lat); 
        			   baiduMap.addOverlay(polygonOption1);
	            	}
//	            	else{
//	            		lat.add(L);	
//	            			}
	            }
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	 
    		 if(tra.getRedsbean()!=null&&tra.getRedsbean().size()>0){
    			 latlng=new LatLng(tra.getRedsbean().get(0).getReds().getRedsx(),tra.getRedsbean().get(0).getReds().getRedsy());
    	    for(redsbean redsbean:tra.getRedsbean()){
    		LatLng lat=new LatLng(redsbean.getReds().getRedsx(), redsbean.getReds().getRedsy());
    		if(redsbean.getReds().getIntrack()==1){
    			if(lastLatLng==null){
    				lastLatLng=lat;
    			}else{
    			List<LatLng> points = new ArrayList<LatLng>();
    			points.add(lastLatLng);
    			points.add(lat);
    			OverlayOptions polygonOption = new PolylineOptions().color(Color.BLUE)
    	    			.points(points).dottedLine(true);  	
    	    		 baiduMap.addOverlay(polygonOption);
    	    		 lastLatLng=lat;
    			}
    		}
    		 BitmapDescriptor bitmap = BitmapDescriptorFactory  
    		            .fromResource(R.drawable.flag);  
    		        //构建MarkerOption，用于在地图上添加Marker  
    		        OverlayOptions option= new MarkerOptions()  
    		            .position(lat)  
    		            .icon(bitmap);  
    		        //在地图上添加Marker，并显示
    		       Marker marker = (Marker) (baiduMap.addOverlay(option)); 
    		      Bundle bundle1 = new Bundle();  
    	         bundle1.putSerializable("redsbean", redsbean);  
    	         marker.setExtraInfo(bundle1);    
    		       baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {					
    					@Override
    					public boolean onMarkerClick(Marker marker) {
    						// TODO Auto-generated method stub
    						redsbean redsbean = (redsbean) marker.getExtraInfo().get("redsbean");  
    		                //生成一个TextView用户在地图中显示InfoWindow  
    		                TextView location = new TextView(getApplicationContext());   
    		                location.setPadding(30, 20, 30, 50);  
    		                location.setText(redsbean.getReds().getWords()); 
    		                location.setTextColor(Color.rgb(199,21,133));
    		                WindowManager wm = (WindowManager) getApplication()
    		                    .getSystemService(Context.WINDOW_SERVICE);
    		         int width = wm.getDefaultDisplay().getWidth();
    		          location.setMaxWidth(width*2/3);
    		                //将marker所在的经纬度的信息转化成屏幕上的坐标  
    		                final LatLng ll = marker.getPosition();  
    		                mInfoWindow =new InfoWindow(location, ll, -47);
    		              //显示InfoWindow  
    		                baiduMap.showInfoWindow(mInfoWindow);  
    		                //设置详细信息布局为可见  
   		                Mymarker.setVisibility(View.VISIBLE);  
    		                //根据商家信息为详细信息布局设置信息  
    		                popupInfo(Mymarker, redsbean);  
    		                return true;  
    					}
    					private void popupInfo(View mymarker,
    							final redsbean redsbean) {
    						// TODO Auto-generated method stub
    					        ViewHolder viewHolder = null;  
    					        if (mymarker.getTag() == null)  
    					        {  
    					            viewHolder = new ViewHolder();  
    					            viewHolder.infoImg = (ImageView) mymarker  
    					                    .findViewById(R.id.info_img);  
    					            viewHolder.infoName = (TextView) mymarker 
    					                    .findViewById(R.id.info_name);  
    					            viewHolder.infoZan = (TextView) mymarker  
    					                    .findViewById(R.id.info_zan);  
    					            viewHolder.zan=(ImageView) mymarker.findViewById(R.id.zan);
    					            viewHolder.but=(Button) mymarker.findViewById(R.id.but);
    					            mymarker.setTag(viewHolder);  
    					            viewHolder.but.setOnClickListener(new OnClickListener() {	
										@Override
										public void onClick(View v) {
											// TODO Auto-generated method stub
											Intent intent =new Intent(getApplication(),Speak_Main.class);
											Bundle bundle=new Bundle();
											bundle.putSerializable("speak_item", redsbean);
											intent.putExtras(bundle);
											startActivity(intent);
										}
									});
    					        }     					        
    					        viewHolder = (ViewHolder) mymarker.getTag();
    					        try {
									 picture picture =redsbean.getListmap().get(0);
									 DisplayImageOptions options = new DisplayImageOptions.Builder()//
	    								.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
	    								.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
	    								.cacheInMemory(true) //内存缓存
	    								.cacheOnDisk(true) //sdcard缓存
	    								.bitmapConfig(Config.RGB_565)//设置最低配置
	    								.build();//
	    						    ImageLoader.getInstance().displayImage("file://"+picture.getImgurl(),
	    						    viewHolder. infoImg  , options);   	
								} catch (Exception e) {
									// TODO: handle exception
									viewHolder. infoImg.setVisibility(View.GONE);
								}
    					    viewHolder.infoName.setText(redsbean.getReds().getWords());  
					        viewHolder.infoZan.setText(redsbean.getReds().getZan() + ""); 
					        viewHolder.zan.setImageResource(R.drawable.icon_details_collect_selected);
    					     }
    					}
    				);
    	}
	}
    		 lastLatLng=null;
}
	 @Override
		public void onDestroy() {
			super.onDestroy();
			// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
			locationclient.stop();
			baiduMap.clear();
			// TODO Auto-generated method stub
			mapView.onDestroy();
			mapView = null;
		}

		@Override
		public void onResume() {
			super.onResume();
			// 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
			mapView.onResume();
			locationclient.start();
		}

		public void onstart() {
			super.onStart();
			locationclient.start();
		}

		@Override
		public void onPause() {
			super.onPause();
			// 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
			mapView.onPause();
		}

		public void onStop() {
			super.onStop();
			// locationClient.stop();
			// myOrientationListener.stop();	
		}
		class ViewHolder
    	{  
    	    ImageView infoImg;  
    	    TextView infoName;   
    	    TextView infoZan;  
    	    ImageView zan;
    	    Button but;
    	}  
}	
