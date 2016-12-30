package com.whut.myMap.fragment;

import java.io.FileNotFoundException;
import java.util.Map;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ChangeSet;
import com.whut.myMap.CreateNewTravel;
import com.whut.myMap.MyDongtai;
import com.whut.myMap.MyFootPrint;
import com.whut.myMap.MyRate;
import com.whut.myMap.R;
import com.whut.myMap.UserInfo;
import com.whut.myMap.domain.medal;

import com.whut.myMap.entites.url;
import com.whut.myMap.entites.user;
import com.whut.myMap.layout.BadgeView;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.getservicelbsnet;
import com.whut.net.getusernet;
import com.whut.net.returnlistadviser;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class SettingsFragment extends Fragment {

	private View mParent;
	protected static String jsonstring;
	private static final int SHOW_RESPONSE = 0;
	private static final int INFO = 1;
	private static final int MAP = 2;
	protected static final int ADVISER = 3;
	private FragmentActivity mActivity;
	private ImageView image;
	private TextView fs_username;
	private TextView words;
	private static user user;
	
	private ProgressDialog pd;
	private static BadgeView badgeView;
	private PushReceiverBadge pushReceiver;
	static NotificationCompat.Builder mBuilder;
	static NotificationManager mNotificationManager;
	private static int length;
	private static int track;
	private static int rank;
	/** Notification的ID */
	static int notifyId = 100;
	protected Map<String, Integer> info;
	// private static int count = 0;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");

					gsonutil gson = new gsonutil();
					user = new user();
					try {
					user = gson.getobject(jsonstring, user.class);
				
					} catch (Exception e) {
						// TODO: handle exception
						user = new user();
					}
					show();
				} else {
					Toast.makeText(getActivity(), "获取数据失败，请重新尝试！", 1).show();
					return;
				}
				break;
			}
		}
	};
	// private static String[] names = { "我的足迹", "收藏夹", "我的评论", "我的回复", "我的动态",
	// "我的信息", "关于软件" };
	// private static int[] pic = { R.drawable.icon_card_foot_blue,
	// R.drawable.icon_details_collect_normal,
	// R.drawable.icon_track_map_bar_cluster,
	// R.drawable.track_icon_timeline_foot,
	// R.drawable.addpoi_record_comment,
	// R.drawable.lbspay_location_refresh_bdmap, R.drawable.bd_wallet_faq_icon
	// };

	/**
	 * Create a new instance of DetailsFragment, initialized to show the text at
	 * 'index'.
	 */
	public static SettingsFragment newInstance(int index) {
		SettingsFragment f = new SettingsFragment();
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
		View view = inflater.inflate(R.layout.fragment_settings, container, false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mParent = getView();
		mActivity = getActivity();
		getuser();
		fs_username = (TextView) mParent.findViewById(R.id.fs_username);
		words=(TextView) mParent.findViewById(R.id.words);
		image = (ImageView) mParent.findViewById(R.id.fs_head);
		image.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity, UserInfo.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivityForResult(intent, 1);
				showNext();
			}
		});
		LinearLayout ll1 = (LinearLayout) mParent.findViewById(R.id.ll1);
		LinearLayout ll2 = (LinearLayout) mParent.findViewById(R.id.ll2);
		LinearLayout ll3 = (LinearLayout) mParent.findViewById(R.id.ll3);

		RelativeLayout r2 = (RelativeLayout) mParent.findViewById(R.id.fs2);

		RelativeLayout r6 = (RelativeLayout) mParent.findViewById(R.id.fs6);

		RelativeLayout r8 = (RelativeLayout) mParent.findViewById(R.id.fs8);

		badgeView = new BadgeView(getActivity());
		badgeView.setBadgeGravity(Gravity.CENTER_VERTICAL);
		// badgeView.setBadgeCount(count);
		badgeView.setText("new");
		badgeView.setBadgeMargin(0, 0, 0, 0);
		badgeView.setVisibility(View.GONE);
		OnClickListener rate = new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent(getActivity(), MyRate.class);
				intent.putExtra("FAVOR_OR_MY", 2);
				startActivity(intent);
				showNext();
			}
		};
		ll1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// popupwindow
				Intent intent1 = new Intent(getActivity(), CreateNewTravel.class);
				startActivity(intent1);
				showNext();

			}
		});
		ll2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent(getActivity(), MyFootPrint.class);
				intent.putExtra("FAVOR_OR_MY", 1);
				startActivity(intent);
				showNext();
			}
		});
		ll3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent(getActivity(), MyDongtai.class);
				startActivity(intent);
				showNext();
			}
		});
	
		r2.setOnClickListener(rate);
		
		
		r6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent(getActivity(), UserInfo.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("user", user);
				intent.putExtras(bundle);
				startActivity(intent);
				showNext();
			}
		});
		
		r8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent;
				intent = new Intent(getActivity(), ChangeSet.class);
				startActivity(intent);
				showNext();
			}
		});
		
			}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			switch (requestCode) {
			case 1:
				boolean isupdate = data.getBooleanExtra("isupdate", false);
				if (isupdate) {
					String imgurl = data.getStringExtra("imgurl");
					Uri uri = Uri.parse(imgurl);
					Bitmap bitmap = decodeUriAsBitmap(uri);
					image.setImageBitmap(bitmap);
					break;
				}
			}
		}
	};

	private Bitmap decodeUriAsBitmap(Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	public void getuser() {
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					getusernet getusernet = new getusernet();
					String jsonstring1 = getusernet.getuser();
					System.out.println(jsonstring1);
					if (jsonstring1 != null) {
						Log.i("resultjson", jsonstring1);
						jsonstring = jsonstring1;
						result = 1 + "";
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = null;
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

	public void getAdviser() {
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					returnlistadviser returnlistadviser = new returnlistadviser();
					String jsonstring1 = returnlistadviser.returnHaveAdviser();
					if (jsonstring1 != null) {
						Log.i("resultjson", jsonstring1);
						jsonstring = jsonstring1;
						result = 1 + "";
					}
				} catch (Exception e) {
					// TODO: handle exception
					result = "0";
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					message.what = ADVISER;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	private void getinfo() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					getservicelbsnet g = new getservicelbsnet();
					String jsonstring1 = g.getinfoCal();
					if (jsonstring1 != null) {
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
					message.what = INFO;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	private void getMapInfo() {

		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			String result;

			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					getservicelbsnet g = new getservicelbsnet();
					String jsonstring1 = g.getinfoThree();
					if (jsonstring1 != null) {
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
					message.what = MAP;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	public void show() {
		SharedPreferences settings = getActivity().getSharedPreferences("userInfo", 0);
		// 2、取出数据
		String name = settings.getString(url.SPUSER_NAME, null);
		String wordsString=settings.getString(url.SPUSER_PERSONAL, null);
		String img = settings.getString(url.SPUSER_IMAGE, null);
		try {
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.default_head) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.default_head) // 设置加载失败的默认图片
					.cacheInMemory(true) // 内存缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();
			ImageLoader.getInstance().displayImage(img, image, options);
		} catch (Exception e) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.default_head).cacheInMemory(true) // 内存缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();
			ImageLoader.getInstance().displayImage(null, image, options);
		}
		try {
			fs_username.setText(name);
		} catch (Exception e) {
			fs_username.setText("");
		}
		try {
			words.setText(wordsString);
		} catch (Exception e) {
			words.setVisibility(View.GONE);;
		}
		
	}

	
	public void showNext() {
		// TODO Auto-generated method stub
		getActivity().overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
	}

	public void onResume() {
		super.onResume();
		pushReceiver = new PushReceiverBadge();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.igexin.sdk.action.tdWqkqZVsW7U55L6oyW2V7");
		getActivity().registerReceiver(pushReceiver, intentFilter);

	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		getActivity().unregisterReceiver(pushReceiver);
	}

	public static class PushReceiverBadge extends BroadcastReceiver {
		public PushReceiverBadge() {
			// TODO Auto-generated constructor stub
			super();
		}

		/**
		 * 应用未启动, 个推 service已经被唤醒,保存在该时间段内离线消息(此时 GetuiSdkDemoActivity.tLogView
		 * == null)
		 */
		public static StringBuilder payloadData = new StringBuilder();

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
			switch (bundle.getInt(PushConsts.CMD_ACTION)) {
			case PushConsts.GET_MSG_DATA:
				// 获取透传数据
				// String appid = bundle.getString("appid");
				byte[] payload = bundle.getByteArray("payload");

				String taskid = bundle.getString("taskid");
				String messageid = bundle.getString("messageid");

				// smartPush第三方回执调用接口，actionid范围为90000-90999，可根据业务场景执行
				boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
				System.out.println("第三方回执接口调用" + (result ? "成功" : "失败"));

				String data = new String(payload);

				Log.d("GetuiSdkDemo", "receiver payload : " + data);
				badgeView.setVisibility(View.VISIBLE);
				// count++;
				// Log.i("count", count + "");
				// badgeView.setBadgeCount(count);
				mBuilder = new NotificationCompat.Builder(context);
				mBuilder.setContentTitle("跟我走吧").setContentText("您有一条新消息，点击查看")
						// .setNumber(number)//显示数量
						.setTicker("您有一条新消息")// 通知首次出现在通知栏，带上升动画效果的
						.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
						.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
						// .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
						.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
						.setDefaults(Notification.DEFAULT_VIBRATE)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
						// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND
						// 添加声音 // requires VIBRATE permission
						.setSmallIcon(R.drawable.ic_launcher);
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
				mNotificationManager.notify(notifyId, mBuilder.build());
				break;

			case PushConsts.GET_CLIENTID:
				// 获取ClientID(CID)
				// 第三方应用需要将CID上传到第三方服务器，并且将当前用户帐号和CID进行关联，以便日后通过用户帐号查找CID进行消息推送
				// String cid = bundle.getString("clientid");
				// if (HomeActivity.tView != null) {
				// HomeActivity.tView.setText(cid);
				// }
				break;

			case PushConsts.THIRDPART_FEEDBACK:
				// /*
				// * String appid = bundle.getString("appid"); String taskid =
				// * bundle.getString("taskid"); String actionid =
				// * bundle.getString("actionid"); String result =
				// * bundle.getString("result"); long timestamp =
				// * bundle.getLong("timestamp");
				// *
				// * Log.d("GetuiSdkDemo", "appid = " + appid);
				// * Log.d("GetuiSdkDemo", "taskid = " + taskid);
				// * Log.d("GetuiSdkDemo", "actionid = " + actionid);
				// * Log.d("GetuiSdkDemo", "result = " + result);
				// * Log.d("GetuiSdkDemo", "timestamp = " + timestamp);
				// */
				break;

			default:
				break;
			}
		}
	}
}
