package es.ucm.fdi.tp.mvc;

import java.util.LinkedList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent.EventType;

/**
 * An event-driven game engine. Keeps a list of players and a state, and
 * notifies observers of any changes to the game.
 */
public class GameTable<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObservable<S, A> {

	// define fields here
	List<GameObserver<S, A>> observerList = new LinkedList<GameObserver<S, A>>();
	List<GamePlayer> playerList = new LinkedList<GamePlayer>();
	S state;

	public GameTable(S initState) {
		// add code here
		state = initState;
		observerList = new java.util.LinkedList<GameObserver<S, A>>();
	}

	public void start() {
		// add code here
		for (GameObserver<S, A> gO : observerList) {
			gO.notifyEvent(new GameEvent<S, A>(EventType.Start, null, null, null, "Game started"));
		}
	}

	public void stop() {
		// add code here
		for (GameObserver<S, A> gO : observerList) {
			gO.notifyEvent(new GameEvent<S, A>(EventType.Stop, null, state, null, "Game stoped"));
		}
	}

	public void execute(A action) {
		// add code here
		
		
		
		for (GameObserver<S, A> gO : observerList) {
			gO.notifyEvent(
					new GameEvent<S, A>(EventType.Change, action, state, null, "Action executed " + action.toString()));
		}
	}

	public S getState() {
		// add code here
		return state;
	}

	public void addObserver(GameObserver<S, A> o) {
		// add code here
		if (!observerList.contains(o))
			observerList.add(o);
	}

	public void removeObserver(GameObserver<S, A> o) {
		// add code here
		if (observerList.contains(o))
			observerList.remove(o);
	}
}
