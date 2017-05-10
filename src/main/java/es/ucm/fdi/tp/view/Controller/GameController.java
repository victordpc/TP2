package es.ucm.fdi.tp.view.Controller;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.PlayerType;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {
	void makeManualMove(A a);

	void makeRandomMove(GamePlayer jugador);

	void makeSmartMove(GamePlayer jugador);

	void restartGame();

	void stopGame();

	void handleEvent(GameEvent<S, A> e);

	void notifyInterfaceNeedBeUpdated();

	PlayerType getPlayerMode();

	void changePlayerMode(PlayerType playerMode);
}