package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.GUIView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public abstract class PlayersInfoViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView {

    protected List<PlayersInfoObserver> playersInfoObserverList = new ArrayList<>();

    public void addObserver(PlayersInfoObserver observer) {
        playersInfoObserverList.add(observer);
    }

    protected void notifyObservers(int player, Color color) {
        for(PlayersInfoObserver observer : playersInfoObserverList) {
            observer.colorChanged(player, color);
        }
    }

    public void setPlayersInfoViewer(PlayersInfoViewer<S, A> playersInfoViewer){}

    abstract public void setNumberOfPlayer(int i);
    abstract public void updateColors();

        /**
         * Used to consult the color assigned to a player
         * @param playerId the id of the player
         * @return the color assigned to the player.
         */
    abstract public Color getPlayerColor(int playerId);

    @Override
    public void update(GameState state) {

    }

    @Override
    public void setMessageViewer(MessageViewer messageViewer) {}

    @Override
    public void setGameController(GameController gameCtrl) {

    }
}
