package com.takeaway.dto;

import com.takeaway.enums.NotificationEnum;

public class NotificationResponseDto {

	private String text;
	private String code;
	
	public NotificationResponseDto(NotificationEnum notificationEnum) {
		
		this.text = notificationEnum.getText();
		this.code = notificationEnum.getCode();
	}

	public String getText() {
		return text;
	}

	public String getCode() {
		return code;
	}
}
