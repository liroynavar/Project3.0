package gameUI;

public enum Player {
    BLACK("B"),
    WHITE("W"),
    EMPTY("E");

    private final String symbol;

    private Player(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
    	return symbol;
    }
}
