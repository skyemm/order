package com.bb.model;

import java.io.Serializable;


/**
 * 购物车
 * @author Administrator
 *
 */
public class Shangjia implements Serializable  {

	
	private String 	name , price,desr;
	
	public String getname() {
		return name;
	}
	public void setname(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	} 
	public String getDesr() {
		return desr;
	}
	public void setDesr(String desr) {
		this.desr = desr;
	} 
	
}
