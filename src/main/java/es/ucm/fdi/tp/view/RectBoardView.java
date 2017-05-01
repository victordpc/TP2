package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.Controller.GameController;

import javax.swing.*;
import java.awt.*;

public abstract class RectBoardView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> {

    private JComponent jBoard;
    protected GameController<S, A> gameController;
    protected S state;

    public RectBoardView(GameController<S, A> gameController, S state) {
        this.gameController = gameController;
        this.state = state;
        initUI();
    }

    private void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
            @Override
            protected void keyTyped(int keyCode) {

            }

            @Override
            protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {

            }

            @Override
            protected Shape getShape(int player) {
                return this.getShape(player);
            }

            @Override
            protected Color getColor(int player) {
                return this.getColor(player);
            }

            @Override
            protected Integer getPosition(int row, int col) {
                return this.getPosition(row, col);
            }

            @Override
            protected Color getBackground(int row, int col) {
                return this.getBackground(row, col);
            }

            @Override
            protected int getNumRows() {
                return 3;
            }

            @Override
            protected int getNumCols() {
                return 3;
            }
        };
        this.add(jBoard, BorderLayout.CENTER);
    }


    protected JBoard.Shape getShape(int player) {
        return JBoard.Shape.CIRCLE;
    }

    protected Color getBackground(int row, int col) {
        return (row + col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
    }

    protected abstract int getNumCols();

    protected abstract int getNumRows();

    protected abstract Integer getPosition(int row, int col);

    protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

    protected abstract void keyTyped(int keyCode);

    protected int getSepPixels() {
        return 1;
    }

    protected Color getPlayerColor(int id) {
        return gameController.getGamePlayers().get(id).getPlayerColor();
    }
}
