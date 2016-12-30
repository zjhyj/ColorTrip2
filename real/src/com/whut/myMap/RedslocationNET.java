package com.whut.myMap;


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
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.entites.picture;
import com.whut.myMap.utils.pictureutil;

import android.R.color;
import android.text.StaticLayout;
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
public class RedslocationNET extends Activity {
	 public MapView mapView = null;  
     public BaiduMap baiduMap = null;  
 	 private View Mymarker; 
 	 private ImageButton local;
 	 private ImageButton red;
 	private static LatLng lat;
 	 public LocationClient locationClient = null;
 	private BitmapDescriptor mIconLocation;
 	protected static boolean isFirstLoc=true;
	protected static boolean isrequest=true;
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
			optionlocation.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ���ö�λģʽ  
			optionlocation.setCoorType("bd09ll"); // ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02  
		    locationClient.setLocOption(optionlocation);
			locationClient.start(); // ��ʼ��λ	
		local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
						// TODO Auto-generated method stub
				isrequest=true;
						 if(locationClient != null &&locationClient.isStarted());
							 locationClient.requestLocation();
						 }
		});
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
	//	///////////////////////
		///////////////
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		redsbean redsbean=  (com.whut.myMap.domain.redsbean) bundle.getSerializable("speak_item");
	    lat=new LatLng(redsbean.getReds().getRedsx(), redsbean.getReds().getRedsy());
		MapStatusUpdate u=MapStatusUpdateFactory.newLatLng(lat);
		baiduMap.animateMapStatus(u);
   	    BitmapDescriptor bitmap = BitmapDescriptorFactory  
	            .fromResource(R.drawable.flag);  
	        //����MarkerOption�������ڵ�ͼ������Marker  
	        OverlayOptions option = new MarkerOptions()  
	            .position(lat)  
	            .icon(bitmap);  
	        //�ڵ�ͼ������Marker������ʾ
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
	                //����һ��TextView�û��ڵ�ͼ����ʾInfoWindow  
	                TextView location = new TextView(getApplicationContext());   
	                location.setTextColor(0xffcc0066);
	                location.setMaxWidth(40);
	                location.setPadding(30, 20, 30, 50);  
	                location.setText(speak.getReds().getWords()); 
	                //��marker���ڵľ�γ�ȵ���Ϣת������Ļ�ϵ�����  
	                final LatLng ll = marker.getPosition();  
//	                Point p = baiduMap.getProjection().toScreenLocation(ll);  
//	                Log.e(TAG, "--!" + p.x + " , " + p.y);  
//	                p.y -= 47;  
//	                LatLng llInfo = baiduMap.getProjection().fromScreenLocation(p); 
	                mInfoWindow =new InfoWindow(location, ll, -47);
	              //��ʾInfoWindow  
	                baiduMap.showInfoWindow(mInfoWindow);  
	                //������ϸ��Ϣ����Ϊ�ɼ�  
	                Mymarker.setVisibility(View.VISIBLE);  
	                //�����̼���ϢΪ��ϸ��Ϣ����������Ϣ  
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
								Intent intent =new Intent(getApplication(),Speak_MainNET.class);
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
							.showImageOnLoading(R.drawable.ic_launcher) // ��������ʾ��Ĭ��ͼƬ
							.showImageOnFail(R.drawable.ic_launcher) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
							.cacheInMemory(true) // �ڴ滺��
							.cacheOnDisk(true) // sdcard����
							.bitmapConfig(Config.RGB_565)// �����������
							.build();
					ImageLoader.getInstance().displayImage(speak.getListmap().get(0).getImgurl() ,viewHolder.infoImg, options);
					Log.e("imageurl", speak.getListmap().get(0).getImgurl());
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
			// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
			locationClient.stop();
			baiduMap.setMyLocationEnabled(false);
			baiduMap.clear();
			// TODO Auto-generated method stub
			mapView.onDestroy();
			mapView = null;
		}

		@Override
		public void onResume() {
			super.onResume();
			// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
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
			// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
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