package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class Browser{

	@SerializedName("name")
	private String name;

	@SerializedName("version")
	private String version;

	public String getName(){
		return name;
	}

	public String getVersion(){
		return version;
	}

	@Override
 	public String toString(){
		return 
			"Browser{" + 
			"name = '" + name + '\'' + 
			",version = '" + version + '\'' + 
			"}";
		}
}