package com.skooltest.common.utils;

public enum Status {
	
	SUCCESS(1), FAILURE(2), ERROR(3), INPROGRESS(4);
	
	private final int statusCode;
	
	Status(int statusCode){
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	@Override
	public String toString() {
		return this.name();
	}

}
