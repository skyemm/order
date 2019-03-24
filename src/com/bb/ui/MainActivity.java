package com.bb.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

import com.bb.R;
import com.bb.util.Constants;

import edu.self.LoginNewActivity;


public class MainActivity  extends   Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_nine); 

		ImageAdapter adapter = new ImageAdapter( this ) ;
		
		GridView  gridview = (GridView)findViewById(R.id.MainActivityGrid);
        gridview.setAdapter(adapter);
        
        gridview.setOnItemClickListener( new OnItemClickListener()  {  
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	
            	Intent intent = null;
            	switch( position ){
		    		case 0:
//		    			intent = new Intent( MainActivity.this, FoodsListActivity.class );
////	    		        "精品菜" 
//	    		        intent.putExtra( "type", Constants.FLAG_TOP );
//		    			break;
//            		case 1:
//		    			intent = new Intent( MainActivity.this, FoodsListActivity.class );
////    		        	地区特色菜
//	    		        intent.putExtra("type", Constants.FLAG_ZEOR); 
//		    			break;
//            		case 2:
//            			"全部菜", 
		    			
		    			intent = new Intent( MainActivity.this, FoodsTabActivity.class );
//	    		        intent.putExtra("type", Constants.FLAG_ALL ); 
            			break;
            			
            		case 1: 
		    			intent = new Intent( MainActivity.this, GwcListActivity.class );
	    		        intent.putExtra("type", Constants.FLAG_ALL ); 
            			break;

//            			"搜索菜品", "优惠活动", 
            		case 2:
                    	showDialog( MainActivity.this );
//		    			intent = new Intent( MainActivity.this, GwcListActivity.class );
            			break;
            		case 3: 
		    			intent = new Intent( MainActivity.this, HuodongListActivity.class );
            			break;
            			 
//            			"留言版" , "退出" ,  
            		case 4: 
		    			intent = new Intent( MainActivity.this, LiuyanbanListActivity.class );
            			break;
            		case 5: 
		    			intent = new Intent( MainActivity.this, LoginNewActivity.class );
            			break;
            	
            	}
            	if( intent != null )
            		startActivity( intent );
            	
//                Toast.makeText(MainActivity.this, "pic" + position, Toast.LENGTH_SHORT).show();  
            }  
        });  
        
	}
	
	
	
	 private void showDialog( Context context ){
	    	
	    	LayoutInflater li = LayoutInflater.from(context);
			View promptsView = li.inflate(R.layout.prompts_search, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
			alertDialogBuilder.setView(promptsView);
			 
			final EditText et_search_name = (EditText) promptsView.findViewById(R.id.search_name); 
//			final EditText et_search_type = (EditText) promptsView.findViewById(R.id.search_type); 
//			final EditText et_search_info = (EditText) promptsView.findViewById(R.id.search_info); 
			
			alertDialogBuilder
				.setCancelable(false)
				.setTitle("搜索")
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
				    	 
			        	  Intent all = new Intent(MainActivity.this, SearchListActivity.class);
			              all.putExtra("search_name", et_search_name.getText().toString().trim() );
//			              all.putExtra("search_type", et_search_type.getText().toString().trim() );
//			              all.putExtra("search_info", et_search_info.getText().toString().trim() );
			              
//			              all.putExtra("search_type", et_search_type.getText().toString().trim() );
//			              all.putExtra("search_info", et_search_info.getText().toString().trim() );
			              startActivity(all);
				    }
				  });
			
			alertDialogBuilder.setNegativeButton("取消",  
	                new DialogInterface.OnClickListener() {  
	                    public void onClick(DialogInterface dialog, int whichButton) {  
	                    	dialog.cancel();
	                    }  
			  });

			AlertDialog alertDialog = alertDialogBuilder.create(); 
			alertDialog.show(); 
	    }
	 
	 
	
	

}
