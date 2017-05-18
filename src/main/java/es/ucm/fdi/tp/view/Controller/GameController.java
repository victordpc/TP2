package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.PlayerType;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {
	void changePlayerMode(PlayerType playerMode);

	PlayerType getPlayerMode();

	void handleEvent(GameEvent<S, A> e);

	void makeManualMove(A a);

	void makeRandomMove(GamePlayer jugador);

	void makeSmartMove(GamePlayer jugador);

	void notifyInterfaceNeedBeUpdated();

	void restartGame();

	void stopGame();
}