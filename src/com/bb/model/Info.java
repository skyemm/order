package com.bb.model;

import java.io.Serializable;


/**
 *  菜的pojo
 * @author Administrator
 *
 */
public class Info implements Serializable  {

	public int food_id = -1;
	public String food_name;
	public String food_pic = null;
	public String food_description;
	public float food_price;
	public float food_discount_price;

	public  String  kouwei , renqun ,shangjia;
	
	
	/**
	 *  flag 	0--地区特色
	 *			1---精品推荐
	 *			2---普通	
	 */
	public int food_flag;
	public int getFood_id() {
		return food_id;
	}
	public void setFood_id(int food_id) {
		this.food_id = food_id;
	}
	public String getFood_name() {
		return food_name;
	}
	public void setFood_name(String food_name) {
		this.food_name = food_name;
	}
	public String getFood_pic() {
		return food_pic;
	}
	public void setFood_pic(String food_pic) {
		this.food_pic = food_pic;
	}
	public String getFood_description() {
		return food_description;
	}
	public void setFood_description(String food_description) {
		this.food_description = food_description;
	}
	public float getFood_price() {
		return food_price;
	}
	public void setFood_price(float food_price) {
		this.food_price = food_price;
	}
	public float getFood_discount_price() {
		return food_discount_price;
	}
	public void setFood_discount_price(float food_discount_price) {
		this.food_discount_price = food_discount_price;
	}
	public int getFood_flag() {
		return food_flag;
	}
	public void setFood_flag(int food_flag) {
		this.food_flag = food_flag;
	}
	public String getKouwei() {
		return kouwei;
	}
	public void setKouwei(String kouwei) {
		this.kouwei = kouwei;
	}
	public String getRenqun() {
		return renqun;
	}
	public void setRenqun(String renqun) {
		this.renqun = renqun;
	}
	public String getShangjia() {
		return shangjia;
	}
	public void setShangjia(String shangjia) {
		this.shangjia = shangjia;
	}
	
	
	
	
}
