package com.skooltest.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CommonUtils {

	public static final ObjectMapper objectMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false)
			.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true).findAndRegisterModules();

	public static <T> T convertJsonToObject(String jsonString, Class<T> clazz) {
		T object = null;
		try {
			object = objectMapper.reader().forType(clazz).readValue(jsonString);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return object;
	}

	public static <T> String convertObjectToJson(T object) {
		String json = null;
		try {
			json = objectMapper.writer().writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return json;
	}

	public static Date getDate(String dateString) throws ParseException {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		return formatter.parse(dateString);
	}
	
	public static String getDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		return formatter.format(date);
	}
}
