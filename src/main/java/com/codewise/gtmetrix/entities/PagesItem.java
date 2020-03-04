package com.codewise.gtmetrix.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PagesItem{

	@SerializedName("startedDateTime")
	private String startedDateTime;

	@SerializedName("pageTimings")
	private PageTimings pageTimings;

	@SerializedName("_resourceUsage")
	private List<List<Double>> resourceUsage;

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	public String getStartedDateTime(){
		return startedDateTime;
	}

	public PageTimings getPageTimings(){
		return pageTimings;
	}

	public List<List<Double>> getResourceUsage(){
		return resourceUsage;
	}

	public String getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	@Override
 	public String toString(){
		return 
			"PagesItem{" + 
			"startedDateTime = '" + startedDateTime + '\'' + 
			",pageTimings = '" + pageTimings + '\'' + 
			",_resourceUsage = '" + resourceUsage + '\'' + 
			",id = '" + id + '\'' + 
			",title = '" + title + '\'' + 
			"}";
		}
}