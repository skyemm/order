package com.bb.ui;

import java.io.Serializable;


/**
 * 购物车
 * @author Administrator
 *
 */
public class GwcInfo implements Serializable  {

	private int 	id ; 
	private String 	food_name ,  seat , price, order_date ,count,tel;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFood_name() {
		return food_name;
	}
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}
	public String getSeat() {
		return seat;
	}
	public void setSeat(String seat) {
		this.seat = seat;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	} 
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	} 
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	} 
	 
	 
	
	
}
