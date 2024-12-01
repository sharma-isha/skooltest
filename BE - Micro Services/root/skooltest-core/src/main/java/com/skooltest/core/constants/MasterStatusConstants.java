package com.skooltest.core.constants;

import java.util.Arrays;
import java.util.List;

public class MasterStatusConstants {

	public static final String ASSESSMENT_CREATED = "ASSESSMENT_CREATED";
	public static final String ASSESSMENT_ASSIGNED = "ASSESSMENT_ASSIGNED";
	public static final String QUESTION_PAPER_GENERATED = "QUESTION_PAPER_GENERATED";
	public static final String ANSWER_SHEET_UPLOADED = "ANSWER_SHEET_UPLOADED";
	public static final String ANSWER_SHEET_EVALUATION = "ANSWER_SHEET_EVALUATION";
	public static final String ASSESSMENT_COMPLETED = "ASSESSMENT_COMPLETED";
	public static final String DUE_DATE_CROSSED = "DUE_DATE_CROSSED";
	
	public static final List<String> TEACHER_STATUS_CONSTANTS = Arrays.asList(ASSESSMENT_ASSIGNED,ANSWER_SHEET_EVALUATION);
}
