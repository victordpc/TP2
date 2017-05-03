package es.ucm.fdi.tp.view.Controller;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.PlayerType;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {
	void makeManualMove(A a);

	void makeRandomMove();

	void makeSmartMove();

	void restartGame();

	void stopGame();

	void handleEvent(GameEvent<S, A> e);

	// public void changePlayerMode(PlayerMode p);
	int getPlayerId();

	List<GamePlayer> getGamePlayers();

	void notifyInterfaceNeedBeUpdated();

	PlayerType getPlayerMode();

	void changePlayerMode(PlayerType playerMode);
}