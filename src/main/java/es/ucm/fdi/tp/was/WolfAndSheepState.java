package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

public class WolfAndSheepState extends GameState<WolfAndSheepState, WolfAndSheepAction> {

	 private static final long serialVersionUID = 1L;
	    private final int turn;
	    private final boolean finished;
	    private final int[][] board;
	    private final int winner;
	    private final int dim;
	    private final static int EMPTY = -1;
	    private final static int WOLF = 0;
	    private final static int SHEEP = 1;

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

	    public WolfAndSheepState(WolfAndSheepState prev, int[][] board, boolean finished, int winner) {
	        super(2);
	        this.dim = prev.dim;
	        this.board = board;
	        this.turn = (prev.turn + 1) % 2;
	        this.finished = finished;
	        this.winner = winner;
	    }

	    public WolfAndSheepAction isValidMove(int playerNumber, int row, int colum) {
	        WolfAndSheepAction wolfAndSheepAction = null;
	        if (isFinished()) {
	            return wolfAndSheepAction;
	        }
	        if ((row >= 0 && row <= 7) && (colum >= 0 && colum <= 7)) {
	            if (isPositionEmpty(row, colum)) {
	                Coordinate wolfCoordinate = getWolfCoordinates();
	                if ((row == wolfCoordinate.getX() - 1) && (colum == wolfCoordinate.getY() - 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(), wolfCoordinate.getY());
	                } else if ((row == wolfCoordinate.getX() + 1) && (colum == wolfCoordinate.getY() - 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(), wolfCoordinate.getY());
	                } else if ((row == wolfCoordinate.getX() - 1) && (colum == wolfCoordinate.getY() + 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(), wolfCoordinate.getY());
	                } else if ((row == wolfCoordinate.getX() + 1) && (colum == wolfCoordinate.getY() + 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, wolfCoordinate.getX(), wolfCoordinate.getY());
	                }

	            }
	        }
	        return wolfAndSheepAction;
	    }

	    public WolfAndSheepAction isValidMove(int playerNumber, int row, int colum, Coordinate sheepCoordinate) {
	        WolfAndSheepAction wolfAndSheepAction = null;
	        if ((row >= 0 && row <= 7) && (colum >= 0 && colum <= 7)) {
	            if (isPositionEmpty(row, colum)) {
	                if ((row == sheepCoordinate.getX() + 1) && (colum == sheepCoordinate.getY() - 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, sheepCoordinate.getX(), sheepCoordinate.getY());
	                } else if ((row == sheepCoordinate.getX() + 1) && (colum == sheepCoordinate.getY() + 1)) {
	                    wolfAndSheepAction = new WolfAndSheepAction(playerNumber, row, colum, sheepCoordinate.getX(), sheepCoordinate.getY());
	                }
	            }
	        }
	        return wolfAndSheepAction;
	    }

	    private boolean isPositionEmpty(int row, int column) {
	        return board[row][column] == EMPTY;
	    }

	    @Override
	    public int getTurn() {
	        return turn;
	    }

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
	                        WolfAndSheepAction wolfAndSheepAction = isValidMove(playerNumber, i, j, sheepCoordinatesList.get(index));
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
	     * @return a copy of the board
	     */
	    public int[][] getBoard() {
	        int[][] copy = new int[board.length][];
	        for (int i = 0; i < board.length; i++) {
	            copy[i] = board[i].clone();
	        }
	        return copy;
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

	    public static boolean isWinner(int[][] board, WolfAndSheepState state, int playerNumber) {
	        boolean won = false;
	        if (playerNumber == 0) {
	            if (board[0][1] == WOLF || board[0][3] == WOLF || board[0][5] == WOLF || board[0][7] == WOLF) {
	                won = true;
	            }
	        } else if (state.validActions(0).size() == 0) {
	            won = true;
	        }
	        return won;
	    }
}
