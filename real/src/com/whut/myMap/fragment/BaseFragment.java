package com.whut.myMap.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;


public class BaseFragment extends FragmentActivity {
	protected void router(Activity aty, Class<? extends Activity> clazz) {
		Intent intent = new Intent(aty, clazz);
		startActivity(intent);
	}

	protected void router(Activity aty, Class<? extends Activity> clazz,
			int requestCode) {
		Intent intent = new Intent(aty, clazz);
		startActivityForResult(intent, requestCode);
	}

	protected void addFragments(int rId, Fragment[] fragments) {
		FragmentTransaction tx = getTransaction();
		for (Fragment f : fragments) {
			tx.add(rId, f);
		}
		tx.commit();
	}

	protected void hideFragments(Fragment[] fragments) {
		FragmentTransaction tx = getTransaction();
		for (Fragment f : fragments) {
			tx.hide(f);
		}
		tx.commit();
	}
	protected void showFragment(Fragment fragment) {
		FragmentTransaction tx = getTransaction();
		tx.show(fragment);
		tx.commit();
	}

	/**
	 * fragmenttransaction
	 * 
	 * @return
	 */
	protected FragmentTransaction getTransaction() {
		return getManager().beginTransaction();
	}

	/**
	 * fragmentmanager
	 * 
	 * @return
	 */
	protected FragmentManager getManager() {
		return getSupportFragmentManager();
	}
	
	
}
