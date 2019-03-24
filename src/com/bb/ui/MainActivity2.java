package com.bb.ui;


import java.util.ArrayList;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.model.Info;
import com.bb.ui.FoodsListActivity.FoodsAdapter;
import com.bb.util.AsyncImageLoader;
import com.bb.util.Constants;
import com.bb.util.AsyncImageLoader.ImageCallback;

import edu.self.LoginNewActivity;
import edu.self.RegisterAccountActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends ListActivity  implements OnClickListener{
	
//	刷新菜单键值
    private static final int MENU_REFRESH = Menu.FIRST + 2;
//    退出菜单键值
    private static final int MENU_EXIT = Menu.FIRST + 6;
//   购物车菜单键值 
    public static final int MENU_REPLY = Menu.FIRST+7;  
    
    public static final int COMPOSE_UPDATE_REQUEST_CODE = 1339;

    private FoodsAdapter adapter = null;
    
     
    private ArrayList<Info> foodsList;
    private ArrayList<Info> List;
	private String type;
	private String name;
    private EditText search_name ;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = (String) getIntent().getExtras().get("type"); 
		
	   // search_name = (EditText) this.findViewById(R.id.search_et_input);
		//final EditText et_search_name = (EditText) this.findViewById(R.id.search_et_input);
		setContentView(R.layout.activity_homepage);
		search_name = (EditText) this.findViewById(R.id.search_et_input);
		findViewById(R.id.bt1).setOnClickListener(this);
		findViewById(R.id.bt2).setOnClickListener(this);
		findViewById(R.id.bt3).setOnClickListener(this);
		findViewById(R.id.bt4).setOnClickListener(this);
		findViewById(R.id.bt5).setOnClickListener(this);
		findViewById(R.id.bt6).setOnClickListener(this);
		findViewById(R.id.bt7).setOnClickListener(this);
		findViewById(R.id.bt8).setOnClickListener(this);
		findViewById(R.id.bt9).setOnClickListener(this);
		findViewById(R.id.bt10).setOnClickListener(this);
		findViewById(R.id.search_btn).setOnClickListener(this);
         
		//name=search_name.getText().toString().trim();
		
		
			new LoadTask().execute();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt1:
			Intent intent1 = new Intent();
			intent1.setClass(MainActivity2.this, FoodteseActivity.class);
			intent1.putExtra("type", Constants.FLAG_ZEOR);//特色菜
			startActivity(intent1);
			break;
		case R.id.bt2:
			Intent intent2 = new Intent();
			intent2.setClass(MainActivity2.this,FoodbetterActivity.class);
			intent2.putExtra("type", Constants.FLAG_TOP);//精品
			startActivity(intent2);
			break;
		case R.id.bt3:
			Intent intent3 = new Intent();
			intent3.setClass(MainActivity2.this,FoodveActivity.class);
			intent3.putExtra("type", Constants.FLAG_ve);//素菜
			startActivity(intent3);
			break;
		case R.id.bt4:
			Intent intent4 = new Intent(MainActivity2.this, FoodsoupActivity.class);
			intent4.putExtra("type", Constants.FLAG_soup);//汤
		    startActivity(intent4);
			break;
		case R.id.bt5:
			Intent intent5 = new Intent(this,HuodongListActivity.class);
			startActivity(intent5);
			break;
		case R.id.bt6:
			Intent intent6 = new Intent(this,DrinkActivity.class);
			intent6.putExtra("type", Constants.FLAG_DRINK);//饮品
		    startActivity(intent6);
			break;
		case R.id.bt7:
			Intent intent7 = new Intent(this, GwcshowActivity.class);
		    startActivity(intent7);
			break;
		case R.id.bt8:
			Intent intent8 = new Intent(this, OrderActivity.class);
			intent8.putExtra("type", "all");
		    startActivity(intent8);
			break;
		case R.id.bt9:
			Intent intent9 = new Intent(this, LiuyanbanListActivity.class);
		    startActivity(intent9);
			break;
		case R.id.search_btn:
			Intent intent10 = new Intent(MainActivity2.this, SearchgetActivity.class);
			intent10.putExtra("search_name",search_name.getText().toString().trim());
		    startActivity(intent10);
			break;
		case R.id.bt10:
			Intent intent11 = new Intent(this, LoginNewActivity.class);
		    startActivity(intent11);
			break;
		}
	}
	 @SuppressLint("InflateParams")
	 
//继承自android的AsyncTask异步类
public class LoadTask extends AsyncTask<Void, Void, Void>{
	
	/**
	 * 后台运行，加载数据
	 */
	protected Void doInBackground(Void... arg0) {
		try {
			foodsList =  HttpApiAccessor.getFollowed(-1, -1,type) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 在执行时调用，将适配器类传入
	 */
	@SuppressWarnings("deprecation")
	protected void onPostExecute(Void result) {
		adapter = new FoodsAdapter() ;
		setListAdapter(adapter);
		removeDialog(0);
		super.onPostExecute(result);
	}
	
	@SuppressWarnings("deprecation")
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
	Intent intent = new Intent( MainActivity2.this, FoodInfoActivity.class);
	intent.putExtra("food", foodsList.get(position));
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
		return foodsList.size();
	}

	
	public Object getItem(int arg0) {
		return foodsList.get(arg0);
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
	    
      Info u = foodsList.get(position); 
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

}

