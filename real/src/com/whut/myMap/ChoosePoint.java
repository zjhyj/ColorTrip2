package com.whut.myMap;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.serverce.Redservice;
import com.whut.myMap.utils.picture;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChoosePoint extends Activity {
	private List<redsbean> list;
	private ListView listView;
	private Map<Integer, Boolean> isSelected;
	private Intent intent;
	private List beSelectedData = new ArrayList();
	private redsbean testPoint = null;
	private Bundle bundle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_container);
		intent = new Intent();
		bundle = new Bundle();
		listView = (ListView) findViewById(R.id.fs_listview);
		list = getData();
		if (list == null) {
			return;
		}
		initList();
		CheckBox_Point myAdapter = new CheckBox_Point(this, list);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		myAdapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Log.i("map", list.get(position).toString());
			}
		});
		listView.setAdapter(myAdapter);
		getActionBar().setTitle("选择动态");
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setTitle(R.string.choosepoint);
		mActionBar.setDisplayShowHomeEnabled(false);
		MenuItem add = menu.add(0, 0, 0, "添加");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (testPoint != null) {
					bundle.putSerializable("POINT", testPoint);
					intent.putExtras(bundle);
					setResult(RESULT_OK, intent);
					finish();
				} else {
					Toast.makeText(getApplicationContext(), "不能为空！", 0).show();
				}

				return true;
			}
		});
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			this.overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}

	}

	void initList() {

		if (list == null || list.size() == 0)
			return;
		if (isSelected != null)
			isSelected = null;
		isSelected = new HashMap<Integer, Boolean>();
		for (int i = 0; i < list.size(); i++) {
			isSelected.put(i, false);
		}
		// 清除已经选择的项
		if (beSelectedData.size() > 0) {
			beSelectedData.clear();
		}
	}

	private List<redsbean> getData() {
		Redservice redservice = new Redservice(this);
		return redservice.getreds(this);
	}

	public class CheckBox_Point extends BaseAdapter {
		private Context context;

		private List<redsbean> list;

		public CheckBox_Point(Context context, List<redsbean> data) {
			this.context = context;
			this.list = data;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(context);
				convertView = mInflater.inflate(R.layout.checkbox_img_two_tv, null);
				holder.ImageView = (ImageView) convertView.findViewById(R.id.img);
				holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
				holder.date = (TextView) convertView.findViewById(R.id.tvDate);
				holder.choose = (CheckBox) convertView.findViewById(R.id.choose);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			redsbean sp = list.get(position);
			if (list != null) {
				if (holder.ImageView != null) {
					// 设置图片
					try {
						DisplayImageOptions options = new DisplayImageOptions.Builder()//
								.showImageOnLoading(R.drawable.loadpic) // 加载中显示的默认图片
								.showImageOnFail(R.drawable.loadfailed) // 设置加载失败的默认图片
								.cacheInMemory(true) // 内存缓存
								.cacheOnDisk(true) // sdcard缓存
								.bitmapConfig(Config.RGB_565)// 设置最低配置
								.build();//
						ImageLoader.getInstance().displayImage("file://"+sp.getListmap().get(0).getImgurl(),
								holder.ImageView, options);
						
					} catch (Exception e) {
						holder.ImageView.setImageResource(R.drawable.loadpic);
					}
				}
				if (holder.title != null) {
					holder.title.setText(sp.getReds().getWords());
				}
				if (holder.date != null) {
					holder.date.setText(sp.getReds().getDate());
				}
				if (holder.choose != null) {
					holder.choose.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// 当前点击的CB
							boolean cu = !isSelected.get(position);
							// 先将所有的置为FALSE
							for (Integer p : isSelected.keySet()) {
								isSelected.put(p, false);
							}
							// 再将当前选择CB的实际状态
							isSelected.put(position, cu);
							CheckBox_Point.this.notifyDataSetChanged();
							beSelectedData.clear();
							if (cu) {
								beSelectedData.add(list.get(position));
								testPoint = list.get(position);
							}
						}
					});
					holder.choose.setChecked(isSelected.get(position));
				}

			}

			return convertView;
		}

		public final class ViewHolder {
			public ImageView ImageView;
			public TextView title;
			public TextView date;
			public CheckBox choose;
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}
