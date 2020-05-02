package com.certimetergroup.formazione.enumeration;

public enum ResponseCodeEnum {

	SUCCESS  (0,"success"),

	ERR_1  (1,"Empty/Invalid Field"),
	ERR_2  (2,"Database action error"),
	ERR_3  (3,"No Result Found"),

	ERR_INVALID_TOKEN  (100,"Invalid authentication token"),
	ERR_EXPIRED_TOKEN  (101,"Expired authentication token"),

	ERR_500 (500,"UnexpectedError");

	
	public int id;
	public String description;
	
	
	ResponseCodeEnum(int id, String descrpiton){
		this.id=id;
		this.description = descrpiton;
	}
	
}
