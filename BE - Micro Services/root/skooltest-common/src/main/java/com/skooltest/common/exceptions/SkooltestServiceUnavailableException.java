package com.skooltest.common.exceptions;

import org.springframework.http.HttpStatus;

public class SkooltestServiceUnavailableException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public SkooltestServiceUnavailableException(String message) {
	    super(message);
	  }

	  public HttpStatus getStatus() {
	    return HttpStatus.SERVICE_UNAVAILABLE;
	  }
}
