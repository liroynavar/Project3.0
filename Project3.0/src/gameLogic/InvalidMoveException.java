package gameLogic;

public class InvalidMoveException extends IllegalArgumentException{
	public InvalidMoveException() {
		super();
	}
	public InvalidMoveException(String message) {
		super(message);
	}
}
