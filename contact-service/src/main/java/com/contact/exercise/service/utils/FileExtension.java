package com.contact.exercise.service.utils;

public enum FileExtension {

	DOC("DOC")
	,DOCX("DOCX")
	,PDF("PDF");

	private String docType;
	
	FileExtension(String docType){
		this.docType = docType;
	}
	
	public String getDocType(){
		return docType;
	}
	
	public static FileExtension fromString(String value){
		if((value == null) || (value == ""))
			throw new NullPointerException("File extension is null");
		
		for(FileExtension fExtn : FileExtension.values()){
			if(fExtn.docType.equalsIgnoreCase(value))
				return fExtn;
		}
		
		return null;
		
	}
	
}
