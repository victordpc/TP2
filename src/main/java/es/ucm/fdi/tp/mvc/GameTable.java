package es.ucm.fdi.tp.mvc;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine. Keeps a list of players and a state, and
 * notifies observers of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	private S initState;
	private S currentState;
	private List<GameObserver<S, A>> observers = new ArrayList<>();
	private List<GamePlayer> gamePlayers;

	public GameTable(S initState) {
		this.initState = initState;
		this.currentState = initState;
		this.observers = new ArrayList<>();
	}

	public void start() {
		currentState = initState;
		notifyGameHasStarted();
		notifyInfo();
	}

	public void stop() {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, null,
				"El juego ha parado");
		for (GameObserver<S, A> gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	public List<GamePlayer> getGamePlayers() {
		return gamePlayers;
	}

	public void setGamePlayers(List<GamePlayer> gamePlayers) {
		this.gamePlayers = gamePlayers;
	}

	// NOTIFICAR DE ERRORES,
	public void execute(A action) {
		if (action.getPlayerNumber() == currentState.getTurn()) {
			// apply move
			currentState = action.applyTo(currentState);
			if (currentState.isFinished()) {
				notifyGameHasChanged();
				notifyGameHasFinished();
			} else {
				notifyGameHasChanged();
				notifyInfo();
			}
		} else {
			notifyErrorHasOcurred("No es tu turno jugador " + action.getPlayerNumber());
		}
	}

	public void restartGame() {
		currentState = initState;
		notifyGameHasStarted();
	}

	public S getState() {
		return currentState;
	}

	@Override
	public void addObserver(GameObserver<S, A> o) {
		observers.add(o);
	}

	@Override
	public void removeObserver(GameObserver<S, A> o) {
		observers.remove(o);
	}

	public void notifyInterfaceNeedBeUpdated() {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Change, null, currentState, null,
				"El juego ha cambiado");
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	private void notifyGameHasStarted() {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Start, null, currentState, null,
				"¡La partida ha empezado!");
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	private void notifyGameHasChanged() {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Change, null, currentState, null,
				"El juego ha cambiado");
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	private void notifyInfo() {
		GameEvent<S, A> event = new GameEvent<>(EventType.Info, null, currentState, null, null);
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	private void notifyErrorHasOcurred(String message) {
		GameEvent<S, A> event = new GameEvent<>(EventType.Error, null, currentState, new GameError(message), null);
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}

	private void notifyGameHasFinished() {
		GameEvent<S, A> event;
		if (currentState.getWinner() == -1) {
			event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, null,
					"El juego ha terminado, Empate");
		} else {
			GamePlayer winner = getGamePlayers().get(currentState.getWinner());
			event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, null,
					"El juego ha terminado, Ganador " + winner.getName());
		}
		for (GameObserver gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}
}
