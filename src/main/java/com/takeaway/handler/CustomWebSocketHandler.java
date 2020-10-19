package com.takeaway.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.takeaway.game.Game;

@Component
public class CustomWebSocketHandler implements ApplicationListener<SessionDisconnectEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebSocketHandler.class);

	@Autowired
    private Game game;
	
	@Override
	public void onApplicationEvent(SessionDisconnectEvent event) {

		String user = event.getUser().getName();
		
		LOGGER.debug("after connection closed for user: {}", user);
		
		game.removeUserFromGame(user);
	}
}
