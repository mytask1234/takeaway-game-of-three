package com.takeaway.dto;

public class FirstUserToPlayResponseDto {

	final private boolean isFirstToPlay;

	public FirstUserToPlayResponseDto(boolean isFirstToPlay) {
		super();
		this.isFirstToPlay = isFirstToPlay;
	}

	public boolean isFirstToPlay() {
		return isFirstToPlay;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FirstUserToPlayResponseDto [isFirstToPlay=");
		builder.append(isFirstToPlay);
		builder.append("]");
		return builder.toString();
	}
}
