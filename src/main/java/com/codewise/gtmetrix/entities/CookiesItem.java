package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class CookiesItem{

	@SerializedName("path")
	private String path;

	@SerializedName("name")
	private String name;

	@SerializedName("value")
	private String value;

	public String getPath(){
		return path;
	}

	public String getName(){
		return name;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"CookiesItem{" + 
			"path = '" + path + '\'' + 
			",name = '" + name + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}