package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameName;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.InfoView;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.swing.*;
import java.awt.*;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView implements GameObserver<S, A> {

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
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(rectBoardView, BorderLayout.CENTER);

        infoView = new InfoView(gameController.getGamePlayers());
        infoView.setOpaque(true);
        this.add(infoView, BorderLayout.EAST);

    }

    //Color.decode("#eeeeee");

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        switch (e.getType()) {
            case Start:
                infoView.setContent(e.toString());
                break;
            case Change:
                rectBoardView.update(e.getState());
//                System.out.print("After action: " + System.getProperty("line.separator") + e.getState() + System.getProperty("line.separator"));
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
    public void setGameController(GameController gameCtrl) {

    }
}
