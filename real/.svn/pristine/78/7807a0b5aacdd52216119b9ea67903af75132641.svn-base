package com.whut.myMap.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.whut.myMap.R;
import com.whut.myMap.TakePhoto;
import com.whut.myMap.entites.lbs2;
import com.whut.net.returnfans;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class locationservice extends Service {
	protected BaiduMap baiduMap;
    private final IBinder mBinder = new LocalBinder();
    private List<LatLng> list;
	protected List<lbs2> lat;
	private int trackid;
	BDLocationListener mylistener = new BDLocationListener() {
		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO Auto-generated method stub
			if (location == null) {
				return;
			}
			MyLocationData locationdata = new MyLocationData.Builder().accuracy(location.getRadius())
					.direction(location.getDerect()).latitude(location.getLatitude()).longitude(location.getLongitude())
					.build();
			baiduMap.setMyLocationData(locationdata);
			MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
					true,null);
			baiduMap.setMyLocationConfigeration(config);
			LatLng L = new LatLng(location.getLatitude(), location.getLongitude());
			LatLng latlng=new LatLng(list.get(list.size()-1).latitude,list.get(list.size()-1).longitude);
			if (DistanceUtil.getDistance(L, latlng) > 50) {
						return;
					} else {
							Date date=new Date(); 
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 String str=format.format(date);
							lat.add(new lbs2(0, L.latitude, L.longitude, trackid, 0,str));
					}
		}
	};
	        public class LocalBinder extends Binder{
		            locationservice getService() {
		               return locationservice.this;
		            }
		        }
		    
		        @Override
	        public IBinder onBind(Intent intent) {
		        	lat=new ArrayList<lbs2>();
		        	
		           return mBinder;
		        }
		    @Override
		    public boolean onUnbind(Intent intent){
		                    return false;
		    }
		    public void settrackid(int trackid) {
				this.trackid=trackid;
			}
		    public List<lbs2> getlbs() {
				return  lat;
			}
}
