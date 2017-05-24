package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;

import javax.swing.*;

public class UIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {

	private GameTable<S, A> gameTable;
	private PlayerType playerType;
    private PlayerInfoObserver playerInfoObserver;

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
    public void setPlayerInfoObserver(PlayerInfoObserver playerInfoObserver) {
	    this.playerInfoObserver = playerInfoObserver;
    }

    @Override
	public void makeManualMove(A a) {
        if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == a.getPlayerNumber()) {
			gameTable.execute(a);
		}
	}

	@Override
	public void makeRandomMove(GamePlayer jugador) {
		if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == jugador.getPlayerNumber() && jugador instanceof RandomPlayer) {
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
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    int evaulation = ((ConcurrentAiPlayer) jugador).getEvaluationCount();
                    gameTable.execute(action);
                }
            });
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
