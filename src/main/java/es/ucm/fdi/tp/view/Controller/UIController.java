package es.ucm.fdi.tp.view.Controller;

import java.util.List;
import java.util.Random;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.AiAlgorithm;
import es.ucm.fdi.tp.base.player.MinMax;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.PlayerType;

public class UIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {

	private GameTable<S, A> gameTable;
	private Random random = new Random();
	protected AiAlgorithm algorithm;
	private PlayerType playerType;

	public UIController(GameTable<S, A> gameTable) {
		this.playerType = PlayerType.MANUAL;
		this.gameTable = gameTable;
		this.algorithm = new MinMax(5);
	}

	@Override
	public void makeManualMove(A a) {
		if (!gameTable.getState().isFinished()) {
			gameTable.execute(a);
		}
	}

	@Override
	public void makeRandomMove(GamePlayer jugador) {
//		if (gameTable.getState().isFinished()) {
//		} else if (gameTable.getState().getTurn() != jugador.getPlayerNumber()) {
//			gameTable.noEsTuTurno(jugador.getPlayerNumber());
//		} else 
			if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == jugador.getPlayerNumber()) {
			List<A> valid = gameTable.getState().validActions(jugador.getPlayerNumber());
			gameTable.execute(valid.get(random.nextInt(valid.size())));
		}
	}

	@Override
	public void makeSmartMove(GamePlayer jugador) {
//		if (gameTable.getState().isFinished()) {
//		} else if (gameTable.getState().getTurn() != jugador.getPlayerNumber()) {
//			gameTable.noEsTuTurno(jugador.getPlayerNumber());
//		} else 
			if (!gameTable.getState().isFinished() && gameTable.getState().getTurn() == jugador.getPlayerNumber()) {
			A action = algorithm.chooseAction(jugador.getPlayerNumber(), gameTable.getState());
			gameTable.execute(action);
		}
	}

	@Override
	public void restartGame() {
		gameTable.restartGame();
	}

	@Override
	public void stopGame() {
		System.exit(0);
	}

	@Override
	public void handleEvent(GameEvent<S, A> e) {
	}

	@Override
	public void notifyInterfaceNeedBeUpdated() {
		gameTable.notifyInterfaceNeedBeUpdated();
	}

	@Override
	public PlayerType getPlayerMode() {
		return playerType;
	}

	@Override
	public void changePlayerMode(PlayerType playerMode) {
		this.playerType = playerMode;
	}
}
