package com.bunge.icc.misc;

public class ImmutableObjectExample {
	
	private static String[] getCountryIds() {
		String countryNames ="7#9";
		
		String[] rtnCountries = null;
		if(countryNames!=null){
			if(countryNames.contains("#")){
				rtnCountries = countryNames.split("#");
			}else{
				rtnCountries = new String[1];
				rtnCountries[0] = countryNames;
			}
		}
		return rtnCountries;
	}
	
	private static boolean processCountryMatch(Long countryId) {
		if (countryId==null)
			return false;
		
		for(String countryIdStr : getCountryIds()){
			if(countryIdStr.equals(String.valueOf(countryId)))
				return true;
		}
		return false;
	}
	
	public static void main(String args[]){
		BigBox b = new BigBox(10, new SmallBox(7));
		
		System.out.println(b.getSmallBox().getNumber());
		System.out.println(b.getNumber());
		
		b = new BigBox(9, new SmallBox(6));
		System.out.println("Created new Immutable BigBox");
		System.out.println(b.getSmallBox().getNumber());
		System.out.println(b.getNumber());
		
		System.out.println("Changing SmallBox number by setNumber method");
		b.getSmallBox().setNumber(12);
		System.out.println(b.getSmallBox().getNumber());
		System.out.println(b.getNumber());
		
		System.out.println(processCountryMatch(null));
		/*for(String country : ){
			System.out.println("xxx"+country);
		}*/
	}
}


class SmallBox{
	Integer number;
	
	public SmallBox(Integer number){
		this.number = number;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number){
		this.number = number;
	}
}

class BigBox{
	final private  Integer number;
	final private SmallBox smallBox;
	
	public BigBox(Integer number, SmallBox smallBox){
		this.number = number;
		this.smallBox = smallBox;
	}

	public Integer getNumber() {
		return number;
	}

	public SmallBox getSmallBox() {
		return smallBox;
	}
	
}