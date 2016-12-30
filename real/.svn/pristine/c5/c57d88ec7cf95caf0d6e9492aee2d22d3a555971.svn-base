package com.whut.myMap;

import java.util.ArrayList;
import java.util.List;

import com.whut.myMap.adapter.TabAdapter;
import com.whut.myMap.fragment.Footprint_point;
import com.whut.myMap.fragment.Footprint_route;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MyFootPrint extends FragmentActivity {
	private RadioGroup mRadioGroup;
	private RadioButton mRadio01;
	private RadioButton mRadio02;
	private ActionBar mActionBar;
	private ImageView mTabLine;// 指导线
	private int screenWidth;// 屏幕的宽度
	private Intent intent;
	private static int FAVOR_OR_MY;
	private ViewPager mViewPager;
	private TabAdapter mAdapter;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_foot_print);
		intent = getIntent();
		FAVOR_OR_MY = intent.getIntExtra("FAVOR_OR_MY", 1);
		initViews();
		initEvent();
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.caogao_menu, menu);
		mActionBar = getActionBar();
			mActionBar.setTitle(R.string.footprint);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
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
		case R.id.post:
			Intent intent = new Intent(MyFootPrint.this, WriteDongtai.class);
			startActivity(intent);
			overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void initViews() {
		mRadioGroup = (RadioGroup) findViewById(R.id.id_radioGroup);
		mRadio01 = (RadioButton) findViewById(R.id.id_tab1);
		mRadio02 = (RadioButton) findViewById(R.id.id_tab2);

		mTabLine = (ImageView) findViewById(R.id.id_tab_line);
		// 获取屏幕的宽度
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		// 设置mTabLine宽度//获取控件的(注意：一定要用父控件的LayoutParams写LinearLayout.LayoutParams)
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();// 获取控件的布局参数对象
		lp.width = screenWidth / 2;
		mTabLine.setLayoutParams(lp); // 设置该控件的layoutParams参数
		mFragments.add(new Footprint_point());
		mFragments.add(new Footprint_route());

		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mAdapter = new TabAdapter(getSupportFragmentManager(), mFragments);
		mViewPager.setAdapter(mAdapter);
	}

	private void initEvent() {
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.id_tab1:
					mViewPager.setCurrentItem(0);// 选择某一页
					break;
				case R.id.id_tab2:
					mViewPager.setCurrentItem(1);
					break;

				}
			}
		});

		mViewPager.setOnPageChangeListener(new TabOnPageChangeListener());
	}

	/**
	 * 页卡滑动改变事件
	 */
	public class TabOnPageChangeListener implements OnPageChangeListener {

		/**
		 * 当滑动状态改变时调用 state=0的时候表示什么都没做，就是停在那 state=1的时候表示正在滑动
		 * state==2的时候表示滑动完毕了
		 */
		public void onPageScrollStateChanged(int state) {

		}

		/**
		 * 当前页面被滑动时调用 position:当前页面 positionOffset:当前页面偏移的百分比
		 * positionOffsetPixels:当前页面偏移的像素位置
		 */
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
			// 获取组件距离左侧组件的距离
			lp.leftMargin = (int) ((positionOffset + position) * screenWidth / 2);
			mTabLine.setLayoutParams(lp);
		}

		// 当新的页面被选中时调用
		public void onPageSelected(int position) {
			switch (position) {
			case 0:
				mRadio01.setChecked(true);
				break;
			case 1:
				mRadio02.setChecked(true);
				break;

			}
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("按下了back键   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}