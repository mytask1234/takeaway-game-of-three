package com.takeaway.model;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.takeaway.dto.GameMoveDto;
import com.takeaway.enums.ErrorEnum;
import com.takeaway.exception.GameException;

public class GameMove {

	private static final Set<Integer> VALID_ADDED_VALUES = Stream.of(1, 0, -1).collect(Collectors.toCollection(HashSet::new));

	private Integer resultingNumber;
	private Integer added;

	public GameMove() {

	}

	public GameMove(Integer resultingNumber, Integer added) {
		super();
		this.resultingNumber = resultingNumber;
		this.added = added;
	}

	public GameMove(GameMoveDto gameMoveDto) {

		this.resultingNumber = validateInteger(gameMoveDto.getResultingNumber(), ErrorEnum.RESULTING_NUMBER_NOT_INTEGER);
		if (this.resultingNumber != null && this.resultingNumber < 2) {
			throw new GameException(ErrorEnum.RESULTING_NUMBER_TOO_SMALL, this.resultingNumber);
		}

		this.added = validateInteger(gameMoveDto.getAdded(), ErrorEnum.ADDED_NUMBER_NOT_INTEGER);
		if (this.added != null) {

			if (VALID_ADDED_VALUES.contains(this.added) == false) {

				throw new GameException(ErrorEnum.ILLEGAL_ADDED_NUMBER, this.added);
			}
		}
	}

	private Integer validateInteger(String numStr, ErrorEnum errorEnum) {

		if (numStr != null) {

			try {

				return Integer.parseInt(numStr);

			} catch (NumberFormatException e) {

				throw new GameException(errorEnum, e, numStr);
			}
		}

		return null;
	}

	public Integer getResultingNumber() {
		return resultingNumber;
	}

	public void setResultingNumber(Integer resultingNumber) {
		this.resultingNumber = resultingNumber;
	}

	public Integer getAdded() {
		return added;
	}

	public void setAdded(Integer added) {
		this.added = added;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMove [resultingNumber=");
		builder.append(resultingNumber);
		builder.append(", added=");
		builder.append(added);
		builder.append("]");
		return builder.toString();
	}
}
