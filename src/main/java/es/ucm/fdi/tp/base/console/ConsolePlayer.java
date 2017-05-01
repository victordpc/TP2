package es.ucm.fdi.tp.base.console;

import java.awt.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * A console player. Can play any game, and offers console users a list of valid
 * actions to choose from.
 */
public class ConsolePlayer implements GamePlayer {

	private Scanner in;
	private String name;
	private int playerNumber;
	private Color playerColor = Color.WHITE;

	public ConsolePlayer(String name, Scanner in) {
		this.name = name;
		this.in = in;
		this.playerNumber = -1;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPlayerNumber() {
		return playerNumber;
	}

	@Override
	public void join(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	@Override
	public <S extends GameState<S, A>, A extends GameAction<S, A>> A requestAction(S state) {

		List<A> actions = new ArrayList<A>();
		actions.addAll(state.validActions(playerNumber));

		// displays a menu with all available actions + exit
		System.out.println("Available actions: \n" + "\t0 - exit game");
		int i = 0;
		for (GameAction<?, ?> a : actions) {
			System.out.println("\t" + (++i) + " - " + a);
		}

		// read the user input and carry out action
		A action = null;
		while (action == null) {
			System.out.print("Please type your move index: ");
			try {
				int choice = in.nextInt();
				if (choice == 0) {
					// user wants to exit
					System.out.println("Game exiting by request of " + name);
					System.exit(0);
				} else if (choice > 0 && choice <= actions.size()) {
					action = actions.get(choice - 1);
				} else {
					System.out.println("Out of range (0 to " + actions.size() + "); please try again.");
				}
			} catch (InputMismatchException ime) {
				System.out.println("Expected an integer; please try again");
			}
		}
		return action;
	}

	@Override
	public Color getPlayerColor() {
		return playerColor;
	}

	@Override
	public void setPlayerColor(Color newColor) {
		this.playerColor = newColor;
	}
}
