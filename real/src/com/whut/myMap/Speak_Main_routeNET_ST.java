package com.whut.myMap;

import java.util.HashMap;
import java.util.Map;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.whut.myMap.adapter.Route_Main_ListviewAdapter;
import com.whut.myMap.adapter.ScollListView;
import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.adapter.SpeakGridViewAdapter;
import com.whut.myMap.domain.dongtaipointbean;
import com.whut.myMap.domain.dongtaitrackbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.picture;
import com.whut.myMap.serverce.findshoucang;
import com.whut.myMap.serverce.findzan;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.favornet;
import com.whut.net.getHuifuNet;
import com.whut.net.getOneSpeakRoute;
import com.whut.net.praisenet;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Speak_Main_routeNET_ST extends Activity {
	private static final int GONE = 8;
	private static final int VISIBLE = 0;
	private ScollListView mListView;
	private Route_Main_ListviewAdapter mAdapter;
	private trackbean trackbean;
	private dongtaitrackbean dongtaibean;
	private ImageView imageHead;
	private TextView name;
	private TextView content;
	ImageView titleImg;
	private boolean iszan;
	private boolean isshoucang;
	private static int ZoF = 0;
	private static int zaned = 2;
	private int track_id;
	private int Dongtai_User_id;
	private int type = 1;
	private static String jsonstring;
	private final int SHOW_RESPONSE_PRAISE = 0;
	private final int SHOW_RESPONSE_DONGTAI = 1;
	private final int SHOW_RESPONSE_SHOUCANG = 2;
	MediaPlayer mp = null;
	private ImageView music;
	private static boolean isplay = false;
	private View parentView;
	private Tencent mTencent;
	private PopupWindow pop = null;
	private MyApplication app;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE_PRAISE:
				Log.i("zai", "enene2");
				if (msg.obj != null) {
					Log.i("zai", "enene");
					if (Integer.parseInt((String) msg.obj) == 1) {
						if (iszan) {
							new findzan(getApplicationContext()).deletezaned(trackbean.getTrack().getTrack_id(),
									trackbean.getTrack().getType(), getApplicationContext());
							iszan = false;
							zaned = 0;
							ZoF = 1;
							getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
						} else {
							new findzan(getApplicationContext()).addzaned(trackbean.getTrack().getTrack_id(),
									trackbean.getTrack().getType(), getApplicationContext());
							iszan = true;
							zaned = 1;
							ZoF = 1;
							getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
						}
					} else {
						Toast.makeText(getApplication(), "网络异常，请检查网络！！", 0).show();
						return;
					}
				}
				break;
			case SHOW_RESPONSE_DONGTAI:
				Log.i("zai", "enene2");
				if (Integer.parseInt((String) msg.obj) == 1) {
					Log.i("zai", "enene");
					Log.i("zai", "enene3");
					gsonutil gson = new gsonutil();
					Log.i("json", jsonstring);
					System.out.println(jsonstring);
					try {
						dongtaibean = (dongtaitrackbean) gsonutil.getobject(jsonstring, dongtaitrackbean.class);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						return;
					}

					Dongtai_User_id = dongtaibean.getTrackbean().getTrack().getUser_id();
					trackbean = dongtaibean.getTrackbean();
					initTheme();
					music = (ImageView) findViewById(R.id.music);
					try {
						trackbean.getTrack().getMusicstring().length();
					} catch (Exception e) {
						music.setVisibility(View.GONE);
					}
					inintpop();
					if (trackbean.getTrack().getMusicstring() != null
							&& trackbean.getTrack().getMusicstring().length() > 0) {
						music.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								if (!isplay) {
									new Thread() {
										@Override
										public void run() {
											mp = new MediaPlayer();
											try {
												System.out.println(trackbean.getTrack().getMusicstring());
												mp.setDataSource(trackbean.getTrack().getMusicstring());
												mp.prepare();
											} catch (Exception e) {
												e.printStackTrace();
											}
											mp.start();
										}
									}.start();
								} else if (isplay) {
									mp.pause();
								}
							}
						});
					} else {
						music.setVisibility(View.GONE);
					}
					mAdapter = new Route_Main_ListviewAdapter(getApplication(), trackbean);
					initListView();
					showzan(new findzan(getApplicationContext()).findzaned(
							dongtaibean.getTrackbean().getTrack().getTrack_id(),
							dongtaibean.getTrackbean().getTrack().getType(), getApplicationContext()));
					showshoucang(new findshoucang(getApplicationContext()).findshoucanged(
							dongtaibean.getTrackbean().getTrack().getTrack_id(),
							dongtaibean.getTrackbean().getTrack().getType(), getApplicationContext()));

					DisplayImageOptions options = new DisplayImageOptions.Builder()//
							.showImageOnLoading(R.drawable.ic_launcher) // 加载中显示的默认图片
							.showImageOnFail(R.drawable.ic_launcher) // 设置加载失败的默认图片
							.cacheInMemory(true) // 内存缓存
							.cacheOnDisk(true) // sdcard缓存
							.bitmapConfig(Config.RGB_565)// 设置最低配置
							.build();//

				} else {
					Toast.makeText(getApplication(), "网络异常，请检查网络！！", 0).show();
					return;
				}
				break;
			case SHOW_RESPONSE_SHOUCANG:
				Log.i("zai", "enene2");
				if (msg.obj != null) {
					Log.i("zai", "enene");
					if (Integer.parseInt((String) msg.obj) == 1) {
						if (isshoucang) {
							new findshoucang(getApplicationContext()).deleteshoucanged(
									trackbean.getTrack().getTrack_id(), trackbean.getTrack().getType(),
									getApplicationContext());
							isshoucang = false;
							ZoF = 2;
							Toast.makeText(getApplication(), "取消收藏成功", 0).show();
							getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
						} else {
							new findshoucang(getApplicationContext()).addshoucanged(trackbean.getTrack().getTrack_id(),
									trackbean.getTrack().getType(), getApplicationContext());
							isshoucang = true;
							ZoF = 2;
							Toast.makeText(getApplication(), "收藏成功", 0).show();
							getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
						}
					} else if (Integer.parseInt((String) msg.obj) == 2) {
						return;
					} else {
						Toast.makeText(getApplication(), "网络异常，请检查网络！！", 0).show();
						return;
					}
				}
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		parentView = getLayoutInflater().inflate(R.layout.speak_route, null);
		setContentView(parentView);
		mTencent = Tencent.createInstance("1105307237", this.getApplicationContext());
		Intent intent = getIntent();
		track_id = intent.getIntExtra("source_id", 0);
		getData();
		mListView = (ScollListView) findViewById(R.id.ui_lv);

	}

	private void getData() {
		new Thread(new Runnable() {
			private String result;

			@Override
			public void run() {
				try {
					// TODO Auto-generated method stub
					Map<String, String> param = new HashMap<String, String>();
					param.put("source_id", track_id + "");
					param.put("type", 1 + "");
					getOneSpeakRoute gHuifuNet = new getOneSpeakRoute();
					String jsonstring1 = gHuifuNet.getOneSpeak(param);
					if (jsonstring1 != null && jsonstring1.length() > 0) {
						jsonstring = jsonstring1;
						result = "1";
					} else {
						result = "0";
						return;
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					result = "2";
				} finally {
					Log.i("result", result.toString());
					Message message = new Message();
					message.what = SHOW_RESPONSE_DONGTAI;
					message.obj = result.toString();
					handler.sendMessage(message);
				}
			}
		}).start();
	}

	private void initListView() {
		Log.i("size", trackbean.getRedsbean().size() + "");
		if (trackbean.getRedsbean() != null && trackbean.getRedsbean().size() > 0) {
			// LayoutInflater inflater =
			// LayoutInflater.from(Speak_Main_routeNET.this);
			// View placeHolderView = inflater.inflate(R.layout.speak_route,
			// null);
			RelativeLayout rela = (RelativeLayout) findViewById(R.id.title);
			rela.setVisibility(VISIBLE);
			imageHead = (ImageView) rela.findViewById(R.id.head);
			name = (TextView) rela.findViewById(R.id.username);
			content = (TextView) rela.findViewById(R.id.text);
			ImageView titleImg = (ImageView) rela.findViewById(R.id.toppic);
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true) // 内存缓存
					.cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			try {
				ImageLoader.getInstance().displayImage(trackbean.getUrl(), imageHead, options);
			} catch (Exception e) {
				imageHead.setImageResource(R.drawable.default_head);
			}
			try {
				ImageLoader.getInstance().displayImage(trackbean.getTrack().getBgurl(), titleImg, options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				titleImg.setImageResource(R.drawable.no_background);
			}
			name.setText(trackbean.getUsername());
			content.setText(trackbean.getTrack().getWords());
			
			// rela.setBackgroundColor(0xFFFFFFFF);
			// mListView.addHeaderView(rela);
			mListView.setAdapter(mAdapter);
		} else {
			// mListView.setVisibility(View.GONE);
			LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll);
			linearLayout.setVisibility(View.VISIBLE);
			imageHead = (ImageView) findViewById(R.id.head);
			name = (TextView) findViewById(R.id.username);
			content = (TextView) findViewById(R.id.text);
			ImageView titleImg = (ImageView) findViewById(R.id.toppic);
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true) // 内存缓存
					.cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			try {
				ImageLoader.getInstance().displayImage(trackbean.getUrl(), imageHead, options);
			} catch (Exception e) {
				imageHead.setImageResource(R.drawable.default_head);
			}
			try {
				ImageLoader.getInstance().displayImage(trackbean.getTrack().getBgurl(), titleImg, options);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				titleImg.setImageResource(R.drawable.no_background);
			}
			name.setText(trackbean.getUsername());
			content.setText(trackbean.getTrack().getWords());
			
		}
	}

	private class ShareListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "分享取消", 0).show();
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "分享成功", 0).show();
			;
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "分享出错", 0).show();
			;
		}
	}

	private void inintpop() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(getApplicationContext());
		View view = getLayoutInflater().inflate(R.layout.shareplatform, null);
		LinearLayout share = (LinearLayout) view.findViewById(R.id.share);
		LinearLayout qq = (LinearLayout) view.findViewById(R.id.qq);
		LinearLayout space = (LinearLayout) view.findViewById(R.id.wx);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
		// share.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // TODO Auto-generated method stub
		// pop.dismiss();
		// }
		// });
		qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareToX(0);
			}
		});
		space.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareToX(1);
			}
		});
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		ShareListener myListener = new ShareListener();
		Tencent.onActivityResultData(requestCode, resultCode, data, myListener);
	}

	private void shareToX(int type) {
		switch (type) {
		case 0:
			ShareListener myListener = new ShareListener();
			final Bundle params = new Bundle();
			params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
			params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
			params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
			params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
			params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://www.baidu.com/img/bd_logo1.png");
			mTencent.shareToQQ(this, params, myListener);
			break;
		case 1:
			ShareListener myListener1 = new ShareListener();
			final Bundle params1 = new Bundle();
			params1.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
			params1.putString(QzoneShare.SHARE_TO_QQ_TITLE, "要分享的标题");
			params1.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
			params1.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
			params1.putString(QzoneShare.SHARE_TO_QQ_IMAGE_URL, "https://www.baidu.com/img/bd_logo1.png");
			mTencent.shareToQzone(this, params1, myListener1);
			break;
		}
	}

	private void shareqqzone() {
		ShareListener myListener = new ShareListener();
		final Bundle params = new Bundle();
		params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
		params.putString(QQShare.SHARE_TO_QQ_TITLE, "要分享的标题");
		params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "要分享的摘要");
		params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.qq.com/news/1.html");
		params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://www.baidu.com/img/bd_logo1.png");
		mTencent.shareToQzone(this, params, myListener);
	}

	private void initTheme() {
		// TODO Auto-generated method stub
		ScrollView linearLayout = (ScrollView) findViewById(R.id.layout_main);
		switch (trackbean.getTrack().getTheme()) {
		case 0:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.LightYello));
			break;
		case 1:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.MistyRose));
			break;
		case 2:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.DarkSeaGreen));
			break;
		case 3:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.MintCream));
			break;
		case 4:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.vintage));
			break;
		case 5:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.PaleTurquoise));
			break;
		case 6:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.BlueTheme));
			break;
		case 7:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.tree));
			break;
		default:
			linearLayout.setBackgroundColor(getResources().getColor(R.color.LightYello));
			break;
		}
	}

	public void showzan(boolean bool) {
		if (bool) {
			iszan = true;
			ZoF = 1;
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
		} else {
			iszan = false;
			ZoF = 1;
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
		}
	}

	public void showshoucang(boolean bool) {
		if (bool) {
			isshoucang = true;
			ZoF = 2;
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
		} else {
			isshoucang = false;
			ZoF = 2;
			getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.route_main_item, menu);
		ActionBar mActionBar = getActionBar();
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
			sendZanBack();
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			return true;
		case R.id.share:
			// 设置文字分享内容
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
		case R.id.map:
			Intent intent = new Intent(getApplication(), TrackshowNET.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("speak_item", trackbean);
			intent.putExtras(bundle);
			startActivity(intent);
			return true;
		case R.id.good:
			new Thread(new Runnable() {
				String result;
				int isjia;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Map<String, String> param = new HashMap<String, String>();
						System.out.println(track_id);
						if (iszan)
							isjia = 0;
						else
							isjia = 1;
						param.put("type", trackbean.getTrack().getType() + "");
						param.put("sourceid", track_id + "");
						param.put("isjia", isjia + "");
						result = praisenet.praise(param);
					} catch (Exception e) {
						// TODO: handle exception
						result = "0";
					} finally {
						Message message = new Message();
						message.what = SHOW_RESPONSE_PRAISE;
						if (result != null || result != "") {
							message.obj = result.toString();
						} else {
							message.obj = "2";
						}
						handler.sendMessage(message);
					}
				}
			}).start();
			return true;
		case R.id.favor:
			new Thread(new Runnable() {
				String result;
				int isjia;

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Map<String, String> param = new HashMap<String, String>();
						System.out.println(track_id);
						System.out.println(type);
						if (isshoucang)
							isjia = 0;
						else
							isjia = 1;
						param.put("isjia", isjia + "");
						param.put("type", type + "");
						param.put("sourceid", track_id + "");
						result = favornet.favor(param);
					} catch (Exception e) {
						// TODO: handle exception
						result = "0";
					} finally {
						Message message = new Message();
						message.what = SHOW_RESPONSE_SHOUCANG;
						if (result != null || result != "") {
							message.obj = result.toString();
						} else {
							message.obj = "2";
						}
						handler.sendMessage(message);
					}
				}
			}).start();
			return true;
	
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		switch (ZoF) {
		case 1:
			if (iszan) {
				menu.findItem(R.id.good).setIcon(R.drawable.good_clicked2);
				if (isshoucang) {
					menu.findItem(R.id.favor).setIcon(R.drawable.favor_clicked2);
				} else {
					menu.findItem(R.id.favor).setIcon(R.drawable.favourite_main2);
				}
			} else {
				menu.findItem(R.id.good).setIcon(R.drawable.good_main2);
				if (isshoucang) {
					menu.findItem(R.id.favor).setIcon(R.drawable.favor_clicked2);
				} else {
					menu.findItem(R.id.favor).setIcon(R.drawable.favourite_main2);
				}
			}
			break;
		case 2:
			if (isshoucang) {
				menu.findItem(R.id.favor).setIcon(R.drawable.favor_clicked2);
				if (iszan) {
					menu.findItem(R.id.good).setIcon(R.drawable.good_clicked2);
				} else {
					menu.findItem(R.id.good).setIcon(R.drawable.good_main2);
				}
			} else {
				menu.findItem(R.id.favor).setIcon(R.drawable.favourite_main2);
				if (iszan) {
					menu.findItem(R.id.good).setIcon(R.drawable.good_clicked2);
				} else {
					menu.findItem(R.id.good).setIcon(R.drawable.good_main2);
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		sendZanBack();
		finish();
		this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
		return;
	}

	protected void sendZanBack() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("zaned", zaned);
		zaned = 2;
		setResult(RESULT_OK, intent);
	}
}