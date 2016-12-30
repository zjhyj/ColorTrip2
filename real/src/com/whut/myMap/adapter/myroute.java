package com.whut.myMap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.adapter.ListViewAdapter_speak_route.ViewHolder;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.picture;
import com.whut.myMap.entites.url;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ���� ͼƬΪ������ͷ�� tvΪ���ݺ�ʱ�䣬�������������
 * 
 * @author xxr
 *
 */
public class myroute extends BaseAdapter {
	private List<trackbean> mList;
	private Context mContext;
	public myroute(List<trackbean> mList, Context mContext) {
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

	// final ListViewItem item = getItem(position); ȱ��item���
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.dongtai_route_base, null);
			holder.head=(ImageView) convertView.findViewById(R.id.head);
			holder.username=(TextView) convertView.findViewById(R.id.username);
			holder.content = (TextView) convertView
					.findViewById(R.id.text);
			holder.date = (TextView) convertView.findViewById(R.id.date);
			holder.picture = (ImageView) convertView
					.findViewById(R.id.image);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		 final trackbean track = this.mList.get(position);
		 SharedPreferences userPreferences = mContext.getSharedPreferences("userInfo", 0);
			String image=userPreferences.getString(url.SPUSER_IMAGE, "");
			String name=userPreferences.getString(url.SPUSER_NAME, "");
		if (this.mList != null) {
			if (holder.date != null) {
				holder.date.setText(track.getTrack().getDate().substring(0, 16));
			}
			if (holder.username != null) {
				holder.username.setText(name);
			}
			if (holder.content != null) {
				holder.content.setText(track.getTrack().getWords());
			}
			if(track.getTrack().getBgurl()!=null&&track.getTrack().getBgurl().length()>0)
			{	DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.no_background) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.background_failed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true)// �ڴ滺��
					.resetViewBeforeLoading(true)  
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)//�����������
					.build();//
			ImageLoader.getInstance().displayImage("file://"+track.getTrack().getBgurl(),
					holder.picture, options);
			System.out.println(track.getTrack().getBgurl());
		}
		DisplayImageOptions	 options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true)// �ڴ滺��
					.resetViewBeforeLoading(true)  
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)// �����������
					.build();//
			ImageLoader.getInstance().displayImage(image,
					holder.head, options);
			}
		return convertView;
	}

//	 protected void imageBrower(int position, ArrayList<String> urls2) {  
//	        Intent intent = new Intent(mContext, ImagePagerActivity.class);  
//	        // ͼƬurl,Ϊ����ʾ����ʹ�ó�����һ������ݿ��л������л�ȡ  
//	        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);  
//	        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);  
//	        mContext.startActivity(intent);  
//	    }  
	
	public final class ViewHolder {
		public TextView date;
		public TextView content;
		private ImageView picture;
		private ImageView head;
		private TextView username;
	}

}