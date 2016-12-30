package com.whut.myMap;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
public class Help extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		RelativeLayout create=(RelativeLayout) findViewById(R.id.re1);
		RelativeLayout edit=(RelativeLayout) findViewById(R.id.re2);
		RelativeLayout finish=(RelativeLayout) findViewById(R.id.re3);
		
		RelativeLayout travelinfo=(RelativeLayout) findViewById(R.id.re6);
		create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Help.this,Create.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Help.this,Edit.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Help.this,Finish.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		travelinfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Help.this,HelpTravel.class);
				startActivity(intent);
				overridePendingTransition(R.anim.tran_in, R.anim.tran_out);
			}
		});
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		ActionBar mActionBar = getActionBar();
		mActionBar.setTitle(R.string.help);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;
	}
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.out.println("°´ÏÂÁËback¼ü   onBackPressed()");
		overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.tran_pre_in, R.anim.tran_pre_out);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
