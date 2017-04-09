package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import javax.swing.*;

public abstract class GameView<S extends GameState<S, A>, A extends GameAction<S, A>>  extends JComponent {

    @Override
    public abstract void enable();

    @Override
    public abstract void disable();

    public abstract void update(GameState state);

    public abstract void setController(GameController<S, A> gameController);
}
