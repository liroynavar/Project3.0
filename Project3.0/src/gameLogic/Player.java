package gameLogic;

public class Player {
	private Soldier _player;
	private GameStatus _gameStatus;
	
	public Player(Soldier p) {
		_player = p;
		
	}

	public Soldier get_player() {
		return _player;
	}

	public void set_player(Soldier _player) {
		this._player = _player;
	}

	public GameStatus get_gameStatus() {
		return _gameStatus;
	}

	public void set_gameStatus(GameStatus _gameStatus) {
		this._gameStatus = _gameStatus;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof Player))
		return false;
		else{
			Player s = (Player)obj;
			return s._player.equals(_player);
		}
	}
}
	
