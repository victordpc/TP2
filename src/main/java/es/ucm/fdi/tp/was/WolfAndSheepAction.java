package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;

public class WolfAndSheepAction implements GameAction<WolfAndSheepState, WolfAndSheepAction> {

	private int player;
	private int row;
	private int col;
	private int originRow;
	private int originColum;

	public WolfAndSheepAction(int player, int row, int col, int originRow, int originColum ) {
		this.player = player;
		this.row = row;
		this.col = col;
		this.originRow = originRow;
		this.originColum = originColum;
	}

	@Override
	public int getPlayerNumber() {
        return player;
    }

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
        }

        return nextState;
	}

	public boolean isSheepValidAction(int originRow, int originColum) {
	    boolean isValidAction = false;
	    if (((row - originRow) == 1) && (((col - originColum) == 1) || ((col - originColum) == -1))) {
            isValidAction = true;
        }
        return isValidAction;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString() {
	    String playerName = (player == 0) ? "Wolf" : "Sheep";
        return "place " + playerName + " at (" + row + ", " + col + ")";
    }
}
