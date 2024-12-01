package com.skooltest.common.beans;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.skooltest.common.utils.Status;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseResource<T> implements Serializable {

	private static final long serialVersionUID = -819156317043642336L;

	private final Integer responseCode;
	private final String message;
	private final Status status;
	private T data;

	public static final String R_MESSAGE_EMPTY = "";
	public static final String R_MESSAGE_SUCCESS = "OK";
	public static final String R_MESSAGE_FAILURE = "OPERATION FAILED";
	public static final String R_MESSAGE_NO_DATA = "NO DATA";
	public static final String R_MESSAGE_NO_TRUNKS_DATA = "NO TRUNKS DATA";

	public static final Integer R_CODE_OK = 200;
	public static final Integer R_CODE_CONFLICT = 409;
	public static final Integer R_CODE_ERROR = 500;
	public static final Integer R_CODE_NOT_FOUND = 404;
	public static final Integer R_CODE_BAD_REQUEST = 400;
	public static final Integer R_CODE_INTERNAL_SERVER_ERROR = 500;
	public static final Integer R_CODE_UNAUTHORISED_ERROR = 401;
	public static final Integer R_CODE_FORBIDDEN_ERROR = 403;

	public ResponseResource() {
		this.message = ResponseResource.R_MESSAGE_EMPTY;
		this.responseCode = ResponseResource.R_CODE_OK;
		this.status = Status.SUCCESS;
		this.setData(data);
	}

	public ResponseResource(final Integer code, final String message, final Status status) {
		this.message = Objects.isNull(message) ? ResponseResource.R_MESSAGE_EMPTY : message;
		this.responseCode = Objects.isNull(code) ? ResponseResource.R_CODE_OK : code;
		this.status = Objects.isNull(status) ? Status.SUCCESS : status;
	}

	public ResponseResource(final Integer code, final String message, T data, final Status status) {
		this.message = Objects.isNull(message) ? ResponseResource.R_MESSAGE_EMPTY : message;
		this.responseCode = Objects.isNull(code) ? ResponseResource.R_CODE_OK : code;
		this.status = Objects.isNull(status) ? Status.SUCCESS : status;
		this.setData(data);
	}

	public ResponseResource(T data) {
		this.message = ResponseResource.R_MESSAGE_EMPTY;
		this.responseCode = ResponseResource.R_CODE_OK;
		this.data = data;
		this.status = Status.SUCCESS;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}

	public Status getStatus() {
		return status;
	}
}
