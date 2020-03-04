package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class EntriesItem{

	@SerializedName("startedDateTime")
	private String startedDateTime;

	@SerializedName("request")
	private Request request;

	@SerializedName("cache")
	private Cache cache;

	@SerializedName("response")
	private Response response;

	@SerializedName("serverIPAddress")
	private String serverIPAddress;

	@SerializedName("timings")
	private Timings timings;

	@SerializedName("connection")
	private String connection;

	@SerializedName("time")
	private double time;

	@SerializedName("pageref")
	private String pageref;

	public String getStartedDateTime(){
		return startedDateTime;
	}

	public Request getRequest(){
		return request;
	}

	public Cache getCache(){
		return cache;
	}

	public Response getResponse(){
		return response;
	}

	public String getServerIPAddress(){
		return serverIPAddress;
	}

	public Timings getTimings(){
		return timings;
	}

	public String getConnection(){
		return connection;
	}

	public double getTime(){
		return time;
	}

	public String getPageref(){
		return pageref;
	}

	@Override
 	public String toString(){
		return 
			"EntriesItem{" + 
			"startedDateTime = '" + startedDateTime + '\'' + 
			",request = '" + request + '\'' + 
			",cache = '" + cache + '\'' + 
			",response = '" + response + '\'' + 
			",serverIPAddress = '" + serverIPAddress + '\'' + 
			",timings = '" + timings + '\'' + 
			",connection = '" + connection + '\'' + 
			",time = '" + time + '\'' + 
			",pageref = '" + pageref + '\'' + 
			"}";
		}
}