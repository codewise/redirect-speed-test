package com.codewise.gtmetrix.entities;

import com.google.gson.annotations.SerializedName;

public class PageTimings{

	@SerializedName("_firstPaint")
	private int firstPaint;

	@SerializedName("_fullyLoaded")
	private double fullyLoaded;

	@SerializedName("_firstContentfulPaint")
	private int firstContentfulPaint;

	@SerializedName("onLoad")
	private int onLoad;

	@SerializedName("onContentLoad")
	private int onContentLoad;

	@SerializedName("_onLoadTime")
	private int onLoadTime;

	public int getFirstPaint(){
		return firstPaint;
	}

	public double getFullyLoaded(){
		return fullyLoaded;
	}

	public int getFirstContentfulPaint(){
		return firstContentfulPaint;
	}

	public int getOnLoad(){
		return onLoad;
	}

	public int getOnContentLoad(){
		return onContentLoad;
	}

	public int getOnLoadTime(){
		return onLoadTime;
	}

	@Override
 	public String toString(){
		return 
			"PageTimings{" + 
			"_firstPaint = '" + firstPaint + '\'' + 
			",_fullyLoaded = '" + fullyLoaded + '\'' + 
			",_firstContentfulPaint = '" + firstContentfulPaint + '\'' + 
			",onLoad = '" + onLoad + '\'' + 
			",onContentLoad = '" + onContentLoad + '\'' + 
			",_onLoadTime = '" + onLoadTime + '\'' + 
			"}";
		}
}