package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameName;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.ttt.TttState;
import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class GameView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JFrame implements GameObserver<S, A> {

    private S state;
    private RectBoardView rectBoardView;
    private GameController gameController;

    public GameView(S state) {
        super("Mi primera ventana - GameView");
        this.state = state;
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        JLabel lblEste = new JLabel("region este");
        lblEste.setBackground(Color.GREEN);
        lblEste.setOpaque(true);
        this.getContentPane().add(lblEste, BorderLayout.EAST);

        JLabel bottomLabel = new JLabel("region centerLabel");
        bottomLabel.setBackground(Color.RED);
        bottomLabel.setOpaque(true);
        this.getContentPane().add(bottomLabel, BorderLayout.SOUTH);
    }

    public void createGameView(GameName gameType, GameController gameController) {
        this.gameController = gameController;
        this.setTitle("Jugador " +gameController.getPlayerId());
        ControlPanel controlPanel = new ControlPanel(gameController);
//        Color.decode("#eeeeee");

        controlPanel.setBackground(Color.BLUE);
        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        rectBoardView = new TttView(gameController, (TttState)state);
        System.out.println();
        rectBoardView.setOpaque(true);
        this.getContentPane().add(rectBoardView, BorderLayout.CENTER);
    }

    public void isEnable() {
        this.setEnabled(state.getTurn() == gameController.getPlayerId());
    }

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        switch (e.getType()) {
            case Start:
                System.out.println(e.toString() + System.getProperty("line.separator"));
                break;
            case Change:
                this.state = e.getState();
                rectBoardView.update(e.getState());
//                System.out.print("After action: " + System.getProperty("line.separator") + e.getState() + System.getProperty("line.separator"));
                break;
            case Stop:
                System.out.println(e.toString() + System.getProperty("line.separator"));
                break;
            default:
                break;
        }
        isEnable();
    }

}
