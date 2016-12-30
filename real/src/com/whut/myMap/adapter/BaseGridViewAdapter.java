package com.whut.myMap.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.adapter.SpeakGridViewAdapter.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class BaseGridViewAdapter extends BaseAdapter{
	/** ������ */
	private Context ctx;
	/** ͼƬUrl���� */
	private ArrayList<String> imageUrls;
	
	public BaseGridViewAdapter(Context ctx, ArrayList<String> urls) {
		this.ctx = ctx;
		this.imageUrls = urls;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls == null ? 0 : imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(ctx);
			convertView = View.inflate(ctx, R.layout.grid_view_item, null);
			holder.imageView = (ImageView) convertView
					.findViewById(R.id.gridview_item_imageview);
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
					.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
					.cacheInMemory(true) // �ڴ滺��
					.cacheOnDisk(true) // sdcard����
					.bitmapConfig(Config.RGB_565)// �����������
					.build();//
			ImageLoader.getInstance().displayImage(imageUrls.get(position),
					holder.imageView, options);
			// ��������Ź��񣬲鿴��ͼ
			holder.imageView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
                    imageBrower(position, imageUrls);
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
		Intent intent = new Intent(ctx, ImagePagerActivity.class);
		// ͼƬurl,Ϊ����ʾ����ʹ�ó�����һ������ݿ��л������л�ȡ
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		ctx.startActivity(intent);
	}
	public final class ViewHolder {
		private ImageView imageView;
	}
}
