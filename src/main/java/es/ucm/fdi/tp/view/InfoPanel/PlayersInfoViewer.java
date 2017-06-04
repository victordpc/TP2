package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.Controller.GameController;

public abstract class PlayersInfoViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {
	private static final long serialVersionUID = 8207252960325652993L;
	protected List<PlayerInfoObserver> playerInfoObserverList = new ArrayList<>();

	public void addObserver(PlayerInfoObserver observer) {
		playerInfoObserverList.add(observer);
	}

	/**
	 * Used to consult the color assigned to a player
	 * 
	 * @param playerId
	 *            the id of the player
	 * @return the color assigned to the player.
	 */
	abstract public Color getPlayerColor(int playerId);

	protected void notifyObservers(int player, Color color) {
		for (PlayerInfoObserver observer : playerInfoObserverList) {
			observer.colorChanged(player, color);
		}
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {

	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer) {
	}

	abstract public void setNumberOfPlayer(int i);

	public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer) {
	}

	@Override
	public void update(S state) {

	}

	abstract public void updateColors();
}
