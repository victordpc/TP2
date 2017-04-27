package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;

import javax.swing.*;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GameView<S, A> implements GameObserver<S, A> {

    private GameView<S, A> gameView;
    private GameController<S, A> gameController;

    public GameContainer(GameView<S, A> gameView, GameController<S, A> gameController, GameObservable<S, A> game) {
        this.gameView = gameView;
        this.gameController = gameController;
        initGUI();
        game.addObserver(this);
    }

    private void initGUI() {
 
    }

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                handleEvent(e);
            }
        });
    }

    public void handleEvent(GameEvent<S, A> e) {
        //Desarrollado en diapositiva GameContainer Part 3
    }

    @Override
    public void setEnabled(boolean b) {

    }

    @Override
    public void update(S state) {

    }

    @Override
    public void setController(GameController gameController) {
    	this.gameController=gameController;
    }
}
