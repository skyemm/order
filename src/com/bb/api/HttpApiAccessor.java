package com.bb.api;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bb.model.Info;
import com.bb.model.Order;
import com.bb.ui.GwcInfo;
import com.bb.util.Constants;


/**
 * 
 * @author 
 *
 */
public class HttpApiAccessor {

	
	public static ArrayList<Info> getSearch(long lastId, int pageNo, String search_name , String search_type , String search_info ) throws Exception{

		String url = Constants.WEB_APP_URL + "foodList.do?method=search";
		
		if(search_name != null && !search_name.equals("") ){
			url += "&search_name=" + search_name;
		}
		if(search_type != null && !search_type.equals("") ){
			url += "&search_type=" + search_type;
		}
		if(search_info != null && !search_info.equals("") ){
			url += "&search_info=" + search_info;
		}

		return getUpdatesList(url,lastId,pageNo);
	}
	
	
	
	public static String saveOrder( HashMap params  ) {
		String url = Constants.WEB_APP_URL + "orderEdit.do?method=save&type=json"  ;
		String result = null ;

		result = BaseAuthenicationHttpClient.doRequest(url, "", "" , params );

		return result ; 
	}
	

//根据flag去获取指定的菜谱类型
	public static ArrayList<Info> getFollowed(long lastId, int pageNo, String flag) throws Exception{
		String url = Constants.WEB_APP_URL + "foodList.do?type=json";
		if(flag != null && !flag.equals( Constants.FLAG_ALL)){
			url += "&f=" + flag;
		}
		return getUpdatesList(url,lastId,pageNo);
	}
	
	//根据name去获取指定的菜谱类型
		public static ArrayList<Info> getFollowedname(long lastId, int pageNo, String search_name) throws Exception{
			String url = Constants.WEB_APP_URL + "foodList.do?method=search";
									
			search_name = URLEncoder.encode(search_name, "utf-8");
			search_name = URLEncoder.encode(search_name, "utf-8");
			
			if(search_name != null && !search_name.equals("") ){
				url += "&search_name=" + search_name;
			}
			return getUpdatesList(url,lastId,pageNo);
		}
		
		//根据用户名去获取指定的订单类型
		public static ArrayList<Order> getFolloweduser(long lastId, int pageNo, String state) throws Exception{
			String url = Constants.WEB_APP_URL + "order.do?method=search";
									
			String id=Constants.userId;
			id = URLEncoder.encode(id, "utf-8");
			id = URLEncoder.encode(id, "utf-8");
			
			state = URLEncoder.encode(state, "utf-8");
			state = URLEncoder.encode(state, "utf-8");
			url += "&user_id=" + id;
			if(state != null && !state.equals("") ){
				url += "&state=" + state;
			}
			return getUpdatesList2(url,lastId,pageNo);
		}
		
		public static ArrayList<Order> getFolloweduserstate(long lastId, int pageNo, String state) throws Exception{
			String url = Constants.WEB_APP_URL + "order.do?method=search";
			
			String id=Constants.userId;
			id = URLEncoder.encode(id, "utf-8");
			id = URLEncoder.encode(id, "utf-8");
			
			state = URLEncoder.encode(state, "utf-8");
			state = URLEncoder.encode(state, "utf-8");
			url += "&user_id=" + id;
			if(state != null && !state.equals("") ){
				url += "&state=" + state;
			}
			return getUpdatesList2(url,lastId,pageNo);
		}


	
//	
	private static ArrayList<Info> getUpdatesList(String url,long lastId, int pageNo) throws Exception{
		url = appendParams(url, lastId, pageNo);
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");

		JSONArray jsonArray = new JSONArray(jsonString);		
		ArrayList<Info> ret = new ArrayList<Info>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);
			Info food = jsonToFood(json);
			ret.add(food);
		}
		return ret;
	}
	
	private static ArrayList<Order> getUpdatesList2(String url,long lastId, int pageNo) throws Exception{
		url = appendParams(url, lastId, pageNo);
		String jsonString = BaseAuthenicationHttpClient.doRequest(url, "", "");

		JSONArray jsonArray = new JSONArray(jsonString);		
		ArrayList<Order> ret = new ArrayList<Order>();
		for( int i = 0; i != jsonArray.length(); i++){
			JSONObject json = jsonArray.getJSONObject(i);
			Order food = jsonToOrder(json);
			ret.add(food);
		}
		return ret;
	}
	
//	将json数据解析赋值到food类
	private static Info jsonToFood(JSONObject json) throws JSONException{
		Info food = new Info();
		food.setFood_description(json.getString("food_description"));
		food.setFood_discount_price(Float.valueOf(json.get("food_discount_price").toString()));
		food.setFood_flag(json.getInt("food_flag"));
		food.setFood_id(json.getInt("food_id"));
		food.setFood_name(json.getString("food_name"));
		food.setFood_pic(json.getString("food_pic"));
		food.setFood_price(Float.valueOf(json.get("food_price").toString()));
		
		food.kouwei =  json.getString("kouwei") ; 
		food.renqun =  json.getString("renqun") ; 
		food.shangjia =  json.getString("shangjia") ;
		
		
		return food;
	}
	
	private static Order jsonToOrder(JSONObject json) throws JSONException{
		Order order = new Order();
		order.setId(json.getString("id"));
		order.setFood_id(json.getString("food_id"));
		order.setSeat(json.getString("seat"));
		order.setOrder_date(json.getString("order_date"));
		order.setDesc(json.getString("desc"));
		order.setBeizhu(json.getString("beizhu"));
		order.setState(json.getString("state"));
		order.setPrice(json.getString("price"));
		order.setTel(json.getString("tel"));
		
		return order;
	}

	
	private static String appendParams(String url, long lastId, int pageNo) {
		if(lastId != -1){
			url = "?last_id=" + lastId;
		}
		if(pageNo != -1){
			url = "&pageNo=" + pageNo;
		}
		return url;
	}

	
	
}



