package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.swing.*;

public abstract class GUIView<S extends GameState<S,A>, A extends GameAction<S,A>> extends JPanel {
    public abstract void update(S state);
    public abstract void setMessageViewer(MessageViewer<S,A> infoViewer);
    public abstract void setGameController(GameController<S, A> gameCtrl);
}
