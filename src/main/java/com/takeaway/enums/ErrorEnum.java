package com.takeaway.enums;

public enum ErrorEnum {

	NOT_YOUR_TURN("This is not your turn."),
	RESULTING_NUMBER_NOT_INTEGER("The resulting number '%s' is not an integer."),
	RESULTING_NUMBER_TOO_SMALL("The resulting number '%d' is less than 2."),
	ILLEGAL_ADDED_NUMBER("added number '%d' is illegal. Legal values are: 1, 0, -1"),
	ADDED_NUMBER_NOT_INTEGER("The added number '%s' is not an integer."),
	NOT_DIVISIBLE_BY_3("The resulting number '%d' not divisible by 3."),
	PARAMETER_IS_NULL("The parameter '%s' can't be null.");

	private String message;

	ErrorEnum(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
