package gameLogic;

import java.io.Serializable;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.Timer;

import gameUI.BoardPanel;
import gameUI.Main;
import gameUI.OuterFrame;
import gameUI.Player;

public class GameManager  implements Serializable{
    private BoardPanel _board;
    final private int sequence_of = 5;
	private State _currentState;
	private GomokuBot _bot;

    public GameManager(BoardPanel board) {
        _board = board;
        _bot = new GomokuBot(board);
		_currentState = State.NO_SEQ;
    }

    /* Input: Row index, Column index
     * Output: True if the move is valid, false otherwise
     * Description: Check if the specified move is valid on the current board
     */
    public boolean isValidMove(int i, int j) throws InvalidMoveException {
        if (!isValidIndex(i) || !isValidIndex(j)) {
            throw new InvalidMoveException("Invalid Move, Out Of Board Bounds!");
        } else if (!_board.getSquare(i, j).isHasSoldier()) {
            throw new InvalidMoveException("Invalid Move, Already Has Soldier!");
        } else {
            return true;
        }
    }

    /* Input: N/A
     * Output: N/A
     * Description: Calculate the winner based on the current state of the board
     */
    public void winnerCulc() {
        winnerCulcHelper('C');
        winnerCulcHelper('R');
        winnerCulcHelper('D');
        winnerCulcHelper('A');

        // Check for a draw
        gameUI.Square[][] sBoard = _board.getPanels();
        int boardSize = sBoard.length;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (sBoard[i][j].isHasSoldier()) {
                    return; // If any empty square is found, the game is not a draw yet
                }
            }
        }
        // If no empty squares are found, and no player has won, then it's a draw
        gameUI.Main.GAME_STATUS = GameStatus.draw;
    }

    /* Input: Check type (R, C, D, A)
     * Output: N/A
     * Description: Helper method to check for a winner in rows, columns, and diagonals
     */
    public void winnerCulcHelper(char checkType) {
        int count = 0, iType = 0, jType = 0;
        switch (checkType) {
            case 'R':
                iType = 0;
                jType = 1;
                break;
            case 'C':
                iType = 1;
                jType = 0;
                break;
            case 'D':
                iType = 1;
                jType = 1;
                break;
            case 'A':
                iType = -1;
                jType = 1;
                break;
            default:
                break;
        }
        gameUI.Square[][] sBoard = _board.getPanels();
        int boardSize = sBoard.length;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                count = 0;
                for (int k = 0; k < sequence_of; k++) {
                    int rowIdx = i + k * iType;
                    int colIdx = j + k * jType;
                    if (isValidIndex(rowIdx) && isValidIndex(colIdx)) {
                        if (sBoard[rowIdx][colIdx].getPlayer() == sBoard[i][j].getPlayer()
                                && sBoard[rowIdx][colIdx].isHasSoldier()) {
                            count++;
                        } else {
                            count = 0;
                        }
                        if (count >= sequence_of) {
                        	gameUI.Main.GAME_STATUS = (gameUI.Main.currentPlayer == Player.BLACK) ? GameStatus.black_player_win : GameStatus.white_player_win;
                            return;
                        }
                    }
                }
            }
        }
    }

    /* Input: Index
     * Output: True if the index is valid, false otherwise
     * Description: Check if the specified index is within the valid board bounds
     */
    public boolean isValidIndex(int i) {
        return (i >= 0 && i <= 14);
    }

    /* Input: N/A
     * Output: N/A
     * Description: Start the game and handle player moves until there is a winner or a draw
     */
    public void startGame(int i,int j) {
        if(gameUI.Main.GAME_STATUS == GameStatus.in_progress) {
            //i = s.nextInt();
            //j = s.nextInt();

            try {
                if (isValidMove(i, j)) {
                    winnerCulc();
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } catch (InvalidMoveException e) {
                System.out.println(e.getMessage());
            }
        }

        switch (gameUI.Main.GAME_STATUS) {
            case black_player_win:
                System.out.println("Black player wins!");
                OuterFrame.endGameWrapper();
                break;
            case white_player_win:
                System.out.println("White player wins!");
                OuterFrame.endGameWrapper();
                break;
            case draw:
                System.out.println("It's a draw!");
                OuterFrame.endGameWrapper();
                break;
            case in_progress:
                System.out.println("Next Turn!");
                String player = (gameUI.Main.currentPlayer.getSymbol()=="B")?"White":"Black";
                int delayInMillis = 500;
                Timer timer = new Timer(delayInMillis, e -> {
                    // This code will be executed after the specified delay
                	JOptionPane.showMessageDialog(null, player+" Turn!", "Next Turn", JOptionPane.INFORMATION_MESSAGE);
                });

                timer.setRepeats(false);
                timer.start();
                break;
            default:
                System.out.println("Unexpected game status.");
                OuterFrame.endGameWrapper();
        }

    }
	public void makeMove() {
		switch (_currentState) {
		case NO_SEQ:
			// Attack mode: Try to find an empty cell to complete a sequence of 5 for the
			// bot.
			Move attackMove = _bot.findAttackMove();
			if (attackMove != null) {
				_board.placeMove(attackMove.getRow(), attackMove.getCol(), Soldier.BLACK); // Bot is always black
				//_bot.counter++;
				return;
			}
			// If no attacking move is found, switch to defending mode.
			//currentState = State.HAS_SEQ;
			break;
		case HAS_SEQ:
			// Defend mode: Try to find an empty cell to block opponent's sequence of 4.
			Move defendMove = _bot.findDefenseMove();
			if (defendMove != null) {
				_board.placeMove(defendMove.getRow(), defendMove.getCol(), Soldier.BLACK);
				//_bot.counter++;
				return;
			}
			// If no defending move is found, switch back to attacking mode.
			//currentState = State.NO_SEQ;
			break;
		}
	}

	public GomokuBot get_bot() {
		return _bot;
	}

	public void set_bot(GomokuBot _bot) {
		this._bot = _bot;
	}
	
}
