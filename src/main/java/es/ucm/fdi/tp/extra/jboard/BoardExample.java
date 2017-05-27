package es.ucm.fdi.tp.extra.jboard;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class BoardExample extends JFrame {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new BoardExample();
			}
		});
	}

	private Integer[][] board;

	private JBoard boardComp;
	private JTextField cols;

	private int numOfCols;
	private int numOfRows;

	private JTextField rows;

	public BoardExample() {
		super("[=] JBoard Example! [=]");
		initGUI();
	}

	private void createBoardData(int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		board = new Integer[numOfRows][numOfCols];
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfCols; j++) {
				double d = Math.random();
				if (d < 0.6) {
					board[i][j] = null;
				} else if (d < 0.8) {
					board[i][j] = 0;
				} else {
					board[i][j] = 1;
				}
			}
	}

	protected Color getColor(int player) {
		return player == 0 ? Color.BLUE : Color.RED;
	}

	protected Integer getPosition(int row, int col) {
		return board[row][col];
	}

	private void initGUI() {
		createBoardData(10, 10);

		JPanel mainPanel = new JPanel(new BorderLayout());

		boardComp = new JBoard() {
			@Override
			protected Color getBackground(int row, int col) {
				return Color.LIGHT_GRAY;
				// use this for 2 chess like board
				// return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
			}

			@Override
			protected Color getColor(int player) {
				return BoardExample.this.getColor(player);
			}

			@Override
			protected int getNumCols() {
				return numOfCols;
			}

			@Override
			protected int getNumRows() {
				return numOfRows;
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return BoardExample.this.getPosition(row, col);
			}

			@Override
			protected int getSepPixels() {
				return 1; // put to 0 if you don't want a separator between cells
			}

			@Override
			protected Shape getShape(int player) {
				return Shape.CIRCLE;
			}

			@Override
			protected Image getImage(int row, int col) {return null;}

			@Override
			protected void keyTyped(int keyCode) {
				System.out.println("Key " + keyCode + " pressed ..");
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				System.out.println("Mouse: " + clickCount + "clicks at position (" + row + "," + col + ") with Button "
						+ mouseButton);
			}
		};

		mainPanel.add(boardComp, BorderLayout.CENTER);
		JPanel sizePabel = new JPanel();
		mainPanel.add(sizePabel, BorderLayout.PAGE_START);

		rows = new JTextField(5);
		cols = new JTextField(5);
		sizePabel.add(new JLabel("Rows"));
		sizePabel.add(rows);
		sizePabel.add(new JLabel("Cols"));
		sizePabel.add(cols);
		JButton setSize = new JButton("Set Size");
		sizePabel.add(setSize);
		setSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int numOfRows = new Integer(rows.getText());
					int numOfCols = new Integer(cols.getText());
					createBoardData(numOfRows, numOfCols);
					boardComp.repaint();
				} catch (NumberFormatException _e) {
				}
			}
		});

		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}
}
