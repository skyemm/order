package com.bb.ui;


import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.db.DbControl;
import com.bb.model.Info;
import com.bb.util.AsyncImageLoader;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.util.Constants;



public class SearchActivity extends Activity {

	
	EditText search_name ;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.search);
		 
		search_name = (EditText) this.findViewById(R.id.search_name);
  
		Button btn = (Button) findViewById(R.id.button1) ;
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

	      	  Intent all = new Intent(SearchActivity.this, SearchListActivity.class);
	          all.putExtra("search_name", search_name.getText().toString().trim() );
	          startActivity(all);
			}
		}) ;
		
	}
	
	
}
