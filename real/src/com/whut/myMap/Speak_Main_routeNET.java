package com.whut.myMap;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.makeramen.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.whut.myMap.adapter.ListViewAdapter_speak_point;
import com.whut.myMap.adapter.Route_Main_ListviewAdapter;
import com.whut.myMap.adapter.ScollListView;
import com.whut.myMap.adapter.SpeakGridViewAdapter;
import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.lbs;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.track;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.findshoucang;
import com.whut.myMap.serverce.findzan;
import com.whut.myMap.utils.Util;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.favornet;
import com.whut.net.getHuifuNet;
import com.whut.net.praisenet;

import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class Speak_Main_routeNET extends Activity {
	private static final int GONE = 8;
	private static final int VISIBLE = 0;
	private ScollListView mListView;
	private Route_Main_ListviewAdapter mAdapter;
	private static trackbean trackbean;
	private ImageView imageHead;
	private TextView name;
	private TextView content;
	private static boolean iszan;
	private static boolean isshoucang;
	private int ZoF = 0;
	private static int zaned = 2;
	private int track_id;
	private int Dongtai_User_id;
	private int type = 1;
	MediaPlayer mp = null;
	private ImageView music;
	private View parentView;
	private Tencent mTencent;
//	private IWeiboShareAPI mWeiboShareAPI;
	private IWXAPI iwxapi;
	private static boolean isplay = false;
	private static String jsonstring;
	private final int SHOW_RESPONSE_PRAISE = 0;
	private final int SHOW_RESPONSE_HUIFU = 1;
	private final int SHOW_RESPONSE_SHOUCANG = 2;
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
							showzan(false);
							zaned = 0;

						} else {
							new findzan(getApplicationContext()).addzaned(trackbean.getTrack().getTrack_id(),
									trackbean.getTrack().getType(), getApplicationContext());
							showzan(true);
							zaned = 1;
						}
					} else {
						Toast.makeText(getApplication(), "�����쳣���������磡��", 0).show();
						return;
					}
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
							showshoucang(false);
							Toast.makeText(getApplication(), "ȡ���ղسɹ�", 0).show();
						} else {
							new findshoucang(getApplicationContext()).addshoucanged(trackbean.getTrack().getTrack_id(),
									trackbean.getTrack().getType(), getApplicationContext());
							showshoucang(true);
							Toast.makeText(getApplication(), "�ղسɹ�", 0).show();
						}
					} else if (Integer.parseInt((String) msg.obj) == 2) {
						return;
					} else {
						Toast.makeText(getApplication(), "�����쳣���������磡��", 0).show();
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
//		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,Constants.APP_KEY);
//		mWeiboShareAPI.registerApp();	// ��Ӧ��ע�ᵽ΢���ͻ���
//		
//		 if (savedInstanceState != null) {
//	            mWeiboShareAPI.handleWeiboResponse(getIntent(), this);
//	        }
		 iwxapi=WXAPIFactory.createWXAPI(this, "wxb41e72a329589390", true);
		 iwxapi.registerApp("wxb41e72a329589390");
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		trackbean = (com.whut.myMap.domain.trackbean) bundle.getSerializable("speak_item1");
		initTheme();
		showzan(new findzan(getApplicationContext()).findzaned(trackbean.getTrack().getTrack_id(),
				trackbean.getTrack().getType(), getApplicationContext()));
		showshoucang(new findshoucang(getApplicationContext()).findshoucanged(trackbean.getTrack().getTrack_id(),
				trackbean.getTrack().getType(), getApplicationContext()));
		Dongtai_User_id = trackbean.getTrack().getUser_id();
		track_id = trackbean.getTrack().getTrack_id();
		mListView = (ScollListView) findViewById(R.id.ui_lv);
		mAdapter = new Route_Main_ListviewAdapter(getApplication(), trackbean);
		music = (ImageView) findViewById(R.id.music);
		try{
		     trackbean.getTrack().getMusicstring().length(); 	
		}catch(Exception e){
			music.setVisibility(View.GONE);
		}
		inintpop();
		if (trackbean.getTrack().getMusicstring() != null&&trackbean.getTrack().getMusicstring().length()>0) {
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
		
		initListView();

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

	private void initListView() {
		Log.i("size", trackbean.getRedsbean().size() + "");
		if (trackbean.getRedsbean() != null && trackbean.getRedsbean().size() > 0) {
//			LayoutInflater inflater = LayoutInflater.from(Speak_Main_routeNET.this);
//			View placeHolderView = inflater.inflate(R.layout.speak_route, null);
			RelativeLayout rela=(RelativeLayout) findViewById(R.id.title);
			rela.setVisibility(VISIBLE);
			imageHead = (ImageView) rela.findViewById(R.id.head);
			name = (TextView) rela.findViewById(R.id.username);
			content = (TextView) rela.findViewById(R.id.text);
			ImageView titleImg = (ImageView)rela.findViewById(R.id.toppic);
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true) // �ڴ滺��
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)// �����������
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
			
			//rela.setBackgroundColor(0xFFFFFFFF);
			//mListView.addHeaderView(rela);
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
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true) // �ڴ滺��
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)// �����������
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
		mAdapter.notifyDataSetChanged();
	}

	private class ShareListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "����ȡ��", 0).show();
		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "�����ɹ�", 0).show();
			;
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub
			Toast.makeText(getApplicationContext(), "��������", 0).show();
			;
		}
	}
	private void inintpop() {
		// TODO Auto-generated method stub
		pop = new PopupWindow(getApplicationContext());
		View view = getLayoutInflater().inflate(R.layout.shareplatform, null);
		LinearLayout share = (LinearLayout) view.findViewById(R.id.share);
		LinearLayout qq = (LinearLayout) view.findViewById(R.id.qq);
		LinearLayout wx=(LinearLayout) view.findViewById(R.id.weixin);
		LinearLayout wxcircle=(LinearLayout) view.findViewById(R.id.wx);
		//LinearLayout sina=(LinearLayout) view.findViewById(R.id.sina);
		pop.setWidth(LayoutParams.MATCH_PARENT);
		pop.setHeight(LayoutParams.WRAP_CONTENT);
		pop.setFocusable(true);
		pop.setOutsideTouchable(true);
		pop.setContentView(view);
//		sina.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				shareToX(2);
//			}
//		});
		qq.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareToX(0);
			}
		});
		wx.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareToX(1);
			}
		});
		wxcircle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				shareToX(3);
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
			params.putString(QQShare.SHARE_TO_QQ_TITLE, trackbean.getUsername()+"�ķ���");
			params.putString(QQShare.SHARE_TO_QQ_SUMMARY,  trackbean.getTrack().getWords());
			params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://115.159.156.204:8080/zjh2/servlet/gettrackbyid?trackid="+trackbean.getTrack().getTrack_id());
			params.putInt(QQShare.SHARE_TO_QQ_IMAGE_URL,com.whut.myMap.R.drawable.ic_launcher);
			mTencent.shareToQQ(this, params, myListener); 
			break;
		case 1:
			WXWebpageObject webpageObject=new WXWebpageObject();
			webpageObject.webpageUrl= "http://115.159.156.204:8080/zjh2/servlet/gettrackbyid?trackid="+trackbean.getTrack().getTrack_id();
			WXMediaMessage mediaMessage=new WXMediaMessage(webpageObject);
			mediaMessage.title=trackbean.getUsername()+"�ķ���";
			mediaMessage.description=trackbean.getTrack().getWords();
			Bitmap bit=BitmapFactory.decodeResource(getResources(), com.whut.myMap.R.drawable.ic_launcher);
			mediaMessage.thumbData=Util.bmpToByteArray(bit,true);
			SendMessageToWX.Req req=new SendMessageToWX.Req();
			req.transaction=String.valueOf(System.currentTimeMillis());
			req.message=mediaMessage;
			req.scene=SendMessageToWX.Req.WXSceneSession;
			iwxapi.sendReq(req);
			break;
		case 2:
//			 // 1. ��ʼ��΢���ķ�����Ϣ
//	        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//	            weiboMessage.textObject = getTextObj();
//	            weiboMessage.imageObject = getImageObj();
//	           // weiboMessage.mediaObject = getWebpageObj();
//	            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//	        // ��transactionΨһ��ʶһ������
//	        request.transaction = String.valueOf(System.currentTimeMillis());
//	        request.multiMessage = weiboMessage;
//	        
//	        // 3. ����������Ϣ��΢��������΢����������
//	            mWeiboShareAPI.sendRequest(this, request);
	            break;
		
	case 3:
		WXWebpageObject webpageObject1=new WXWebpageObject();
		webpageObject1.webpageUrl= "http://115.159.156.204:8080/zjh2/servlet/gettrackbyid?trackid="+trackbean.getTrack().getTrack_id();
		WXMediaMessage mediaMessage1=new WXMediaMessage(webpageObject1);
		mediaMessage1.title=trackbean.getUsername()+"�ķ���";
		mediaMessage1.description=trackbean.getTrack().getWords();
		Bitmap bit1=BitmapFactory.decodeResource(getResources(), com.whut.myMap.R.drawable.ic_launcher);
		mediaMessage1.thumbData=Util.bmpToByteArray(bit1,true);
		SendMessageToWX.Req req1=new SendMessageToWX.Req();
		req1.transaction=String.valueOf(System.currentTimeMillis());
		req1.message=mediaMessage1;
		req1.scene=SendMessageToWX.Req.WXSceneTimeline;
		iwxapi.sendReq(req1);
		break;
		}
	}
//	 /**
//     * �����ı���Ϣ����
//     * 
//     * @return �ı���Ϣ����
//     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = trackbean.getTrack().getWords();
//        return textObject;
//    }
//    /**
//     * ����ͼƬ��Ϣ����
//     * 
//     * @return ͼƬ��Ϣ����
//     */
//    private ImageObject getImageObj() {
//        ImageObject imageObject = new ImageObject();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_tome);
//        imageObject.setImageObject(bitmap);
//        return imageObject;
//    }
//    /**
//     * ������ý�壨��ҳ����Ϣ����
//     * 
//     * @return ��ý�壨��ҳ����Ϣ����
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title =  "wodefenxaing";
//        mediaObject.actionUrl = "http://115.159.156.204:8080/zjh2/servlet/getredsbyid?redsid="+trackbean.getTrack().getTrack_id();
//        mediaObject.defaultText = "Webpage Ĭ���İ�";
//        return mediaObject;
//    }
//	 /**
//     * @see {@link Activity#onNewIntent}
//     */	
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        
//        // �ӵ�ǰӦ�û���΢�������з����󣬷��ص���ǰӦ��ʱ����Ҫ�ڴ˴����øú���
//        // ������΢���ͻ��˷��ص����ݣ�ִ�гɹ������� true��������
//        // {@link IWeiboHandler.Response#onResponse}��ʧ�ܷ��� false�������������ص�
//        mWeiboShareAPI.handleWeiboResponse(intent, this);
//    }
//    /**
//     * ����΢�ͻ��˲���������ݡ�
//     * ��΢���ͻ��˻���ǰӦ�ò����з���ʱ���÷��������á�
//     * 
//     * @param baseRequest ΢���������ݶ���
//     * @see {@link IWeiboShareAPI#handleWeiboRequest}
//     */
//    public void onResponse(BaseResponse baseResp) {
//        if(baseResp!= null){
//            switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//                Toast.makeText(this, "�����ɹ�", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//                Toast.makeText(this, "ȡ������", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//                Toast.makeText(this, "����ʧ��", 
//                        Toast.LENGTH_LONG).show();
//                break;
//            }
//        }
//    }

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showzan(new findzan(getApplicationContext()).findzaned(trackbean.getTrack().getTrack_id(),
				trackbean.getTrack().getType(), getApplicationContext()));
		showshoucang(new findshoucang(getApplicationContext()).findshoucanged(trackbean.getTrack().getTrack_id(),
				trackbean.getTrack().getType(), getApplicationContext()));
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
			// �������ַ�������
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			return true;
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
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}

	protected void sendZanBack() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.putExtra("zaned", zaned);
		zaned = 2;
		setResult(RESULT_OK, intent);
	}
}