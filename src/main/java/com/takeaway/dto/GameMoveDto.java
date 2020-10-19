package com.takeaway.dto;

import com.takeaway.model.GameMove;

public class GameMoveDto {

	private String resultingNumber;
	private String added;
	
	public GameMoveDto() {
		
	}

	public GameMoveDto(String resultingNumber, String added) {
		super();
		this.resultingNumber = resultingNumber;
		this.added = added;
	}
	
	public GameMoveDto(GameMove gameMove) {
		
		if (gameMove.getResultingNumber() != null) {
			
			this.resultingNumber = gameMove.getResultingNumber().toString();
		}
		
		if (gameMove.getAdded() != null) {
			
			this.added = gameMove.getAdded().toString();
		}
	}

	public String getResultingNumber() {
		return resultingNumber;
	}

	public void setResultingNumber(String resultingNumber) {
		this.resultingNumber = resultingNumber;
	}

	public String getAdded() {
		return added;
	}

	public void setAdded(String added) {
		this.added = added;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMoveDto [resultingNumber=");
		builder.append(resultingNumber);
		builder.append(", added=");
		builder.append(added);
		builder.append("]");
		return builder.toString();
	}
}
