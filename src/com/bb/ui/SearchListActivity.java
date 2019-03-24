package com.bb.ui;

import java.util.ArrayList;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.model.Info; 
import com.bb.util.AsyncImageLoader;
import com.bb.util.Constants;
import com.bb.util.AsyncImageLoader.ImageCallback;


/**
 * 列表activity
 * @author Administrator
 *
 */
public class SearchListActivity  extends  ListActivity {



    private FoodsAdapter adapter = null;
     
    private ArrayList<Info> list;

    private String  search_name , search_type ,  search_info ; 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foods_list);  
        
        if( getIntent().getExtras().get("search_name") != null )
        	search_name = (String) getIntent().getExtras().get("search_name")  ;     
        
        if( getIntent().getExtras().get("search_type") != null )
        	search_type = (String) getIntent().getExtras().get("search_type")  ;  
        
        if( getIntent().getExtras().get("search_info") != null )
        	search_info = (String) getIntent().getExtras().get("search_info")  ;     
        
//        加载数据
        new LoadTask().execute();     
    }
    
//    继承自android的AsyncTask异步类
    public class LoadTask extends AsyncTask<Void, Void, Void>{
		
		/**
		 * 后台运行，加载数据
		 */
		protected Void doInBackground(Void... arg0) {
			try {
				list =  HttpApiAccessor.getSearch(-1, -1, search_name , search_type ,  search_info  ) ;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		/**
		 * 在执行时调用，将适配器类传入
		 */
		protected void onPostExecute(Void result) {
			adapter = new FoodsAdapter() ;
			setListAdapter(adapter);
			removeDialog(0);
			super.onPostExecute(result);
		}
		
		protected void onPreExecute() {
			showDialog(0);
			super.onPreExecute();
		}
	}

   
    
    /**
     *  点击每一行时跳转到FoodInfoActivity
     */
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Log.i( " onListItemClick "  , "============= foods list ================" );
		Intent intent = new Intent( SearchListActivity.this, FoodInfoActivity.class);
		intent.putExtra("food", list.get(position));
		startActivity(intent);
	}
	
	
    
/**
 * 适配器类
 * @author Administrator
 *
 */ 
	public class FoodsAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;
		
		public int getCount() {
			return list.size();
		}

		
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		
		public long getItemId(int position) {
			return position;
		}

/**
 * 		将每一行设置为foods_list_row中定义的格式，并且赋值
 */
		public View getView(int position, View convertView, ViewGroup parent) {
		
			asyncImageLoader = new AsyncImageLoader();
			 
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.foods_list_row, null);
 
		    TextView name, todayPrice , description , price ;
		    
	        Info u = list.get(position); 
	        name = (TextView) convertView.findViewById(R.id.name) ; 
	        name.setText(u.getFood_name());
	        
	        todayPrice = (TextView) convertView.findViewById(R.id.price);
	        todayPrice.setText( " 原价: " + u.getFood_price() + "   特价: " + String.valueOf(u.getFood_discount_price())  );
	        
	        ImageView iv = (ImageView) convertView.findViewById(R.id.foodPic) ; 
	        String picPath = Constants.WEB_APP_URL + "upload/" + u.getFood_pic() ;
	        
	    	Drawable cachedImage = asyncImageLoader.loadDrawable(
	    			picPath , iv , new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								ImageView imageView, String imageUrl) {
							imageView.setImageDrawable(imageDrawable);
						}
					});

			if (cachedImage == null) {
				iv.setImageResource(R.drawable.pork);
			} else {
				iv.setImageDrawable(cachedImage);
			} 
			return convertView;
		}
	}
	
	 

	
    
}
