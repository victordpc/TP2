//package es.ucm.fdi.tp.ataxx;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.print.attribute.standard.Finishings;
//
//import es.ucm.fdi.tp.base.model.GameState;
//import es.ucm.fdi.tp.was.Coordinate;
//import es.ucm.fdi.tp.was.WolfAndSheepAction;
//import es.ucm.fdi.tp.was.WolfAndSheepState;
//
//public class AtaxxState extends GameState<AtaxxState, AtaxxAction> {
//	private static final long serialVersionUID = -8761462977037791128L;
//
//	/** Casilla en blanco */
//	final static int EMPTY = -1;
//	/** Representación del tablero. */
//	private final int[][] board;
//	/** Dimensión del tablero */
//	private final int dim;
//	/** Define si la partida ha terminado. */
//	private final boolean finished;
//	/** Contiene que jugador (1-4) le toca mover ficha. */
//	private final int turn;
//	/** El jugador que ha ganado. */
//	private final int winner;
//	/** numero de jugadores */
//	private int players;
//
//	/**
//	 * Evalua si el jugador pasado por parámetro ha ganado.
//	 * 
//	 * @param board
//	 *            tablero actúal.
//	 * @param state
//	 *            Estado actúal de la partida.
//	 * @param playerNumber
//	 *            Jugador a evaluar.
//	 * @return Si el jugador ha ganado la partida devuelve true, en caso
//	 *         contrario false.
//	 */
//	public static boolean isWinner(int[][] board, AtaxxState state, int playerNumber) {
//		boolean won = false;
//
//		if (!state.otherPlayerCanMove(playerNumber)) {
//			if (state.validActions(playerNumber).size() >= 0) {
//				won = true;
//			}
//		}
//		return won;
//	}
//
//	private boolean otherPlayerCanMove(int playerNumber) {
//		boolean resultado = false;
//
//		for (int i = 0; i <= players && !resultado; i++) {
//			if (i != playerNumber) {
//				if (validActions(i).size() > 0) {
//					resultado = true;
//				}
//			}
//		}
//
//		return resultado;
//	}
//
//	public AtaxxState(int dim, int players) {
//		super(players);
//		if (dim < 5 && (dim % 2) != 1) {
//			throw new IllegalArgumentException("Expected dim to be 5 or more, but allways odd");
//		}
//		if (players < 2 || players > 4) {
//			throw new IllegalArgumentException("Expected players between 2-4");
//		}
//
//		this.dim = dim;
//		this.players = players;
//		board = new int[dim][];
//
//		for (int i = 0; i < dim; i++) {
//			board[i] = new int[dim];
//			for (int j = 0; j < dim; j++) {
//				if ((i == 0 && j == 0) || (i == dim - 1 && j == dim - 1)) {
//					board[i][j] = 0;
//				} else if ((i == 0 && j == dim - 1) || (i == dim - 1 && j == 0)) {
//					board[i][j] = 1;
//				} else if ((players == 3)
//						&& ((i == 0 && j == ((dim / 2) + 1)) || (i == dim - 1 && j == ((dim / 2) + 1)))) {
//					board[i][j] = 2;
//				} else if ((players == 4)
//						&& ((i == 0 && j == ((dim / 2) + 1)) || (i == dim - 1 && j == ((dim / 2) + 1)))) {
//					board[i][j] = 3;
//				} else {
//					board[i][j] = -1;
//				}
//			}
//		}
//		this.turn = 0;
//		this.winner = -1;
//		this.finished = false;
//	}
//
//	public AtaxxState(AtaxxState prev, int[][] board, boolean finished, int winner) {
//		super(prev.players);
//		this.dim = prev.dim;
//		this.board = board;
//		this.turn = (prev.turn + 1) % prev.players;
//		this.finished = finished;
//		this.winner = winner;
//		this.players = prev.getPlayerCount();
//	}
//
//	@Override
//	public int getPlayerCount() {
//		return players;
//	}
//
//	@Override
//	public int getTurn() {
//		return turn;
//	}
//
//	public int getDim() {
//		return dim;
//	}
//
//	@Override
//	public int getWinner() {
//		return winner;
//	}
//
//	@Override
//	public boolean isFinished() {
//		return finished;
//	}
//
//	public int at(int row, int col) {
//		return board[row][col];
//	}
//
//	/**
//	 * Crea un listado de las acciones válidas para un determinado jugador
//	 * 
//	 * @param playerNumber
//	 *            Jugador al que se le van a evaluar las acciones.
//	 * @return el listado de las acciones válidas para el jugador.
//	 */
//	@Override
//	public List<AtaxxAction> validActions(int playerNumber) {
//		List<AtaxxAction> validActions = new ArrayList<>();
//		if (finished) {
//			return validActions;
//		}
//
//		for (int i = 0; i < dim; i++) {
//			for (int j = 0; j < dim; j++) {
//				if (at(i, j) == playerNumber) {
//					Coordinate coordenada = new Coordinate(i, j);
//
//					for (int x = i - 2; x <= i + 2; x++) {
//						for (int y = j - 2; y <= j + 2; y++) {
//							AtaxxAction accion = isValidMoveForPlayerInCoordinate(coordenada, playerNumber, x, y);
//							if (accion != null) {
//								validActions.add(accion);
//							}
//						}
//					}
//				}
//			}
//		}
//		return validActions;
//	}
//
//	public AtaxxAction isValidMoveForPlayerInCoordinate(Coordinate coordenadas, int playerNumber, int row, int col) {
//		if (isFinished()) {
//			return null;
//		}
//
//		AtaxxAction action = null;
//		if ((row >= 0 && row <= dim - 1) && (col >= 0 && col <= dim - 1)) {
//			if (isPositionEmpty(row, col)) {
//
//				if ((((row - coordenadas.getX()) <= 2) && ((row - coordenadas.getX()) >= -2))
//						&& (((col - coordenadas.getY()) <= 2) && ((col - coordenadas.getY()) >= -2))) {
//					action = new AtaxxAction(playerNumber, row, col, coordenadas.getX(), coordenadas.getY());
//				}
//			}
//		}
//		return action;
//	}
//
//	public int[][] getBoard() {
//		int[][] copy = new int[board.length][];
//		for (int i = 0; i < board.length; i++) {
//			copy[i] = board[i].clone();
//		}
//		return copy;
//	}
//
//	/**
//	 * Método que evalua si una determinada posición ene el tablero está vacía.
//	 * 
//	 * @param row
//	 *            Fila en la que se encuentra la posición a evaular.
//	 * @param column
//	 *            Colummna en la que se encuentra la posición a evaular.
//	 * @return Devuelve true en caso de que la posición evaluada esté vacía y
//	 *         false en caso contrario.
//	 */
//	public boolean isPositionEmpty(int row, int column) {
//		return board[row][column] == EMPTY;
//	}
//
//	@Override
//	public String toString() {
//		StringBuilder stringBuilder = new StringBuilder();
//		for (int i = 0; i < board.length; i++) {
//			stringBuilder.append("|");
//			for (int j = 0; j < board.length; j++) {
//				if (board[i][j] == EMPTY) {
//					stringBuilder.append("   |");
//				} else {
//					stringBuilder.append(" " + board[i][j] + " |");
//				}
//			}
//			stringBuilder.append("\n");
//		}
//		return stringBuilder.toString();
//	}
//
//	public List<Coordinate> getValidMoves(int playerId, Coordinate coordinates) {
//		List<Coordinate> validMoves = new ArrayList<>();
//		if (finished) {
//			return validMoves;
//		}
//		for (int i = 0; i < dim; i++) {
//			for (int j = 0; j < dim; j++) {
//				if (isValidMoveForPlayerInCoordinate(coordinates, playerId, i, j) != null) {
//					validMoves.add(new Coordinate(i, j));
//				}
//			}
//		}
//		return validMoves;
//	}
//
//}
