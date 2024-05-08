package gameLogic;

public class Square {
    private Soldier _Soldier;
    private boolean _hasPlayer;

    public Square() {
        _Soldier = Soldier.EMPTY;
        _hasPlayer = false;
    }

    public Square(Soldier s) {
        _Soldier = s;
        _hasPlayer = false;
    }

    /* Input: N/A
     * Output: The current soldier on the square
     * Description: Get the current soldier on the square
     */
    public Soldier get_Soldier() {
        return _Soldier;
    }

    /* Input: N/A
     * Output: True if the square has a player, false otherwise
     * Description: Check if the square has a player
     */
    public boolean is_hasPlayer() {
        return _hasPlayer;
    }

    /* Input: N/A
     * Output: N/A
     * Description: Clear the square from soldiers and mark it as not having a player
     */
    public void clearSquare() {
        _Soldier = Soldier.EMPTY;
        _hasPlayer = false;
    }

    /* Input: Soldier type
     * Output: N/A
     * Description: Update the square with the specified soldier type and mark it as having a player
     */
    public void updateSquare(Soldier s) {
        _Soldier = s;
        _hasPlayer = true;
    }

    @Override
    public String toString() {
        return (_Soldier == Soldier.BLACK) ? "B" : (_Soldier == Soldier.WHITE) ? "W" : "E";
    }
}
