package com.bb.ui;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.db.DbControl;
import com.bb.model.Info;
import com.bb.model.Order;
import com.bb.ui.GwcshowActivity.LoadTask;
import com.bb.util.AsyncImageLoader;
import com.bb.util.AsyncImageLoader.ImageCallback;
import com.bb.util.Constants;

import edu.self.LoginNewActivity;


/**
 * 特色列表activity
 * @author Administrator
 *
 */
public class OrderActivity  extends  ListActivity {

//	刷新菜单键值
    private static final int MENU_REFRESH = Menu.FIRST + 2;
//    退出菜单键值
    private static final int MENU_EXIT = Menu.FIRST + 6;
//   购物车菜单键值 
    public static final int MENU_REPLY = Menu.FIRST+7;  
    
    public static final int COMPOSE_UPDATE_REQUEST_CODE = 1339;

    private OrderAdapter adapter = null;
    
     
    private ArrayList<Order> orderList;

//    private String type;
    private String Id;
    private String type;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  type = (String) getIntent().getExtras().get("type");        
        setContentView(R.layout.order_list);  
        type = (String) getIntent().getExtras().get("type");
        
        Button all =(Button)findViewById(R.id.button_all);
        Button other =(Button)findViewById(R.id.button_his);
        
         all.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	//showDialog(GwcLActivity.this);
				Intent intent = new Intent();
				intent.putExtra("type", "all");
	         	intent.setClass(OrderActivity.this,OrderActivity.class);
	         	startActivity(intent);
			}
		});
         
         other.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
             	//showDialog(GwcLActivity.this);
 				Intent intent = new Intent();
 				intent.putExtra("type", "已发货");
 	         	intent.setClass(OrderActivity.this,OrderActivity.class);
 	         	startActivity(intent);
 			}
 		});
        
//        加载数据
        new LoadTask().execute();     
    }
    
private void showDialog( Context context ,Order order){
    	
    	LayoutInflater li = LayoutInflater.from(context);    //获取布局文件对象
		View promptsView = li.inflate(R.layout.order_list_more, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);//设置弹出框格式
		

		final TextView name = (TextView) promptsView.findViewById(R.id.name);
		final TextView price = (TextView) promptsView.findViewById(R.id.price);
		final TextView desc = (TextView) promptsView.findViewById(R.id.desc);
		final TextView time = (TextView) promptsView.findViewById(R.id.time);
		final TextView tel = (TextView) promptsView.findViewById(R.id.tel);
		
		name.setText(order.getFood_id());
		price.setText("总价："+order.getPrice());
		desc.setText(""+order.getDesc());
		time.setText("下单时间："+order.getOrder_date());
		tel.setText("商家电话："+order.getTel());
		
		
		alertDialogBuilder
			.setCancelable(false)
			.setTitle("订单详情")
			.setPositiveButton("电话催单",
			  new DialogInterface.OnClickListener() {
			    @SuppressWarnings({ "unchecked", "rawtypes" })
				public void onClick(DialogInterface dialog,int pid) {
					
			    	Intent call= new Intent();
					call.setAction(Intent.ACTION_VIEW);  //设置动作：显示数据
					call.setData(Uri.parse("tel:"+tel.getText().toString()));
					startActivity(call);
			         new LoadTask().execute();   
			    }
			  });
		
	
		alertDialogBuilder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	
    			    	 
    			         new LoadTask().execute(); 
                    }  
		  });
	
		
	
		AlertDialog alertDialog = alertDialogBuilder.create(); 
		alertDialog.show(); 

    }

private void showDialog2( Context context ,Order order){
	
	LayoutInflater li = LayoutInflater.from(context);    //获取布局文件对象
	View promptsView = li.inflate(R.layout.order_list_more, null);
	AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	alertDialogBuilder.setView(promptsView);//设置弹出框格式
	

	final TextView name = (TextView) promptsView.findViewById(R.id.name);
	final TextView price = (TextView) promptsView.findViewById(R.id.price);
	final TextView desc = (TextView) promptsView.findViewById(R.id.desc);
	final TextView time = (TextView) promptsView.findViewById(R.id.time);
	final TextView tel = (TextView) promptsView.findViewById(R.id.tel);
	
	name.setText(order.getFood_id());
	price.setText("总价："+order.getPrice());
	desc.setText(""+order.getDesc());
	time.setText("下单时间："+order.getOrder_date());
	tel.setText("商家电话："+order.getTel());
	
	
	alertDialogBuilder
		.setCancelable(false)
		.setTitle("订单详情")
		.setPositiveButton("ok",
		  new DialogInterface.OnClickListener() {
		    @SuppressWarnings({ "unchecked", "rawtypes" })
			public void onClick(DialogInterface dialog,int pid) {
				  
		    }
		  });
	

	

	AlertDialog alertDialog = alertDialogBuilder.create(); 
	alertDialog.show(); 

}
    
//    继承自android的AsyncTask异步类
    public class LoadTask extends AsyncTask<Void, Void, Void>{
		
		/**
		 * 后台运行，加载数据
		 */
		protected Void doInBackground(Void... arg0) {
			try {
				orderList =  HttpApiAccessor.getFolloweduser(-1, -1,type) ;
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
			adapter = new OrderAdapter() ;
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
		Order orderInfo = orderList.get(position) ;
		Id=orderInfo.getId();
		if(orderInfo.getState().equals("未发货")){
			showDialog(OrderActivity.this,orderInfo);
			
		}
		else{
			showDialog2(OrderActivity.this,orderInfo);
		}
		//Id=orderInfo.getId();
		//showDialog(OrderActivity.this,orderInfo);
	}
	
    
/**
 * 适配器类
 * @author Administrator
 *
 */ 
	public class OrderAdapter extends BaseAdapter {

		private AsyncImageLoader asyncImageLoader;
		
		public int getCount() {
			return orderList.size();
		}

		
		public Object getItem(int arg0) {
			return orderList.get(arg0);
		}

		
		public long getItemId(int position) {
			return position;
		}

/**
 * 		将每一行设置为order_list中定义的格式，并且赋值
 */
		public View getView(int position, View convertView, ViewGroup parent) {
		
			asyncImageLoader = new AsyncImageLoader();
			 
			convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.order_list_item, null);
 
		    TextView name, allPrice ,time;
		    TextView cui;
		    
	        Order u = orderList.get(position); 
	        name = (TextView) convertView.findViewById(R.id.name) ; 
	        name.setText(u.getFood_id());
	        
	        allPrice = (TextView) convertView.findViewById(R.id.price);
	        allPrice.setText("总价："+u.getPrice() );
	        
	        time = (TextView) convertView.findViewById(R.id.shijian);
	        time.setText( u.getState() );
	        
	        cui = (TextView) convertView.findViewById(R.id.cui);
	        if(u.getState().equals("已发货"))
	        {
	        	cui.setBackgroundColor(Color.parseColor("#CDC8B1"));
	        }
	     //   todayPrice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线
	        
	      //  dprice = (TextView) convertView.findViewById(R.id.price2);
	    //    dprice.setText( String.valueOf(u.getSeat())  );
	        
	     //   ImageView iv = (ImageView) convertView.findViewById(R.id.foodimage) ; 
	      //  String picPath = Constants.WEB_APP_URL + "upload/" + u.getState() ;
	        
	   // 	Drawable cachedImage = asyncImageLoader.loadDrawable(
	    //			picPath , iv , new ImageCallback() {

		//				public void imageLoaded(Drawable imageDrawable,
		//						ImageView imageView, String imageUrl) {
		//					imageView.setImageDrawable(imageDrawable);
		//				}
		//			});

		//	if (cachedImage == null) {
	//			iv.setImageResource(R.drawable.pork);
	//		} else {
	//			iv.setImageDrawable(cachedImage);
	//		} 
			return convertView;
		}
	}
	
	
//    创建menu菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_REPLY, 0, "购物车")
        		.setAlphabeticShortcut('G');
        menu.add(0, MENU_REFRESH, 0, "刷新")
                .setAlphabeticShortcut('R');
        menu.add(0, MENU_EXIT, 0, "返回主页")
                .setAlphabeticShortcut('X');
        return true;
    }
    
//响应menu操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_REFRESH: {
            	new LoadTask().execute();
                return true;
            }case MENU_EXIT: {
            	Intent intent = new Intent();
				intent.setClass(OrderActivity.this, MainActivity2.class);
				intent.putExtra("type", Constants.FLAG_ALL);
				startActivity(intent);
                return true;
            }case MENU_REPLY: {
                Intent i = new Intent( OrderActivity.this, GwcshowActivity.class);
    	        startActivity(i);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
    
}
