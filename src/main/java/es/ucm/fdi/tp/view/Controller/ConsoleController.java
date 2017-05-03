package es.ucm.fdi.tp.view.Controller;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>> implements Runnable {

	private List<GamePlayer> players;
	private GameTable<S, A> game;

	public ConsoleController(List<GamePlayer> players, GameTable<S, A> game) {
		this.players = players;
		this.game = game;
	}

	@Override
	public void run() {
		game.start();
		while (!game.getState().isFinished()) {
			// request move
			int playerTurnId = game.getState().getTurn();
			A action = players.get(playerTurnId).requestAction(game.getState());
			game.execute(action);
			int nextTurn = game.getState().getTurn();
		}
		System.out.print("Ganador: " + players.get(game.getState().getWinner()).getName()
				+ System.getProperty("line.separator"));
	}
}
