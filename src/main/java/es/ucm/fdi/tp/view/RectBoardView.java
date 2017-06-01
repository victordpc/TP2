package es.ucm.fdi.tp.view;

import java.awt.*;
import java.util.List;

import javax.swing.JComponent;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;

public abstract class RectBoardView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {

    /**
     *
     */
    private static final long serialVersionUID = 5811685384437753352L;

    /**
     * Controlador del juego.
     */
    protected GameController<S, A> gameController;
    /**
     *UI tablero del juego.
     */
    protected JComponent jBoard;
    /**
     * Jugador de la ventana.
     */
    protected GamePlayer jugador;
    /**
     * Listado de jugadores del juego.
     */
    protected List<GamePlayer> listaJugadores;
    /**
     * Estado/modelo del juego.
     */
    protected S state;
    /**
     * Observador para mostrar info sobre las jugadas.
     */
    protected PlayerInfoObserver playerInfoObserver;
    /**
     * Booleano que índica si tiene imágenes este juego.
     */
    protected Boolean hasImages;

    public RectBoardView(GameController<S, A> gameController, S state, Boolean hasImages) {
        this.gameController = gameController;
        this.state = state;
        this.hasImages = hasImages;
        initUI();
    }

    protected Color getBackground(int row, int col) {
        return (row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
    }

    protected abstract int getNumCols();

    protected abstract int getNumRows();

    protected Color getPlayerColor(int id) {
        return this.playerInfoObserver.getColorPlayer(id);
    }

    protected abstract Integer getPosition(int row, int col);

    protected int getSepPixels() {
        return 1;
    }

    protected JBoard.Shape getShape(int player) {
        return JBoard.Shape.CIRCLE;
    }

    protected Image getImage(int row, int col) {
        return null;
    }

    protected abstract void initUI();

    protected abstract void keyTyped(int keyCode);

    protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

    protected void resetValidMoves() {}

    public void setListPlayers(List<GamePlayer> jugadores, GamePlayer jugadorActual) {
        this.listaJugadores = jugadores;
        this.jugador = jugadorActual;
    }

    protected void setPlayerInfoObserver(PlayerInfoObserver observer) {
        this.playerInfoObserver = observer;
    }

    @Override
    public void update(S state) {
        this.state = state;
        jBoard.repaint();
    }

}
