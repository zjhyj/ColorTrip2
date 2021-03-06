package com.whut.myMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.whut.myMap.ChooseRoute.CheckBox_Route;
import com.whut.myMap.ChooseRoute.CheckBox_Route.ViewHolder;
import com.whut.myMap.domain.TestRoute;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.track;
import com.whut.myMap.serverce.trackservice;
import com.whut.myMap.utils.picture;

import android.R.integer;
import android.R.string;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class DeleteTrack extends Activity{
	private ActionBar mActionBar;
	private List<trackbean> list;
	private ListView listView;
	private Map<Integer, Boolean> isSelected;
	private Intent intent;
	private Bundle bundle;
    private Map<Integer, Integer>  map;
    private int track_id;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_container);
		map=new HashMap<Integer, Integer>();
		intent=getIntent();
		track_id=intent.getIntExtra("track_id", 0);
		listView = (ListView) findViewById(R.id.fs_listview);
		list = getData();
		intent = new Intent();
		bundle=new Bundle();
		initList();
		CheckBox_Route myAdapter = new CheckBox_Route(list, DeleteTrack.this);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		myAdapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("map", list.get(position).toString());
			}
		});
		listView.setAdapter(myAdapter);
		getActionBar().setTitle("删除轨迹");
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.edit, menu);
		mActionBar = getActionBar();
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;
	}
	private List<trackbean> getData() {
		trackservice service=new trackservice(getApplication());
		return service.gettrackbyfir(getApplication(),track_id);
		
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
		
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		showPre();
	}
	public void showPre() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			showPre();
			return true;
		case R.id.delete:
			trackservice service=new trackservice(getApplication());
			for (Integer value : map.values()) {  
				  
				service.deletetrack(getApplication(), track_id, value);
			  
			}  
			showPre();
			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	public class CheckBox_Route extends BaseAdapter {
		private List<trackbean> mList;
		private Context mContext;

		public CheckBox_Route(List<trackbean> list, Context mContext) {
			super();
			this.mList = list;
			this.mContext = mContext;
		}

		@Override
		public int getCount() {
			if (mList == null) {
				return 0;
			} else {
				return this.mList.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (mList == null) {
				return null;
			} else {
				return this.mList.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		// final ListViewItem item = getItem(position); 缺少item项？？
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(mContext);
				convertView = mInflater.inflate(R.layout.two_textview,
						null);
				holder.title = (TextView) convertView
						.findViewById(R.id.tvTitle);
				holder.date = (TextView) convertView.findViewById(R.id.tvDate);
				holder.choose = (CheckBox) convertView
						.findViewById(R.id.choose);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			trackbean sp = this.mList.get(position);
			if (this.mList != null) {
				
				if (holder.title != null) {
					holder.title.setText(sp.getTrack().getWords());
				}
				if (holder.date != null) {
					holder.date.setText(sp.getTrack().getDate());
				}
				if (holder.choose != null) {
					holder.choose.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 当前点击的CB
							boolean cu = !isSelected.get(position);
							// 再将当前选择CB的实际状态
							isSelected.put(position, cu);
							CheckBox_Route.this.notifyDataSetChanged();
							if (cu) {
								map.put(position, list.get(position).getTrack().getTrack_id());
							}else {
								map.remove(position);
							}
						}
					});
					holder.choose.setChecked(isSelected.get(position));
				}
			}
			return convertView;
		}
		public final class ViewHolder {
			public TextView title;
			public TextView date;
			public CheckBox choose;
		}
	}

}
