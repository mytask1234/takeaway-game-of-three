package com.takeaway.game;

import com.takeaway.dto.GameMoveDto;
import com.takeaway.enums.ErrorEnum;
import com.takeaway.enums.NotificationEnum;
import com.takeaway.exception.GameException;
import com.takeaway.model.GameMove;
import com.takeaway.service.SocketSendOutMessagesService;

public class GameImpl implements Game {

	private volatile String user1 = null;
	private volatile String user2 = null;
	private volatile String nextUserTurn = null;
	private volatile int numOfUsers = 0;
	private volatile int numOfGameMove = 0;
	private volatile int lastResultingNumber = 0;

	final private SocketSendOutMessagesService socketSendOutMessagesService;

	public GameImpl(SocketSendOutMessagesService socketSendOutMessagesService) {

		this.socketSendOutMessagesService = socketSendOutMessagesService;
	}

	public synchronized void addUserToGame(String user) {

		if (numOfUsers == 2) {

			return;
		}

		if (user1 == null) {

			user1 = user;
			nextUserTurn = user;
			++numOfUsers;

		} else if (user2 == null) {

			user2 = user;

			if (numOfGameMove == 1) {

				nextUserTurn = user;
			}

			++numOfUsers;
		}
	}

	@Override
	public synchronized void playGameMoveForUser(String user, GameMoveDto gameMoveDto) {

		if (user.equals(nextUserTurn) == false) {

			throw new GameException(ErrorEnum.NOT_YOUR_TURN);
		}
		
		GameMove gameMove = new GameMove(gameMoveDto);

		String otherUser = getOtherUser(user);

		if (numOfGameMove == 0) { // if first move

			if (gameMove.getResultingNumber() == null) {

				throw new GameException(ErrorEnum.PARAMETER_IS_NULL, "resultingNumber");
			}

			lastResultingNumber = gameMove.getResultingNumber();

			gameMove.setAdded(null);

		} else { // if not first move

			if (gameMove.getAdded() == null) {

				throw new GameException(ErrorEnum.PARAMETER_IS_NULL, "added");
			}

			if ((lastResultingNumber + gameMove.getAdded()) % 3 != 0) {

				throw new GameException(ErrorEnum.NOT_DIVISIBLE_BY_3, lastResultingNumber + gameMove.getAdded());
			}

			gameMove.setResultingNumber(lastResultingNumber);
			
			lastResultingNumber += gameMove.getAdded();

			lastResultingNumber /= 3;
		}

		if (otherUser != null) {

			socketSendOutMessagesService.sendGameMoveToUser(otherUser, gameMove);
		}

		socketSendOutMessagesService.sendGameMoveToUser(user, gameMove);

		if (lastResultingNumber == 1) {

			socketSendOutMessagesService.sendNotificationToUser(user, NotificationEnum.YOU_WON);

			if (otherUser != null) {

				socketSendOutMessagesService.sendNotificationToUser(otherUser, NotificationEnum.YOU_LOST);
			}

			reset();
			
		} else {
			
			nextUserTurn = otherUser;

			++numOfGameMove;
		}
	}

	private String getOtherUser(String user) {

		return user.equals(user1) ? user2 : user1;
	}

	private void reset() {

		user1 = null;
		user2 = null;
		nextUserTurn = null;
		numOfUsers = 0;
		numOfGameMove = 0;
		lastResultingNumber = 0;
	}

	@Override
	public synchronized void removeUserFromGame(String user) {

		if (isUserInGame(user)) {
			
			String otherUser = getOtherUser(user);

			if (otherUser != null) {

				socketSendOutMessagesService.sendNotificationToUser(otherUser, NotificationEnum.OTHER_USER_DISCONNECTED);
			}

			reset();
		}
	}

	@Override
	public void getFirstResultingNumber(String user) {

		if (user == null) {

			throw new GameException(ErrorEnum.PARAMETER_IS_NULL, "user");
		}

		if (numOfGameMove > 0) {

			socketSendOutMessagesService.sendGameMoveToUser(user, new GameMove(lastResultingNumber, null));
		}
	}
	
	@Override
	public boolean isUserInGame(String user) {
		
		if (user == null) {
			
			throw new GameException(ErrorEnum.PARAMETER_IS_NULL, "user");
		}
		
		if (user.equals(user1) || user.equals(user2)) {
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean isFirstToPlay(String user) {
		
		if (user == null) {
			
			throw new GameException(ErrorEnum.PARAMETER_IS_NULL, "user");
		}
		
		if (user.equals(user1)) {
			
			return true;
		}
		
		return false;
	}
}
