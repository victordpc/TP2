package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import javax.swing.*;

public class InfoView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel {

    private S state;
    MessageViewer messageViewer;

    public InfoView(S state) {
        this.state = state;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initGUI();
    }

    private void initGUI() {
        messageViewer = new MessageViewerComponent(state);
        add(messageViewer);
    }
}
