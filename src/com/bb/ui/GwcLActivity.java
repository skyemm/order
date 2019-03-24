package com.bb.ui;


import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.db.DbControl; 
import com.bb.model.Shangjia;
import com.bb.ui.GwcListActivity.LoadTask;
import com.bb.ui.GwcshowActivity.NoteAdapter;
import com.bb.ui.GwcshowActivity.ViewHolder;
import com.bb.util.Constants;


/**
 * 
 *  
 * @author Administrator
 *
 */
public class GwcLActivity extends ListActivity {
	

//    private static final int DELETE_ID = Menu.FIRST ;
    
//	private NoteAdapter adapter;
	private List<GwcInfo> list ;
	EditText lianxi;
	EditText tel;
	EditText address;
	TextView show;
	TextView count;
//	HashMap orderMap = new HashMap(); 
	String lianxiren;
	String dizhi;
	String description;
	double price ;
	String shangjia ;
	String shangjiadh ;
//	public static class ViewHolder {
//		public TextView tvName;
//		public TextView tvPrice;
//		public TextView tvId;
//	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gwc_choose);
        
         lianxi = (EditText)findViewById(R.id.lianxi);
         tel = (EditText)findViewById(R.id.tel);
         address = (EditText)findViewById(R.id.address);
         show = (TextView)findViewById(R.id.orders);
         count = (TextView)findViewById(R.id.textView6);
        Button del =(Button)findViewById(R.id.del);
        Button sure =(Button)findViewById(R.id.sure);
        
         del.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	//showDialog(GwcLActivity.this);
				Intent intent = new Intent();
	         	intent.setClass(GwcLActivity.this,GwcshowActivity.class);
	         	startActivity(intent);
			}
		});
         
         sure.setOnClickListener(new View.OnClickListener() {
 			
 			@Override
 			public void onClick(View v) {
 				if(list.size()==0){
 					Toast.makeText(GwcLActivity.this, "订单内容为空！", Toast.LENGTH_SHORT).show();
 				}
 				else{
                   add();
                   Intent intent = new Intent();
         	       intent.setClass(GwcLActivity.this,GwcshowActivity.class);
         	       startActivity(intent);
 				     }
 			}
 		});
        
         DbControl dbControl = new DbControl(GwcLActivity.this);
			list =  dbControl.getGwc( GwcLActivity.this ) ;
            show();
       // registerForContextMenu(getListView());
    }

    
    

	public void add(){
    	 lianxiren = lianxi.getText().toString().trim() + "--" +  tel.getText().toString().trim() ;
	 	 dizhi = address.getText().toString().trim() ;
	 
	 	//HashMap orderMap = new HashMap();  
		
		description  = "";
		price = 0 ;	
		shangjia = null;
		shangjiadh = null;
		 new Thread(new Runnable() {
             @Override
             public void run() {
                 try {      	 
                int str[]=null;//声明数组
          		 str=new int[list.size()+1];//开辟空间，大小为list.size()
          		for(int i=0;i<list.size();i++){
          			str[i]=1;
          		}
          		str[list.size()]=0;
		for(int j=0;j<list.size();j++){
			if(str[j]==1&&shangjia==null){
				GwcInfo gwcInfo = list.get(j);
				shangjia=gwcInfo.getSeat();
				shangjiadh=gwcInfo.getTel();
				for(int i=j; i<list.size();i++){
					GwcInfo gwcInfo2 = list.get(i);
					if(shangjia.equals( gwcInfo2.getSeat() )&&str[i]==1){
						description =description  +  gwcInfo2.getFood_name() + "--" + gwcInfo2.getPrice() + "--x"+gwcInfo2.getCount()+" ,"; 
						price=price+Double.valueOf( gwcInfo2.getPrice() )*Double.valueOf( gwcInfo2.getCount() ) ;
						str[i]=0;
					}
				}
				
				Log.i("price ", price + "" + description  ) ;
				
//				发送订单信息给服务端
				
		                        	HashMap orderMap = new HashMap();
		                        	orderMap.put("order.food_id",  shangjia );	
		                			orderMap.put("order.food_name",  description   );
		            				orderMap.put("order.seat",  lianxiren );
		            				orderMap.put("order.beizhu",  dizhi );
		            				orderMap.put("order.price",  String.valueOf( price ) );
		            				orderMap.put("order.userid",  Constants.userId);
		            				orderMap.put("order.tel",  shangjiadh);
		                        	HttpApiAccessor.saveOrder(  orderMap );	
			//	HttpApiAccessor.saveOrder(  orderMap )   ;
			}
			description = "" ;
			price = 0;
			shangjia=null;
		
	    }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
}).start();
		DbControl 	dbControl   = new DbControl( GwcLActivity.this ) ;
		dbControl.deleteAllGwc(GwcLActivity.this ) ;
		Toast.makeText(GwcLActivity.this, "下订单成功！", Toast.LENGTH_SHORT).show();
	}
	 
    
    
    public void show(){
    	 String desr="";
         double price=0;
   	   for(int i=0 ; i< list.size() ;i++){
   		  
   			GwcInfo gwcInfo = list.get(i);
   			
   					desr=desr+gwcInfo.getFood_name() + "--¥" + gwcInfo.getPrice() +"--x"+gwcInfo.getCount()+ " ,";
   					price=price+Double.valueOf( gwcInfo.getPrice() )*Double.valueOf( gwcInfo.getCount() ) ;
   				
   			}
   	   show.setText(desr);
   	   count.setText(String.valueOf( price ));
         
    }
  /*  Runnable downloadRun = new Runnable(){  
    	  
    	@Override  
    	public void run() {  
    	    // TODO Auto-generated method stub  
    		HttpApiAccessor.saveOrder(  orderMap )   ; 
    	}  
    	  };  
*/
    	 
}

