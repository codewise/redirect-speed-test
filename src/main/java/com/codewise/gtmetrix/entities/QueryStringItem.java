package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class QueryStringItem{

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
			"QueryStringItem{" + 
			"name = '" + name + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}