package com.whut.myMap;

import java.io.File;

import com.whut.myMap.dao.redsdao;
import com.whut.myMap.domain.redsbean;
import com.whut.myMap.domain.trackbean;
import com.whut.myMap.entites.reds;
import com.whut.myMap.entites.url;
import com.whut.myMap.serverce.Redservice;
import com.whut.myMap.serverce.trackservice;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class EditReds extends Activity{
	private ActionBar mActionBar;
	private EditText content;
	private static redsbean reds;
	private LinearLayout delete;
	private int type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editreds);
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		type=intent.getIntExtra("type", 0);
		reds= (redsbean) bundle.getSerializable("redsbean");
		content=(EditText) findViewById(R.id.content);
		content.setText(reds.getReds().getWords());
		delete=(LinearLayout) findViewById(R.id.delete);
		delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 删除动态
				dialogdelete();
			}
		});
	}
	private void dialogdelete() {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder=new Builder(EditReds.this);
		builder.setMessage("确定删除？");
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {		
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				return;
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {	
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (type==0) {
					//设置trackid为0
					Redservice service=new Redservice(EditReds.this);
					service.delete_redsintrack(EditReds.this,reds);
					trackservice trackservice=new trackservice(EditReds.this);
					trackservice.deletelbs(EditReds.this,reds.getReds().getRedsx(),reds.getReds().getRedsy());
					showPre();
				} else {
					Redservice service=new Redservice(EditReds.this);
					service.deletereds(EditReds.this,reds);
					Intent intent2 = new Intent();
					intent2.putExtra("delete", 2);
					setResult(Activity.RESULT_OK, intent2);
					showPre();
				}
			}
		});	
		builder.create().show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.doodle, menu);
		mActionBar = getActionBar();
		mActionBar.setTitle("");
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		return true;

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
		case R.id.save:
//              setResult(RESULT_OK, intent);  
			redsdao redsdao=new redsdao(EditReds.this);
			String words=content.getText().toString();
			redsdao.updatewords(reds.getReds().getReds_id(), words);
			Intent intent2 = new Intent();
			intent2.putExtra("change", content.getText().toString());
			setResult(Activity.RESULT_OK, intent2);
              showPre();
              return true; 
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
