package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class Timings{

	@SerializedName("receive")
	private double receive;

	@SerializedName("wait")
	private double wait;

	@SerializedName("blocked")
	private double blocked;

	@SerializedName("dns")
	private double dns;

	@SerializedName("send")
	private double send;

	@SerializedName("ssl")
	private double ssl;

	@SerializedName("_blocked_queueing")
	private double blockedQueueing;

	@SerializedName("connect")
	private double connect;

	@SerializedName("_failed")
	private double failed;

	public double getReceive(){
		return receive;
	}

	public double getWait(){
		return wait;
	}

	public double getBlocked(){
		return blocked;
	}

	public double getDns(){
		return dns;
	}

	public double getSend(){
		return send;
	}

	public double getSsl(){
		return ssl;
	}

	public double getBlockedQueueing(){
		return blockedQueueing;
	}

	public double getConnect(){
		return connect;
	}

	public double getFailed(){
		return failed;
	}

	@Override
 	public String toString(){
		return 
			"Timings{" + 
			"receive = '" + receive + '\'' + 
			",wait = '" + wait + '\'' + 
			",blocked = '" + blocked + '\'' + 
			",dns = '" + dns + '\'' + 
			",send = '" + send + '\'' + 
			",ssl = '" + ssl + '\'' + 
			",_blocked_queueing = '" + blockedQueueing + '\'' + 
			",connect = '" + connect + '\'' + 
			",_failed = '" + failed + '\'' + 
			"}";
		}
}