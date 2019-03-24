package com.bb.ui;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle; 
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.model.Liuyanban;
import com.bb.api.LiuyanbanHttpAdapter;
import com.bb.util.AsyncImageLoader;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.util.Constants;



public class LiuyanbanListActivity  extends  ListActivity {


    private LiuyanbanAdapter adapter = null;
    
    private ArrayList<Liuyanban> liuyanbanList;

    private String type;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        type = (String) getIntent().getExtras().get("type");        
        setContentView(R.layout.liuyanban_list);  
        
        Button left_button = (Button) findViewById(R.id.left_button);
		left_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
		        new LoadTask().execute();    
			}
		});

		Button right_button = (Button) findViewById(R.id.right_button);
		right_button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intent = new Intent( LiuyanbanListActivity.this, LiuyanbanInfoAddActivity.class);
				startActivity(intent);
			}
		});

        new LoadTask().execute();     
    }
    
	//    @Override  
  // public boolean onCreateOptionsMenu(Menu menu) {  
  //     // Inflate the menu; this adds items to the action bar if it is present.  
  //     super.onCreateOptionsMenu(menu);
  //   
  //     MenuItem refresh = 	menu.add(0, 1, 0, "刷新");  
  //     MenuItem add = 		menu.add(0, 2, 1, "添加");   
  //     
  //     //绑定到ActionBar    
  //     refresh.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);   
  //     add.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);   
  //     return true;  
  // }  
  //
  //  @Override  
  //  protected void onStart() {  
  //      super.onStart();  
  //      ActionBar actionBar = this.getActionBar();  
  //      actionBar.setDisplayHomeAsUpEnabled(true);  
  //  }  
  //  
  //  public boolean onOptionsItemSelected(MenuItem item) {
	//	// TODO Auto-generated method stub
	//	switch (item.getItemId()) {
	//		case 1:
	//	        new LoadTask().execute();   
	//	        break;
	//		case 2:
	//			Intent intent = new Intent(this, LiuyanbanInfoAddActivity.class);
	//			startActivity(intent);
	//			break;
	//		default:
	//			break;
	//	}
	//	return super.onOptionsItemSelected(item);
	//}
	
	/**
	 * 异步加载所有资源
	 *
	 */
	public class LoadTask extends AsyncTask<Void, Void, Void>{
	 
		protected Void doInBackground(Void... arg0) {
			try {
				liuyanbanList =  LiuyanbanHttpAdapter.getAllLiuyanbanList(-1, -1,type) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
 
		protected void onPostExecute(Void result) {
			adapter = new LiuyanbanAdapter() ;
			setListAdapter(adapter);
			removeDialog(0);
			super.onPostExecute(result);
		}
		
		protected void onPreExecute() {
			showDialog(0);
			super.onPreExecute();
		}
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent intent = new Intent( LiuyanbanListActivity.this, LiuyanbanInfoActivity.class);
		intent.putExtra("object", liuyanbanList.get(position));
		startActivity(intent);
	}
	

	public class LiuyanbanAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;
		
		public int getCount() {
			return liuyanbanList.size();
		}

		public Object getItem(int arg0) {
			return liuyanbanList.get(arg0);
		}
		
		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
		
			asyncImageLoader = new AsyncImageLoader();
			 
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.liuyanban_list_row, null);
 
		    TextView name =  (TextView) convertView.findViewById(R.id.name); 
		    TextView yonghu =  (TextView) convertView.findViewById(R.id.yonghu); 
		    
	        Liuyanban u = liuyanbanList.get(position); 
	        name.setText(  u.biaoti    );
	        yonghu.setText(  u.shijian    );
	        
			return convertView;
		}
	}
	
	
 
    
}
