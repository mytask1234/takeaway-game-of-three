package com.takeaway.dto;

import com.takeaway.exception.GameException;

public class ErrorMessageResponseDto {

	private String errorMessage;
	private String errorCode = "UNKNOWN";
	
	public ErrorMessageResponseDto(String errorMessage, String errorCode) {
		super();
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;
	}
	
	public ErrorMessageResponseDto(Throwable t) {
		
		this.errorMessage = t.getMessage();
		
		if (t instanceof GameException) {
			
			GameException ge = (GameException)t;
			
			if (ge.getErrorCode() != null) {
				
				this.errorCode = ge.getErrorCode();
			}
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ErrorMessageResponseDto [errorMessage=");
		builder.append(errorMessage);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append("]");
		return builder.toString();
	}
}
