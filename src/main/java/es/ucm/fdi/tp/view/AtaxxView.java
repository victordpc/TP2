//package es.ucm.fdi.tp.view;
//
//import java.awt.BorderLayout;
//import java.awt.Color;
//import java.awt.Image;
//import java.awt.event.KeyEvent;
//import java.util.List;
//import java.util.Observable;
//import java.util.Observer;
//
//import es.ucm.fdi.tp.extra.jboard.JBoard;
//import es.ucm.fdi.tp.mvc.PlayerType;
//import es.ucm.fdi.tp.ataxx.AtaxxAction;
//import es.ucm.fdi.tp.ataxx.AtaxxState;
//import es.ucm.fdi.tp.view.Controller.GameController;
//import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
//import es.ucm.fdi.tp.was.Coordinate;
//import es.ucm.fdi.tp.was.WolfAndSheepAction;
//
//public class AtaxxView extends RectBoardView<AtaxxState, AtaxxAction> implements Observer {
//
//	private static final long serialVersionUID = -3136654431325082580L;
//	private Coordinate originCoordinates;
//	private List<Coordinate> validMoves;
//	private int dimension;
//
//	public AtaxxView(GameController<AtaxxState, AtaxxAction> gameController, AtaxxState state) {
//		super(gameController, state, false);
//		this.dimension = state.getDim();
//	}
//
//	@Override
//	protected Color getBackground(int row, int col) {
//		if ((originCoordinates != null) && (originCoordinates.isEqual(new Coordinate(row, col)))) {
//			return Color.decode("#9E9E9E");
//		} else if (validMoves != null && validMoves.size() > 0 && validMoves.contains(new Coordinate(row, col))) {
//			return Color.YELLOW;
//		} else {
//			return (row + col) % 2 == 0 ? Color.decode("#934d1a") : Color.decode("#d8b283");
//		}
//	}
//
//	@Override
//	protected int getNumCols() {
//		return dimension;
//	}
//
//	@Override
//	protected int getNumRows() {
//		return dimension;
//	}
//
//	@Override
//	protected Integer getPosition(int row, int col) {
//		int shape = state.at(row, col);
//		if (shape != -1) {
//			return shape;
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	protected void initUI() {
//		this.setLayout(new BorderLayout());
//		jBoard = new JBoard() {
//			private static final long serialVersionUID = -4597273473901577673L;
//
//			@Override
//			protected Color getBackground(int row, int col) {
//				return AtaxxView.this.getBackground(row, col);
//			}
//
//			@Override
//			protected Color getColor(int player) {
//				return AtaxxView.this.getPlayerColor(player);
//			}
//
//			@Override
//			protected int getNumCols() {
//				return AtaxxView.this.getNumCols();
//			}
//
//			@Override
//			protected int getNumRows() {
//				return AtaxxView.this.getNumRows();
//			}
//
//			@Override
//			protected Integer getPosition(int row, int col) {
//				return AtaxxView.this.getPosition(row, col);
//			}
//
//			@Override
//			protected Shape getShape(int player) {
//				return AtaxxView.this.getShape(player);
//			}
//
//			@Override
//			protected Image getImage(int row, int col) {
//				return null;
//			}
//
//			@Override
//			protected void keyTyped(int keyCode) {
//				AtaxxView.this.keyTyped(keyCode);
//			}
//
//			@Override
//			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
//				if (isEnabled()) {
//					AtaxxView.this.mouseClicked(row, col, clickCount, mouseButton);
//				}
//			}
//		};
//		this.add(jBoard, BorderLayout.CENTER);
//	}
//
//	@Override
//	protected void keyTyped(int keyCode) {
//		if ((gameController.getPlayerMode() == PlayerType.MANUAL) && (originCoordinates != null)
//		/* && (keyCode == KeyEvent.VK_ESCAPE) */) {
//			playerInfoObserver.postMessage("Selección cancelada, elige una nueva ficha de origen");
//			originCoordinates = null;
//			jBoard.repaint();
//			this.validMoves = null;
//		}
//	}
//
//	/**
//	 * Método que reacciona cuando un jugador hace click sobre el tablero.
//	 * 
//	 * @param row
//	 *            Fila donde ha hecho click el jugador.
//	 * @param col
//	 *            Columna donde ha hecho click el jugador.
//	 * @param clickCount
//	 * @param mouseButton
//	 */
//	@Override
//	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
//		if (this.jugador.getPlayerNumber() == state.getTurn()) {
//			if (!state.isPositionEmpty(row, col)) {
//				if (originCoordinates == null) {
//					originCoordinates = new Coordinate(row, col);
//					playerInfoObserver.postMessage("Haz click en una celda destino");
//					validMoves = state.getValidMoves(this.jugador.getPlayerNumber(), originCoordinates);
//					jBoard.repaint();
//				}
//			} else if (originCoordinates != null) {
//				if (state.at(originCoordinates.getX(), originCoordinates.getY()) == this.jugador.getPlayerNumber()) {
//					AtaxxAction newAction = state.isValidMoveForPlayerInCoordinate(originCoordinates,
//							this.jugador.getPlayerNumber(), row, col);
//					if (newAction != null) {
//						gameController.makeManualMove(newAction);
//						originCoordinates = null;
//						validMoves = null;
//					}
//				} else {
//					playerInfoObserver.postMessage("Movimiento no válido");
//				}
//			}
//		}
//	}
//
//	@Override
//	protected void resetValidMoves() {
//		validMoves = null;
//		originCoordinates = null;
//	}
//
//	@Override
//	public void setGameController(GameController<AtaxxState, AtaxxAction> gameCtrl) {
//	}
//
//	@Override
//	public void setMessageViewer(MessageViewer<AtaxxState, AtaxxAction> messageViewer) {
//	}
//
//	@Override
//	public void update(Observable o, Object arg) {
//	}
//}