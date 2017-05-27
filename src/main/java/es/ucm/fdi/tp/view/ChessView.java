package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessBoard;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChessView extends RectBoardView<ChessState, ChessAction> {

    public ChessView(GameController<ChessState, ChessAction> gameController, ChessState state) {
        super(gameController, state, true);
    }


    @Override
    protected void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
            /**
             *
             */
            private static final long serialVersionUID = -4597273473901577673L;

            @Override
            protected Color getBackground(int row, int col) {
                return ChessView.this.getBackground(row, col);
            }

            @Override
            protected Color getColor(int player) {
                return ChessView.this.getPlayerColor(player);
            }

            @Override
            protected int getNumCols() {
                return ChessView.this.getNumCols();
            }

            @Override
            protected int getNumRows() {
                return ChessView.this.getNumRows();
            }

            @Override
            protected Integer getPosition(int row, int col) {
                return ChessView.this.getPosition(row, col);
            }

            @Override
            protected Shape getShape(int player) {
                return ChessView.this.getShape(player);
            }

            @Override
            protected Image getImage(int row, int col) {
                return ChessView.this.getImage(row, col);
            }

            @Override
            protected void keyTyped(int keyCode) {
                ChessView.this.keyTyped(keyCode);
            }

            @Override
            protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
                if (isEnabled()) {
                    ChessView.this.mouseClicked(row, col, clickCount, mouseButton);
                }
            }
        };
        this.add(jBoard, BorderLayout.CENTER);
    }

    @Override
    public void setGameController(GameController<ChessState, ChessAction> gameCtrl) {
    }

    @Override
    public void setMessageViewer(MessageViewer<ChessState, ChessAction> messageViewer) {
    }

    @Override
    public void update(ChessState state) {
    }

    @Override
    protected Color getBackground(int row, int col) {
        return (row + col) % 2 == 0 ? Color.decode("#934d1a") : Color.decode("#d8b283");
    }

    @Override
    protected int getNumCols() {
        return 8;
    }

    @Override
    protected int getNumRows() {
        return 8;
    }

    @Override
    protected JBoard.Shape getShape(int player) {
        return JBoard.Shape.IMAGE;
    }

    @Override
    protected Image getImage(int row, int col) {
        byte piceByte = state.getBoard().get(row, col);
        String iconName = ChessBoard.Piece.iconName(piceByte);
        try {
            Image image = ImageIO.read( getClass().getResource("/chess/" +iconName));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Integer getPosition(int row, int col) {
        int shape = state.at(row, col);
        if (shape != ChessBoard.EMPTY) {
            return shape;
        } else {
            return null;
        }
    }

    @Override
    protected void keyTyped(int keyCode) {
    }

    @Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {

    }
}
