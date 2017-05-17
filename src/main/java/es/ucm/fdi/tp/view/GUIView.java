package es.ucm.fdi.tp.view;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

public abstract class GUIView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel {

	protected JFrame window;

	public void disableWindowMode() {
		window.dispose();
		window = null;
	}

	public void enableWindowMode() {
		this.window = new JFrame("");
		this.window.setContentPane(this);
		this.window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.window.setSize(800, 800);
		window.setVisible(true);
	}

	public JFrame getWindow() {
		return window;
	}

	public abstract void setGameController(GameController<S, A> gameCtrl);

	public abstract void setMessageViewer(MessageViewer<S, A> messageViewer);

	public void setTitle(String newTitle) {
		if (window != null) {
			window.setTitle(newTitle);
		} else {
			this.setBorder(BorderFactory.createTitledBorder(newTitle));
		}
	}

	public abstract void update(S state);
}
