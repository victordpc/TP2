package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import javax.swing.*;
import java.awt.*;

import static javax.swing.UIManager.getColor;

public abstract class RectBoardGameViewOld<S extends GameState<S, A>, A extends GameAction<S, A>> extends GameViewOld<S, A> {
	private static final long serialVersionUID = -8117144953007391819L;
	private JComponent jboard;
	protected Integer board[][];

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private int _SEPARATOR = -2;

	public enum PlayerShape {
		CIRCLE, RECTANGLE
	}

	public RectBoardGameViewOld() {
		this.enableWindowMode();
		initGUI();
	}

	private void initGUI() {
		jboard = new JBoard() {
			@Override
			protected void keyTyped(int keyCode) {

			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {

			}

			@Override
			protected Shape getShape(int player) {
				return null;
			}

			@Override
			protected Color getColor(int player) {
				return null;
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return null;
			}

			@Override
			protected Color getBackground(int row, int col) {
				return null;
			}

			@Override
			protected int getNumRows() {
				return 0;
			}

			@Override
			protected int getNumCols() {
				return 0;
			}
		};
		this.add(jboard, BorderLayout.CENTER);
		createBoardData(10, 10);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setOpaque(true);
		this.window.setContentPane(mainPanel);
		this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}

	protected PlayerShape getShape(int player) {
		return PlayerShape.CIRCLE;
	}

	protected Color getBackground(int row, int col) {
		return Color.LIGHT_GRAY;
	}

	protected int getSepPixels() {
		return 1; // put to 0 if you don't want a separator between cells
	}

	protected Color getPlayerColor(int player) {
		return player == 0 ? Color.BLUE : Color.RED;
	}

	public void setEnabled(boolean b) {

	}

	public void udpate(S state) {

	}

	protected abstract int getNumCols(); // devuelve el número de filas del
											// tablero.

	protected abstract int getNumRows(); // devuelve el número de columnas.

	protected abstract Integer getPosition(int row, int col); // devuelve el
																// valor de una
																// posición del
																// tablero en el
																// estado actual
																// del juego.

	protected abstract void mouseClicked(int row, int column, int clickCount, int mouseButton);

	protected abstract void keyTyped(int keyCode);

	protected abstract void createBoardData(int numOfRows, int numOfCols);

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		fillBoard(g);
	}

	private void fillBoard(Graphics g) {
		int numCols = getNumCols();
		int numRows = getNumRows();

		if (numCols <= 0 || numRows <= 0) {
			g.setColor(Color.red);
			g.drawString("Waiting for game to start!", 20, this.getHeight() / 2);
			return;
		}

		_CELL_WIDTH = this.getWidth() / numCols;
		_CELL_HEIGHT = this.getHeight() / numRows;

		for (int i = 0; i < numRows; i++)
			for (int j = 0; j < numCols; j++)
				drawCell(i, j, g);
	}

	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(getBackground(row, col));
		g.fillRect(x + _SEPARATOR, y + _SEPARATOR, _CELL_WIDTH - 2 * _SEPARATOR, _CELL_HEIGHT - 2 * _SEPARATOR);

		Integer p = getPosition(row, col);

		if (p != null) {
			Color c = getColor(p);
			PlayerShape s = getShape(p);

			g.setColor(c);
			switch (s) {
			case CIRCLE:
				g.fillOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4,
						_CELL_HEIGHT - 2 * _SEPARATOR - 4);
				g.setColor(Color.black);
				g.drawOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4,
						_CELL_HEIGHT - 2 * _SEPARATOR - 4);
				break;
			case RECTANGLE:
				g.fillRect(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4,
						_CELL_HEIGHT - 2 * _SEPARATOR - 4);
				g.setColor(Color.black);
				g.drawRect(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4,
						_CELL_HEIGHT - 2 * _SEPARATOR - 4);
				break;
			default:
				break;

			}
		}
	}

}
