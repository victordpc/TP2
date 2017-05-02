package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.was.Coordinate;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WasView extends RectBoardView<WolfAndSheepState, WolfAndSheepAction> {

    private JComponent jBoard;
    private Coordinate originCoordinates;


    public WasView(GameController gameController, WolfAndSheepState state) {
        super(gameController, state);
        initUI();
    }

    private void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
            @Override
            protected void keyTyped(int keyCode) {
                WasView.this.keyTyped(keyCode);
            }

            @Override
            protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
                if (isEnabled()) {
                    WasView.this.mouseClicked(row, col, clickCount, mouseButton);
                }
            }

            @Override
            protected Shape getShape(int player) {
                return WasView.this.getShape(player);
            }

            @Override
            protected Color getColor(int player) {
                return gameController.getGamePlayers().get(player).getPlayerColor();
            }

            @Override
            protected Integer getPosition(int row, int col) {
                return WasView.this.getPosition(row, col);
            }

            @Override
            protected Color getBackground(int row, int col) {
                return WasView.this.getBackground(row, col);
            }

            @Override
            protected int getNumRows() {
                return WasView.this.getNumRows();
            }

            @Override
            protected int getNumCols() {
                return WasView.this.getNumCols();
            }
        };
        this.add(jBoard, BorderLayout.CENTER);
    }


    @Override
    public void update(WolfAndSheepState state) {
        this.state = state;
        jBoard.repaint();
    }

    @Override
    public void setMessageViewer(MessageViewer<WolfAndSheepState, WolfAndSheepAction> messageViewer) {}

    @Override
    public void setGameController(GameController<WolfAndSheepState, WolfAndSheepAction> gameCtrl) {}

    @Override
    protected int getNumCols() {
        return 8;
    }

    @Override
    protected int getNumRows() {
        return 8;
    }

//    @Override
//    protected Color getBackground(int row, int col) {
//        return super.getBackground(row, col);
//    }

    @Override
    protected Integer getPosition(int row, int col) {
        int shape = state.at(row, col);
        if (shape != -1) {
            return shape;
        } else {
            return null;
        }
    }

    @Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
        if (!state.isPositionEmpty(row, col)) {
            if (originCoordinates == null) {
                originCoordinates = new Coordinate(row, col);
            }
        }else if (originCoordinates != null) {
            WolfAndSheepAction newAction = state.isValidMoveForPlayerInCoordinate(originCoordinates, gameController.getPlayerId(), row, col);
            if (newAction != null) {
                gameController.makeManualMove(newAction);
                originCoordinates = null;
            }
        }
    }

    @Override
    protected void keyTyped(int keyCode) {
        if (originCoordinates != null && keyCode == KeyEvent.VK_ESCAPE) {
            originCoordinates = null;
        }
    }
}
