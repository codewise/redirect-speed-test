package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class Content{

	@SerializedName("size")
	private int size;

	@SerializedName("mimeType")
	private String mimeType;

	@SerializedName("encoding")
	private String encoding;

	@SerializedName("compression")
	private int compression;

	@SerializedName("text")
	private String text;

	public int getSize(){
		return size;
	}

	public String getMimeType(){
		return mimeType;
	}

	public String getEncoding(){
		return encoding;
	}

	public int getCompression(){
		return compression;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"Content{" + 
			"size = '" + size + '\'' + 
			",mimeType = '" + mimeType + '\'' + 
			",encoding = '" + encoding + '\'' + 
			",compression = '" + compression + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}