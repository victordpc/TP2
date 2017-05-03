package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;
import es.ucm.fdi.tp.was.Coordinate;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class WasView extends RectBoardView<WolfAndSheepState, WolfAndSheepAction> {

	private static final long serialVersionUID = -3136654431325082580L;
	private JComponent jBoard;
	private Coordinate originCoordinates;
	private PlayersInfoObserver playersInfoObserver;

    public WasView(GameController gameController, WolfAndSheepState state) {
        super(gameController, state);
        initUI();
    }

	private void initUI() {
		this.setLayout(new BorderLayout());
		jBoard = new JBoard() {
			/**
			 *
			 */
			private static final long serialVersionUID = -4597273473901577673L;

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
    protected void setPlayersInfoObserver(PlayersInfoObserver observer) {
        this.playersInfoObserver = observer;
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

    protected Color getBackground(int row, int col) {
        if ((originCoordinates != null) && (originCoordinates.isEqual(new Coordinate(row, col)))) {
            return Color.decode("#9E9E9E");
        }else {
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
        if (gameController.getPlayerId() == state.getTurn()) {
            if (!state.isPositionEmpty(row, col)) {
                if (originCoordinates == null) {
                    originCoordinates = new Coordinate(row, col);
                    playersInfoObserver.postMessage("Haz click en una celda destino");
                    jBoard.repaint();
                }
            } else if (originCoordinates != null) {
               if (state.at(originCoordinates.getX(), originCoordinates.getY()) == gameController.getPlayerId()) {
                   WolfAndSheepAction newAction = state.isValidMoveForPlayerInCoordinate(originCoordinates, gameController.getPlayerId(), row, col);
                   if (newAction != null) {
                       gameController.makeManualMove(newAction);
                       originCoordinates = null;
                   }
               } else {
                    playersInfoObserver.postMessage("Movimiento no válido");
                }
            }
        }
    }

    @Override
    protected void keyTyped(int keyCode) {
        if ((gameController.getPlayerMode() == PlayerType.MANUAL) && (originCoordinates != null) && (keyCode == KeyEvent.VK_ESCAPE)) {
            playersInfoObserver.postMessage("Selección cancelada, elige una nueva ficha de origen");
            originCoordinates = null;
            jBoard.repaint();
        }
    }
}
