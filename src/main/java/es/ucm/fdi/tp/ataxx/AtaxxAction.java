//package es.ucm.fdi.tp.ataxx;
//
//import javax.swing.border.EmptyBorder;
//
//import es.ucm.fdi.tp.base.model.GameAction;
//import es.ucm.fdi.tp.ttt.TttAction;
//import es.ucm.fdi.tp.ttt.TttState;
//import es.ucm.fdi.tp.was.WolfAndSheepState;
//
//public class AtaxxAction implements GameAction<AtaxxState, AtaxxAction> {
//	private static final long serialVersionUID = -462619977260568957L;
//	/**
//	 * Columna de la nueva posición
//	 */
//	private int col;
//	/**
//	 * Columna de origen
//	 */
//	private int originColum;
//	/**
//	 * Fila de origen
//	 */
//	private int originRow;
//	/**
//	 * Jugador de la acción
//	 */
//	private int player;
//	/**
//	 * Fila de la nueva posición
//	 */
//	private int row;
//
//	public AtaxxAction(int player, int row, int col, int originRow, int originColum) {
//		this.player = player;
//		this.row = row;
//		this.col = col;
//		this.originRow = originRow;
//		this.originColum = originColum;
//	}
//
//	@Override
//	public AtaxxState applyTo(AtaxxState state) {
//		if (player != state.getTurn()) {
//			throw new IllegalArgumentException("Not the turn of this player");
//		}
//
//		// make move
//		int[][] board = state.getBoard();
//		board[row][col] = player;
//		
//		for (int i = row - 1; i <= row + 1; i++) {
//			for (int j = col - 1; j <= col + 1; j++) {
//				if ((i>=0 && j>=0 && i<board.length && j<board.length) &&(state.at(i, j) != player) && (state.at(i, j) != -1)) {
//					board[i][j]=player;
//				}
//			}
//		}
//
//		if (Math.abs(originRow - row) <= 1 && Math.abs(originColum - col) <= 1)
//			board[originRow][originColum] = player;
//		else
//			board[originRow][originColum] = -1;
//
//		AtaxxState nextState = null;
//		if (AtaxxState.isWinner(board, state, state.getTurn())) {
//			nextState = new AtaxxState(state, board, true, state.getTurn());
//		} else {
//			nextState = new AtaxxState(state, board, false, -1);
//			if (AtaxxState.isWinner(board, nextState, state.getTurn())) {
//				nextState = new AtaxxState(state, board, true, state.getTurn());
//			}
//		}
//
//		return nextState;
//	}
//
//	@Override
//	public int getPlayerNumber() {
//		return player;
//	}
//
//	/**
//	 * @return the row
//	 */
//	public int getRow() {
//		return row;
//	}
//
//	/**
//	 * @return the col
//	 */
//	public int getCol() {
//		return col;
//	}
//
//}
