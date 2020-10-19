package com.takeaway.service;

import com.takeaway.enums.NotificationEnum;
import com.takeaway.model.GameMove;

public interface SocketSendOutMessagesService {

	void sendGameMoveToUser(String toUser, GameMove gameMove);
	void sendNotificationToUser(String toUser, NotificationEnum notificationEnum);
}
