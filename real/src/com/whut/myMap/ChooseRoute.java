package com.whut.myMap;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ObjectUtils.Null;

import com.baidu.pano.platform.http.r;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.whut.myMap.ChoosePoint.CheckBox_Point;
import com.whut.myMap.R.drawable;
import com.whut.myMap.domain.TestRoute;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.serverce.trackservice;
import com.whut.myMap.utils.picture;

import android.R.integer;
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
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ChooseRoute extends Activity {
	private List<trackbean> list;
	private ListView listView;
	private Map<Integer, Boolean> isSelected;
	private List beSelectedData = new ArrayList();
	private Intent intent;
	private Bundle bundle;
    private trackbean testRoute=new trackbean();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview_container);
		listView = (ListView) findViewById(R.id.fs_listview);
		list = getData();
		intent = new Intent();
		bundle=new Bundle();
		initList();
		CheckBox_Route myAdapter = new CheckBox_Route(list, ChooseRoute.this);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		myAdapter.notifyDataSetChanged();
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("map", list.get(position).toString());
			}
		});
		listView.setAdapter(myAdapter);
		getActionBar().setTitle("ѡ���μ�");
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
		// ����Ѿ�ѡ�����
		if (beSelectedData.size() > 0) {
			beSelectedData.clear();
		}
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.chooseroute);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		MenuItem add = menu.add(0, 0, 0, "����");
		add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (testRoute != null) {
					bundle.putSerializable("ROUTE",
						testRoute);
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
				} else {
					Toast.makeText(getApplicationContext(), "����Ϊ�գ�", 0).show();
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
			this.overridePendingTransition(R.anim.tran_pre_in,
					R.anim.tran_pre_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
/**
 * ���� ���� ͼƬ
 * 
 * @return
 */
	private List<trackbean> getData() {
		List<TestRoute> person = new ArrayList<TestRoute>();
		trackservice service=new trackservice(getApplication());
		return service.gettrack(getApplication());
//		for (int i = 0; i < 10; i++) {
//			TestRoute model = new TestRoute();
//			long time = System.currentTimeMillis();// long now =
//													// android.os.SystemClock.uptimeMillis();
//			SimpleDateFormat format = new SimpleDateFormat(
//					"yyyy-MM-dd HH:mm:ss");
//			Date d1 = new Date(time);
//			model.setDate(d1);
//			model.setWords("��ӰԺ");
//			person.add(model);
//		}
		
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

		// final ListViewItem item = getItem(position); ȱ��item���
		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				LayoutInflater mInflater = LayoutInflater.from(mContext);
				convertView = mInflater.inflate(R.layout.checkbox_img_two_tv,
						null);
				holder.title = (TextView) convertView
						.findViewById(R.id.tvTitle);
				holder.date = (TextView) convertView.findViewById(R.id.tvDate);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.img);
				holder.choose = (CheckBox) convertView
						.findViewById(R.id.choose);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			trackbean sp = this.mList.get(position);
			if (this.mList != null) {
				if (holder.imageView != null) {
					// ����ͼ
					try{
						DisplayImageOptions options = new DisplayImageOptions.Builder()//
								.showImageOnLoading(R.drawable.loadpic) // ��������ʾ��Ĭ��ͼƬ
								.showImageOnFail(R.drawable.loadfailed) // ���ü���ʧ�ܵ�Ĭ��ͼƬ
								.cacheInMemory(true) // �ڴ滺��
								.cacheOnDisk(true) // sdcard����
								.bitmapConfig(Config.RGB_565)// �����������
								.build();//
						ImageLoader.getInstance().displayImage("file://"+sp.getTrack().getBgurl(),
								holder.imageView, options);
						
					}catch(Exception e){
						e.printStackTrace();
						holder.imageView.setImageResource(R.drawable.loadpic);
					}
				}
				if (holder.title != null) {
					holder.title.setText(sp.getTrack().getWords());
				}
				if (holder.date != null) {
					holder.date.setText(sp.	getTrack().getDate());
				}
				if (holder.choose != null) {
					holder.choose.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// ��ǰ�����CB
							boolean cu = !isSelected.get(position);
							// �Ƚ����е���ΪFALSE
							for (Integer p : isSelected.keySet()) {
								isSelected.put(p, false);
							}
							// �ٽ���ǰѡ��CB��ʵ��״̬
							isSelected.put(position, cu);
							CheckBox_Route.this.notifyDataSetChanged();
							beSelectedData.clear();
							if (cu) {
								beSelectedData.add(list.get(position));
								testRoute=list.get(position);			
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
			public ImageView imageView;
			public CheckBox choose;
		}
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("������back��   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}