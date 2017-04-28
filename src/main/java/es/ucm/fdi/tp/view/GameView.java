package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.GameName;
import es.ucm.fdi.tp.ttt.TttState;
import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import java.awt.*;

public class GameView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JFrame {

    private S state;

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
        ControlPanel controlPanel = new ControlPanel(gameController);
//        Color.decode("#eeeeee");

        controlPanel.setBackground(Color.BLUE);
        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        TttView tttView = new TttView(gameController, (TttState) state);
        System.out.println();
        tttView.setOpaque(true);
        this.getContentPane().add(tttView, BorderLayout.CENTER);
    }

}
