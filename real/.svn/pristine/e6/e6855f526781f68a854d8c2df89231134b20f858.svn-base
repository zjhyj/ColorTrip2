package com.whut.myMap;

import java.util.ArrayList;
import java.util.List;

import com.whut.myMap.utils.Bimp;
import com.whut.myMap.utils.PublicWay;
import com.whut.myMap.utils.Res;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import uk.co.senab.photoview.PhotoView;

public class GalleryActivity extends Activity {
	private Intent intent;
	// 发送按钮
	private Button send_bt;
	//删除按钮
	private Button del_bt;
	//顶部显示预览图片位置的textview
	private TextView positionTextView;
	//当前的位置
	private int location = 0;

	private ArrayList<View> listViews = null;
	private PhotoView pager;

	public List<Bitmap> bmp = new ArrayList<Bitmap>();
	public List<String> drr = new ArrayList<String>();
	public List<String> del = new ArrayList<String>();
	Bitmap bitmap;
	private Context mContext;

	RelativeLayout photo_relativeLayout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(Res.getLayoutID("activity_gallery"));// 切屏到主界面
		PublicWay.activityList.add(this);
		mContext = this;
		send_bt = (Button) findViewById(Res.getWidgetID("send_button"));
		del_bt = (Button) findViewById(Res.getWidgetID("gallery_del"));
		send_bt.setOnClickListener(new GallerySendListener());
		del_bt.setOnClickListener(new DelListener());
		intent = getIntent();
		Bundle bundle = intent.getExtras();
		location = Integer.parseInt(intent.getStringExtra("position"));
		isShowOkBt();
		// 为发送按钮设置文字
		pager = (PhotoView) findViewById(Res.getWidgetID("gallery01"));
		int id = intent.getIntExtra("ID", 0);
		bitmap=BitmapFactory.decodeFile(Bimp.tempSelectBitmap.get(id).getImagePath());
		pager.setImageBitmap(bitmap);
	}

	
	// 删除按钮添加的监听器
	private class DelListener implements OnClickListener {

		public void onClick(View v) {
				Bimp.tempSelectBitmap.remove(location);
				Bimp.max--;
//				listViews.remove(location);
				send_bt.setText(
						Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
				if(Bimp.tempSelectBitmap.size()==0){
					Bimp.tempSelectBitmap.clear();
					Bimp.max = 0;
					send_bt.setText(
							Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
					Intent intent = new Intent("data.broadcast.action");
					sendBroadcast(intent);

			}
				bitmap.recycle();
				finish();
		}
	}
	// 完成按钮的监听
	private class GallerySendListener implements OnClickListener {
		public void onClick(View v) {
			bitmap.recycle();
			finish();
		}

	}

	public void isShowOkBt() {
		if (Bimp.tempSelectBitmap.size() > 0) {
			send_bt.setText(Res.getString("finish") + "(" + Bimp.tempSelectBitmap.size() + "/" + PublicWay.num + ")");
			send_bt.setPressed(true);
			send_bt.setClickable(true);
			send_bt.setTextColor(Color.WHITE);
		} else {
			send_bt.setPressed(false);
			send_bt.setClickable(false);
			send_bt.setTextColor(Color.parseColor("#E1E0DE"));
		}
	}

	/**
	 * 监听返回按钮
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK ) {
			bitmap.recycle();
				this.finish();
		}
		return true;
	}

	
}
