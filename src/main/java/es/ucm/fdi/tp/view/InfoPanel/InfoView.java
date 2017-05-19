package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.Controller.GameController;

public class InfoView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {
	private static final long serialVersionUID = -4962889679551454457L;
	private MessageViewer<S, A> messageViewer;
	private PlayersInfoObserver playersInfoObserver;
	private PlayersInfoViewer<S, A> playersInfoViewer;

	public InfoView(List<GamePlayer> gamePlayers, PlayersInfoObserver playersInfoObserver) {
		this.playersInfoObserver = playersInfoObserver;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initGUI(gamePlayers);
	}

	public void addContent(String message) {
		messageViewer.addContent(message);
	}

	public Color getColorPlayer(int jugador) {
		return playersInfoViewer.getPlayerColor(jugador);
	}

	private void initGUI(List<GamePlayer> gamePlayers) {
		messageViewer = new MessageViewerComponent<S, A>();
		playersInfoViewer = new PlayersInfoComponent<S, A>(gamePlayers, playersInfoObserver);
		add(messageViewer);
		add(playersInfoViewer);
	}

	public void repaintPlayersInfoViewer() {
		playersInfoViewer.updateColors();
	}

	public void setContent(String message) {
		messageViewer.setContent(message);
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> messageViewer) {
		this.messageViewer = messageViewer;
	}

	@Override
	public void update(S state) {
	}

}
