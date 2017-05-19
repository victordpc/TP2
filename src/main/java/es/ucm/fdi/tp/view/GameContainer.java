package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.BoxLayout;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanel;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanelObservable;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.InfoView;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView
		implements GameObserver<S, A>, PlayersInfoObserver, ControlPanelObservable {

	private GameController gameController;
	private InfoView infoView;
	private GUIView<S, A> rectBoardView;
	GamePlayer gamePlayer;
	List<GamePlayer> listaJugadores;

	public GameContainer(int idPlayer, GUIView<S, A> gameView, GameController gameController, GameObservable<S, A> game,
			List<GamePlayer> jugadores) {
		this.listaJugadores = jugadores;
		this.gamePlayer = jugadores.get(idPlayer);

		this.setTitle("Jugador " + this.gamePlayer.getName());
		this.setLayout(new BorderLayout(5, 5));
		this.rectBoardView = gameView;
		((RectBoardView) rectBoardView).setListPlayers(this.listaJugadores, this.gamePlayer);
		((RectBoardView) rectBoardView).setPlayersInfoObserver(this);
		this.gameController = gameController;
		game.addObserver(this);
		initGUI();
	}

	@Override
	public void colorChanged(int player, Color color) {
		gamePlayer.setPlayerColor(color);
		gameController.notifyInterfaceNeedBeUpdated();
	}

	public void initGUI() {
		ControlPanel controlPanel = new ControlPanel(gameController, this.gamePlayer.getPlayerNumber());
		controlPanel.setBackground(Color.decode("#eeeeee"));
		controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
		controlPanel.addControlPanelObserver(this);
		this.add(controlPanel, BorderLayout.NORTH);
		this.add(rectBoardView, BorderLayout.CENTER);

		infoView = new InfoView(listaJugadores, this);
		infoView.setOpaque(true);
		this.add(infoView, BorderLayout.EAST);
	}

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch (e.getType()) {
		case Start:
			infoView.setContent(e.toString());
			rectBoardView.update(e.getState());
			infoView.repaintPlayersInfoViewer();
			break;
		case Change:
			rectBoardView.update(e.getState());
			infoView.repaintPlayersInfoViewer();
			if (e.getState().getTurn() == this.gamePlayer.getPlayerNumber()) {
				if (gameController.getPlayerMode() == PlayerType.RANDOM) {
					gameController.makeRandomMove(gamePlayer);
				} else if (gameController.getPlayerMode() == PlayerType.SMART) {
					gameController.makeSmartMove(gamePlayer);
				}
			}
			break;
		case Info:
			if (e.getState().getTurn() == this.gamePlayer.getPlayerNumber()) {
				infoView.addContent("Tu turno");
			} else {
				infoView.addContent("Turno del jugador " + e.getState().getTurn());
			}
			break;
		case Error:
			infoView.addContent(e.getError().getMessage());
			break;
		case Stop:
			infoView.addContent(e.toString());
			break;
		default:
			break;
		}
	}

	@Override
	public void playerModeHasChange(PlayerType newPlayerMode) {
		gameController.changePlayerMode(newPlayerMode);
		switch (newPlayerMode) {
		case MANUAL:
			rectBoardView.setEnabled(true);
			break;
		case SMART:
		case RANDOM:
			rectBoardView.setEnabled(false);
			break;
		default:
			break;
		}
	}

	@Override
	public void postMessage(String message) {
		infoView.addContent(message);
	}

	@Override
	public void setGameController(GameController gameCtrl) {
	}

	@Override
	public void setMessageViewer(MessageViewer messageViewer) {
		infoView.setMessageViewer(messageViewer);
	}

	@Override
	public void update(GameState state) {
	}
}
