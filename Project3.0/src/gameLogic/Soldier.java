package gameLogic;

public enum Soldier {
    BLACK("B"),
    WHITE("W"),
    EMPTY("E");

    private final String symbol;

    private Soldier(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
