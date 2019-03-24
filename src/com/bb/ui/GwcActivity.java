package com.bb.ui;


import java.util.HashMap;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bb.R;
import com.bb.api.HttpApiAccessor;
import com.bb.db.DbControl; 
import com.bb.model.Shangjia;


/**
 * 
 *  
 * @author Administrator
 *
 */
public class GwcActivity extends ListActivity {
	

    private static final int DELETE_ID = Menu.FIRST ;
    
	private NoteAdapter adapter;
	private List<GwcInfo> list ;
 
	public static class ViewHolder {
		public TextView tvName;
		public TextView tvPrice;
		public TextView tvId;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.gwc_list);
        
        TextView tv_add = (TextView)findViewById(R.id.btn_add);
        tv_add.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
            	showDialog(GwcActivity.this);
			}
		});
        
    	new LoadTask().execute();	
        registerForContextMenu(getListView());
    }

    
    
    private void showDialog( Context context ){
    	
    	LayoutInflater li = LayoutInflater.from(context);    //获取布局文件对象
		View promptsView = li.inflate(R.layout.prompts_gequ, null);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		alertDialogBuilder.setView(promptsView);//设置弹出框格式

		final EditText et_search_name = (EditText) promptsView.findViewById(R.id.search_name);
		final EditText et_search_type = (EditText) promptsView.findViewById(R.id.search_type);
		final EditText et_search_song = (EditText) promptsView.findViewById(R.id.search_song);
		
		alertDialogBuilder
			.setCancelable(false)
			.setTitle("提交订单")
			.setPositiveButton("OK",
			  new DialogInterface.OnClickListener() {
			    public void onClick(DialogInterface dialog,int pid) {
					
			    	 	String dizhi = et_search_name.getText().toString().trim() + "--" +  et_search_type.getText().toString().trim() ;
			    	 	String beizhu = et_search_song.getText().toString().trim() ;
			    	 
						//HashMap orderMap = new HashMap();  
						
						String description  = "";
						double price = 0d ;
						
						//String shangjia = null;
						Shangjia shangjia[] = null;
						int k,j=0;
				//		shangjia[0].setId(list.get(0).getSeat_id());
						shangjia[0].setname(list.get(0).getSeat());
						shangjia[0].setPrice(list.get(0).getPrice());
						shangjia[0].setDesr(list.get(0).getFood_name());
						
						for(int i=1 ; i< list.size() ;i++){
							
							GwcInfo gwcInfo = list.get(i);
							for(k=0;shangjia[k]!=null;k++)
							{
								if(shangjia[k].getname()==gwcInfo.getSeat())
								{
									shangjia[k].setDesr(shangjia[k].getDesr()+gwcInfo.getFood_name() + "--" + gwcInfo.getPrice() + " ,");
									double price1=Double.valueOf(shangjia[k].getPrice())+Double.valueOf( gwcInfo.getPrice() );
									shangjia[k].setPrice(String.valueOf( price1 ));
								}
							
							}
							if(j==k)
							{
								shangjia[j].setname(list.get(0).getSeat());
								shangjia[j].setname(list.get(0).getSeat());
								shangjia[j].setPrice(list.get(0).getPrice());
								shangjia[j].setDesr(list.get(0).getFood_name());
								j++;
							}
						}
						for(int i=0;i<=j;i++)
						{
							HashMap orderMap = new HashMap(); 
							orderMap.put("order.food_id",  shangjia[i].getname() );	
							orderMap.put("order.food_name", shangjia[i].getDesr());
							orderMap.put("order.seat",  dizhi );
							orderMap.put("order.beizhu",  beizhu );
							orderMap.put("order.price",  shangjia[i].getPrice());
							
							HttpApiAccessor.saveOrder(  orderMap );
						}
							
						
						DbControl 	dbControl   = new DbControl( GwcActivity.this ) ;
						dbControl.deleteAllGwc(GwcActivity.this ) ;
						Toast.makeText(GwcActivity.this, "下订单成功！", Toast.LENGTH_SHORT).show();
			    	 
			         new LoadTask().execute();   
			    }
			  });
		
		alertDialogBuilder.setNegativeButton("取消",  
                new DialogInterface.OnClickListener() {  
                    public void onClick(DialogInterface dialog, int whichButton) {  
                         
                    }  
		  });

		AlertDialog alertDialog = alertDialogBuilder.create(); 
		alertDialog.show(); 
    }
 
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, "删除");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
//            	从sqlite数据库清空数据
                AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
                DbControl dbControl = new DbControl(GwcActivity.this);
                GwcInfo object = list.get(  Long.valueOf(info.id).intValue() );
                dbControl.deleteGwc( GwcActivity.this , object.getId() ) ; 
            	new LoadTask().execute();	
                return true;
        }
        return super.onContextItemSelected(item);
    }
    
    
	/**
	 * 异步加载所有 
	 */
	public class LoadTask extends AsyncTask<Void, Void, Void>{
		
		
		protected Void doInBackground(Void... arg0) { 
			
			DbControl dbControl = new DbControl(GwcActivity.this);
			list =  dbControl.getAllGwc( GwcActivity.this ) ;
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
				holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
				
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			
			GwcInfo gwcInfo = list.get(position) ;
			
			
			holder.tvName.setText(  gwcInfo.getSeat() + ": " + gwcInfo.getFood_name()  ); 	
			holder.tvPrice.setText( gwcInfo.getPrice()    ); 
			return convertView;
		}
		
	}
	

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id); 
//		Intent intent = new Intent(GwcListActivity.this, NoteEditActivity.class);
//		intent.putExtra( "note" , list.get(position)  );
//        startActivityForResult( intent , ACTIVITY_EDIT);
	}

 
    
	 
}
