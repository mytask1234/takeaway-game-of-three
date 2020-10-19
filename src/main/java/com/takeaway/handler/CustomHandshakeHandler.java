package com.takeaway.handler;

import java.security.Principal;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.takeaway.game.Game;
import com.takeaway.security.StompPrincipal;

/**
 * 
 * Custom class for storing principal.
 *
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomHandshakeHandler.class);

	private final Game game;

	public CustomHandshakeHandler(Game game) {

		this.game = game;
	}

	@Override
	protected Principal determineUser(
			ServerHttpRequest request,
			WebSocketHandler wsHandler,
			Map<String, Object> attributes) {

		ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
		HttpSession session = servletRequest.getServletRequest().getSession();

		String user = (String)session.getAttribute("socketUserName");

		if (user == null) {

			user = UUID.randomUUID().toString();

			session.setAttribute("socketUserName", user);
		}

		game.addUserToGame(user);

		LOGGER.debug("user={}", user);

		// Generate principal with sessionId or integer as name
		return new StompPrincipal(user);
	}
}
