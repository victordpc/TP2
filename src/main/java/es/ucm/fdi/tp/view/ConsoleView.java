package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;

public class ConsoleView<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A> {

	private GameObservable<S, A> gameTable;

	public ConsoleView(GameObservable<S, A> gameTable) {
		this.gameTable = gameTable;
		gameTable.addObserver(this);
	}

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch (e.getType()) {
		case Start:
			System.out.println(e.toString() + System.getProperty("line.separator"));
			break;
		case Change:
			System.out.print("After action: " + System.getProperty("line.separator") + e.getState()
					+ System.getProperty("line.separator"));
			break;
		case Stop:
			System.out.println(e.toString() + System.getProperty("line.separator"));
			break;
		default:
			break;
		}
	}
}
