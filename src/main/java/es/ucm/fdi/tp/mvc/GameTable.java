package es.ucm.fdi.tp.mvc;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * An event-driven game engine.
 * Keeps a list of players and a state, and notifies observers
 * of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

    // define fields here

    public GameTable(S initState) {
        // add code here
    }
    public void start() {
        // add code here
    }
    public void stop() {
        // add code here
    }
    public void execute(A action) {
        // add code here
    }
    public S getState() {
        // add code here
    } 

    public void addObserver(GameObserver<S, A> o) {
        // add code here
    }
    public void removeObserver(GameObserver<S, A> o) {
        // add code here
    }
}
