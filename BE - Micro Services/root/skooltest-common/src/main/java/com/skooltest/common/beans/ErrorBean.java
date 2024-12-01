package com.skooltest.common.beans;

import java.io.Serializable;

public class ErrorBean implements Serializable{

	private static final long serialVersionUID = -3846016862523449988L;
	
	public String status;
	public String message;
	public String time;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
}