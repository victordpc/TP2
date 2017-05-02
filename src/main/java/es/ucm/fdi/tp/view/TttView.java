package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;

import javax.swing.*;
import java.awt.*;

public class TttView extends RectBoardView<TttState, TttAction> {

    private JComponent jBoard;

    public TttView(GameController gameController, TttState state) {
        super(gameController, state);
        initUI();
    }

    private void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
            @Override
            protected void keyTyped(int keyCode) {}

            @Override
            protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
                if (isEnabled()) {
                    TttView.this.mouseClicked(row, col, clickCount, mouseButton);
                }
            }

            @Override
            protected Shape getShape(int player) {
                return TttView.this.getShape(player);
            }

            @Override
            protected Color getColor(int player) {
                return gameController.getGamePlayers().get(player).getPlayerColor();
            }

            @Override
            protected Integer getPosition(int row, int col) {
                return TttView.this.getPosition(row, col);
            }

            @Override
            protected Color getBackground(int row, int col) {
                return TttView.this.getBackground(row, col);
            }

            @Override
            protected int getNumRows() {
                return TttView.this.getNumRows();
            }

            @Override
            protected int getNumCols() {
                return TttView.this.getNumCols();
            }
        };
        this.add(jBoard, BorderLayout.CENTER);
    }

    @Override
    protected void setPlayersInfoObserver(PlayersInfoObserver observer) {

    }

    @Override
    public void setEnabled(boolean enabled) {
        jBoard.setEnabled(enabled);
    }

    @Override
    public void update(TttState state) {
        this.state = state;
        jBoard.repaint();
    }

    @Override
    public void setMessageViewer(MessageViewer<TttState, TttAction> infoViewer) {}

    @Override
    public void setGameController(GameController<TttState, TttAction> gameCtrl) {}

    @Override
    protected int getNumCols() {
        return 3;
    }

    @Override
    protected int getNumRows() {
        return 3;
    }

    @Override
    protected Integer getPosition(int row, int col) {
        int shape = state.at(row, col);
        if (shape != -1) {
            return shape;
        } else {
            return null;
        }
    }

    ///QUE SE ILUMINEN LAS CASILLAS VÁLIDAS.
    @Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
        TttAction action = new TttAction(gameController.getPlayerId(), row, col);
        gameController.makeManualMove(action);
    }

    @Override
    protected void keyTyped(int keyCode) {}
}
