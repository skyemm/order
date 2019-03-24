package com.bb.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.model.Info;
import com.bb.ui.SearchListActivity.FoodsAdapter;
import com.bb.ui.SearchListActivity.LoadTask;
import com.bb.util.AsyncImageLoader;
import com.bb.util.Constants;
import com.bb.util.AsyncImageLoader.ImageCallback;

public class SearchgetActivity extends  ListActivity implements OnClickListener{

	  private FoodsAdapter adapter = null;
	     
	    private ArrayList<Info> list;

	    private String  search_name , search_type ,  search_info ; 
	    private EditText name ;
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	    	
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.search_layout);  
	        
	        if( getIntent().getExtras().get("search_name") != null )
	        	search_name = (String) getIntent().getExtras().get("search_name")  ; 
	        
	        name = (EditText) this.findViewById(R.id.search_et_input);  
	        name.setText(search_name.toCharArray(), 0, search_name.length());
	        Button search = (Button)findViewById(R.id.search_btn);
	        search.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(SearchgetActivity.this, SearchgetActivity.class);
					intent.putExtra("search_name",name.getText().toString().trim());
				    startActivity(intent);
				}
			});
	        
//	        加载数据
	        new LoadTask().execute();     
	    }
	    
//	    继承自android的AsyncTask异步类
	    public class LoadTask extends AsyncTask<Void, Void, Void>{
			
			/**
			 * 后台运行，加载数据
			 */
			protected Void doInBackground(Void... arg0) {
				try {
					list =  HttpApiAccessor.getFollowedname(-1, -1, search_name) ;
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
			Intent intent = new Intent( SearchgetActivity.this, FoodInfoActivity.class);
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
				 
				convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.food_list_item, null);
	 
			    TextView name, todayPrice , description , dprice ;
			    
		        Info u = list.get(position); 
		        name = (TextView) convertView.findViewById(R.id.name1) ; 
		        name.setText(u.getFood_name());
		        
		        todayPrice = (TextView) convertView.findViewById(R.id.price1);
		        todayPrice.setText( String.valueOf(u.getFood_price() ));
		        todayPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
		        
		        dprice = (TextView) convertView.findViewById(R.id.price2);
		        dprice.setText( String.valueOf(u.getFood_discount_price())  );
		        
		        ImageView iv = (ImageView) convertView.findViewById(R.id.foodimage) ; 
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



	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
		
		
		 

		
	    
	}
