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
import com.bb.model.Huodong;


public class  HuodongInfoActivity extends Activity {


	private Huodong huodong ;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.huodong_info);
		 
		huodong = (Huodong) getIntent().getSerializableExtra("object");

		TextView tv_biaoti = (TextView) this.findViewById(R.id.biaoti);
		tv_biaoti.setText(  "" + huodong.biaoti   ) ;
		TextView tv_neirong = (TextView) this.findViewById(R.id.neirong);
		tv_neirong.setText(  "     " + huodong.neirong   ) ;
		TextView tv_shijian = (TextView) this.findViewById(R.id.shijian);
		tv_shijian.setText(  "" + huodong.shijian   ) ;
     
		TextView btn_button1 = (TextView) findViewById(R.id.button1) ;
		btn_button1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

		    	Intent intent = new Intent( HuodongInfoActivity.this , HuodongListActivity.class ) ;
//				intent.putExtra("id",   info.info_id  );
				startActivity( intent );
				finish();
			}
		}) ; 
		
	}
	
	
}
