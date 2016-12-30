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
	private ImageView mTabLine;// ָ����
	private int screenWidth;// ��Ļ�Ŀ��
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
		// ��ȡ��Ļ�Ŀ��
		DisplayMetrics outMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		screenWidth = outMetrics.widthPixels;
		// ����mTabLine���//��ȡ�ؼ���(ע�⣺һ��Ҫ�ø��ؼ���LayoutParamsдLinearLayout.LayoutParams)
		LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();// ��ȡ�ؼ��Ĳ��ֲ�������
		lp.width = screenWidth / 2;
		mTabLine.setLayoutParams(lp); // ���øÿؼ���layoutParams����
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
					mViewPager.setCurrentItem(0);// ѡ��ĳһҳ
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
	 * ҳ�������ı��¼�
	 */
	public class TabOnPageChangeListener implements OnPageChangeListener {

		/**
		 * ������״̬�ı�ʱ���� state=0��ʱ���ʾʲô��û��������ͣ���� state=1��ʱ���ʾ���ڻ���
		 * state==2��ʱ���ʾ���������
		 */
		public void onPageScrollStateChanged(int state) {

		}

		/**
		 * ��ǰҳ�汻����ʱ���� position:��ǰҳ�� positionOffset:��ǰҳ��ƫ�Ƶİٷֱ�
		 * positionOffsetPixels:��ǰҳ��ƫ�Ƶ�����λ��
		 */
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			LinearLayout.LayoutParams lp = (android.widget.LinearLayout.LayoutParams) mTabLine.getLayoutParams();
			// ��ȡ��������������ľ���
			lp.leftMargin = (int) ((positionOffset + position) * screenWidth / 2);
			mTabLine.setLayoutParams(lp);
		}

		// ���µ�ҳ�汻ѡ��ʱ����
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
		System.out.println("������back��   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
}