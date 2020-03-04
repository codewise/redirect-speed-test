package com.codewise.gtmetrix.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("headers")
	private List<HeadersItem> headers;

	@SerializedName("httpVersion")
	private String httpVersion;

	@SerializedName("redirectURL")
	private String redirectURL;

	@SerializedName("statusText")
	private String statusText;

	@SerializedName("bodySize")
	private int bodySize;

	@SerializedName("headersSize")
	private int headersSize;

	@SerializedName("cookies")
	private List<CookiesItem> cookies;

	@SerializedName("content")
	private Content content;

	@SerializedName("status")
	private int status;

	@SerializedName("_errorText")
	private String errorText;

	@SerializedName("_error")
	private String error;

	public List<HeadersItem> getHeaders(){
		return headers;
	}

	public String getHttpVersion(){
		return httpVersion;
	}

	public String getRedirectURL(){
		return redirectURL;
	}

	public String getStatusText(){
		return statusText;
	}

	public int getBodySize(){
		return bodySize;
	}

	public int getHeadersSize(){
		return headersSize;
	}

	public List<CookiesItem> getCookies(){
		return cookies;
	}

	public Content getContent(){
		return content;
	}

	public int getStatus(){
		return status;
	}

	public String getErrorText(){
		return errorText;
	}

	public String getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"headers = '" + headers + '\'' + 
			",httpVersion = '" + httpVersion + '\'' + 
			",redirectURL = '" + redirectURL + '\'' + 
			",statusText = '" + statusText + '\'' + 
			",bodySize = '" + bodySize + '\'' + 
			",headersSize = '" + headersSize + '\'' + 
			",cookies = '" + cookies + '\'' + 
			",content = '" + content + '\'' + 
			",status = '" + status + '\'' + 
			",_errorText = '" + errorText + '\'' + 
			",_error = '" + error + '\'' + 
			"}";
		}
}