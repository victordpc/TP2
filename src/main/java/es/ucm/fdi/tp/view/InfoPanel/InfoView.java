package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.GUIView;

import javax.swing.*;

import static com.sun.tools.internal.xjc.reader.Ring.add;

public class InfoView<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView {

    private MessageViewer messageViewer;

    public InfoView() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initGUI();
    }

    private void initGUI() {
        messageViewer = new MessageViewerComponent();
        add(messageViewer);
    }

    public void addContent(String message) {
        messageViewer.addContent(message);
    }

    public void setContent(String message) {
        messageViewer.setContent(message);
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public void setMessageViewer(MessageViewer messageViewer) {
        this.messageViewer = messageViewer;
    }

    @Override
    public void setGameController(GameController gameCtrl) {}

}
