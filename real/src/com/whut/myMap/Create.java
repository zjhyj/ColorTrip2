package com.whut.myMap;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class Create extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.create);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("������back��   onBackPressed()");
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
