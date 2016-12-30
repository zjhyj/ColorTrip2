package com.whut.myMap.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.adapter.ListViewAdapter_speak_point.ViewHolder;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.track;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Route_Main_ListviewAdapter extends BaseAdapter {

	trackbean track;
	private Context mContext;

	public Route_Main_ListviewAdapter(Context context, trackbean track) {
		super();
		this.track = track;
		this.mContext = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		try {
			return track.getRedsbean().size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return track.getRedsbean().get(position).getReds().getReds_id();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater mInflater = LayoutInflater.from(mContext);
			convertView = mInflater.inflate(R.layout.route_item, null);
			holder.banner = (ImageView) convertView.findViewById(R.id.banner);
			holder.content = (TextView) convertView.findViewById(R.id.tvContent);
			holder.date = (TextView) convertView.findViewById(R.id.tvDate);
			holder.iv1 = (ImageView) convertView.findViewById(R.id.iv1);
			holder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
			holder.iv3 = (ImageView) convertView.findViewById(R.id.iv3);
			holder.iv4 = (ImageView) convertView.findViewById(R.id.iv4);
			holder.iv5 = (ImageView) convertView.findViewById(R.id.iv5);
			holder.iv6 = (ImageView) convertView.findViewById(R.id.iv6);
			holder.iv7 = (ImageView) convertView.findViewById(R.id.iv7);
			holder.iv8 = (ImageView) convertView.findViewById(R.id.iv8);
			holder.iv9 = (ImageView) convertView.findViewById(R.id.iv9);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		final redsbean sp = this.track.getRedsbean().get(position);
		if (this.track != null) {
			if (holder.date != null) {
				holder.date.setText(sp.getReds().getDate().substring(0, 16));
			}
			if (holder.banner != null) {
				switch (track.getTrack().getTheme()) {
				case 0:
                   holder.banner.setVisibility(View.GONE);
					break;
				case 1:
	                   holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg1_h));
					break;
				case 2:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg2_h));
					break;
				case 3:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg3_h));
					break;
				case 4:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg5_h));
					break;
				case 5:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg6_h));
					break;
				case 6:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg7_h));
					break;
				case 7:
					 holder.banner.setVisibility(View.VISIBLE);
	                   holder.banner.setImageBitmap(BitmapFactory
								.decodeResource(mContext.getResources(),
										R.drawable.bg8_h));
					break;
				default:
					break;
				}
				holder.date.setText(sp.getReds().getDate().substring(0, 16));
			}
			if (holder.content != null) {
				if (sp.getReds().getWords() != null) {
					holder.content.setText(sp.getReds().getWords());
				} else {
					holder.content.setVisibility(View.GONE);
				}
			}
			DisplayImageOptions options = new DisplayImageOptions.Builder()//
					.showImageOnLoading(R.drawable.bg_trip_pic_placeholder) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.bg_trip_pic_placeholder) // 设置加载失败的默认图片
					.cacheInMemory(true)// 内存缓存
					.resetViewBeforeLoading(true).cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			ArrayList<String> transPicture = new ArrayList<String>();
			ArrayList<String> imageUrls = new ArrayList<String>();
			for (int i = 0; i < sp.getListmap().size(); i++) {
				transPicture.add(sp.getListmap().get(i).getImgurl());
			}
			for (int i = 0; i < transPicture.size(); i++) {
				String image = transPicture.get(i);
				//int index = image.lastIndexOf("_");
				imageUrls.add(image);
			}
			final ArrayList<String> imageUrls2 = imageUrls;
			if (holder.iv1 != null) {

				try {
					holder.iv1.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(0), holder.iv1, options);
					holder.iv1.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(0, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv1.setVisibility(View.GONE);
				}
			}
			if (holder.iv2 != null) {
				try {
					holder.iv2.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(1), holder.iv2, options);
					holder.iv2.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(1, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv2.setVisibility(View.GONE);
				}
			}
			if (holder.iv3 != null) {
				try {
					holder.iv3.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(2), holder.iv3, options);
					holder.iv3.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(2, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv3.setVisibility(View.GONE);
				}
			}
			if (holder.iv4 != null) {
				try {
					holder.iv4.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(3), holder.iv4, options);
					holder.iv4.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(3, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv4.setVisibility(View.GONE);
				}
			}
			if (holder.iv5 != null) {
				try {
					holder.iv5.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(4), holder.iv5, options);
					holder.iv5.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(4, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv5.setVisibility(View.GONE);
				}
			}
			if (holder.iv6 != null) {
				try {
					holder.iv6.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(5), holder.iv6, options);
					holder.iv6.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(5, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv6.setVisibility(View.GONE);
				}
			}
			if (holder.iv7 != null) {
				try {
					holder.iv7.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(6), holder.iv7, options);
					holder.iv7.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(6, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv7.setVisibility(View.GONE);
				}
			}
			if (holder.iv8 != null) {
				try {
					holder.iv8.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(7), holder.iv8, options);
					holder.iv8.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(7, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv8.setVisibility(View.GONE);
				}
			}
			if (holder.iv9 != null) {
				try {
					holder.iv9.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(imageUrls.get(8), holder.iv9, options);
					holder.iv9.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							imageBrower(8, imageUrls2);
						}
					});
				} catch (Exception e) {
					holder.iv9.setVisibility(View.GONE);
				}
			}
		}
		return convertView;
	}

	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		mContext.startActivity(intent);
	}

	public final class ViewHolder {
		public TextView date;
		public TextView content;
		public ImageView banner;
		public ImageView iv1;
		public ImageView iv2;
		public ImageView iv3;
		public ImageView iv4;
		public ImageView iv5;
		public ImageView iv6;
		public ImageView iv7;
		public ImageView iv8;
		public ImageView iv9;

	}
}
