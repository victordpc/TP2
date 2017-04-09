package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

public abstract class GameController<S extends GameState<S, A>, A extends GameAction<S, A>>  implements Runnable {

    @Override
    public abstract void run();
}
