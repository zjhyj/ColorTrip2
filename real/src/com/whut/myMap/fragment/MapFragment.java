package com.whut.myMap.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.DistanceUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ChooseConcernTravel;
import com.whut.myMap.R;
import com.whut.myMap.Savetrack;
import com.whut.myMap.Speak_MainNET;
import com.whut.myMap.TakePhoto;
import com.whut.myMap.sharetrack;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.lbs2;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.track;
import com.whut.myMap.serverce.trackservice;
import com.whut.myMap.utils.MyOrientationListener;
import com.whut.net.getcountnet;
import com.whut.net.getlocalredsnet;
import com.whut.net.priseservicenet;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Point;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout.LayoutParams;

public class MapFragment extends Fragment {
	protected static final int SHOW_RESPONSE = 0;
	protected static final int SHOW_RESPONSE_like = 1;
	MapView mapView = null;
	private View mParent;
	SupportMapFragment map;
	private FragmentActivity mActivity;
	public BaiduMap baiduMap = null;
	private View Mymarker;
	public LocationClient locationClient = null;
	public TextView text1;
	public TextView text2;
	LatLng lat1=null ;
 	LatLng lat2=null;
	public ImageButton weixing;
	public ImageButton jiluguiji;
	public ImageButton dingwei;
	public ImageButton zengjia;
	public ImageButton chooseconcerntravel;
	private static double latx;
	private static double laty;
	int mapType = 0;
	boolean showResource = false;
	boolean showPublic = false;
    private boolean old=false;
	// �Զ��嶨λͼ��
	private BitmapDescriptor mIconLocation;
	private MyOrientationListener myOrientationListener;
	LocationClient locationclient;
	static boolean isrequest = false;
	static boolean isFirstLoc = true;// �Ƿ��״ζ�λ
	static private boolean isjilu = false;
	static private boolean isfirstjilu = true;
	static private boolean isshowtravel = false;
	private static boolean iscontain = false;
	// private static boolean isfirstsousuo = true;
	static int trackid = 0;
	protected static boolean isaddcom;
	static int likenumber;
	private String jsonstring;
	static private List<lbs2> lat;
	private List<LatLng> latall=new ArrayList<LatLng>();
	private List<LatLng> latnow=new ArrayList<LatLng>();
	List <LatLng>  latlng=new ArrayList<LatLng>();
	private static List<LatLng> listlat;
	private static String address;
	protected final int SERVICE_LBS = 2;
	protected String result;
	private static boolean isdo=false;
//	private static Marker marker2;
	private static List<Overlay> listoverlaylocal = new ArrayList<Overlay>();
	private static List<Overlay> listoverlayjilu = new ArrayList<Overlay>();
	private static List<Overlay> listoverlayservice = new ArrayList<Overlay>();
	private static List<Overlay> listoverlayfavor=new ArrayList<Overlay>();
	BitmapDescriptor mCurrentMarker = null; // �Զ���ͼ��
	protected static final int ACTIVITY_RESULT=2;
	protected static final int SHOW_CONCERN_TRAVEL = 7;
	/** Notification������ */
	NotificationCompat.Builder mBuilder;
	/** Notification��ID */
	int notifyId = 100;
	static NotificationManager mNotificationManager;
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
					true, mIconLocation);
			baiduMap.setMyLocationConfigeration(config);
			if (isFirstLoc || isrequest) {
				LatLng firstloc = new LatLng(location.getLatitude(), location.getLongitude());
				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(firstloc);
				baiduMap.animateMapStatus(u);
				isFirstLoc = false;
				isrequest = false;
			}
			latx = location.getLatitude();
			laty = location.getLongitude();
			if (isjilu) {
				if (isfirstjilu) {
					isfirstjilu = false;
					LatLng L = new LatLng(location.getLatitude(), location.getLongitude());
					Date date=new Date(); 
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					 String str=format.format(date);
					lat.add(new lbs2(0, L.latitude, L.longitude, trackid, 0,str));
					listlat.add(new LatLng(L.latitude, L.longitude));
					BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_track_navi_end);
					// ����MarkerOption�������ڵ�ͼ������Marker
					OverlayOptions option = new MarkerOptions().position(L).icon(bitmap);
					// �ڵ�ͼ������Marker������ʾ
					Overlay overlay = baiduMap.addOverlay(option);
					listoverlayjilu.add(overlay);
				} else {
					LatLng L = new LatLng(location.getLatitude(), location.getLongitude());
					LatLng latlng = new LatLng(lat.get(lat.size() - 1).getLbsx(), lat.get(lat.size() - 1).getLbsy());
					if (DistanceUtil.getDistance(L, latlng) > 50) {
						return;
					} else {
						if (iscontain) {
							Date date=new Date(); 
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 String str=format.format(date);
							lat.add(new lbs2(0, L.latitude, L.longitude, trackid, 1,str));
							listlat.add(new LatLng(L.latitude, L.longitude));
							Log.i("iscontain", 1 + "");
							iscontain = false;
							Intent intent = new Intent(getActivity(), TakePhoto.class);
							intent.putExtra("trackid", trackid);
							intent.putExtra("lat", L.latitude);
							intent.putExtra("lon", L.longitude);
							startActivity(intent);
						} else {
							Date date=new Date(); 
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							 String str=format.format(date);
							lat.add(new lbs2(0, L.latitude, L.longitude, trackid, 0,str));
							listlat.add(new LatLng(L.latitude, L.longitude));
							Log.i("iscontain", 0 + "");
						}
						if (lat.size() > 1) {
//							List<LatLng> list=new ArrayList<LatLng>();
//							list.add(listlat.get(listlat.size()-2));
//							list.add(listlat.get(listlat.size()-1));
							OverlayOptions ooPolyline = new PolylineOptions().width(5).color(0x4B5CC400).points(listlat)
									.visible(true);
							listoverlayjilu.add(baiduMap.addOverlay(ooPolyline));
						}
					}
				}
			}
		}
	};
	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static MapFragment newInstance(int index) {
		MapFragment f = new MapFragment();
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("index", index);
		f.setArguments(args);
		return f;
	}

	public int getShownIndex() {
		return getArguments().getInt("index", 0);
	}

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		SDKInitializer.initialize(getActivity().getApplicationContext());
		View view = inflater.inflate(R.layout.fragment_map, container, false);
		return view;
	}
	private void priseservice(boolean b, int i) {
		// TODO Auto-generated method stub
		
		new priseservicenet().priseservice(b,i);
		Log.i("serviceid",i+"");
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mActivity = getActivity();
		mParent = getView();
		initNotify();
		mapView = (MapView) mParent.findViewById(R.id.bmapView);
		Mymarker = mParent.findViewById(R.id.markerinfo);
		mapView = (MapView) mParent.findViewById(R.id.bmapView);
		mapView.removeViewAt(1);
		mapView.showScaleControl(false);
		
		baiduMap = mapView.getMap();
		baiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			TextView good_number;
			TextView bad_number ;
			TextView public_name;
			@Override
			public boolean onMarkerClick(Marker marker) {
				// TODO Auto-generated method stub
				isdo=false;
			if((Integer) marker.getExtraInfo().get("is")==2){
				redsbean redsbean = (redsbean) marker.getExtraInfo().get("redsbean");  
	                InfoWindow mInfoWindow = null;  
	                //����һ��TextView�û��ڵ�ͼ����ʾInfoWindow  
	                TextView location = new TextView(getActivity());   
	                location.setPadding(30, 20, 30, 50);  
	                location.setText(redsbean.getReds().getWords()); 
	                location.setTextColor(Color.rgb(199,21,133));
	            
	                WindowManager wm = (WindowManager)getActivity()
	                    .getSystemService(Context.WINDOW_SERVICE);
	         int width = wm.getDefaultDisplay().getWidth();
	          location.setMaxWidth(width*2/3);
	                //��marker���ڵľ�γ�ȵ���Ϣת������Ļ�ϵ�����  
	                final LatLng ll = marker.getPosition();  
	                mInfoWindow =new InfoWindow(location, ll, -47);
	              //��ʾInfoWindow  
	                baiduMap.showInfoWindow(mInfoWindow);  
	                //������ϸ��Ϣ����Ϊ�ɼ�  
                Mymarker.setVisibility(View.VISIBLE);  
	                //�����̼���ϢΪ��ϸ��Ϣ����������Ϣ  
	                popupInfo(Mymarker, redsbean);  
	                return true;  
				}
				return false;
			}
		});
		baiduMap.setMapType(baiduMap.MAP_TYPE_NORMAL);
		baiduMap.setMyLocationEnabled(true);
		locationClient = new LocationClient(getActivity().getApplicationContext());
		locationClient.registerLocationListener(mylistener);
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll"); // ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(1000); // ���÷���λ����ļ��ʱ��Ϊ2000ms
		option.setIsNeedAddress(true); // ���صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true); // ���صĶ�λ��������ֻ���ͷ�ķ���
		option.setOpenGps(true);
		// ��ʼ��ͼ��
		mIconLocation = BitmapDescriptorFactory.fromResource(R.drawable.follow);
		myOrientationListener = new MyOrientationListener(mActivity);
		myOrientationListener
				.setOnOrientationListener(new com.whut.myMap.utils.MyOrientationListener.OnOrientationListener() {
					public void onOrientationChanged(float x, float lastX) {
						RotateAnimation ra = new RotateAnimation(lastX, -x, Animation.RELATIVE_TO_SELF, 0.5f,
								Animation.RELATIVE_TO_SELF, 0.5f);
					}
				});
		locationClient.setLocOption(option);
		locationClient.start(); // ��ʼ��λ
		myOrientationListener.start();
		LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
		if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			dialogopengps();
		}
		// button��ȡȥ���ü����¼�
		weixing = (ImageButton) mParent.findViewById(R.id.weixing);
		jiluguiji = (ImageButton) mParent.findViewById(R.id.jiluguiji);
		dingwei = (ImageButton) mParent.findViewById(R.id.dingwei);
		zengjia = (ImageButton) mParent.findViewById(R.id.zengjia);
		chooseconcerntravel = (ImageButton) mParent.findViewById(R.id.concern_travel);
		weixing.setOnClickListener(new myweixing());
		jiluguiji.setOnClickListener(new myjiluguiji());
		dingwei.setOnClickListener(new mydingwei());
		zengjia.setOnClickListener(new myzengjia());
		chooseconcerntravel.setOnClickListener(new myconcerntravel());
		baiduMap.setOnMapClickListener(new OnMapClickListener() {
			@Override
			public boolean onMapPoiClick(MapPoi arg0) {
				return false;
			}

			@Override
			public void onMapClick(LatLng arg0) {
				Mymarker.setVisibility(View.GONE);
				baiduMap.hideInfoWindow();
			}
		});
		baiduMap.setOnMapDoubleClickListener(new OnMapDoubleClickListener() {
			@Override
			public void onMapDoubleClick(LatLng arg0) {
				// TODO Auto-generated method stub
				// baiduMap.clear();
			}
		});

		//com.capricorn.RayMenu rayMenu = (com.capricorn.RayMenu) mParent.findViewById(R.id.ray_menu);
		/**
		final int itemCount = ITEM_DRAWABLES.length;
		for (int i = 0; i < itemCount; i++) {
			ImageView item = new ImageView(getActivity());
			item.setImageResource(ITEM_DRAWABLES[i]);
			final int position = i;
			rayMenu.addItem(item, new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (position == 0) {
						type = 0;
					} else if (position == 1) {
						type = 1;
					} else if (position == 2) {
						type = 2;
					} else {
						return;
					}
					dialogaddcom(type);
				}
			});// Add a menu item
		}*/
	}
	
	private void popupInfo(View mymarker,final redsbean redsbean) {
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
						Intent intent =new Intent(getActivity(),Speak_MainNET.class);
						Bundle bundle=new Bundle();
						bundle.putSerializable("speak_item", redsbean);
						intent.putExtras(bundle);
						startActivity(intent);
					}
				});
	        }     					        
	        viewHolder = (ViewHolder) mymarker.getTag();
	        if(null!=redsbean.getListmap()&&redsbean.getListmap().size()>0){
	        	 picture picture =redsbean.getListmap().get(0);
	        	 DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true) //�ڴ滺��
					.cacheOnDisk(true) //sdcard����
					.bitmapConfig(Config.RGB_565)//�����������
					.build();//
			    ImageLoader.getInstance().displayImage(picture.getImgurl(),
			    viewHolder.infoImg , options);   			
	        }else{
	        	viewHolder. infoImg.setVisibility(View.GONE);
	        }
	    viewHolder.infoName.setText(redsbean.getReds().getWords());  
        viewHolder.infoZan.setText(redsbean.getReds().getZan() + ""); 
        viewHolder.zan.setImageResource(R.drawable.icon_details_collect_selected);
}
	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
	}
	
	public void dialogopengps() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("����GPS�������ܸ��Ӿ�ȷ�Ķ�λ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(intent, 0); // ������ɺ󷵻ص�ԭ���Ľ���
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}
	public void dialogaddreds() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("�Ƿ񽫴˶�̬�����ڹ켣�ϣ�");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				iscontain = true;
				locationClient.requestLocation();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(getActivity(), TakePhoto.class);
				intent.putExtra("trackid", 0);
				intent.putExtra("lat", 0.0);
				intent.putExtra("lon", 0.0);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void dialoggps() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setMessage("�켣��¼�����GPS���Ƿ������");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// ת���ֻ����ý��棬�û�����GPS
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(intent, 0); // ������ɺ󷵻ص�ԭ���Ľ���
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	public void dialogtrack(final List<lbs2> lat2) {
		final trackservice service = new trackservice(mActivity);
		AlertDialog.Builder builder = new Builder(getActivity());
		builder.setTitle("��ʾ��").setMessage("����Ҫ�����򱣴�˴μ�¼��?");
		builder.setPositiveButton("����", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method
				if (lat2.size() < 2) {
					Toast.makeText(getActivity(), "������Ĺ켣��С���������������ɼ�¼��", 0).show();
					;
					new trackservice(mActivity).deletemaxtrack(mActivity);
					return;
				}
				service.addlbs(lat2, mActivity);
				trackbean trackbean = service.gettrack(mActivity, trackid);
				if (trackbean == null) {
					Log.i("id", "kong");
				}
				Intent intent = new Intent(mActivity, sharetrack.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("trackbean", trackbean);
				intent.putExtras(bundle);
				startActivity(intent);
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("����", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (lat2.size() < 2) {
					Toast.makeText(getActivity(), "������Ĺ켣��С���������������ɼ�¼��", 0).show();
					
					new trackservice(mActivity).deletemaxtrack(mActivity);
					return;
				}
				if(!old){
				service.addlbs(lat2, mActivity);
				Log.i("trackid1", trackid + "");
				Intent intent = new Intent(mActivity, Savetrack.class);
				intent.putExtra("trackid", trackid);
				startActivity(intent);
				trackid = 0;
				dialog.dismiss();}
			}
		});
		builder.setNeutralButton("������", new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				new trackservice(mActivity).deletemaxtrack(mActivity);
				dialog.dismiss();
				return;
			}
		});
		builder.create().show();
	}
	private void showPopupWindow(View view) {
		// һ���Զ���Ĳ��֣���Ϊ��ʾ������
		View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popupwindow, null);
		final ImageView normal = (ImageView) contentView.findViewById(R.id.normal_map_btn);
		final ImageView satellite = (ImageView) contentView.findViewById(R.id.satellite_map_btn);
		// ��ImageView��ɻ�ɫ
		ColorMatrix matrix = new ColorMatrix();
		matrix.setSaturation(0);
		final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
		matrix.setSaturation(1);
		final ColorMatrixColorFilter filter2 = new ColorMatrixColorFilter(matrix);
		if (mapType == 0) {
			satellite.setColorFilter(filter);
		} else {
			normal.setColorFilter(filter);
		}
		// ��ʾ��ͨͼ
		normal.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapType = 0;
				satellite.setColorFilter(filter);
				normal.setColorFilter(filter2);
				baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
				// weixing.setText("��");
				Toast.makeText(getActivity(), "��ͨͼ", 0).show();
			}
		});
		// ��ʾ����ͼ
		satellite.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mapType = 1;
				normal.setColorFilter(filter);
				satellite.setColorFilter(filter2);
				baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
				// weixing.setText("��");
				Toast.makeText(getActivity(), "����ͼ", 0).show();
			}
		});
		// ������ť����
		ToggleButton source = (ToggleButton) contentView.findViewById(R.id.show_source);

		source.setChecked(showResource);
	
		source.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if (!showResource) {
					showResource = true;
					// ��ʾ������Դ
					int b = mapView.getBottom();
					int t = mapView.getTop();
					int r = mapView.getRight();
					int l = mapView.getLeft();
					LatLng ne = baiduMap.getProjection().fromScreenLocation(new Point(r, t));
					LatLng sw = baiduMap.getProjection().fromScreenLocation(new Point(l, b));
					getData(ne, sw);
				} else {
					showResource = false;
					// ����ʾ������Դ
					for (Overlay overlay : listoverlaylocal) {
						overlay.remove();
					}
					listoverlaylocal = new ArrayList<Overlay>();
				}
			}
		});
		
		// popupwindow����
		final PopupWindow popupWindow = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
		LayoutParams.WRAP_CONTENT, true);
		popupWindow.setTouchable(true);
		popupWindow.setTouchInterceptor(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("mengdd", "onTouch : ");
				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});
		// ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		// �Ҿ���������API��һ��bug
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		popupWindow.setBackgroundDrawable(dw);
		popupWindow.setAnimationStyle(R.style.popwin_anim_style);
		// ���úò���֮����show
		popupWindow.showAsDropDown(view);
		WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
		lp.alpha = 0.7f;
		getActivity().getWindow().setAttributes(lp);
		popupWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1f;
				getActivity().getWindow().setAttributes(lp);
			}
		});

	}

	 /**
	   * 
	   * ��ʾ�ղص��μ�λ��
	   * @author xxr
	   *
	   */
		public class myconcerntravel implements OnClickListener {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!isshowtravel) {
					isshowtravel = true;
					int b = mapView.getBottom();
					int t = mapView.getTop();
					int r = mapView.getRight();
					int l = mapView.getLeft();
					LatLng ne = baiduMap.getProjection().fromScreenLocation(new Point(r, t));
					LatLng sw = baiduMap.getProjection().fromScreenLocation(new Point(l, b));
					chooseconcerntravel.setImageResource(R.drawable.choose_concern_travel_pressed);
					Intent intent = new Intent(getActivity(), ChooseConcernTravel.class);
					intent.putExtra("lef", sw.latitude);
					intent.putExtra("rig", ne.latitude);
					intent.putExtra("top", sw.longitude);
					intent.putExtra("bot", ne.longitude);
					startActivityForResult(intent, SHOW_CONCERN_TRAVEL);
				} else {
					isshowtravel = false;
					for(Overlay overlay:listoverlayfavor){
						overlay.remove();
					}
					chooseconcerntravel.setImageResource(R.drawable.choose_concern_travel);
				    listoverlayfavor=new ArrayList<Overlay>();
				}
			}
		}
	public class myjiluguiji implements OnClickListener {
		trackservice service = new trackservice(mActivity);
		@Override
		public void onClick(View v) {
			if (!isjilu) {
				LocationManager locationManager = (LocationManager) getActivity()
						.getSystemService(Context.LOCATION_SERVICE);
				if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
					dialoggps();
				}
				if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
//					final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//					builder.setTitle("ѡ��켣���ӷ�ʽ");
//					// ָ�������б�����ʾ����
//					final String[] options = { "���ӵ������μ�", "�������μ�" };
//					// ����һ���������б�ѡ����
//					builder.setItems(options, new DialogInterface.OnClickListener() {
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							switch (which) {
//							case 0:
//								Intent intent = new Intent(getActivity(), ChooseRoute.class);
//								startActivityForResult(intent,  ACTIVITY_RESULT);
//								getActivity().overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
//								old=true;
//								startNotification();
//								Toast.makeText(getActivity(), "���㼣��¼", 0).show();
//								isjilu = true;
//								isfirstjilu = true;
//								lat = new ArrayList<lbs2>();
//								listlat = new ArrayList<LatLng>();
//								jiluguiji.setImageResource(R.drawable.track_icon_timeline_custom_sync);
//								break;
//							case 1:
//								old=false;
								final trackservice trackservice = new trackservice(mActivity);
								Date date = new Date();
								SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								final String str = format.format(date);
								while (true) {
									if (locationClient != null && locationClient.isStarted()) {
										locationClient.requestLocation();
										Log.i("latlng", latx + "  " + laty);
										break;
									}
								}
								GeoCoder geo = GeoCoder.newInstance();
								OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
									// �����������ѯ����ص�����
									@Override
									public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
										String local;
										if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
											// û�м�⵽���
											return;
										}
										local=result.getAddressDetail().province+" "+result.getAddressDetail().city;
										System.out.println(local);
										trackid = trackservice.addtrack(new track(0, 0, null, str, 1, 0, null,0,local,0,null), mActivity);
									}
									// ���������ѯ����ص�����
									@Override
									public void onGetGeoCodeResult(GeoCodeResult result) {
										if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
											// û�м�⵽���
										}
									}
								};
								geo.setOnGetGeoCodeResultListener(listener);
								geo.reverseGeoCode(new ReverseGeoCodeOption().location(new LatLng(latx,laty)));
								if (locationClient != null && locationClient.isStarted()) {
									isrequest = true;
									locationClient.requestLocation();
								}
								startNotification();
								Toast.makeText(getActivity(), "�򿪹켣��¼", 0).show();
								isjilu = true;
								isfirstjilu = true;
								lat = new ArrayList<lbs2>();
								listlat = new ArrayList<LatLng>();
								jiluguiji.setImageResource(R.drawable.track_icon_timeline_custom_sync);
//								break;
//							default:
//								break;
//							}
							}
//					});
//					builder.show();
//					
//					}
			} else {
				isjilu = false;
				isfirstjilu = true;
				jiluguiji.setImageResource(R.drawable.track_icon_timeline_custom);
				Toast.makeText(getActivity(), "�رչ켣��¼", 0).show();
				closeNofitation();
				if(!old){
				dialogtrack(lat);}
				for (Overlay overlay : listoverlayjilu) {
					overlay.remove();
				}
				listoverlayjilu = new ArrayList<Overlay>();
			}
		}
	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		 if (resultCode != Activity.RESULT_OK) {// result is not correct
				return;
			}else{
		switch (requestCode) {
		case ACTIVITY_RESULT:
			trackbean trackbean=new trackbean();
			trackbean=(com.whut.myMap.domain.trackbean) data.getSerializableExtra("ROUTE");
			trackid=trackbean.getTrack().getTrack_id();
			System.out.println("okok");
			//��ʼ��¼gui'j
				break;	
		case SHOW_CONCERN_TRAVEL:
			trackbean trackbean2 =new trackbean();
			trackbean2= (com.whut.myMap.domain.trackbean) data.getSerializableExtra("trackbean");
			if (trackbean2!=null) {
					showfavor(trackbean2);
			Log.i("travel", "show");
			} else {
				isshowtravel = false;
				chooseconcerntravel.setImageResource(R.drawable.choose_concern_travel);
			}
		
			}
			}
	}	
	public void showfavor(trackbean tra){
		try {
			 lat1 =new LatLng(tra.getLbsbean().get(0).getLbsx(), tra.getLbsbean().get(0).getLbsy());
			 lat2 =new LatLng(tra.getLbsbean().get(tra.getLbsbean().size()-1).getLbsx(), tra.getLbsbean().get(tra.getLbsbean().size()-1).getLbsy());
			 BitmapDescriptor bitmapstart = BitmapDescriptorFactory.fromResource(R.drawable.icon_track_navi_end);  
	 	        //����MarkerOption�������ڵ�ͼ������Marker  
	 	     OverlayOptions option1 = new MarkerOptions().position(lat1).icon(bitmapstart);  
	 	     BitmapDescriptor bitmapend = BitmapDescriptorFactory.fromResource(R.drawable.icon_track_navi_start); 
	 	 	   //����MarkerOption�������ڵ�ͼ������Marker  
	 	 	 OverlayOptions option2 = new MarkerOptions().position(lat2).icon(bitmapend);  
	 	        //�ڵ�ͼ������Marker������ʾ
	 	    listoverlayfavor.add(baiduMap.addOverlay(option1));
			listoverlayfavor.add(baiduMap.addOverlay(option2));
			List<lbs> listlbs=tra.getLbsbean();
			Log.i("lbslength",listlbs.size() +"");
			latall=new ArrayList<LatLng>();
			latnow=new ArrayList<LatLng>();
			for(int i=0;i<listlbs.size();i++){
				Log.i("lbs", listlbs.size()+" "+i);
				lbs lb=listlbs.get(i);			
				LatLng L=new LatLng(lb.getLbsx(), lb.getLbsy());
				latall.add(L);	
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
	            		latall.remove(latall.size()-1);
	            		if(latall.size()>1){
	            			OverlayOptions polygonOption1 = new PolylineOptions().color(Color.RED).width(3)
	                    			.points(latall); 
	            			listoverlayfavor.add( baiduMap.addOverlay(polygonOption1));
	            		}
	            		OverlayOptions polygonOption = new PolylineOptions().color(Color.BLUE).width(3)
	    	    			.points(latloc).dottedLine(true);  	
	            		listoverlayfavor.add(baiduMap.addOverlay(polygonOption));
	            			 latall=new ArrayList<LatLng>();	
	            	}
	            	else if(lbfor.getIscontain()==2)		
	            	{
	            		OverlayOptions polygonOption = new PolylineOptions().color(Color.BLUE).width(3)
		    	    			.points(latloc).dottedLine(true);  	
	            		listoverlayfavor.add(baiduMap.addOverlay(polygonOption));
	            	}
	            	if(i==listlbs.size()-1){
	            		OverlayOptions polygonOption1 = new PolylineOptions().color(Color.RED).width(3)
                    			.points(latall); 
	            		listoverlayfavor.add(baiduMap.addOverlay(polygonOption1));
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
		try {
			if(tra.getRedsbean()!=null&&tra.getRedsbean().size()>0){
	    	    for(redsbean redsbean:tra.getRedsbean()){
	    		LatLng lat=new LatLng(redsbean.getReds().getRedsx(), redsbean.getReds().getRedsy());
	    		 BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.flag);  
	    		        //����MarkerOption�������ڵ�ͼ������Marker  
	    		        OverlayOptions option= new MarkerOptions()  
	    		            .position(lat)  
	    		            .icon(bitmap);  
	    		        //�ڵ�ͼ������Marker������ʾ
	    		    Marker marker = (Marker) (baiduMap.addOverlay(option)); 
	    		    listoverlayfavor.add(marker);
	    		    Bundle bundle1 = new Bundle(); 
	    		    bundle1.putInt("is", 2);
	    	        bundle1.putSerializable("redsbean", redsbean);  
	    	        marker.setExtraInfo(bundle1);    
	    			
	    	}
	    	    	    }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	    	
	}
	class ViewHolder
	{  
	    ImageView infoImg;  
	    TextView infoName;   
	    TextView infoZan;  
	    ImageView zan;
	    Button but;
	}  
	
private void startNotification(){
	mNotificationManager = (NotificationManager) getActivity().getSystemService("notification");
	mBuilder.setContentTitle("�������")
	.setContentText("���ڼ�¼�켣")
//	.setNumber(number)//��ʾ����
	.setTicker("��ʼ��¼�켣");//֪ͨ�״γ�����֪ͨ��������������Ч����
mNotificationManager.notify(notifyId, mBuilder.build());
}
	public void closeNofitation() {
	// TODO Auto-generated method stub
		mNotificationManager.cancel(notifyId);
}
	public class mydingwei implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (locationClient != null && locationClient.isStarted()) {
				isrequest = true;
				locationClient.requestLocation();
			}
		}
	}
	private void getData(final LatLng ne, final LatLng sw) {
		// Variable variable=new Variable();
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					getlocalredsnet getlocalredsnet = new getlocalredsnet();
					String jsonstring1 = getlocalredsnet.getlocal(ne, sw);
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
					message.what = SHOW_RESPONSE;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			
			
			case SHOW_RESPONSE_like:
				likenumber=0;
				likenumber = Integer.parseInt((String) msg.obj);
				break;
			}
		}
	};


	private void getcount(final int clusterid) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			String result;
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					getcountnet getcountnet = new getcountnet();
					result = getcountnet.getlocal(clusterid);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					result = "99999";
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					message.what = SHOW_RESPONSE_like;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	public class mysousuo implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		};
	}

	// class ViewHolder {
	// TextView address;
	// TextView length;
	// TextView like;
	// TextView more;
	// }
	public class myweixing implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			showPopupWindow(v);
		}

	}

	public class myzengjia implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (isjilu) {
				dialogaddreds();
			} else {
				Intent intent = new Intent(getActivity(), TakePhoto.class);
				intent.putExtra("trackid", 0);
				intent.putExtra("lat", 0);
				intent.putExtra("lon", 0);
				startActivity(intent);
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// ��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���
		Log.i("tag","ondestroy");
		locationClient.stop();
		//myOrientationListener.stop();
		baiduMap.setMyLocationEnabled(false);
		baiduMap.clear();
		// TODO Auto-generated method stub
		mapView.onDestroy();
		mapView = null;
	}
	/** ��ʼ��֪ͨ�� */
	private void initNotify(){
		mBuilder = new NotificationCompat.Builder(getActivity());
		mBuilder.setContentTitle("���Ա���")
				.setContentText("��������")
//				.setNumber(number)//��ʾ����
				.setTicker("����֪ͨ����")//֪ͨ�״γ�����֪ͨ��������������Ч����
				.setWhen(System.currentTimeMillis())//֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
				.setPriority(Notification.PRIORITY_DEFAULT)//���ø�֪ͨ���ȼ�
//				.setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��  
				.setOngoing(false)//ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
				.setDefaults(Notification.DEFAULT_VIBRATE)//��֪ͨ�������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND �������� // requires VIBRATE permission
				.setSmallIcon(R.drawable.ic_launcher);
	}
	@Override
	public void onResume() {
		super.onResume();
		// ��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���
		Log.i("tag","onresume");

		mapView.onResume();
		locationClient.start();
		myOrientationListener.start();
	}
	public void onstart() {
		super.onStart();
		Log.i("tag","onstart");

		locationClient.start();
		myOrientationListener.start();
	}	
	@Override
	public void onPause() {
		super.onPause();
		Log.i("tag","onpouse");

		// ��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���
		mapView.onPause();
	}
	public void onStop() {
		super.onStop();
		Log.i("tag","onstop");

		// locationClient.stop();
		// myOrientationListener.stop();
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}