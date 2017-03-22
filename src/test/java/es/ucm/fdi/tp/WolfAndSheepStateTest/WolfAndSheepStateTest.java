package es.ucm.fdi.tp.WolfAndSheepStateTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.ucm.fdi.tp.was.Coordinate;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class WolfAndSheepStateTest {

<<<<<<< HEAD
	private final static int EMPTY = -1;
	private final static int WOLF = 0;
	private final static int SHEEP = 1;

	/**
	 * Test que evalua: Un lobo rodeado resulta en victoria de las ovejas
	 */
	@Test
	public void sheepsShouldWons() {
		int[][] board = getEmptyBoard();
		board[3][1] = SHEEP;
		board[3][3] = SHEEP;
		board[5][1] = SHEEP;
		board[5][3] = SHEEP;
		board[4][2] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
		WolfAndSheepState.isWinner(board, wolfAndSheepState, 1);
		assertTrue(WolfAndSheepState.isWinner(board, wolfAndSheepState, 1));
	}

	@Test
	public void wolfShouldLoose() {
		int[][] board = getEmptyBoard();
		board[0][1] = SHEEP;
		board[0][3] = SHEEP;
		board[0][5] = SHEEP;
		board[5][0] = SHEEP;
		board[7][0] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(SHEEP, board, false, -1);
		System.out.println(wolfAndSheepState);
		WolfAndSheepAction action = new WolfAndSheepAction(SHEEP, 6, 1, 5, 0);
		wolfAndSheepState = action.applyTo(wolfAndSheepState);

		assertTrue(wolfAndSheepState.isFinished());
	}

	@Test
	public void wolfShouldLoose2() {
		int[][] board = getEmptyBoard();
		board[0][1] = SHEEP;
		board[0][3] = SHEEP;
		board[2][1] = SHEEP;
		board[2][3] = SHEEP;
		board[1][2] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(SHEEP, board, false, -1);
		System.out.println(wolfAndSheepState);
		WolfAndSheepAction action = new WolfAndSheepAction(SHEEP, 6, 1, 5, 0);
		wolfAndSheepState = action.applyTo(wolfAndSheepState);

		assertTrue(wolfAndSheepState.isFinished());
	}

	/**
	 * Test que evalua: Un lobo en una casilla con y = 0 resulta en victoria del
	 * lobo
	 */
	@Test
	public void wolfShouldWons() {
		int[][] board = getEmptyBoard();
		board[3][1] = SHEEP;
		board[3][3] = SHEEP;
		board[5][1] = SHEEP;
		board[5][3] = SHEEP;
		board[0][1] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(1, board, false, -1);
		assertTrue(WolfAndSheepState.isWinner(board, wolfAndSheepState, 0));
	}

	/**
	 * Test que evalua: Un lobo en su posición inicial sólo tiene 1 acción
	 * válida; y tras llevarla a cabo, en su siguiente turno, tiene 4 acciones
	 * válidas.
	 */
	@Test
	public void wolfValidActions() {
		int[][] board = getEmptyBoard();
		board[3][1] = SHEEP;
		board[3][3] = SHEEP;
		board[5][1] = SHEEP;
		board[5][3] = SHEEP;
		board[7][0] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
		System.out.println("ValidActions " + wolfAndSheepState.validActions(0).size());
		assertEquals(1, wolfAndSheepState.validActions(0).size());
		WolfAndSheepAction wolfAndSheepAction = new WolfAndSheepAction(0, 6, 1, 7, 0);
		wolfAndSheepState = wolfAndSheepAction.applyTo(wolfAndSheepState);
		assertEquals(4, wolfAndSheepState.validActions(0).size());
	}

	/**
	 * Test que evalua: Una oveja en su posición inicial tiene 2 acciones
	 * válidas; y si está en un lateral, tiene 1 acciones válidas.
	 */
	@Test
	public void sheepsValidActions() {
		int dim = 8;
		int[][] board = getEmptyBoard();
		board[0][1] = SHEEP;
		board[0][3] = SHEEP;
		board[0][1] = SHEEP;
		board[0][7] = SHEEP;
		board[7][0] = WOLF;

		WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
		Coordinate sheepCoordinates = new Coordinate(0, 1);
		List<WolfAndSheepAction> validActions = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				WolfAndSheepAction wolfAndSheepAction = wolfAndSheepState.isValidMove(1, i, j, sheepCoordinates);
				if (wolfAndSheepAction != null) {
					validActions.add(wolfAndSheepAction);
				}
			}
		}
		assertEquals(2, validActions.size());

		sheepCoordinates = new Coordinate(2, 0);
		validActions = new ArrayList<>();
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				WolfAndSheepAction wolfAndSheepAction = wolfAndSheepState.isValidMove(1, i, j, sheepCoordinates);
				if (wolfAndSheepAction != null) {
					validActions.add(wolfAndSheepAction);
				}
			}
		}
		assertEquals(1, validActions.size());
	}

	// private void showBoard(int[][] board) {
	// StringBuilder stringBuilder = new StringBuilder();
	// for (int i = 0; i < board.length; i++) {
	// stringBuilder.append("|");
	// for (int j = 0; j < board.length; j++) {
	// if (board[i][j] == WOLF) {
	// stringBuilder.append(" W |");
	// } else if (board[i][j] == SHEEP) {
	// stringBuilder.append(" S |");
	// } else {
	// stringBuilder.append(" |");
	// }
	// }
	// stringBuilder.append("\n");
	// }
	// System.out.println(stringBuilder.toString());
	// }

	private int[][] getEmptyBoard() {
		int dim = 8;
		int[][] board = new int[dim][dim];
		for (int i = 0; i < dim; i++) {
			for (int j = 0; j < dim; j++) {
				board[i][j] = EMPTY;
			}
		}
		return board;
	}
=======
	 private final static int EMPTY = -1;
	    private final static int WOLF = 0;
	    private final static int SHEEP = 1;

	    /**
	     * Test que evalua: Un lobo rodeado resulta en victoria de las ovejas
	     */
	    @Test
	    public void sheepsShouldWons() {
	        int[][] board = getEmptyBoard();
	        board[3][1] = SHEEP;
	        board[3][3] = SHEEP;
	        board[5][1] = SHEEP;
	        board[5][3] = SHEEP;
	        board[4][2] = WOLF;

	        WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
	        WolfAndSheepState.isWinner(board, wolfAndSheepState, 1);
	        assertTrue(WolfAndSheepState.isWinner(board, wolfAndSheepState, 1));
	    }

	    /**
	     * Test que evalua: Un lobo en una casilla con y = 0 resulta en victoria del lobo
	     */
	    @Test
	    public void wolfShouldWons() {
	        int[][] board = getEmptyBoard();
	        board[3][1] = SHEEP;
	        board[3][3] = SHEEP;
	        board[5][1] = SHEEP;
	        board[5][3] = SHEEP;
	        board[0][1] = WOLF;

	        WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(1, board, false, -1);
	        assertTrue(WolfAndSheepState.isWinner(board, wolfAndSheepState, 0));
	    }

	    /**
	     * Test que evalua: Un lobo en su posición inicial sólo tiene 1 acción válida;
	     * y tras llevarla a cabo, en su siguiente turno, tiene 4 acciones válidas.
	     */
	    @Test
	    public void wolfValidActions() {
	        int[][] board = getEmptyBoard();
	        board[3][1] = SHEEP;
	        board[3][3] = SHEEP;
	        board[5][1] = SHEEP;
	        board[5][3] = SHEEP;
	        board[7][0] = WOLF;

	        WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
	        System.out.println("ValidActions " + wolfAndSheepState.validActions(0).size());
	        assertEquals(1, wolfAndSheepState.validActions(0).size());
	        WolfAndSheepAction wolfAndSheepAction = new WolfAndSheepAction(0, 6, 1, 7, 0);
	        wolfAndSheepState = wolfAndSheepAction.applyTo(wolfAndSheepState);
	        assertEquals(4, wolfAndSheepState.validActions(0).size());
	    }

	    /**
	     * Test que evalua: Una oveja en su posición inicial tiene 2 acciones válidas;
	     * y si está en un lateral, tiene 2 acciones válidas.
	     */
	    @Test
	    public void sheepsValidActions() {
	        int dim = 8;
	        int[][] board = getEmptyBoard();
	        board[0][1] = SHEEP;
	        board[0][3] = SHEEP;
	        board[0][1] = SHEEP;
	        board[0][7] = SHEEP;
	        board[7][0] = WOLF;

	        WolfAndSheepState wolfAndSheepState = new WolfAndSheepState(0, board, false, -1);
	        Coordinate sheepCoordinates = new Coordinate(0, 1);
	        List<WolfAndSheepAction> validActions = new ArrayList<>();
	        for (int i = 0; i < dim; i++) {
	            for (int j = 0; j < dim; j++) {
	                WolfAndSheepAction wolfAndSheepAction = wolfAndSheepState.isValidMoveForPlayerInCoordinate(sheepCoordinates, 1, i, j);	                		
	                if (wolfAndSheepAction != null) {
	                    validActions.add(wolfAndSheepAction);
	                }
	            }
	        }
	        assertEquals(2, validActions.size());

	        sheepCoordinates = new Coordinate(2, 0);
	         validActions = new ArrayList<>();
	        for (int i = 0; i < dim; i++) {
	            for (int j = 0; j < dim; j++) {
	                WolfAndSheepAction wolfAndSheepAction = wolfAndSheepState.isValidMoveForPlayerInCoordinate(sheepCoordinates, 1, i, j);	                		
	                if (wolfAndSheepAction != null) {
	                    validActions.add(wolfAndSheepAction);
	                }
	            }
	        }
	        assertEquals(1, validActions.size());
	    }

	    private void showBoard(int[][] board) {
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
	        System.out.println(stringBuilder.toString());
	    }

	    private int[][] getEmptyBoard() {
	        int dim = 8;
	        int[][] board = new int[dim][dim];
	        for (int i = 0; i < dim; i++) {
	            for (int j = 0; j < dim; j++) {
	                board[i][j] = EMPTY;
	            }
	        }
	        return board;
	    }
>>>>>>> origin/master
}
