package gameLogic;

import gameUI.BoardPanel;

public class GomokuBot {

	private State currentState;
	private BoardPanel board;
	public byte counter;

	public GomokuBot(BoardPanel board) {
		this.currentState = State.NO_SEQ;
		this.board = board;
		this.counter = 0;
	}

	public Move findAttackMove() {
		counter++;
		return new Move(counter, counter);
		// Implement logic to find an empty cell to complete a sequence of 5 for the bot
		// (BLACK).
		// ...
	}

	public Move findDefenseMove() {
		counter++;
		return new Move(counter, counter);
		// Implement logic to find an empty cell to block opponent's sequence of 4.
		// You can check for opponent's potential sequences and prioritize blocking
		// them.
		// ...
	}
}


