package gameLogic;

import java.util.*;

import gameLogic.GameStatus;

public class FiniteStateMachine {
	private enum State {
		NoSeq(true, "Attacking"), HasSeq(false, "Defending"), Exiting(false, "Quitting");

		final List<String> inputs;
		final static Map<String, State> map = new HashMap<>();
		final boolean explicit;

		static {
			for (State state : State.values()) {
				for (String input : state.inputs) {
					map.put(input, state);
				}
			}
		}

		State(boolean explicit, String... inputs) {
			this.inputs = Arrays.asList(inputs);
			this.explicit = explicit;
		}

		State nextState(String input) {
			return map.getOrDefault(input, this);
		}
	}

	public void makeMove(int i, int j, String strat) {
		switch (strat) {
		case "Attack":

			break;
		case "Defend":

			break;

		default:
			break;
		}

	}

	

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		State state = State.NoSeq;

		while (state != State.Exiting) {
			System.out.println("Valid inputs: " + state.inputs);

			if (state.explicit) {
				System.out.print("> ");
				String userInput = sc.nextLine().trim();
				state = state.nextState(userInput);
			} else {
				state = state.nextState(state.inputs.get(0));
			}
		}
		sc.close();
	}
}