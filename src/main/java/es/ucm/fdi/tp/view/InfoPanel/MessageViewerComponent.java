package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;

public class MessageViewerComponent<S extends GameState<S, A>, A extends GameAction<S, A>> extends MessageViewer {

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
	public void update(GameState state) {
	}

	@Override
	public void setMessageViewer(MessageViewer infoViewer) {
	}

	@Override
	public void setGameController(GameController gameCtrl) {
	}
}
