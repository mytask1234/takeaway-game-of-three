package com.takeaway.exception;

import com.takeaway.enums.ErrorEnum;

public class GameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7472766231549032854L;

	public GameException() {

	}
	
	public GameException(String message) {
		super(message);
	}
	
	public GameException(ErrorEnum errorEnum) {
		super(errorEnum.getMessage());
	}
	
	public GameException(ErrorEnum errorEnum, Object... args) {
		
		super(String.format(errorEnum.getMessage(), args));
	}

	public GameException(Throwable cause) {
		super(cause);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public GameException(ErrorEnum errorEnum, Throwable cause, Object... args) {
		super(String.format(errorEnum.getMessage(), args), cause);
	}

	public GameException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
