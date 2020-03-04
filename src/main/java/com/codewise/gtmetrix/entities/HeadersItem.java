package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class HeadersItem{

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;

	public String getName(){
		return name;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"HeadersItem{" + 
			"name = '" + name + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}