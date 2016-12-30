package com.whut.myMap;


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
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.logic.task.StatisticsTask;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.entites.picture;
import com.whut.myMap.utils.pictureutil;

import android.R.color;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Redslocation extends Activity {
	 public MapView mapView = null;  
     public BaiduMap baiduMap = null;  
 	 private View Mymarker; 
 	 private ImageButton local;
 	 private ImageButton red;
 	private static LatLng lat;
 	 private BitmapDescriptor mIconLocation;
 	 private LocationClient locationClient;
 	protected static boolean isFirstLoc=true;
	protected static boolean isrequest=false;
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
				LatLng  firstloc=new LatLng(location.getLatitude(),location.getLongitude());
				if (isFirstLoc || isrequest) {
					MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(firstloc);
				    baiduMap.animateMapStatus(u);
					isFirstLoc = false;
					isrequest = false;
				}
				
		}
 	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_redslocation);
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.follow);
		mapView=(MapView) findViewById(R.id.bmapView);
		local=(ImageButton) findViewById(R.id.dingwei);
		Mymarker=findViewById(R.id.markerinfo);
		baiduMap=mapView.getMap();
		baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);
		baiduMap.setMyLocationEnabled(true);
		 LocationClientOption optionlocation=new LocationClientOption();
			locationClient=new LocationClient(this);
			locationClient.registerLocationListener(mylistener);	
			optionlocation.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式  
			optionlocation.setCoorType("bd09ll"); // 返回的定位结果是百度经纬度,默认值gcj02  
		    locationClient.setLocOption(optionlocation);
			locationClient.start(); // 开始定位	
		local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
						// TODO Auto-generated method stub
				if (locationClient != null && locationClient.isStarted()) {
					isrequest = true;
					locationClient.requestLocation();
				}
			}}
		);
		red=(ImageButton) findViewById(R.id.red);
		red.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(lat);
				baiduMap.animateMapStatus(u);
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
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		redsbean redsbean=  (com.whut.myMap.domain.redsbean) bundle.getSerializable("speak_item");
		 lat=new LatLng(redsbean.getReds().getRedsx(), redsbean.getReds().getRedsy());
		
		MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(lat);
		baiduMap.animateMapStatus(u);

		
   	     BitmapDescriptor bitmap = BitmapDescriptorFactory  
	            .fromResource(R.drawable.flag);  
	        //构建MarkerOption，用于在地图上添加Marker  
	        OverlayOptions option = new MarkerOptions()  
	            .position(lat)  
	            .icon(bitmap);  
	        //在地图上添加Marker，并显示
	       Marker marker = (Marker) (baiduMap.addOverlay(option)); 
	     bundle = new Bundle();  
         bundle.putSerializable("speak", redsbean);  
         marker.setExtraInfo(bundle);    
	       baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {					
				@Override
				public boolean onMarkerClick(Marker marker) {
					// TODO Auto-generated method stub
					redsbean speak =  (com.whut.myMap.domain.redsbean) marker.getExtraInfo().get("speak");  
	                InfoWindow mInfoWindow = null;  
	                //生成一个TextView用户在地图中显示InfoWindow  
	                TextView location = new TextView(getApplicationContext());   
	                location.setTextColor(0xffcc0066);
	                location.setMaxWidth(40);
	                location.setPadding(30, 20, 30, 50);  
	                location.setText(speak.getReds().getWords()); 
	                //将marker所在的经纬度的信息转化成屏幕上的坐标  
	                final LatLng ll = marker.getPosition();  
//	                Point p = baiduMap.getProjection().toScreenLocation(ll);  
//	                Log.e(TAG, "--!" + p.x + " , " + p.y);  
//	                p.y -= 47;  
//	                LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p); 
	                mInfoWindow =new InfoWindow(location, ll, -47);
	              //显示InfoWindow  
	                baiduMap.showInfoWindow(mInfoWindow);  
	                //设置详细信息布局为可见  
	                Mymarker.setVisibility(View.VISIBLE);  
	                //根据商家信息为详细信息布局设置信息  
	                popupInfo(Mymarker, speak);  
	                return true;  
				}
				private void popupInfo(View mymarker,
						final redsbean speak) {
					// TODO Auto-generated method stub
				        ViewHolder viewHolder = null;  
				        if (mymarker.getTag() == null)  
				        {  
				            viewHolder = new ViewHolder();  
				            viewHolder.infoImg = (ImageView) mymarker  
				                    .findViewById(R.id.info_img);  
				            viewHolder.infoName = (TextView) mymarker 
				                    .findViewById(R.id.info_name);  
				           viewHolder.zan=(ImageView) findViewById(R.id.zan);
				            viewHolder.infoZan = (TextView) mymarker  
				                    .findViewById(R.id.info_zan);  
				            viewHolder.but=(Button) findViewById(R.id.but);
				            viewHolder.but.setOnClickListener(new OnClickListener() {	
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
								Intent intent =new Intent(getApplication(),Speak_Main.class);
								Bundle bundle=new Bundle();
								bundle.putSerializable("speak_item", speak);
								intent.putExtras(bundle);
								startActivity(intent);
								}
							});				            
				            mymarker.setTag(viewHolder);  
				        }  
				        viewHolder = (ViewHolder) mymarker.getTag(); 
				        
				        if(speak.getListmap().get(0).getImgurl()==null){
				        	viewHolder.infoImg.setVisibility(View.GONE);
				        }else{
							DisplayImageOptions options = new DisplayImageOptions.Builder()//
							.showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
							.showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
							.cacheInMemory(true) // 内存缓存
							.cacheOnDisk(true) // sdcard缓存
							.bitmapConfig(Config.RGB_565)// 设置最低配置
							.build();//
					ImageLoader.getInstance().displayImage("file://"+speak.getListmap().get(0).getImgurl(),
							viewHolder.infoImg, options);
				        } 
				        viewHolder.infoName.setText(speak.getReds().getWords());
				        viewHolder.zan.setImageResource(R.drawable.icon_details_collect_selected);
				        viewHolder.infoZan.setText(speak.getReds().getZan() + "");  				        
				        }  				
	       });	
	}
	 @Override
		public void onDestroy() {
			super.onDestroy();
			// 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
			locationClient.stop();
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
			locationClient.start();
		}

		public void onstart() {
			super.onStart();
			
			locationClient.start();
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
	    ImageView zan;  
	    Button but;
	    TextView infoZan;  
	}  
	
}
