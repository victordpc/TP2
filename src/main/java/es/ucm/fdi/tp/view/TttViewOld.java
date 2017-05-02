package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

public class TttViewOld extends RectBoardGameViewOld<TttState, TttAction> {

	private static final long serialVersionUID = -1587997524896273708L;
	private GameController<TttState, TttAction> gameController;
	private boolean isEnabled;
	private int rows;
	private int colums;
	private int player;

	public TttViewOld(int numPlayer) {
		this.player = numPlayer;
		this.setTitle("Ejemplo");
		this.setSize(340, 200);

	}

	@Override
	public void setController(GameController gameController) {
		this.gameController = gameController;
	}

	@Override
	protected int getNumCols() {
		return colums;
	}

	@Override
	protected int getNumRows() {
		return rows;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		return board[row][col];
	}

	@Override
	protected void mouseClicked(int row, int column, int clickCount, int mouseButton) {

	}

	@Override
	protected void keyTyped(int keyCode) {

	}

	@Override
	protected void createBoardData(int numOfRows, int numOfCols) {
	}

	@Override
	public void update(TttState state) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEvent(GameEvent<TttState, TttAction> e) {
		// TODO Auto-generated method stub
		
	}

}
