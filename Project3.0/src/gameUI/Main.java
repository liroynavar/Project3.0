package gameUI;

public class Main {
    public static gameLogic.GameStatus GAME_STATUS = gameLogic.GameStatus.in_progress;
    public static Player currentPlayer = Player.WHITE;

    public static Player togglePlayer() {
        currentPlayer = (currentPlayer == Player.BLACK) ? Player.WHITE : Player.BLACK;
        OuterFrame.updateButton();
        OuterFrame.turnTimerRestart();
        return currentPlayer;
    }

    public static void main(String[] args) {
        StartPageFrame startPage = new StartPageFrame(new Settings());
        EndPageFrame e = new EndPageFrame();
    }
}
