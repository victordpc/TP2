package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.PlayerType;

import java.awt.*;

public class UIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {

	private GameTable<S, A> gameTable;
	private PlayerType playerType;

	public UIController(GameTable<S, A> gameTable) {
		this.playerType = PlayerType.MANUAL;
		this.gameTable = gameTable;
	}

	@Override
	public void changePlayerMode(PlayerType playerMode) {
		this.playerType = playerMode;
	}

	@Override
	public PlayerType getPlayerMode() {
		return playerType;
	}

	@Override
	public void handleEvent(GameEvent<S, A> e) {
	}

	@Override
	public void makeManualMove(A a) {
		if (!gameTable.getState().isFinished()) {
			gameTable.execute(a);
		}
	}

	@Override
	public void makeRandomMove(GamePlayer jugador) {
		if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == jugador.getPlayerNumber()
				&& jugador instanceof RandomPlayer) {
			A action = jugador.requestAction(gameTable.getState());
			gameTable.execute(action);
		}
	}

	@Override
	public void makeSmartMove(GamePlayer jugador) {
        System.out.println("!gameTable.getState().isFinished() " +gameTable.getState().isFinished() + " -- " +
                gameTable.getState().getTurn() + " -- "+
                jugador.getPlayerNumber() + " -- "+
                (jugador.getClass())
        );
		if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == jugador.getPlayerNumber() && jugador instanceof ConcurrentAiPlayer) {

            A action = jugador.requestAction(gameTable.getState());
			int evaulation = ((ConcurrentAiPlayer) jugador).getEvaluationCount();
			gameTable.execute(action);
		}
	}

	@Override
	public void notifyInterfaceNeedBeUpdated() {
		gameTable.notifyInterfaceNeedBeUpdated();
	}

	@Override
	public void restartGame() {
		gameTable.restartGame();
	}

	@Override
	public void stopGame() {
		System.exit(0);
	}
}
