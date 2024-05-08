package gameLogic;

public class Board {
	private Square[][] _board;

	public Board() {
		_board = new Square[15][15];
		int count = 0;
		boolean flag = true;
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board.length; j++) {
				_board[i][j] = new Square();
			}
		}
	}

	/*
	 * Input: N/A Output: N/A Description: Clear The Board From Soldiers
	 */
	public void clearBoard() {
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board.length; j++) {
				_board[i][j].clearSquare();
			}
		}
	}

	/*
	 * Input: N/A Output: N/A Description: Print the current state of the board to
	 * the console
	 */
	public void printBoard() {
		for (int i = 0; i < _board.length; i++) {
			for (int j = 0; j < _board.length; j++) {
				System.out.printf("[%s]", _board[i][j].toString());
			}
			System.out.println("\n");
		}
		System.out.println("---------------------------------------------");
	}

	/*
	 * Input: N/A Output: The current board Description: Get the current state of
	 * the board
	 */
	public Square[][] get_board() {
		return _board;
	}

	/*
	 * Input: Row index, Column index Output: The square at the specified position
	 * Description: Get the square at the specified position on the board
	 */
	public Square getSquare(int i, int j) {
		return _board[i][j];
	}

	/*
	 * Input: Soldier type, Row index, Column index Output: N/A Description: Update
	 * the board with the specified soldier type at the specified position
	 */
	public void updateBoard(Soldier p, int row, int col) {
		_board[row][col].updateSquare(p);
		printBoard();
	}
}
