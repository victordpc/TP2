package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.AiAlgorithm;
import es.ucm.fdi.tp.base.player.MinMax;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.Controller.GameController;

import java.util.List;
import java.util.Random;

public class UIController<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameController<S, A> {

    private GamePlayer randomPlayer;
    private GamePlayer smartPlayer;
    //    private PlayerMode playerMode;
    private int playerId;
    private GameTable<S, A> gameTable;
    private Random random = new Random();
    protected AiAlgorithm algorithm;
    private PlayerType playerType;

    public UIController(int playerId, GamePlayer randomPlayer, GamePlayer smartPlayer, GameTable<S, A> gameTable) {
        this.playerType = PlayerType.MANUAL;
        this.playerId = playerId;
        this.randomPlayer = randomPlayer;
        this.smartPlayer = smartPlayer;
        this.gameTable = gameTable;
        this.algorithm = new MinMax(5);
    }

    @Override
    public void makeManualMove(A a) {
        gameTable.execute(a);
    }

    @Override
    public void makeRandomMove() {
        List<A> valid = gameTable.getState().validActions(playerId);
        gameTable.execute(valid.get(random.nextInt(valid.size())));
    }

    @Override
    public void makeSmartMove() {
        A action = algorithm.chooseAction(playerId, gameTable.getState());
        gameTable.execute(action);
    }

    @Override
    public void restartGame() {
        gameTable.restartGame();
    }

    @Override
    public void stopGame() {
        System.exit(0);
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

    @Override
    public PlayerType getPlayerMode() {
        return playerType;
    }

    @Override
    public void changePlayerMode(PlayerType playerMode) {
        this.playerType = playerMode;
    }
}
