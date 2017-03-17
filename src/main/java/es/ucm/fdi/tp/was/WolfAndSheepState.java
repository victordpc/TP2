package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {

	/**
	 * Representación de lugar vacío en el tablero.
	 */
	private final static int EMPTY = -1;
	private static final long serialVersionUID = 1L;

	/**
	 * Representación de una oveja en el tablero.
	 */
	private final static int SHEEP = 1;

	/**
	 * Representación del lobo en el tablero.
	 */
	private final static int WOLF = 0;

	/**
	 * Evalua si el jugador pasado por parámetro ha ganado.
	 * 
	 * @param board
	 *            tablero actúal.
	 * @param state
	 *            Estado actúal de la partida.
	 * @param playerNumber
	 *            Jugador a evaluar.
	 * @return Si el jugador ha ganado la partida devuelve true, en caso
	 *         contrario false.
	 */
	public static boolean isWinner(int[][] board, WolfAndSheepState state, int playerNumber) {
		boolean won = false;
		if (playerNumber == WOLF) {
			if (board[0][1] == WOLF || board[0][3] == WOLF || board[0][5] == WOLF || board[0][7] == WOLF) {
				won = true;
			} else if (state.validActions(SHEEP).size() == 0)
				won = true;
		} else if (state.validActions(WOLF).size() == 0) {
			won = true;
		}
		return won;
	}

	/**
	 * Representación del tablero.
	 */
	private final int[][] board;
	/**
	 * CLa dimensión del tablero
	 */
	private final int dim;
	/**
	 * Define si la partida ha terminado.
	 */
	private final boolean finished;
	/**
	 * Contiene que jugador (1-2) le toca mover ficha.
	 */
	private final int turn;

	/**
	 * El jugador que ha ganado.
	 */
	private final int winner;

	public WolfAndSheepState(int dim) {
		super(2);
		if (dim != 8) {
			throw new IllegalArgumentException("Expected dim to be 8");
		}
		this.dim = dim;
		board = new int[dim][];
		for (int i = 0; i < dim; i++) {
			board[i] = new int[dim];
			for (int j = 0; j < dim; j++) {
				if (i == 0 && j % 2 != 0) {
					board[i][j] = SHEEP;
				} else if (i == 7 && j == 0) {
					board[i][j] = WOLF;
				} else {
					board[i][j] = EMPTY;
				}
			}
		}
		this.turn = 0;
		this.winner = -1;
		this.finished = false;
	}

	public WolfAndSheepState(int turn, int[][] board, boolean finished, int winner) {
		super(2);
		this.dim = 8;
		this.board = board;
		this.turn = turn;
		this.finished = finished;
		this.winner = winner;
	}

	public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int winner) {
		super(2);
		this.dim = prev.dim;
		this.board = board;
		this.turn = (prev.turn + 1) % 2;
		this.finished = finished;
		this.winner = winner;
	}

	/**
	 * @return a copy of the board
	 */
	public int[][] getBoard() {
		int[][] copy = new int[board.length][];
		for (int i = 0; i < board.length; i++) {
			copy[i] = board[i].clone();
		}
		return copy;
	}

	/**
	 * Analiza el tablero en busca de las posiciones de las ovejas.
	 * 
	 * @return devuelve un listado con las coordenas de todas las ovejas en el
	 *         tablero.
	 */
	private List<Coordinate> getSheepsCoordinates() {
		List<Coordinate> sheepCoordinatesList = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (board[i][j] == SHEEP) {
					sheepCoordinatesList.add(new Coordinate(i, j));
				}
			}
		}
		return sheepCoordinatesList;
	}

	/**
	 * Devuelve el turno
	 * 
	 * @return Devuelve el turno
	 */
	@Override
	public int getTurn() {
		return turn;
	}

	/**
	 * Devuelve el ganador de la partida.
	 * 
	 * @return Devuelve el ganador de la partida.
	 */
	@Override
	public int getWinner() {
		return winner;
	}

	/**
	 * Analiza el tablero en busca de la posición del lobo.
	 * 
	 * @return Devuelve las coordenadas del lobo.
	 */
	private Coordinate getWolfCoordinates() {
		Coordinate wolfCoordinate = null;
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				if (board[i][j] == WOLF) {
					wolfCoordinate = new Coordinate(i, j);
				}
			}
		}
		return wolfCoordinate;
	}

	/**
	 * Devuelve si la partida ha terminado o no.
	 * 
	 * @return Devuelve true en caso de que la partida halla acabado, en caso
	 *         contrario false.
	 */
	@Override
	public boolean isFinished() {
		return finished;
	}

	/**
	 * Método que evalua si una determinada posición ene el tablero está vacía.
	 * 
	 * @param row
	 *            Fila en la que se encuentra la posición a evaular.
	 * @param column
	 *            Colummna en la que se encuentra la posición a evaular.
	 * @return Devuelve true en caso de que la posición evaluada esté vacía y
	 *         false en caso contrario.
	 */
	private boolean isPositionEmpty(int row, int column) {
		return board[row][column] == EMPTY;
	}

	/**
	 * Método que evalua si un movimiento es válido para un jugador
	 * 
	 * @param playerNumber
	 *            El turno del jugador al que evaluaremos si el movimiento es
	 *            válido.
	 * @param row
	 *            La fila a que pretende moverse el jugador
	 * @param colum
	 *            La columna a que pretende moverse el jugador
	 * @return devuelve una acción en caso de que el movimiento sea válido, si
	 *         no, devuelve nulo.
	 */
	public WolfAndSheepAction isValidMove(int playerNumber, int row, int colum) {
		WolfAndSheepAction wolfAndSheepAction = null;
		if (isFinished()) {
			return wolfAndSheepAction;
		}
		if ((row >= 0 && row <= 7) && (colum >= 0 && colum <= 7)) {
			if (isPositionEmpty(row, colum)) {
				Coordinate wolfCoordinate = getWolfCoordinates();
				if ((row == wolfCoordinate.getX() - 1) && (colum == wolfCoordinate.getY() - 1)) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(),
							wolfCoordinate.getY());
				} else if ((row == wolfCoordinate.getX() + 1) && (colum == wolfCoordinate.getY() - 1)) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(),
							wolfCoordinate.getY());
				} else if ((row == wolfCoordinate.getX() - 1) && (colum == wolfCoordinate.getY() + 1) ) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(),
							wolfCoordinate.getY());
				} else if ((row == wolfCoordinate.getX() + 1) && (colum == wolfCoordinate.getY() + 1)) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(),
							wolfCoordinate.getY());
				}

			}
		}
		return wolfAndSheepAction;
	}

	/**
	 * Método que evalua si un movimiento es válido para un jugador
	 * 
	 * @param playerNumber
	 *            El turno del jugador al que evaluaremos si el movimiento es
	 *            válido.
	 * @param row
	 *            La fila a que pretende moverse el jugador
	 * @param colum
	 *            La columna a que pretende moverse el jugador
	 * @param sheepCoordinate
	 *            coordenadas de la oveja
	 * @return devuelve una acción en caso de que el movimiento sea válido, si
	 *         no, devuelve nulo.
	 */
	public WolfAndSheepAction isValidMove(int playerNumber, int row, int colum, Coordinate sheepCoordinate) {
		WolfAndSheepAction wolfAndSheepAction = null;
		if ((row >= 0 && row <= 7) && (colum >= 0 && colum <= 7)) {
			if (isPositionEmpty(row, colum)) {
				if ((row == sheepCoordinate.getX() + 1) && (colum == sheepCoordinate.getY() - 1)) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, sheepCoordinate.getX(),
							sheepCoordinate.getY());
				} else if ((row == sheepCoordinate.getX() + 1) && (colum == sheepCoordinate.getY() + 1)) {
					wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, sheepCoordinate.getX(),
							sheepCoordinate.getY());
				}
			}
		}
		return wolfAndSheepAction;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			stringBuilder.append("|");
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] == WOLF) {
					stringBuilder.append(" W |");
				} else if (board[i][j] == SHEEP) {
					stringBuilder.append(" S |");
				} else {
					stringBuilder.append("   |");
				}
			}
			stringBuilder.append("\n");
		}
		return stringBuilder.toString();
	}

	/**
	 * Crea un listado de las acciones válidas para un determinado jugador
	 * 
	 * @param playerNumber
	 *            Jugador al que se le van a evaluar las acciones.
	 * @return el listado de las acciones válidas para el jugador.
	 */
	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
		List<WolfAndSheepAction> validActions = new ArrayList<>();
		if (finished) {
			return validActions;
		}

		if (playerNumber == 0) {
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					WolfAndSheepAction wolfAndSheepAction = isValidMove(playerNumber, i, j);
					if (wolfAndSheepAction != null) {
						validActions.add(wolfAndSheepAction);
					}
				}
			}
		} else {
			List<Coordinate> sheepCoordinatesList = getSheepsCoordinates();
			for (int i = 0; i < dim; i++) {
				for (int j = 0; j < dim; j++) {
					int index = 0;
					while (index < 4) {
						WolfAndSheepAction wolfAndSheepAction = isValidMove(playerNumber, i, j,
								sheepCoordinatesList.get(index));
						if (wolfAndSheepAction != null) {
							validActions.add(wolfAndSheepAction);
						}
						index++;
					}
				}
			}
		}
		return validActions;
	}
}
