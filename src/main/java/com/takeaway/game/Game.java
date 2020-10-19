package com.takeaway.game;

import com.takeaway.dto.GameMoveDto;

public interface Game {

	void addUserToGame(String user);
	void playGameMoveForUser(String user, GameMoveDto gameMoveDto);
	void removeUserFromGame(String user);
	void getFirstResultingNumber(String user);
	boolean isUserInGame(String user);
	boolean isFirstToPlay(String user);
}
