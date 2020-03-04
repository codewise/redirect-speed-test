package com.codewise.gtmetrix.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Request{

	@SerializedName("headers")
	private List<HeadersItem> headers;

	@SerializedName("httpVersion")
	private String httpVersion;

	@SerializedName("method")
	private String method;

	@SerializedName("bodySize")
	private int bodySize;

	@SerializedName("headersSize")
	private int headersSize;

	@SerializedName("queryString")
	private List<Object> queryString;

	@SerializedName("cookies")
	private List<Object> cookies;

	@SerializedName("url")
	private String url;

	public List<HeadersItem> getHeaders(){
		return headers;
	}

	public String getHttpVersion(){
		return httpVersion;
	}

	public String getMethod(){
		return method;
	}

	public int getBodySize(){
		return bodySize;
	}

	public int getHeadersSize(){
		return headersSize;
	}

	public List<Object> getQueryString(){
		return queryString;
	}

	public List<Object> getCookies(){
		return cookies;
	}

	public String getUrl(){
		return url;
	}

	@Override
 	public String toString(){
		return 
			"Request{" + 
			"headers = '" + headers + '\'' + 
			",httpVersion = '" + httpVersion + '\'' + 
			",method = '" + method + '\'' + 
			",bodySize = '" + bodySize + '\'' + 
			",headersSize = '" + headersSize + '\'' + 
			",queryString = '" + queryString + '\'' + 
			",cookies = '" + cookies + '\'' + 
			",url = '" + url + '\'' + 
			"}";
		}
}