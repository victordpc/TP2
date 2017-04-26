package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {
    public void makeManualMove(A a);

    public void makeRandomMove();

    public void makeSmartMove();

    public void restartGame();

    public void stopGame();

    public void handleEvent(GameEvent<S, A> e);

    //public void changePlayerMode(PlayerMode p);
    public int getPlayerId();
}