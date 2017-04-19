package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameTable;

import java.util.List;

public class ConsoleController<S extends GameState<S, A>, A extends GameAction<S, A>> extends GameController {

    private List<GamePlayer> players;
    private GameTable<S, A> game;

    public ConsoleController(List<GamePlayer> players, GameTable<S, A> game) {
        this.players = players;
        this.game = game;
    }

    @Override
    public void run() {
        game.start();
        while (!game.getState().isFinished()) {
            // request move
            A action = players.get(game.getState().getTurn()).requestAction(game.getState());
            game.execute(action);
        }
        System.out.print("Ganador: " +players.get(game.getState().getWinner()).getName() + System.getProperty("line.separator"));
    }
}
