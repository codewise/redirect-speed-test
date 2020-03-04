package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class GtmetrixHar{

	@SerializedName("log")
	private Log log;

	public Log getLog(){
		return log;
	}

	@Override
 	public String toString(){
		return 
			"GtmetrixHar{" + 
			"log = '" + log + '\'' + 
			"}";
		}
}