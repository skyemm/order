package com.bb.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;
import com.bb.api.BaseAuthenicationHttpClient;

import com.bb.model.Huodong;
import com.bb.util.Constants;



public class  HuodongHttpAdapter {

	
	public static ArrayList<Huodong> getAllHuodongList(long lastId, int pageNo, String flag) throws Exception{
		String url = Constants.WEB_APP_URL + "huodong.do?method=list&type=json";
		if( flag != null && !flag.equals("") ){
			try {
				flag  = URLEncoder.encode(flag, "utf-8") ;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			url += "&f=" + flag;
		}
		
		System.out.println( " getUpdatesList url ::::::::::" + url );
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");

		JSONArray jsonArray = new JSONArray(jsonString);		
		ArrayList<Huodong> ret = new ArrayList<Huodong>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);

			Huodong object = new Huodong();
			object.id =  json.getString("id");

     		object.biaoti = json.getString("biaoti");
     		object.neirong = json.getString("neirong");
     		object.shijian = json.getString("shijian");
          
           
			ret.add(object);
		}
		return ret;
	}
	
 
	
//	public static ArrayList<Huodong> getFollowedByType(long lastId, int pageNo,  String news_type ) throws Exception  {
//	String url = Constants.WEB_APP_URL + "foodList.do?type=json";
//	if( news_type != null ){
//		try {
//			news_type  = URLEncoder.encode(news_type, "utf-8") ;
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		url += "&news_type=" +  news_type  ;
//	}
//	return getUpdatesList(url,lastId,pageNo);
//}
	
	
}



