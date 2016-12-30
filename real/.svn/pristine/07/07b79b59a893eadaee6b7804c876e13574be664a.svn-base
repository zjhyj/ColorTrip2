package com.whut.myMap.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 功能：主页引导栏的三个Fragment页面设置适配器
 */
public class TabAdapter extends FragmentPagerAdapter {
	private List<Fragment> mFragments;

	public TabAdapter(FragmentManager fm, List<Fragment> mFragments) {
		super(fm);
		this.mFragments = mFragments;
	}

	public Fragment getItem(int position) {
		return mFragments.get(position);
	}

	public int getCount() {
		return mFragments.size();
	}
}




