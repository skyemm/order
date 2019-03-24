package com.bb.db;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log; 

import com.bb.ui.GwcInfo;
import com.bb.util.Constants;
import com.bb.util.Tool;
 

/**
 *  sqlite数据库类
 * @author Administrator
 *
 */
public class DbControl {

 

	private static final String CREATE_TABLE_NOTE = "create table if not exists gwc ( id integer primary key autoincrement, "
		+ "food_name text not null , price text not null , count text not null, seat text not null  ,order_date text not null ,user_id text not null,tel text not null );" ;
 
	 
	private static final String DATABASE_NAME = "b_food";
 
	private static final String TABLE_NAME = "gwc" ; 
	
	
	private SQLiteDatabase db;
	 

	public DbControl(Context ctx) {
		
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
		
//		db.execSQL(" drop table " + TABLE_NAME );
		db.execSQL(CREATE_TABLE_NOTE); 

		int test = db.getVersion(); 
		Log.i( "===db version===", String.valueOf( test )  );
		 
		db.close();
	}
	 


	/**
	 *  新增
	 * @param ctx
	 * @param note
	 * @return
	 */
   public boolean addGwc( Context ctx , String food_name , String  seat , float price ,int count ,String tel  ) {
	   
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
		ContentValues values = new ContentValues();
//		food_name ,  seat , order_date
		
		values.put("food_name", food_name);
		values.put("seat", seat);
		values.put("count",  String.valueOf(count));
		values.put("price", String.valueOf(price) ) ;
		values.put("order_date", Tool.getNowTime() ); 
		values.put("user_id", Constants.userId );
		values.put("tel", tel);

		boolean returnValue = db.insert(TABLE_NAME, null, values) > 0;
		db.close();
		return (returnValue);
    }
   

   public boolean updateGwc( Context ctx , long id,String count   ) {
	   
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
		ContentValues values = new ContentValues();
//		food_name ,  seat , order_date
		
		
		values.put("count",  count);

		boolean returnValue = db.update(TABLE_NAME, values,"id=" + id ,null) >0;
		db.close();
		return (returnValue);
   }
   
  
	/**
	 *  删除
	 * @param ctx
	 * @param note
	 * @return
	 */
  public boolean deleteGwc( Context ctx , long id  ) {
		
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
		
		boolean returnValue = false;

		int result = 0;
		result = db.delete(TABLE_NAME, "id=" + id , null);
		db.close();
		
		if (result == 1){
			returnValue = true;
		}
		
		return returnValue;
   }
  
  
  public boolean deleteAllGwc( Context ctx   ) {
		
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);
		
		boolean returnValue = false;

		int result = 0;
		result = db.delete(TABLE_NAME, null , null);
		db.close();
		
		if (result == 1){
			returnValue = true;
		}
		
		return returnValue;
   }
  
  
  
  /**   
	 * 
	 * 获取所有购物车
	 * @param ctx
	 * @return
	 * 
	 */
	public List<GwcInfo> getAllGwc( Context ctx ){
		
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);	
		
		List<GwcInfo> list = new ArrayList<GwcInfo>();
//		food_name ,  seat , order_date
		Cursor c = db.query(TABLE_NAME, new String[] { "id", "food_name" , "seat" ,"order_date","price" ,"count","tel"}, null, null,null,null, "seat");

		int numRows = c.getCount();
		c.moveToFirst();
		for (int i = 0; i < numRows; i++) {

			GwcInfo object = new GwcInfo() ;
			object.setId( c.getInt(0) );
			object.setFood_name( c.getString(1) );
			object.setSeat( c.getString(2) );
			//object.setSeat_id( c.getString(3) );
			object.setOrder_date( c.getString(3) );
			object.setPrice(  c.getString(4) );
			object.setCount(  c.getString(5) );
			object.setTel(  c.getString(6) );
			 
			list.add(i, object) ;
			c.moveToNext();
		}
		c.close();
		db.close();
		return  list ;
	}
	
public List<GwcInfo> getGwc( Context ctx ){
		
		db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);	
		
		List<GwcInfo> list = new ArrayList<GwcInfo>();
//		food_name ,  seat , order_date
		Cursor c = db.query(TABLE_NAME, new String[] { "id", "food_name" , "seat" ,"order_date","price" ,"count","tel"}, "user_id=?", new String []{Constants.userId},null,null, "seat");

		int numRows = c.getCount();
		c.moveToFirst();
		for (int i = 0; i < numRows; i++) {

			GwcInfo object = new GwcInfo() ;
			object.setId( c.getInt(0) );
			object.setFood_name( c.getString(1) );
			object.setSeat( c.getString(2) );
			//object.setSeat_id( c.getString(3) );
			object.setOrder_date( c.getString(3) );
			object.setPrice(  c.getString(4) );
			object.setCount(  c.getString(5) );
			object.setTel(  c.getString(6) );
			 
			list.add(i, object) ;
			c.moveToNext();
		}
		c.close();
		db.close();
		return  list ;
	}
public GwcInfo getoneGwc( Context ctx,long Id ){
	
	db = ctx.openOrCreateDatabase(DATABASE_NAME, 0, null);	
	
	List<GwcInfo> list = new ArrayList<GwcInfo>();
	GwcInfo object = new  GwcInfo();
	Cursor cursor = db.query(true, TABLE_NAME, new String[] { "id", "food_name" , "seat" ,"order_date","price" ,"count","tel"},
		    "id"+ "="+Id, null, null, null, null, null); 
	if(cursor.moveToFirst()){
//		  GwcInfo object = new  GwcInfo();
			object.setId(  cursor.getInt(0));
			object.setFood_name( cursor.getString(1)) ;
			object.setSeat( cursor.getString(2) ); 
			object.setOrder_date( cursor.getString(3) ); 
			object.setPrice(  String.valueOf( cursor.getFloat(4) ) );
			object.setCount(  String.valueOf( cursor.getFloat(5) ) );
			object.setTel(  cursor.getString(6) );

		}
	return object;
}

	
	
  
}

