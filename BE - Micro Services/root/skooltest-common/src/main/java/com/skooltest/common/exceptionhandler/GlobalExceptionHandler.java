package com.skooltest.common.exceptionhandler;

import java.util.Date;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.skooltest.common.beans.ErrorBean;
import com.skooltest.common.exceptions.SkooltestCommonException;
import com.skooltest.common.exceptions.SkooltestServiceUnavailableException;
import com.skooltest.common.exceptions.SkooltestUnauthorizedException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(SkooltestUnauthorizedException.class)
	public ResponseEntity<ErrorBean> generateUnauthorizedException(SkooltestUnauthorizedException ex) {
		ErrorBean errorDTO = new ErrorBean();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
		errorDTO.setTime(new Date().toString());

		return new ResponseEntity<ErrorBean>(errorDTO, ex.getStatus());
	}
	
	@ExceptionHandler(SkooltestCommonException.class)
	public ResponseEntity<ErrorBean> generateCommonException(SkooltestCommonException ex) {
		ErrorBean errorDTO = new ErrorBean();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
		errorDTO.setTime(new Date().toString());

		return new ResponseEntity<ErrorBean>(errorDTO, ex.getStatus());
	}
	
	@ExceptionHandler(SkooltestServiceUnavailableException.class)
	public ResponseEntity<ErrorBean> generateServiceUnavailableException(SkooltestServiceUnavailableException ex) {
		ErrorBean errorDTO = new ErrorBean();
		errorDTO.setMessage(ex.getMessage());
		errorDTO.setStatus(String.valueOf(ex.getStatus().value()));
		errorDTO.setTime(new Date().toString());

		return new ResponseEntity<ErrorBean>(errorDTO, ex.getStatus());
	}
}