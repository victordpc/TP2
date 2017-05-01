package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.swing.*;

public abstract class GUIView<S extends GameState<S,A>, A extends GameAction<S,A>> extends JPanel {

    protected JFrame window;

    public void enableWindowMode() {
        this.window = new JFrame("");
        this.window.setContentPane(this);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.setSize(600, 600);
        window.setVisible(true);
    }

    public void disableWindowMode() {
        window.dispose();
        window = null;
    }

    public JFrame getWindow() {
        return window;
    }

    public void setTitle(String newTitle) {
        if (window != null) {
            window.setTitle(newTitle);
        }else {
            this.setBorder(BorderFactory.createTitledBorder(newTitle));
        }
    }

    public abstract void update(S state);
    public abstract void setMessageViewer(MessageViewer<S,A> messageViewer);
    public abstract void setGameController(GameController<S, A> gameCtrl);
}
