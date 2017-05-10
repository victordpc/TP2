package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JComponent;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;

public abstract class RectBoardView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811685384437753352L;
	protected GameController<S, A> gameController;
	protected JComponent jBoard;
	protected S state;
	protected List<GamePlayer> listaJugadores;
	protected GamePlayer jugador;

	public RectBoardView(GameController<S, A> gameController, S state) {
		this.gameController = gameController;
		this.state = state;
		initUI();
	}

	protected Color getBackground(int row, int col) {
		return (row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
	}

	protected abstract int getNumCols();

	protected abstract int getNumRows();

	protected Color getPlayerColor(int id) {
		return this.listaJugadores.get(id).getPlayerColor();
	}

	protected abstract Integer getPosition(int row, int col);

	protected int getSepPixels() {
		return 1;
	}

	protected JBoard.Shape getShape(int player) {
		return JBoard.Shape.CIRCLE;
	}

	protected abstract void initUI();

	protected abstract void keyTyped(int keyCode);

	protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

	protected abstract void setPlayersInfoObserver(PlayersInfoObserver observer);

	public void setListPlayers(List<GamePlayer> jugadores,GamePlayer jugadorActual) {
		this.listaJugadores = jugadores;
		this.jugador=jugadorActual;
	}
}
