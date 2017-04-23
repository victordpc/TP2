package es.ucm.fdi.tp.view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GameView extends JFrame {

    private static BufferedImage bi;


    public GameView () {
        super("Mi primera ventana - GameView");
        this.setSize(600, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BorderLayout());

        ControlPanel controlPanel = new ControlPanel(new GameController() {
            @Override
            public void run() {

            }
        });
//        Color.decode("#eeeeee");

        controlPanel.setBackground(Color.BLUE);
        this.getContentPane().add(controlPanel, BorderLayout.NORTH);
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));

        JLabel lblEste = new JLabel("region este");
        lblEste.setBackground(Color.GREEN);
        lblEste.setOpaque(true);
        this.getContentPane().add(lblEste,BorderLayout.EAST);

        JLabel westLabel = new JLabel("region oeste");
        westLabel.setBackground(Color.YELLOW);
        westLabel.setOpaque(true);
        this.getContentPane().add(westLabel, BorderLayout.WEST);

        TttView tttView = new TttView();
        System.out.println();
        tttView.setOpaque(true);
        this.getContentPane().add(tttView, BorderLayout.CENTER);


        JLabel bottomLabel = new JLabel("region centerLabel");
        bottomLabel.setBackground(Color.RED);
        bottomLabel.setOpaque(true);
        this.getContentPane().add(bottomLabel, BorderLayout.SOUTH);
    }

}
