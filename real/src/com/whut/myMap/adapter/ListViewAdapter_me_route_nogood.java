package com.whut.myMap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main;
import com.whut.myMap.Speak_Main_route;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.speak;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.utils.GridViewUtils;
import com.whut.myMap.utils.gsonutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 五项 图片为被评者头像 tv为内容和时间，点击跳到评论项
 * 
 * @author xxr
 *
 */
public class ListViewAdapter_me_route_nogood extends BaseAdapter {
	private List<trackbean> mList;
	private Context mContext;
	private final int SHOW_RESPONSE_PRAISE=0;
	private final int SHOW_RESPONSE_HUIFU=1;
	public ListViewAdapter_me_route_nogood(List<trackbean> mList, Context mContext) {
		super();
		this.mList = mList;
		this.mContext = mContext;
	}
	String jsonstring;
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return this.mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// final ListViewItem item = getItem(position); 缺少item项？？
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
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
		 final trackbean track = this.mList.get(position);
		if (this.mList != null) {
			if (holder.name != null) {
				holder.name.setText(track.getUsername());
			}
			if (holder.date != null) {
				holder.date.setText(track.getTrack().getDate().substring(0, 16));
			}
			if (holder.content != null) {
				holder.content.setText(track.getTrack().getWords());
			}
			  holder.like.setVisibility(View.GONE);
			    holder.zan.setVisibility(View.GONE);
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true)// 内存缓存
					.resetViewBeforeLoading(true)  
					.cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			ImageLoader.getInstance().displayImage(track.getUrl(),
					holder.ImageView, options);
			ArrayList<String> transPicture=new ArrayList<String>();
			List<picture> picturelist = new ArrayList<picture>();
			for (int i = 0; i < track.getRedsbean().size(); i++) {
				picturelist.addAll(track.getRedsbean().get(i).getListmap());
			}
			for (int i = 0; i < picturelist.size(); i++) {
				transPicture.add(picturelist.get(i).getImgurl());
			}
			ArrayList<String> imageUrls = new ArrayList<String>();
			for (int i = 0; i < transPicture.size(); i++) {
				String image=transPicture.get(i);
				int index=image.lastIndexOf("_");
				imageUrls.add(image.substring(0, index)+".jpg");	
			}
			if (imageUrls == null || imageUrls.size() == 0) {
				holder.picture.setVisibility(View.GONE);
			} else {
				holder.picture.setVisibility(View.VISIBLE);				
				holder.picture.setAdapter(new SpeakGridViewAdapter(mContext, imageUrls,transPicture));
			}
			holder.picture.setOnTouchInvalidPositionListener(new OnTouchInvalidPositionListener() {
				
				@Override
				public boolean onTouchInvalidPosition(int motionEvent) {
					// TODO Auto-generated method stub
					return false;
				}
			});
			}
			
			
			
		return convertView;
	}

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
