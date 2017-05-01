package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GameView;

public abstract class MessageViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GameView<S, A> {

    public MessageViewer(S state) {
        super(state);
    }

    public void setMessageViewer(MessageViewer<S,A> infoViewer) { }
    abstract public void addContent(String msg);
    abstract public void setContent(String msg);
}
