package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import java.util.ArrayList;
import java.util.List;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    private S initState;
    private S currentState;
    private List<GameObserver<S, A>> observers = new ArrayList<>();

    public GameTable(S initState) {
        this.initState = initState;
        this.currentState = initState;
        this.observers = new ArrayList<>();
    }

    public void start() {
        currentState = initState;
        notifyGameHasStarted();
    }

    public void stop() {
        GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, null, "El juego ha parado");
        for (GameObserver<S, A> gameObserver : observers) {
            gameObserver.notifyEvent(event);
        }
    }

    public void execute(A action) {
        // apply move
        currentState = action.applyTo(currentState);
        notifyGameHasChanged();
        if (currentState.isFinished()) {
            notifyGameHasFinished();
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

    private void notifyGameHasStarted() {
        GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Start, null, currentState, null, "¡¡¡¡¡La partida ha empezado!!!!!!");
        for (GameObserver gameObserver : observers) {
            gameObserver.notifyEvent(event);
        }
    }

    private void notifyGameHasChanged() {
        GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Change, null, currentState, null, "El juego ha cambiado");
        for (GameObserver gameObserver : observers) {
            gameObserver.notifyEvent(event);
        }
    }

    private void notifyGameHasFinished() {
        GameEvent<S, A> event = new GameEvent<>(GameEvent.EventType.Stop, null, currentState, null, "El juego ha terminado ");
        for (GameObserver gameObserver : observers) {
            gameObserver.notifyEvent(event);
        }
    }
}