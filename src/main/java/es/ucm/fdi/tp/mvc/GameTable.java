package es.ucm.fdi.tp.mvc;

import java.util.LinkedList;
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
	private List<GameObserver<S, A>> observers;
	private boolean iniciado;

	public GameTable(S initState) {
		this.initState = initState;
		this.currentState = initState;
		this.observers = new LinkedList<>();
		this.iniciado = false;
	}

	public void start() {
		currentState = initState;
		this.iniciado = true;
		notifyGameHasStarted();
	}

	public void stop() {
		this.iniciado = false;
		notifyGameHasStoped();
	}

	public void execute(A action) {
		// apply move
		if (iniciado == false) {
			notifyGameError("El juego no estaba arrancado");
		} else {
			if (!currentState.isFinished()) {
				try {
					currentState = action.applyTo(currentState);
					notifyGameHasChanged(action);
					if (currentState.isFinished()) {
				iniciado =false;
						notifyGameHasFinished(action);
					}
				} catch (Exception e) {
					notifyGameError("El juego ha dado un error");
				}
			} else {
				notifyGameError("El juego ya estaba parado");
			}
		}
	}

	public S getState() {
		return currentState;
	}

	public void addObserver(GameObserver<S, A> o) {
		observers.add(o);
	}

	public void removeObserver(GameObserver<S, A> o) {
		observers.remove(o);
	}

	private void notifyGameError(String mensaje) {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, new GameError(mensaje),
				"El juego ha dado un error");
		notifyAll(event);
	}

	private void notifyGameHasStarted() {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Start, null, currentState, null,
				"¡¡¡¡¡La partida ha empezado!!!!!!");
		notifyAll(event);
	}

	private void notifyGameHasChanged(A action) {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Change, action, currentState, null,
				"El juego ha cambiado");
		notifyAll(event);
	}

	private void notifyGameHasFinished(A action) {
		GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Stop, action, currentState, null,
				"El juego ha terminado ");
		notifyAll(event);
	}

	private void notifyGameHasStoped() {
		GameEvent<S, A> event;
		if (currentState.isFinished())
			event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState,
					new GameError("El juego ya estaba parado"), "El juego ha parado");
		else
			event = new GameEvent<>(GameEvent.EventType.Error, null, currentState, null, "El juego ha parado");
		notifyAll(event);
	}

	private void notifyAll(GameEvent<S, A> event) {
		for (GameObserver<S, A> gameObserver : observers) {
			gameObserver.notifyEvent(event);
		}
	}
}
