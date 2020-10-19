package com.takeaway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.takeaway.dto.NotificationResponseDto;
import com.takeaway.enums.NotificationEnum;
import com.takeaway.model.GameMove;

@Service
public class SocketSendOutMessagesServiceImpl implements SocketSendOutMessagesService {

	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;

	@Override
	public void sendGameMoveToUser(String toUser, GameMove gameMove) {

		simpMessagingTemplate.convertAndSendToUser(toUser, "/queue/game_moves", gameMove);
	}

	@Override
	public void sendNotificationToUser(String toUser, NotificationEnum notificationEnum) {

		simpMessagingTemplate.convertAndSendToUser(toUser, "/queue/notifications", new NotificationResponseDto(notificationEnum));
	}
}
