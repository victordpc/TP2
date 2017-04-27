package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.was.WolfAndSheepState;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.was.WolfAndSheepAction;;

public class WasView extends RectBoardGameView<WolfAndSheepState, WolfAndSheepAction> {

	private int player;

	public WasView(int numPlayer) {
		this.player = numPlayer;
	}

	@Override
	protected int getNumCols() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getNumRows() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected Integer getPosition(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void mouseClicked(int row, int column, int clickCount, int mouseButton) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void keyTyped(int keyCode) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void createBoardData(int numOfRows, int numOfCols) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(GameState state) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setController(GameController gameController) {
		// TODO Auto-generated method stub

	}

}
