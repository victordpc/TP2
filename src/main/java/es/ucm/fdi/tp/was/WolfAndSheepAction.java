package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1745600182000535724L;
	/**
	 * Columna de la nueva posición
	 */
	private int col;
	/**
	 * Columna de origen
	 */
	private int originColum;
	/**
	 * Fila de origen
	 */
	private int originRow;
	/**
	 * Jugador de la acción
	 */
	private int player;
	/**
	 * Fila de la nueva posición
	 */
	private int row;

	public WolfAndSheepAction(int player, int row, int col, int originRow, int originColum) {
		this.player = player;
		this.row = row;
		this.col = col;
		this.originRow = originRow;
		this.originColum = originColum;
	}

	/**
	 * Mueve la ficha al estado pasado por parámetro.
	 * 
	 * @param state
	 *            Estado al que se le va aplicar el movimiento.
	 * @return Devuelve el estado/tablero actualizado tras mover la ficha.
	 */
	@Override
	public WolfAndSheepState applyTo(WolfAndSheepState state) {
		if (player != state.getTurn()) {
			throw new IllegalArgumentException("Not the turn of this player");
		}

		// make move
		int[][] board = state.getBoard();
		board[row][col] = player;
		board[originRow][originColum] = -1;

		WolfAndSheepState nextState = null;
		if (WolfAndSheepState.isWinner(board, state, state.getTurn())) {
			nextState = new WolfAndSheepState(state, board, true, state.getTurn());
		} else {
			nextState = new WolfAndSheepState(state, board, false, -1);
			if (WolfAndSheepState.isWinner(board, nextState, state.getTurn())) {
				nextState = new WolfAndSheepState(state, board, true, state.getTurn());
			}
		}

		return nextState;
	}

	/**
	 * Devuelve el número del jugador.
	 * 
	 * @return Devuelve un número que índica el número del jugador.
	 */
	@Override
	public int getPlayerNumber() {
		return player;
	}

	@Override
	public String toString() {
		String playerName = (player == 0) ? "Wolf" : "Sheep";
		return "place " + playerName + " at (" + row + ", " + col + ")";
	}
}
