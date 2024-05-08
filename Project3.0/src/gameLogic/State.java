package gameLogic;

public enum State {
	NO_SEQ(true, "Attacking"), HAS_SEQ(false, "Defending");

	private final boolean explicit;
	private final String description;

	State(boolean explicit, String description) {
		this.explicit = explicit;
		this.description = description;
	}
}
