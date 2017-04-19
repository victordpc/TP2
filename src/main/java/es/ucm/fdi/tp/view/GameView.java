package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import javax.swing.*;

public abstract class GameView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JComponent {

    protected JFrame window;

    public JFrame getWindow() {
        return window;
    }

    public void enableWindowMode() {
        this.window = new JFrame("Swing");
        window.setContentPane(this);
    }

    public void disableWindowMode() {
        window.dispose();
        window = null;
    }

    public void setTitle(String title) {
        if (window != null) {
            window.setTitle(title);
        }else {
            this.setBorder(BorderFactory.createTitledBorder(title));
        }
    }

    @Override
    public abstract void setEnabled(boolean b);
    public abstract void update(S state);
    public abstract void setController(GameController gameController);

}
