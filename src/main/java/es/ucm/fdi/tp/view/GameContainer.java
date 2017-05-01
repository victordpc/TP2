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

        InfoView infoView = new InfoView();
        infoView.setOpaque(true);
        this.add(infoView, BorderLayout.EAST);

    }

    //Color.decode("#eeeeee");
//    public void createGameView(GameName gameType) {
//        ControlPanel controlPanel = new ControlPanel(gameController);
//        controlPanel.setBackground(Color.decode("#eeeeee"));
//        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
//        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
////
////        rectBoardView = new TttView(gameController, (TttState)state);
////        System.out.println();
////        rectBoardView.setOpaque(true);
////        this.window.getContentPane().add(rectBoardView, BorderLayout.CENTER);
//    }

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        switch (e.getType()) {
            case Start:
                System.out.println(e.toString() + System.getProperty("line.separator"));
                break;
            case Change:
                rectBoardView.update(e.getState());
//                System.out.print("After action: " + System.getProperty("line.separator") + e.getState() + System.getProperty("line.separator"));
                break;
            case Stop:
                System.out.println(e.toString() + System.getProperty("line.separator"));
                break;
            default:
                break;
        }

    }

    @Override
    public void update(GameState state) {

    }

    @Override
    public void setMessageViewer(MessageViewer infoViewer) {

    }

    @Override
    public void setGameController(GameController gameCtrl) {

    }
}
