package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {
	void changePlayerMode(PlayerType playerMode);

	PlayerType getPlayerMode();

	void makeManualMove(A a);

	void makeRandomMove(RandomPlayer jugador);

	void makeSmartMove(SmartPlayer jugador);

	void notifyInterfaceNeedBeUpdated();

	void restartGame();

	void stopGame();

	void setPlayerInfoObserver(PlayerInfoObserver playerInfoObserver);

}