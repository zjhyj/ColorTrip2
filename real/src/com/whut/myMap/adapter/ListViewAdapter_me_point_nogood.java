package com.whut.myMap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.speak;
import com.whut.myMap.utils.GridViewUtils;
import com.whut.myMap.utils.gsonutil;
import com.whut.net.loginnet;

import android.R.string;
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
 * ���� ͼƬΪ������ͷ�� tvΪ���ݺ�ʱ�䣬�������������
 * 
 * @author xxr
 *
 */
public class ListViewAdapter_me_point_nogood extends BaseAdapter {
	private List<redsbean> mList;
	private Context mContext;
	private final int SHOW_RESPONSE_PRAISE=0;
	private final int SHOW_RESPONSE_HUIFU=1;
	String jsonstring;
	

	public ListViewAdapter_me_point_nogood(List<redsbean> mList, Context mContext) {	
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
	// final ListViewItem item = getItem(position); ȱ��item���
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
//				holder.praise.setText("�� " + like);
//			}
//		});
		if (this.mList != null) {
		    holder.like.setVisibility(View.GONE);
		    holder.zan.setVisibility(View.GONE);
			if (holder.name != null) {
				holder.name.setText(sp.getUsername());
			}
			if (holder.date != null) {
				holder.date.setText(sp.getReds().getDate().substring(0, 16));
			}
			if (holder.content != null) {
				holder.content.setText(sp.getReds().getWords());
			}
		   }
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true)// �ڴ滺��
					.resetViewBeforeLoading(true)  
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)// �����������
					.build();//
			ImageLoader.getInstance().displayImage(sp.getUrl(),
					holder.ImageView, options);
			ArrayList<String> transPicture=new ArrayList<String>();
			for (int i = 0; i < sp.getListmap().size(); i++) {
				transPicture.add(sp.getListmap().get(i).getImgurl());
			}
			ArrayList<String> imageUrls = new ArrayList<String>();
			for (int i = 0; i < transPicture.size(); i++) {
				String image=transPicture.get(i);
				int index=image.lastIndexOf("_");
				imageUrls.add(image.substring(0, index)+".jpg");	
			}
			if (sp.getListmap()== null || sp.getListmap().size()<= 0) { // û��ͼƬ��Դ������GridView
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
			}
			
			
			
			
		return convertView;
	}
	   /** 
     * ��ͼƬ�鿴�� 
     *  
     * @param position 
     * @param urls2 
     */  
    protected void imageBrower(int position, ArrayList<String> urls2) {  
        Intent intent = new Intent(mContext, ImagePagerActivity.class);  
        // ͼƬurl,Ϊ����ʾ����ʹ�ó�����һ������ݿ��л������л�ȡ  
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