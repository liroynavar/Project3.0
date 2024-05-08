package gameLogic;

public class Move {
	private final byte row;
	private final byte col;

	public Move(byte row, byte col) {
		this.row = row;
		this.col = col;
	}

	public byte getRow() {
		return row;
	}

	public byte getCol() {
		return col;
	}
}
