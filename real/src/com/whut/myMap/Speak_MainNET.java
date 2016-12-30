package com.whut.myMap;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.handmark.pulltorefresh.library.internal.Utils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
//import com.sina.weibo.sdk.api.ImageObject;
//import com.sina.weibo.sdk.api.TextObject;
//import com.sina.weibo.sdk.api.WebpageObject;
//import com.sina.weibo.sdk.api.WeiboMultiMessage;
//import com.sina.weibo.sdk.api.share.BaseResponse;
//import com.sina.weibo.sdk.api.share.IWeiboHandler;
//import com.sina.weibo.sdk.api.share.IWeiboHandler.Response;
//import com.sina.weibo.sdk.api.share.IWeiboShareAPI;
//import com.sina.weibo.sdk.api.share.SendMultiMessageToWeiboRequest;
//import com.sina.weibo.sdk.api.share.WeiboShareSDK;
//import com.sina.weibo.sdk.constant.WBConstants;
//import com.sina.weibo.sdk.utils.Utility;
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
import com.whut.myMap.adapter.ScollListView;
import com.whut.myMap.adapter.SpeakGridViewAdapter;
import com.whut.myMap.adapter.SpeakGridView;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.serverce.findshoucang;
import com.whut.myMap.serverce.findzan;
import com.whut.myMap.utils.Util;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.favornet;
import com.whut.net.getHuifuNet;
import com.whut.net.getLredsbytimenet;
import com.whut.net.loginnet;
import com.whut.net.praisenet;

import android.R.drawable;
import android.R.integer;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/**
 * list为评论列表 其余从上级传 坐标不知道
 * @author xxr
 *
 */
public class Speak_MainNET extends Activity {
	private static final int GONE = 8;
	private static final int VISIBLE = 0;
	private ScollListView mListView;
	private redsbean redsbean;
	private ImageView imageHead;
	private TextView name;
	private TextView date;
	private TextView content;
	private ImageView comment;
	private ImageView praise;
	private ImageView favour;
	private TextView comment_tv;
	private TextView praise_tv;
	private TextView favour_tv;
	private Button map_show;
	private static boolean iszan;
	private static boolean isshoucang;
	private static int zaned=2;
	private SpeakGridView speakGridView;

private int Reds_id;
private int Dongtai_User_id;
private static String jsonstring;
private final int SHOW_RESPONSE_PRAISE=0;
private final int SHOW_RESPONSE_HUIFU=1;
private final int SHOW_RESPONSE_SHOUCANG=2;
private PopupWindow pop = null;
private Tencent mTencent;
//private IWeiboShareAPI mWeiboShareAPI;
private View parentView;
private IWXAPI iwxapi;
private Handler handler=new Handler(){
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case SHOW_RESPONSE_PRAISE:
			Log.i("zai", "enene2");
			if(msg.obj!=null){
				Log.i("zai", "enene");
				if(Integer.parseInt((String) msg.obj)==1){
					if(iszan){
						new findzan(getApplicationContext()).deletezaned(redsbean.getReds().getReds_id(),redsbean.getReds().getType(),getApplicationContext());
					    showzan(false);
					    zaned=0;
					}else{
						new findzan(getApplicationContext()).addzaned(redsbean.getReds().getReds_id(),redsbean.getReds().getType(),getApplicationContext());
					    showzan(true);
					    zaned=1;
					}
				}
				else{
					Toast.makeText(getApplication(), "网络异常，请检查网络！！", 0).show();
					return;
				}
			}
			break;
	
		case SHOW_RESPONSE_SHOUCANG:
			Log.i("zai", "enene2");
			if(msg.obj!=null){
				Log.i("zai", "enene");
				if(Integer.parseInt((String) msg.obj)==1){
					if(isshoucang){
						new findshoucang(getApplicationContext()).deleteshoucanged(redsbean.getReds().getReds_id(),redsbean.getReds().getType(),getApplicationContext());
					    showshoucang(false);
					}else{
						new findshoucang(getApplicationContext()).addshoucanged(redsbean.getReds().getReds_id(),redsbean.getReds().getType(),getApplicationContext());
					    showshoucang(true);
					}
				}else if (Integer.parseInt((String) msg.obj)==2) {
					return;
				}
				else{
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
		parentView = getLayoutInflater().inflate(R.layout.speak_item_main, null);
		setContentView(parentView);
		mTencent = Tencent.createInstance("1105307237", this.getApplicationContext());
//		mWeiboShareAPI = WeiboShareSDK.createWeiboAPI(this,Constants.APP_KEY);
//		mWeiboShareAPI.registerApp();	// 将应用注册到微博客户端
//		 if (savedInstanceState != null) {
//	           mWeiboShareAPI.handleWeiboResponse(getIntent(),this);
//	        }
		 iwxapi=WXAPIFactory.createWXAPI(this, "wxb41e72a329589390", true);
		 iwxapi.registerApp("wxb41e72a329589390");
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		redsbean= (com.whut.myMap.domain.redsbean) bundle.getSerializable("speak_item");
		 getData();
		 Reds_id=redsbean.getReds().getReds_id();
		 Dongtai_User_id=redsbean.getReds().getUser_id();
		Log.i("Speak_main", ""+Reds_id);	
		mListView = (ScollListView) findViewById(R.id.sim_comment);
		// 把view层对象ListView和控制器BaseAdapter关联起来
		imageHead=(ImageView) findViewById(R.id.imgHead);
		name=(TextView) findViewById(R.id.tvName);
		date=(TextView) findViewById(R.id.tvDate);
		content=(TextView) findViewById(R.id.tvContent);
        map_show=(Button) findViewById(R.id.map_show);   
        inintpop();
       
        map_show.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Speak_MainNET.this, RedslocationNET.class);
				Bundle bundle=new Bundle();
				bundle.putSerializable("speak_item", redsbean);
				intent.putExtras(bundle);
				startActivity(intent);
				Log.e("imageurl",redsbean.getListmap().get(0).getImgurl());
			}
		});
		try {
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
			.showImageOnLoading(R.drawable.default_head) // 加载中显示的默认图片
			.showImageOnFail(R.drawable.default_head) // 设置加载失败的默认图片
			.cacheInMemory(true) // 内存缓存
			.cacheOnDisk(true) // sdcard缓存
			.bitmapConfig(Config.RGB_565)// 设置最低配置
			.build();//
	ImageLoader.getInstance().displayImage(redsbean.getUrl(),
			imageHead, options);
		}catch(Exception e){
			imageHead.setImageResource(R.drawable.default_head);
		}
		name.setText(redsbean.getUsername());
		date.setText(redsbean.getReds().getDate().substring(0, 16));
		content.setText(redsbean.getReds().getWords());
		comment = (ImageView) findViewById(R.id.comment);
		praise = (ImageView) findViewById(R.id.praise);
		favour = (ImageView) findViewById(R.id.favour);
		comment_tv = (TextView) findViewById(R.id.comment_tv);
		praise_tv = (TextView) findViewById(R.id.praise_tv);
		favour_tv = (TextView) findViewById(R.id.favour_tv);
		speakGridView=(SpeakGridView) findViewById(R.id.tvGridview);
		
		showzan(new findzan(getApplicationContext()).findzaned(redsbean.getReds().getReds_id(), redsbean.getReds().getType(), getApplicationContext()));
		showshoucang(new findshoucang(getApplicationContext()).findshoucanged(redsbean.getReds().getReds_id(), redsbean.getReds().getType(), getApplicationContext()));
		if(redsbean.getListmap()==null){
			speakGridView.setVisibility(GONE);
		}
		else {
			speakGridView.setVisibility(VISIBLE);
			ArrayList<String> transPicture=new ArrayList<String>();
			for (int i = 0; i < redsbean.getListmap().size(); i++) {
				transPicture.add(redsbean.getListmap().get(i).getImgurl()); 
				Log.e("imageurl", redsbean.getListmap().get(0).getImgurl());
			}
			ArrayList<String> imageUrls = new ArrayList<String>();
			for (int i = 0; i < transPicture.size(); i++) {
				String image=transPicture.get(i);
				int index=image.lastIndexOf("_");
				imageUrls.add(image.substring(0, index)+".jpg");	
			}
			speakGridView.setAdapter(new SpeakGridViewAdapter(this, imageUrls,transPicture));
		}
		
		praise.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {	
					String result;
					int isjia;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Map<String, String> param=new HashMap<String, String>();
							System.out.println(Reds_id);
							System.out.println(redsbean.getReds().getType());
                            if(iszan) isjia=0;
                            else isjia=1;
							param.put("type", redsbean.getReds().getType()+"");
                            param.put("sourceid", Reds_id+"");
                            param.put("isjia", isjia+"");
				            result=praisenet.praise(param); 
							} catch (Exception e) {
							// TODO: handle exception
							result="0";
						}finally{
					Message message=new Message();
					message.what=SHOW_RESPONSE_PRAISE;
					if(result!=null||result!=""){
					message.obj=result.toString();
					}else {
						message.obj="2";
					}
					handler.sendMessage(message);	
						}				
					}
				}).start();	
			}
		});
		favour.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {	
					String result;
					int isjia;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Map<String, String> param=new HashMap<String, String>();
							System.out.println(Reds_id);
							System.out.println(redsbean.getReds().getType());
							 if(isshoucang) isjia=0;
	                            else isjia=1;
							param.put("isjia", isjia+"");
                            param.put("type", redsbean.getReds().getType()+"");
                            param.put("sourceid", Reds_id+"");
				            result=favornet.favor(param); 
							} catch (Exception e) {
							// TODO: handle exception
							result="0";
						}finally{
					Message message=new Message();
					message.what=SHOW_RESPONSE_SHOUCANG;
					if(result!=null||result!=""){
						message.obj=result.toString();
						}else {
							message.obj="2";
						}		
					handler.sendMessage(message);	
						}				
					}
				}).start();	
			}
		});
		
	}
	public void showzan(boolean bool) {
		if(bool){
			praise.setImageBitmap(BitmapFactory
					.decodeResource(getResources(),
							R.drawable.good_clicked));
			praise_tv.setText("已赞");
			iszan=true;
		}else{
			praise.setImageBitmap(BitmapFactory
					.decodeResource(getResources(),
							R.drawable.good_main));
			praise_tv.setText("赞");
			iszan=false;
		}
	}
	public void showshoucang(boolean bool) {
		if(bool){
					favour.setImageBitmap(BitmapFactory
							.decodeResource(getResources(),
									R.drawable.favor_clicked));
					favour_tv.setText("已收藏");
			isshoucang=true;
		}else{
			favour.setImageBitmap(BitmapFactory
					.decodeResource(getResources(),
							R.drawable.favourite_main));
			favour_tv.setText("收藏");
			isshoucang=false;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.reds_main_item, menu);
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
			this.overridePendingTransition(R.anim.tran_pre_in,
					R.anim.tran_pre_out);
			return true;
		case R.id.share:
			// 设置文字分享内容
			pop.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
			return true;	
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	private class ShareListener implements IUiListener{	 
	    @Override
	    public void onCancel() {
	        // TODO Auto-generated method stub
	       Toast.makeText(getApplicationContext(), "分享取消", 0).show();
	    }
	 
	    @Override
	    public void onComplete(Object arg0) {
	        // TODO Auto-generated method stub
	    	 Toast.makeText(getApplicationContext(), "分享成功", 0).show();;
	    }
	 
	    @Override
	    public void onError(UiError arg0) {
	        // TODO Auto-generated method stub
	    	 Toast.makeText(getApplicationContext(), "分享出错", 0).show();;
	    }
	}
	private void inintpop() {
		// TODO Auto-generated method stub
			pop = new PopupWindow(getApplicationContext());
			View view = getLayoutInflater().inflate(R.layout.shareplatform, null);
			LinearLayout share=(LinearLayout) view.findViewById(R.id.share);
			LinearLayout qq=(LinearLayout) view.findViewById(R.id.qq);
			LinearLayout wx=(LinearLayout) view.findViewById(R.id.weixin);
			LinearLayout wxcircle=(LinearLayout)view. findViewById(R.id.wx);
			LinearLayout sina=(LinearLayout) view.findViewById(R.id.sina);
			pop.setWidth(LayoutParams.MATCH_PARENT);
			pop.setHeight(LayoutParams.WRAP_CONTENT);
			pop.setFocusable(true);
			pop.setOutsideTouchable(true);
			pop.setContentView(view);
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
			sina.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					shareToX(2);
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
	private void shareToX(int type) {
		switch (type) {
		case 0:
			ShareListener myListener = new ShareListener();
			final Bundle params = new Bundle();
			params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);  
			params.putString(QQShare.SHARE_TO_QQ_TITLE, redsbean.getUsername()+"的分享");
			params.putString(QQShare.SHARE_TO_QQ_SUMMARY, redsbean.getReds().getWords());
			params.putString(QQShare.SHARE_TO_QQ_TARGET_URL,  "http://115.159.156.204:8080/zjh2/servlet/getredsbyid?redsid="+redsbean.getReds().getReds_id());
			params.putInt(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,com.whut.myMap.R.drawable.ic_launcher);
			mTencent.shareToQQ(this, params, myListener); 
			break;
		case 1:
			WXWebpageObject webpageObject=new WXWebpageObject();
			webpageObject.webpageUrl="http://115.159.156.204:8080/zjh2/servlet/getredsbyid?redsid="+redsbean.getReds().getReds_id();
			WXMediaMessage mediaMessage=new WXMediaMessage(webpageObject);
			mediaMessage.title=redsbean.getUsername()+"的分享";
			mediaMessage.description=redsbean.getReds().getWords();
			Bitmap bit=BitmapFactory.decodeResource(getResources(), com.whut.myMap.R.drawable.ic_launcher);
			mediaMessage.thumbData=Util.bmpToByteArray(bit,true);
			SendMessageToWX.Req req=new SendMessageToWX.Req();
			req.transaction=String.valueOf(System.currentTimeMillis());
			req.message=mediaMessage;
			req.scene=SendMessageToWX.Req.WXSceneSession;
			iwxapi.sendReq(req);
			break;
		case 2:
//			 // 1. 初始化微博的分享消息
//	        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
//	            weiboMessage.textObject = getTextObj();
//	            weiboMessage.imageObject = getImageObj();
//	           // weiboMessage.mediaObject = getWebpageObj();
//	            SendMultiMessageToWeiboRequest request = new SendMultiMessageToWeiboRequest();
//	        // 用transaction唯一标识一个请求
//	        request.transaction = String.valueOf(System.currentTimeMillis());
//	        request.multiMessage = weiboMessage;
//	        
//	        // 3. 发送请求消息到微博，唤起微博分享界面
//	            mWeiboShareAPI.sendRequest(this, request);
	            break;
		case 3:
			WXWebpageObject webpageObject1=new WXWebpageObject();
			webpageObject1.webpageUrl="http://115.159.156.204:8080/zjh2/servlet/getredsbyid?redsid="+redsbean.getReds().getReds_id();
			WXMediaMessage mediaMessage1=new WXMediaMessage(webpageObject1);
			mediaMessage1.title=redsbean.getUsername()+"的分享";
			mediaMessage1.description=redsbean.getReds().getWords();
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
	 /**
     * 创建文本消息对象。
     * 
     * @return 文本消息对象。
     */
//    private TextObject getTextObj() {
//        TextObject textObject = new TextObject();
//        textObject.text = redsbean.getReds().getWords();
//        return textObject;
//    }
//    /**
//     * 创建图片消息对象。
//     * 
//     * @return 图片消息对象。
//     */
//    private ImageObject getImageObj() {
//        ImageObject imageObject = new ImageObject();
//        Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_tome);
//        imageObject.setImageObject(bitmap);
//        return imageObject;
//    }
//    /**
//     * 创建多媒体（网页）消息对象。
//     * 
//     * @return 多媒体（网页）消息对象。
//     */
//    private WebpageObject getWebpageObj() {
//        WebpageObject mediaObject = new WebpageObject();
//        mediaObject.identify = Utility.generateGUID();
//        mediaObject.title =  "wodefenxaing";
//        mediaObject.actionUrl = "http://115.159.156.204:8080/zjh2/servlet/getredsbyid?redsid="+redsbean.getReds().getReds_id();
//        mediaObject.defaultText = "Webpage 默认文案";
//        return mediaObject;
//    }
//	 /**
//     * @see {@link Activity#onNewIntent}
//     */	
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        
//        // 从当前应用唤起微博并进行分享后，返回到当前应用时，需要在此处调用该函数
//        // 来接收微博客户端返回的数据；执行成功，返回 true，并调用
//        // {@link IWeiboHandler.Response#onResponse}；失败返回 false，不调用上述回调
//        mWeiboShareAPI.handleWeiboResponse(intent, this);
//    }
//    /**
//     * 接收微客户端博请求的数据。
//     * 当微博客户端唤起当前应用并进行分享时，该方法被调用。
//     * 
//     * @param baseRequest 微博请求数据对象
//     * @see {@link IWeiboShareAPI#handleWeiboRequest}
//     */
//    public void onResponse(BaseResponse baseResp) {
//        if(baseResp!= null){
//            switch (baseResp.errCode) {
//            case WBConstants.ErrorCode.ERR_OK:
//                Toast.makeText(this, "分享成功", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_CANCEL:
//                Toast.makeText(this, "取消分享", Toast.LENGTH_LONG).show();
//                break;
//            case WBConstants.ErrorCode.ERR_FAIL:
//                Toast.makeText(this, "分享失败", 
//                        Toast.LENGTH_LONG).show();
//                break;
//            }
//        }
//    }
	/**
	 * 
	 * 评论
	 * 评论，日期，被评头像，被评人名字
	 * @return
	 */	
	private void getData() {
		new Thread(new Runnable() {
			private String result;
			@Override
			public void run() { 
		try { 
		// TODO Auto-generated method stub
		Log.i("comment",Reds_id+"");
		Map<String,String> param=new HashMap<String, String>();
		param.put("source_id", Reds_id+"");
		param.put("type", 0+"");
		getHuifuNet gHuifuNet=new getHuifuNet();
		String jsonstring1=gHuifuNet.getHuifu(param);
		  if(jsonstring1!=null&&jsonstring1.length()>0){
          	jsonstring=jsonstring1;	
          	result="1";
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
	message.what=SHOW_RESPONSE_HUIFU;
	message.obj=result.toString();		
	handler.sendMessage(message);	
		}
			}
		}).start();         
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode != Activity.RESULT_OK) {// result is not correct
			return;
		} else {
			if (requestCode == 0) {
				
			}
			
	}
		 ShareListener myListener = new ShareListener();   
		 Tencent.onActivityResultData(requestCode,resultCode,data,myListener);
	}

	@Override
	public void onBackPressed() {
		sendZanBack();
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	return;
	}
	
protected void sendZanBack() {
	// TODO Auto-generated method stub
	Intent intent=new Intent();
	intent.putExtra("zaned", zaned);
	zaned = 2;
	setResult(RESULT_OK,intent);
}
}