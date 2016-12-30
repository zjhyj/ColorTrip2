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
	/** Notification��ID */
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
					Toast.makeText(getActivity(), "��ȡ����ʧ�ܣ������³��ԣ�", 1).show();
					return;
				}
				break;
			}
		}
	};
	// private static String[] names = { "�ҵ��㼣", "�ղؼ�", "�ҵ�����", "�ҵĻظ�", "�ҵĶ�̬",
	// "�ҵ���Ϣ", "��������" };
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
		// 2��ȡ������
		String name = settings.getString(url.SPUSER_NAME, null);
		String wordsString=settings.getString(url.SPUSER_PERSONAL, null);
		String img = settings.getString(url.SPUSER_IMAGE, null);
		try {
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.default_head) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.default_head) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true) // �ڴ滺��
					.bitmapConfig(Config.RGB_565)// �����������
					.build();
			ImageLoader.getInstance().displayImage(img, image, options);
		} catch (Exception e) {
			DisplayImageOptions options = new DisplayImageOptions.Builder()
					.showImageForEmptyUri(R.drawable.default_head).cacheInMemory(true) // �ڴ滺��
					.bitmapConfig(Config.RGB_565)// �����������
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
		 * Ӧ��δ����, ���� service�Ѿ�������,�����ڸ�ʱ�����������Ϣ(��ʱ GetuiSdkDemoActivity.tLogView
		 * == null)
		 */
		public static StringBuilder payloadData = new StringBuilder();

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			Log.d("GetuiSdkDemo", "onReceive() action=" + bundle.getInt("action"));
			switch (bundle.getInt(PushConsts.CMD_ACTION)) {
			case PushConsts.GET_MSG_DATA:
				// ��ȡ͸������
				// String appid = bundle.getString("appid");
				byte[] payload = bundle.getByteArray("payload");

				String taskid = bundle.getString("taskid");
				String messageid = bundle.getString("messageid");

				// smartPush��������ִ���ýӿڣ�actionid��ΧΪ90000-90999���ɸ���ҵ�񳡾�ִ��
				boolean result = PushManager.getInstance().sendFeedbackMessage(context, taskid, messageid, 90001);
				System.out.println("��������ִ�ӿڵ���" + (result ? "�ɹ�" : "ʧ��"));

				String data = new String(payload);

				Log.d("GetuiSdkDemo", "receiver payload : " + data);
				badgeView.setVisibility(View.VISIBLE);
				// count++;
				// Log.i("count", count + "");
				// badgeView.setBadgeCount(count);
				mBuilder = new NotificationCompat.Builder(context);
				mBuilder.setContentTitle("�����߰�").setContentText("����һ������Ϣ������鿴")
						// .setNumber(number)//��ʾ����
						.setTicker("����һ������Ϣ")// ֪ͨ�״γ�����֪ͨ��������������Ч����
						.setWhen(System.currentTimeMillis())// ֪ͨ������ʱ�䣬����֪ͨ��Ϣ����ʾ
						.setPriority(Notification.PRIORITY_DEFAULT)// ���ø�֪ͨ���ȼ�
						// .setAutoCancel(true)//���������־���û��������Ϳ�����֪ͨ���Զ�ȡ��
						.setOngoing(false)// ture��������Ϊһ�����ڽ��е�֪ͨ������ͨ����������ʾһ����̨����,�û���������(�粥������)����ĳ�ַ�ʽ���ڵȴ�,���ռ���豸(��һ���ļ�����,ͬ������,������������)
						.setDefaults(Notification.DEFAULT_VIBRATE)// ��֪ͨ�������������ƺ���Ч������򵥡���һ�µķ�ʽ��ʹ�õ�ǰ���û�Ĭ�����ã�ʹ��defaults���ԣ�������ϣ�
						// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND
						// �������� // requires VIBRATE permission
						.setSmallIcon(R.drawable.ic_launcher);
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
				mNotificationManager.notify(notifyId, mBuilder.build());
				break;

			case PushConsts.GET_CLIENTID:
				// ��ȡClientID(CID)
				// ������Ӧ����Ҫ��CID�ϴ��������������������ҽ���ǰ�û��ʺź�CID���й������Ա��պ�ͨ���û��ʺŲ���CID������Ϣ����
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