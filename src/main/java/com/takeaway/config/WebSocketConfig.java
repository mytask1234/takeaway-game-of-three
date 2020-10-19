package com.takeaway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.takeaway.game.Game;
import com.takeaway.game.GameImpl;
import com.takeaway.handler.CustomHandshakeHandler;
import com.takeaway.service.SocketSendOutMessagesService;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {

		registry.enableSimpleBroker("/queue/game_moves", "/queue/notifications", "/queue/errors");

		/*
		 * The application destination prefix `/app` designates the broker to send
		 * messages prefixed with `/app` to our `@MessageMapping`s.
		 */
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {

		/*
		 * This configures a STOMP (Simple Text Oriented Messaging Protocol)
		 * endpoint for our websocket to be hosted on
		 */
		registry.addEndpoint("/websocket").setHandshakeHandler(new CustomHandshakeHandler(game(null))); 
	}

	@Bean
	public Game game(SocketSendOutMessagesService socketSendOutMessagesService) {

		return new GameImpl(socketSendOutMessagesService);
	}
}
