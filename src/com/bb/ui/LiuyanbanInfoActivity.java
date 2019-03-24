package com.bb.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button; 
import android.widget.SeekBar;
import android.widget.TextView;
import com.bb.R; 
import com.bb.model.Info; 
import com.bb.util.Constants;
import com.bb.model.Liuyanban;


public class  LiuyanbanInfoActivity extends Activity {


	private Liuyanban liuyanban ;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.liuyanban_info);
		 
		liuyanban = (Liuyanban) getIntent().getSerializableExtra("object");

		TextView tv_biaoti = (TextView) this.findViewById(R.id.biaoti);
		tv_biaoti.setText(  "" + liuyanban.biaoti   ) ;
		TextView tv_neirong = (TextView) this.findViewById(R.id.neirong);
		tv_neirong.setText(  "     " + liuyanban.neirong   ) ;
		TextView tv_shijian = (TextView) this.findViewById(R.id.shijian);
		tv_shijian.setText(  "" + liuyanban.shijian   ) ;
		TextView tv_uid = (TextView) this.findViewById(R.id.uid);
		tv_uid.setText(  "用户 : " + liuyanban.uid   ) ;
//		TextView tv_xingming = (TextView) this.findViewById(R.id.xingming);
//		tv_xingming.setText(  "用户姓名 : " + liuyanban.xingming   ) ;
     
		TextView btn_button1 = (TextView) findViewById(R.id.button1) ;
		btn_button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

		    	Intent intent = new Intent( LiuyanbanInfoActivity.this , LiuyanbanListActivity.class ) ;
//				intent.putExtra("id",   info.info_id  );
				startActivity( intent );
				finish();
			}
		}) ; 
		
	}
	
	
}
