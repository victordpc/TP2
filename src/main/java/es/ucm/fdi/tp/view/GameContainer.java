package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.*;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanel;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanelObservable;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.InfoView;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayersInfoObserver;

import javax.swing.*;
import java.awt.*;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView implements GameObserver<S, A>, PlayersInfoObserver, ControlPanelObservable {

    private GUIView<S, A> rectBoardView;
    private GameController gameController;
    private InfoView infoView;

    public GameContainer(GUIView<S, A> gameView, GameController gameController, GameObservable<S, A> game) {
        this.setTitle("Jugador " +gameController.getPlayerId());
        this.setLayout(new BorderLayout(5, 5));
        this.rectBoardView = gameView;
        this.gameController = gameController;
        game.addObserver(this);
        initGUI();
    }

    public void initGUI() {
        ControlPanel controlPanel = new ControlPanel(gameController);
        controlPanel.setBackground(Color.decode("#eeeeee"));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.addControlPanelObserver(this);
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(rectBoardView, BorderLayout.CENTER);

        infoView = new InfoView(gameController.getGamePlayers(), this);
        infoView.setOpaque(true);
        this.add(infoView, BorderLayout.EAST);
    }

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        switch (e.getType()) {
            case Start:
                infoView.setContent(e.toString());
                rectBoardView.update(e.getState());
                infoView.repaintPlayersInfoViewer();
                break;
            case Change:
                rectBoardView.update(e.getState());
                infoView.repaintPlayersInfoViewer();
                if (e.getState().getTurn() ==  gameController.getPlayerId()) {
                    if (gameController.getPlayerMode() == PlayerType.RANDOM) {
                        gameController.makeRandomMove();
                    }else if(gameController.getPlayerMode() == PlayerType.SMART) {
                        gameController.makeSmartMove();
                    }
                }
                break;
            case Info:
                if (e.getState().getTurn() == gameController.getPlayerId()) {
                    infoView.addContent("Tu turno");
                }else {
                    infoView.addContent("Turno del jugador " + e.getState().getTurn());
                }
                break;
            case Error:
                infoView.addContent(e.getError().getMessage());
                break;
            case Stop:
                infoView.addContent(e.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public void setMessageViewer(MessageViewer messageViewer) {
        infoView.setMessageViewer(messageViewer);
    }

    @Override
    public void setGameController(GameController gameCtrl) {}

    @Override
    public void colorChanged(int player, Color color) {
        GamePlayer gamePlayer =  (GamePlayer)gameController.getGamePlayers().get(player);
        gamePlayer.setPlayerColor(color);
        gameController.notifyInterfaceNeedBeUpdated();
    }

    @Override
    public void playerModeHasChange(PlayerType newPlayerMode) {
        gameController.changePlayerMode (newPlayerMode);
        switch (newPlayerMode) {
            case MANUAL:
                rectBoardView.setEnabled(true);
                break;
            case SMART:
            case RANDOM:
                rectBoardView.setEnabled(false);
                break;
        }
    }
}
