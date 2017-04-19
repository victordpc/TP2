package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;

public class TttView extends RectBoardGameView<TttState, TttAction> {

    private GameController gameController;
    private boolean isEnabled;
    private int rows;
    private int colums;

    public TttView(int rows, int colums) {
        this.rows = rows;
        this.colums = colums;
    }

    @Override
    public void update(GameState state) {

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
    protected void createBoardData(int numOfRows, int numOfCols) {}

}
