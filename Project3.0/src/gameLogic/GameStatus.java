package gameLogic;

public enum GameStatus {
	white_player_win("White Win!"),
	black_player_win("Black Win!"),
	draw("It's A Draw!"),
	in_progress(" ");
    private final String symbol;

    private GameStatus(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}

