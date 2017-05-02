package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

import java.util.List;
import java.util.Observer;

public class GUIController<S extends GameState<S, A>, A extends GameAction<S, A>> extends GameController<S, A> {
	private SmartPlayer smartPlayer = null;
	private RandomPlayer randPlayer = null;
	private GamePlayer jugador;
	private GameTable<S, A> game;

	public GUIController(GamePlayer players, GameTable<S, A> game) {
		this.jugador = players;
		this.game = game;
		this.smartPlayer = new SmartPlayer("dummy", 5);
		this.randPlayer = new RandomPlayer("dummy");
	}

	@Override
	public void run() {
		game.start();
	}

	public GamePlayer getPlayer() {
		return jugador;
	}

	public void makeMove(A action) {
		game.execute(action);
	}

	public void startGame() {
		game.start();
	}

	public void stopGame() {
		game.stop();
	}

	public void doSmartMove(S currentState) {
		A action = smartPlayer.requestAction(currentState);
		this.makeMove(action);
	}

	public void doRandMove(S gameState) {
		A action = randPlayer.requestAction(gameState);
		this.makeMove(action);
	}

	public void addModelObserver(GameObserver<S, A> observador) {
		game.addObserver(observador);
	}

	public void removeModelObserver(GameObserver<S, A> observador) {
		game.removeObserver(observador);
	}

}