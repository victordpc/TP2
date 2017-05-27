package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.chess.ChessAction;
import es.ucm.fdi.tp.chess.ChessBoard;
import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.was.Coordinate;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class ChessView extends RectBoardView<ChessState, ChessAction> {

    private Coordinate originCoordinates;
    private java.util.List<Coordinate> validMoves;

    public ChessView(GameController<ChessState, ChessAction> gameController, ChessState state) {
        super(gameController, state, true);
    }

    @Override
    protected void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
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
    public void setGameController(GameController<ChessState, ChessAction> gameCtrl) {}

    @Override
    public void setMessageViewer(MessageViewer<ChessState, ChessAction> messageViewer) {}

    @Override
    protected Color getBackground(int row, int col) {
        if ((originCoordinates != null) && (originCoordinates.isEqual(new Coordinate(row, col)))) {
            return Color.decode("#9E9E9E");
        } else if (validMoves != null && validMoves.size() > 0 && validMoves.contains(new Coordinate(row, col))) {
            return Color.YELLOW;
        } else {
            return (row + col) % 2 == 0 ? Color.decode("#934d1a") : Color.decode("#d8b283");
        }
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
            Image image = ImageIO.read(getClass().getResource("/chess/" + iconName));
            return image;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Integer getPosition(int row, int col) {
        byte piceByte = state.getBoard().get(row, col);
        if (ChessBoard.empty(piceByte)) {
            return null;
        } else if (ChessBoard.black(piceByte)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected void keyTyped(int keyCode) {
        if ((gameController.getPlayerMode() == PlayerType.MANUAL) && (originCoordinates != null) && (keyCode == KeyEvent.VK_ESCAPE)) {
            playerInfoObserver.postMessage("Selección cancelada, elige una nueva ficha de origen");
            originCoordinates = null;
            jBoard.repaint();
            this.validMoves = null;
        }
    }

    @Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
        if (this.jugador.getPlayerNumber() == state.getTurn()) {
            if (originCoordinates == null) {
                originCoordinates = new Coordinate(row, col);
                playerInfoObserver.postMessage("Haz click en una celda destino");
                validMoves = state.getValidMoves(this.jugador.getPlayerNumber(), originCoordinates);
                jBoard.repaint();
            } else if (originCoordinates != null) {
                int originPlayerId = getPosition(originCoordinates.getX(), originCoordinates.getY());
                if (originPlayerId == jugador.getPlayerNumber()) {
                    ChessAction newAction = new ChessAction(jugador.getPlayerNumber(), originCoordinates.getX(), originCoordinates.getY(), row, col);
                    if (state.isValid(newAction)) {
                        gameController.makeManualMove(newAction);
                        originCoordinates = null;
                        validMoves = null;
                    }else {
                        playerInfoObserver.postMessage("Movimiento no válido");
                    }
                }
            }
        } else {
            playerInfoObserver.postMessage("No es tu turno");
        }
    }

    @Override
    protected void resetValidMoves() {
        validMoves = null;
        originCoordinates = null;
    }
}
