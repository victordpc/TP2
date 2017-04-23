package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;

import javax.swing.*;
import java.awt.*;

public class TttView extends RectBoardView {

    private JComponent jBoard;

    public TttView() {
        initUI();
    }

    private void initUI() {
        this.setLayout(new BorderLayout());
        jBoard = new JBoard() {
            @Override
            protected void keyTyped(int keyCode) {

            }

            @Override
            protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {

            }

            @Override
            protected Shape getShape(int player) {
                return TttView.this.getShape(player);
            }

            @Override
            protected Color getColor(int player) {
                return TttView.this.getPlayerColor(player);
            }

            @Override
            protected Integer getPosition(int row, int col) {
                return TttView.this.getPosition(row, col);
            }

            @Override
            protected Color getBackground(int row, int col) {
                return TttView.this.getBackground(row, col);
            }

            @Override
            protected int getNumRows() {
                return TttView.this.getNumRows();
            }

            @Override
            protected int getNumCols() {
                return TttView.this.getNumCols();
            }
        };
        this.add(jBoard, BorderLayout.CENTER);
    }

    @Override
    public void setEnable() {

    }

    @Override
    public void update(GameState state) {

    }

    @Override
    protected int getNumCols() {
        return 3;
    }

    @Override
    protected int getNumRows() {
        return 3;
    }

    @Override
    protected Integer getPosition(int row, int col) {
        return 0;
    }

    @Override
    protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {

    }

    @Override
    protected void keyTyped(int keyCode) {

    }
}
