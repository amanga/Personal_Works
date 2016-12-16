package com.bunge.icc.text.operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TibcoCacheFunctionality {

	
Map<String,List<String>> cacheObj = null;
	
	public void addToCache(String key, List<String> obj){
		if(cacheObj == null);
			cacheObj = new HashMap<String,List<String>>();
			
		cacheObj.put(key, obj);
	}
	
	public String[] getCacheValue(String key){
		List<String> rtnObj = null;
		if(cacheObj == null)
			rtnObj = null;
		
		if(cacheObj.containsKey(key))
			rtnObj = cacheObj.get(key);
		
		return rtnObj.toArray(new String[4]);
	}
	
	
	public List<String> getCommodityValues(){
		List<String> commodityVals= new ArrayList<String>();
		//Databse query
		//resultset
		//add values;
		
		commodityVals.add("VAL1");

		return commodityVals;
	}
}
