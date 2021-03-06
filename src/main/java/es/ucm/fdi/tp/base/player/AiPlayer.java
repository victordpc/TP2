package es.ucm.fdi.tp.base.player;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;

/**
 * A player that can play any game.
 */
public class AiPlayer implements GamePlayer {

	protected AiAlgorithm algorithm;

	protected String name;
	protected int playerNumber;

	public AiPlayer(String name, AiAlgorithm algorithm) {
		this.name = name;
		this.algorithm = algorithm;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getPlayerNumber() {
		return playerNumber;
	}

	@Override
	public void join(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	@Override
	public <S extends GameState<S, A>, A extends GameAction<S, A>> A requestAction(S state) {
		return algorithm.chooseAction(playerNumber, state);
	}
}
