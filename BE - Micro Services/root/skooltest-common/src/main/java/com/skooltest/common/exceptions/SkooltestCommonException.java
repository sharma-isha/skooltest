package com.skooltest.common.exceptions;

import org.springframework.http.HttpStatus;

public class SkooltestCommonException extends RuntimeException{
	
private static final long serialVersionUID = 1L;
	
	public SkooltestCommonException(String message) {
	    super(message);
	  }

	  public HttpStatus getStatus() {
	    return HttpStatus.INTERNAL_SERVER_ERROR;
	  }

}
