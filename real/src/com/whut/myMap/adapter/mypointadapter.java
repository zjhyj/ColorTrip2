package com.whut.myMap.adapter;

import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.redsbean;
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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ���� ͼƬΪ������ͷ�� tvΪ���ݺ�ʱ�䣬�������������
 * 
 * @author xxr
 *
 */
public class mypointadapter extends BaseAdapter {
	private List<redsbean> mList;
	private Context mContext;

	public mypointadapter(List<redsbean> mList, Context mContext) {
		super();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.mypoint, null);
			holder.ImageView = (ImageView) convertView.findViewById(R.id.imgHead);
			holder.content = (TextView) convertView.findViewById(R.id.tvContent);
			holder.name = (TextView) convertView.findViewById(R.id.tvName);
			holder.date = (TextView) convertView.findViewById(R.id.tvDate);
			holder.picture = (SpeakGridView) convertView.findViewById(R.id.tvGridview);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final redsbean sp = this.mList.get(position);
		SharedPreferences userPreferences = mContext.getSharedPreferences("userInfo", 0);
		String image = userPreferences.getString(url.SPUSER_IMAGE, "");
		String name = userPreferences.getString(url.SPUSER_NAME, "");
		if (this.mList != null) {
			if (holder.name != null) {
				holder.name.setText(name);
			}
			if (holder.date != null) {
				holder.date.setText(sp.getReds().getDate().substring(0, 16));
			}
			if (holder.content != null) {
				holder.content.setText(sp.getReds().getWords());
			}
			if (holder.ImageView != null) {
				// ����ͼƬ
				if (image == null) {
					holder.ImageView.setImageBitmap(
							BitmapFactory.decodeResource(mContext.getResources(), R.drawable.loadfailed));
				} else {
					DisplayImageOptions options = new DisplayImageOptions.Builder()//
							.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
							.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
							.cacheInMemory(true) // �ڴ滺��
							.cacheOnDisk(true) // sdcard����
							.bitmapConfig(Config.RGB_565)// �����������
							.build();//
					ImageLoader.getInstance().displayImage(image, holder.ImageView, options);
				}
			}
			ArrayList<String> transPicture = new ArrayList<String>();
			final ArrayList<String> imageUrls;
			if (sp.getListmap() == null || sp.getListmap().size() == 0) { // û��ͼƬ��Դ������GridView
				holder.picture.setVisibility(View.GONE);
			} else {
				holder.picture.setVisibility(View.VISIBLE);
				for (int i = 0; i < sp.getListmap().size(); i++) {
					transPicture.add("file://" + sp.getListmap().get(i).getImgurl());
				}
			}
			imageUrls = transPicture;
			holder.picture.setAdapter(new BaseGridViewAdapter(mContext, imageUrls));
			// ��������Ź��񣬲鿴��ͼ
			holder.picture.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					// TODO Auto-generated method stub
					imageBrower(position, imageUrls);
				}
			});
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
		private SpeakGridView picture;

	}

}