package com.skooltest.common.exceptions;
import org.springframework.http.HttpStatus;

public class SkooltestUnauthorizedException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public SkooltestUnauthorizedException(String message) {
	    super(message);
	  }

	  public HttpStatus getStatus() {
	    return HttpStatus.UNAUTHORIZED;
	  }
}
