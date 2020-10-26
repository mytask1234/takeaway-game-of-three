package com.takeaway.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.takeaway.dto.ErrorMessageResponseDto;
import com.takeaway.dto.FirstUserToPlayResponseDto;
import com.takeaway.dto.GameMoveDto;
import com.takeaway.enums.NotificationEnum;
import com.takeaway.exception.GameException;
import com.takeaway.game.Game;
import com.takeaway.service.SocketSendOutMessagesService;

@RestController
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	@Autowired
	private Game game;

	@Autowired
	private SocketSendOutMessagesService socketSendOutMessagesService;

	/************************** WebSocket endpoints *******************************/
	
	@MessageMapping("/send")
	public void gameMove(GameMoveDto gameMoveDto, SimpMessageHeaderAccessor ha) {

		LOGGER.debug("gameMoveDto={}", gameMoveDto);

		String user = ha.getUser().getName();

		LOGGER.debug("user={}", user);

		game.playGameMoveForUser(user, gameMoveDto);
	}

	@MessageMapping("/after_connect")
	public void afterConnect(SimpMessageHeaderAccessor ha) {

		String user = ha.getUser().getName();

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("user={}", user);
		}

		if (game.isUserInGame(user) == false) {

			socketSendOutMessagesService.sendNotificationToUser(user, NotificationEnum.GAME_IS_BUSY_WITH_TWO_PLAYERS);

		} else {

			game.getFirstResultingNumber(user);
		}
	}

	@MessageExceptionHandler
	@SendToUser("/queue/errors")
	public ErrorMessageResponseDto handleException(Throwable t, SimpMessageHeaderAccessor ha) {

		String user = ha.getUser().getName();

		LOGGER.debug("user={}", user);

		ErrorMessageResponseDto errorMessageResponseDto = new ErrorMessageResponseDto(t);
		
		LOGGER.error(errorMessageResponseDto.toString(), t);
		
		return errorMessageResponseDto;
	}
	
	/************************** Rest endpoint *******************************/

	@GetMapping("/is_first_to_play")
	public FirstUserToPlayResponseDto isFirstToPlay(HttpSession session) {

		String socketUserName = (String)session.getAttribute("socketUserName");

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("socketUserName={}", socketUserName);
		}

		boolean isFirstToPlay = game.isFirstToPlay(socketUserName);

		FirstUserToPlayResponseDto firstUserToPlayResponseDto = new FirstUserToPlayResponseDto(isFirstToPlay);

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("firstUserToPlayResponseDto={}", firstUserToPlayResponseDto);
		}

		return firstUserToPlayResponseDto;
	}
}
