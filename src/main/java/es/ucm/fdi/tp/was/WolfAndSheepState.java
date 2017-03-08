package es.ucm.fdi.tp.was;

import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int turn;
	private final boolean finished;
	private final int[][] board;
	private final int winner;
	private final int dim;
	final static int EMPTY = -1;
	
	public WolfAndSheepState(int dim) {
		super(2);
		if (dim != 8) {
			throw new IllegalArgumentException("Expected dim to be 8");
		}
		this.dim = dim;
		board = new int[dim][];
		for (int i = 0; i < dim; i++) {
			board[i] = new int[dim];
			for (int j = 0; j < dim; j++)
				board[i][j] = EMPTY;
		}
		this.turn = 0;
		this.winner = -1;
		this.finished = false;
	}

	@Override
	public int getTurn() {
		return turn;
	}

	@Override
	public List<WolfAndSheepAction> validActions(int playerNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	@Override
	public int getWinner() {
		return winner;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < board.length; i++) {
			sb.append("|");
			for (int j = 0; j < board.length; j++) {
				sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == 0 ? " O |" : " X |");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
