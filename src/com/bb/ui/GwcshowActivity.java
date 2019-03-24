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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.db.DbControl; 
import com.bb.ui.FoodInfoActivity.OnButtonClickListener;
import com.bb.ui.FoodInfoActivity.OnTextChangeListener;
import com.bb.ui.GwcListActivity.LoadTask;
import com.bb.util.Constants;


/**
 * 
 *  
 * @author Administrator
 *
 */
public class GwcshowActivity extends ListActivity {
	

    private static final int DELETE_ID = Menu.FIRST ;
    
	private NoteAdapter adapter;
	private List<GwcInfo> list ;
	int num;
	long Id;
 
	public static class ViewHolder {
		public TextView tvName;
		public TextView tvPrice;
		public TextView tvId;
		public TextView tvcount;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gwc_list);
        
        Button tv_add = (Button)findViewById(R.id.btn_add);
    
         Button tv_back = (Button)findViewById(R.id.btn_back);
        
           tv_back.setOnClickListener(new View.OnClickListener() {
    			public void onClick(View v) {
                	Intent intent1 = new Intent();
                	intent1.setClass(GwcshowActivity.this,MainActivity2.class);
                	intent1.putExtra("type", Constants.FLAG_ALL);
                	startActivity(intent1);
    			}
    		});
			
        tv_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	Intent intent = new Intent();
            	intent.setClass(GwcshowActivity.this,GwcLActivity.class);
            	startActivity(intent);
			}
		});
        
    	new LoadTask().execute();	
        registerForContextMenu(getListView());
    }
    
    
   private void showDialog( Context context ,GwcInfo food){
    	
    	LayoutInflater li = LayoutInflater.from(context);    //获取布局文件对象
		View promptsView = li.inflate(R.layout.change, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);//设置弹出框格式
		

		final TextView name = (TextView) promptsView.findViewById(R.id.name);
		final TextView price = (TextView) promptsView.findViewById(R.id.price);
		final EditText count = (EditText) promptsView.findViewById(R.id.count);
		//ImageButton add = (ImageButton) this.findViewById(R.id.addbt );
		//ImageButton  sub = (ImageButton) this.findViewById(R.id.subbt );
		
		//add.setOnClickListener(new OnButtonClickListener());
		//sub.setOnClickListener(new OnButtonClickListener());
		
		//count.addTextChangedListener(new OnTextChangeListener());
		//Button sure= (Button)findViewById(R.id.sure);
       // Button out = (Button)findViewById(R.id.out);
		name.setText(food.getSeat()+":"+food.getFood_name());
		price.setText("单价："+food.getPrice());
		count.setText(food.getCount());
		
		
		alertDialogBuilder
			.setCancelable(false)
			.setTitle("修改")
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    @SuppressWarnings({ "unchecked", "rawtypes" })
				public void onClick(DialogInterface dialog,int pid) {
					
						DbControl 	dbControl   = new DbControl( GwcshowActivity.this ) ;
					boolean upfood=dbControl.updateGwc(GwcshowActivity.this ,Id,count.getText().toString()) ;
					if(upfood){
						Toast.makeText(GwcshowActivity.this, "修改成功！", Toast.LENGTH_SHORT).show();
					}
			    	 
			         new LoadTask().execute();   
			    }
			  });
		

		alertDialogBuilder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                        // Intent intent = new Intent();
		            	//intent.setClass(GwcshowActivity.this,GwcshowActivity.class);
		            	//startActivity(intent);
                    }  
		  });
	
		alertDialogBuilder.setNegativeButton("删除",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                    	DbControl 	dbControl   = new DbControl( GwcshowActivity.this ) ;
    					boolean upfood=dbControl.deleteGwc(GwcshowActivity.this ,Id) ;
    					if(upfood){
    						Toast.makeText(GwcshowActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
    					}
    			    	 
    			         new LoadTask().execute(); 
                    }  
		  });
	
		
	
		AlertDialog alertDialog = alertDialogBuilder.create(); 
		alertDialog.show(); 

    }


    
   
	/**
	 * 异步加载所有 
	 */
	public class LoadTask extends AsyncTask<Void, Void, Void>{
		
		
		protected Void doInBackground(Void... arg0) { 
			
			DbControl dbControl = new DbControl(GwcshowActivity.this);
			list =  dbControl.getGwc( GwcshowActivity.this ) ;
			return null;
		} 
		
		protected void onPostExecute(Void result) {
			adapter = new NoteAdapter();
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
		Log.i( " onListItemClick "  , "============= GWC list ================" );
		//Id=id;
		GwcInfo gwcInfo = list.get(position) ;
		Id=gwcInfo.getId();
		showDialog(GwcshowActivity.this,gwcInfo);
	}
	
	public class NoteAdapter extends BaseAdapter {

		
		public int getCount() {
			return list.size();
		}

		
		public Object getItem(int arg0) {
			return list.get(arg0);
		}

		
		public long getItemId(int position) {
			return position;
		}
		
		

		
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(getApplicationContext())
						.inflate(R.layout.gwc_item, null);
				holder.tvName = (TextView) convertView.findViewById(R.id.name);
				holder.tvPrice = (TextView) convertView.findViewById(R.id.count);
				holder.tvcount = (TextView) convertView.findViewById(R.id.price);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			GwcInfo gwcInfo = list.get(position) ;
			
			
			holder.tvName.setText(  gwcInfo.getSeat() + ": " + gwcInfo.getFood_name()  ); 	
			holder.tvPrice.setText( "单价："+gwcInfo.getPrice()    ); 
			holder.tvcount.setText( "数量："+gwcInfo.getCount()    ); 
			return convertView;
		}
		
	}
	

//	protected void onListItemClick(ListView l, View v, int position, long id) {
//		super.onListItemClick(l, v, position, id); 
//		Intent intent = new Intent(GwcListActivity.this, NoteEditActivity.class);
//		intent.putExtra( "note" , list.get(position)  );
//        startActivityForResult( intent , ACTIVITY_EDIT);
//	}

 
    
	 
}
