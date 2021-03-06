package com.whut.myMap.adapter;

import java.util.ArrayList;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.EditReds;
import com.whut.myMap.ImagePagerActivity;
import com.whut.myMap.R;
import com.whut.myMap.Speak_Main_route;
import com.whut.myMap.adapter.Route_Main_ListviewAdapter.ViewHolder;
import com.whut.myMap.adapter.SpeakGridView.OnTouchInvalidPositionListener;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.layout.ResizableImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Route_Base_ListviewAdapter extends BaseAdapter {
	trackbean track;
	private Context mContext;

	public Route_Base_ListviewAdapter(Context context, trackbean track) {
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
			convertView = mInflater.inflate(R.layout.route_item2, null);
			holder.content = (TextView) convertView.findViewById(R.id.tvContent);
			holder.date = (TextView) convertView.findViewById(R.id.tvDate);
			holder.iv1 = (ResizableImageView) convertView.findViewById(R.id.iv1);
			holder.iv2 = (ResizableImageView) convertView.findViewById(R.id.iv2);
			holder.iv3 = (ResizableImageView) convertView.findViewById(R.id.iv3);
			holder.iv4 = (ResizableImageView) convertView.findViewById(R.id.iv4);
			holder.iv5 = (ResizableImageView) convertView.findViewById(R.id.iv5);
			holder.iv6 = (ResizableImageView) convertView.findViewById(R.id.iv6);
			holder.iv7 = (ResizableImageView) convertView.findViewById(R.id.iv7);
			holder.iv8 = (ResizableImageView) convertView.findViewById(R.id.iv8);
			holder.iv9 = (ResizableImageView) convertView.findViewById(R.id.iv9);
			holder.edit=(LinearLayout) convertView.findViewById(R.id.edit);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		final redsbean sp = this.track.getRedsbean().get(position);
		if (sp!= null) {
			if (holder.date != null) {
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
					.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
					.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
					.cacheInMemory(true)// 内存缓存
					.resetViewBeforeLoading(true).cacheOnDisk(true) // sdcard缓存
					.bitmapConfig(Config.RGB_565)// 设置最低配置
					.build();//
			ArrayList<String> transPicture = new ArrayList<String>();
			if (sp.getListmap() == null || sp.getListmap().size() == 0) { // 没有图片资源就隐藏GridView
			} else {

				for (int i = 0; i < sp.getListmap().size(); i++) {
					transPicture.add("file://" + sp.getListmap().get(i).getImgurl());
				}
			}
			final ArrayList<String> imageUrls2 = transPicture;
			if (holder.iv1 != null) {

				try {
					holder.iv1.setVisibility(View.VISIBLE);
					ImageLoader.getInstance().displayImage(transPicture.get(0), holder.iv1, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(1), holder.iv2, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(2), holder.iv3, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(3), holder.iv4, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(4), holder.iv5, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(5), holder.iv6, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(6), holder.iv7, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(7), holder.iv8, options);
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
					ImageLoader.getInstance().displayImage(transPicture.get(8), holder.iv9, options);
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
			if (holder.edit != null) {
				try {
					holder.edit.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Intent intent= new Intent(mContext, EditReds.class);
							Bundle bundle=new Bundle();
							intent.putExtra("type", 0);
							bundle.putSerializable("redsbean", sp);
							intent.putExtras(bundle);
							mContext.startActivity(intent);
							((Activity) mContext).overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
						}
					});
				} catch (Exception e) {
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
		public ResizableImageView iv1;
		public ResizableImageView iv2;
		public ResizableImageView iv3;
		public ResizableImageView iv4;
		public ResizableImageView iv5;
		public ResizableImageView iv6;
		public ResizableImageView iv7;
		public ResizableImageView iv8;
		public ResizableImageView iv9;
         public LinearLayout edit;
	}

}

