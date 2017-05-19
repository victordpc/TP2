package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;

public class MessageViewerComponent<S extends GameState<S, A>, A extends GameAction<S, A>> extends MessageViewer<S, A> {
	private static final long serialVersionUID = 1962500266199361838L;

	public MessageViewerComponent() {
		super();
	}

	@Override
	public void addContent(String msg) {
		if (textArea.getText().length() > 0) {
			textArea.append(System.getProperty("line.separator") + msg);
		} else {
			textArea.append(msg);
		}
	}

	@Override
	public void setContent(String msg) {
		textArea.setText(msg);
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> infoViewer) {
	}

	@Override
	public void update(S state) {
	}
}
