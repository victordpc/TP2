package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;

import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;

public class TttView extends RectBoardView<TttState, TttAction> {
	private static final long serialVersionUID = 3367678913075958511L;

	public TttView(GameController<TttState, TttAction> gameController, TttState state) {
		super(gameController, state);
	}

	@Override
	protected Color getBackground(int row, int col) {
		return Color.decode("#424242");// (row + col) % 2 == 0 ?
										// Color.decode("#1565C0") :
										// Color.decode("#64B5F6");
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
		int shape = state.at(row, col);
		if (shape != -1) {
			return shape;
		} else {
			return null;
		}
	}

	@Override
	protected void initUI() {
		this.setLayout(new BorderLayout());
		jBoard = new JBoard() {
			private static final long serialVersionUID = -3440821874820166441L;

			@Override
			protected Color getBackground(int row, int col) {
				return TttView.this.getBackground(row, col);
			}

			@Override
			protected Color getColor(int player) {
				return TttView.this.getPlayerColor(player);
			}

			@Override
			protected int getNumCols() {
				return TttView.this.getNumCols();
			}

			@Override
			protected int getNumRows() {
				return TttView.this.getNumRows();
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return TttView.this.getPosition(row, col);
			}

			@Override
			protected Shape getShape(int player) {
				return TttView.this.getShape(player);
			}

			@Override
			protected void keyTyped(int keyCode) {
			}

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				if (isEnabled()) {
					TttView.this.mouseClicked(row, col, clickCount, mouseButton);
				}
			}
		};
		this.add(jBoard, BorderLayout.CENTER);
	}

	@Override
	protected void keyTyped(int keyCode) {
	}

	/// QUE SE ILUMINEN LAS CASILLAS VÁLIDAS.
	@Override
	protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
		TttAction action = new TttAction(this.jugador.getPlayerNumber(), row, col);
		gameController.makeManualMove(action);
	}

	@Override
	public void setEnabled(boolean enabled) {
		jBoard.setEnabled(enabled);
	}

	@Override
	public void setGameController(GameController<TttState, TttAction> gameCtrl) {
	}

	@Override
	public void setMessageViewer(MessageViewer<TttState, TttAction> infoViewer) {
	}

	@Override
	protected void setPlayersInfoObserver(PlayersInfoObserver observer) {
	}

	@Override
	public void update(TttState state) {
		this.state = state;
		jBoard.repaint();
	}
}
