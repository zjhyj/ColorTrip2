package com.whut.myMap.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;

import java.util.ArrayList;
import java.util.List;

import com.astuetz.PagerSlidingTabStrip;
import com.whut.myMap.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MutiFragment extends Fragment {
	static final int SPEAK_FRAGMENT = 1;
	static final int SPEAK_FRAGMENT_2 = 3;
	static final int SPEAK_FRAGMENT_3 = 4;
	static final int SPEAK_FRAGMENT_4 = 5;
	static final int SPEAK_FRAGMENT_5 = 6;
	private final Handler handler = new Handler();
    static List<Fragment> fragments;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private MyPagerAdapter adapter;

	private Drawable oldBackground = null;
	private int currentColor = 0xFF666666;
	private View rootView;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method st
	      rootView = inflater.inflate(R.layout.fragment_muti, container, false);
		tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
		fragments=new ArrayList<Fragment>();
		SpeakFragment5 speakFragment5=new SpeakFragment5();
		SpeakFragment speakFragment=new SpeakFragment();
		SpeakFragment2 speakFragment2=new SpeakFragment2();
		SpeakFragment3 speakFragment3=new SpeakFragment3();
		SpeakFragment4 speakFragment4=new SpeakFragment4();
		fragments.add(speakFragment5);
		fragments.add(speakFragment4);
		fragments.add(speakFragment2);
		fragments.add(speakFragment3);
		fragments.add(speakFragment);
		pager = (ViewPager) rootView.findViewById(R.id.pager);
		pager.setOffscreenPageLimit(5);
		adapter = new MyPagerAdapter(getChildFragmentManager());
		pager.setAdapter(adapter);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);

		tabs.setViewPager(pager);
		return rootView;
	}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (resultCode != Activity.RESULT_OK) {// result is not correct
		return;
	} else {
		Fragment f;
		switch (requestCode) {
		case SPEAK_FRAGMENT:
			/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
			 f = fragments.get(4);
			/* 然后在碎片中调用重写的onActivityResult方法 */
			f.onActivityResult(requestCode, resultCode, data);
			break;
		case SPEAK_FRAGMENT_2:
			/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
			 f = fragments.get(2);
			/* 然后在碎片中调用重写的onActivityResult方法 */
			f.onActivityResult(requestCode, resultCode, data);
			break;
		case SPEAK_FRAGMENT_3:
			/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
			 f =fragments.get(3);
			/* 然后在碎片中调用重写的onActivityResult方法 */
			f.onActivityResult(requestCode, resultCode, data);
			break;
		case SPEAK_FRAGMENT_4:
			/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
			 f = fragments.get(1);
			/* 然后在碎片中调用重写的onActivityResult方法 */
			f.onActivityResult(requestCode, resultCode, data);
			break;
		case SPEAK_FRAGMENT_5:
			/* 在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment */
			 f = fragments.get(0);
			/* 然后在碎片中调用重写的onActivityResult方法 */
			f.onActivityResult(requestCode, resultCode, data);
			break;
		}
	}
}



public class MyPagerAdapter extends FragmentPagerAdapter {

	private final String[] TITLES = { "达人游记","热门游记", "最新游记", "热门动态", "最新动态"};

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {
		return fragments.get(position);
	}

}
}
