package gameUI;

public class Settings {
    private String gameMode; // PvP or PvC
    private int musicVolume;
    private String playerColor; // Black or White
    private String difficulty;
    private String playerName;
	public Settings(String gameMode, int musicVolume, String playerColor, String difficulty, String playerName) {
		super();
		this.gameMode = gameMode;
		this.musicVolume = musicVolume;
		this.playerColor = playerColor;
		this.difficulty = difficulty;
		this.playerName = playerName;
	}
	public Settings() {
		this.gameMode = "PVC";
		this.musicVolume = 30;
		this.playerColor = "Black";
		this.difficulty = "Easy";
		this.playerName = " ";
	}
	public String getGameMode() {
		return gameMode;
	}
	public void setGameMode(String gameMode) {
		this.gameMode = gameMode;
	}
	public int getMusicVolume() {
		return musicVolume;
	}
	public void setMusicVolume(int musicVolume) {
		this.musicVolume = musicVolume;
	}
	public String getPlayerColor() {
		return playerColor;
	}
	public void setPlayerColor(String playerColor) {
		this.playerColor = playerColor;
	}
	public String getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	@Override
	public String toString() {
		return "Settings [gameMode=" + gameMode + ", musicVolume=" + musicVolume + ", playerColor=" + playerColor
				+ ", difficulty=" + difficulty + ", playerName=" + playerName + "]";
	}
	
}

