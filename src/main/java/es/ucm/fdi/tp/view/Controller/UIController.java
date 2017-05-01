package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.Controller.GameController;

import java.util.List;

public class UIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {

    private GamePlayer randomPlayer;
    private GamePlayer smartPlayer;
    //    private PlayerMode playerMode;
    private int playerId;
    private GameTable<S, A> gameTable;

    public UIController(int playerId, GamePlayer randomPlayer, GamePlayer smartPlayer, GameTable<S, A> gameTable) {
        this.playerId = playerId;
        this.randomPlayer = randomPlayer;
        this.smartPlayer = smartPlayer;
        this.gameTable = gameTable;
    }

    @Override
    public void makeManualMove(A a) {
        gameTable.execute(a);
    }

    @Override
    public void makeRandomMove() {

    }

    @Override
    public void makeSmartMove() {

    }

    @Override
    public void restartGame() {

    }

    @Override
    public void stopGame() {

    }

    @Override
    public void handleEvent(GameEvent<S, A> e) {

    }

    @Override
    public int getPlayerId() {
        return playerId;
    }

    @Override
    public List<GamePlayer> getGamePlayers() {
        return gameTable.getGamePlayers();
    }

    @Override
    public void notifyInterfaceNeedBeUpdated() {
        gameTable.notifyInterfaceNeedBeUpdated();
    }
}
