package com.codewise.gtmetrix.entities;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Log{

	@SerializedName("creator")
	private Creator creator;

	@SerializedName("entries")
	private List<EntriesItem> entries;

	@SerializedName("pages")
	private List<PagesItem> pages;

	@SerializedName("browser")
	private Browser browser;

	@SerializedName("version")
	private String version;

	public Creator getCreator(){
		return creator;
	}

	public List<EntriesItem> getEntries(){
		return entries;
	}

	public List<PagesItem> getPages(){
		return pages;
	}

	public Browser getBrowser(){
		return browser;
	}

	public String getVersion(){
		return version;
	}

	@Override
 	public String toString(){
		return 
			"Log{" + 
			"creator = '" + creator + '\'' + 
			",entries = '" + entries + '\'' + 
			",pages = '" + pages + '\'' + 
			",browser = '" + browser + '\'' + 
			",version = '" + version + '\'' + 
			"}";
		}
}