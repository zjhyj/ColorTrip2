package com.whut.myMap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main;
import com.whut.myMap.Speak_MainNET;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.utils.GridViewUtils;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.loginnet;

import android.R.string;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 五项 图片为被评者头像 tv为内容和时间，点击跳到评论项
 * 
 * @author xxr
 *
 */
public class ListViewAdapter_speak_point extends BaseAdapter {
	private List<redsbean> mList;
	private Context mContext;
	String jsonstring;
	

	public ListViewAdapter_speak_point(List<redsbean> mList, Context mContext) {	
		super();
		if(mList!=null){
			Log.i("result","kongde");
		}
		this.mList = mList;
		this.mContext = mContext;
	}
	@Override
	public int getCount() {
		return this.mList.size();
	}
	@Override
	public Object getItem(int position) {
		return position;
	}
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	// final ListViewItem item = getItem(position); 缺少item项？？
	@Override
	public View getView( final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.speak_item, null);
			holder.ImageView = (ImageView) convertView
					.findViewById(R.id.imgHead);
			holder.content = (TextView) convertView
					.findViewById(R.id.tvContent);
			holder.name = (TextView) convertView.findViewById(R.id.tvName);
			holder.date = (TextView) convertView.findViewById(R.id.tvDate);
			holder.picture = (SpeakGridView) convertView
					.findViewById(R.id.tvGridview);
		    holder.like=(ImageView) convertView.findViewById(R.id.like);
		    holder.zan=(TextView) convertView.findViewById(R.id.tvZan);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			final redsbean sp = this.mList.get(position);
//		holder.praise.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				int like = sp.getReds().getZan() + 1;
//				holder.praise.setText("赞 " + like);
//			}
//		});
		if (this.mList != null) {
		
			if (holder.name != null) {
				holder.name.setText(sp.getUsername());
			}
			if (holder.date != null) {
				holder.date.setText(sp.getReds().getDate().substring(0, 16));
			}
			if (holder.content != null) {
				holder.content.setText(sp.getReds().getWords());
			}
			Log.i("zan", sp.getReds().getZan()+"");
			if (holder.zan!= null) {
				holder.zan.setText(sp.getReds().getZan()+"");
		   }
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true)// 内存缓存
					.resetViewBeforeLoading(true)  
					.cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			ImageLoader.getInstance().displayImage(sp.getUrl(),
					holder.ImageView, options);
			
			ArrayList<String> transPicture=new ArrayList<String>();
			 final ArrayList<String> imageUrls = new ArrayList<String>();
			for (int i = 0; i < sp.getListmap().size(); i++) {
				transPicture.add(sp.getListmap().get(i).getImgurl());
			}
			for (int i = 0; i < transPicture.size(); i++) {
				String image=transPicture.get(i);
				int index=image.lastIndexOf("_");
				imageUrls.add(image.substring(0, index)+".jpg");	
			}
			if (sp.getListmap()== null || sp.getListmap().size()<0) { // 没有图片资源就隐藏GridView
				holder.picture.setVisibility(View.GONE);
			} else {
				holder.picture.setAdapter(new SpeakGridViewAdapter(mContext, imageUrls,transPicture));
				holder.picture.setOnTouchInvalidPositionListener(new OnTouchInvalidPositionListener() {
					
					@Override
					public boolean onTouchInvalidPosition(int motionEvent) {
						// TODO Auto-generated method stub
						return false;
					}
				});
				// 点击回帖九宫格，查看大图
//				holder.picture.setOnItemClickListener(new OnItemClickListener() {
//					@Override
//					public void onItemClick(AdapterView<?> parent, View view,
//							int position, long id) {
//						// TODO Auto-generated method stub
//						imageBrower(position, imageUrls);
//					}
//				});
			}
			}
			
			
			
		return convertView;
	}
	   /** 
     * 打开图片查看器 
     *  
     * @param position 
     * @param urls2 
     */  
    protected void imageBrower(int position, ArrayList<String> urls2) {  
        Intent intent = new Intent(mContext, ImagePagerActivity.class);  
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取  
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);  
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);  
        mContext.startActivity(intent);  
    }  
	public final class ViewHolder {
		public ImageView ImageView;
		public TextView name;
		public TextView date;
		public TextView content;
		public ImageView comment;
		public  ImageView like;
		public TextView zan;
		private SpeakGridView picture;
		
	}

}
