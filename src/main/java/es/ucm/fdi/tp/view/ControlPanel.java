package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>>  extends UIComponent {

    private GameController<S, A> gameController;

    private final String DICE_ICON_PATH = "/dice.png";
    private final String EXIT_ICON_PATH = "/exit.png";
    private final String NERD_ICON_PATH = "/nerd.png";
    private final String RESTART_ICON_PATH = "/restart.png";

    public ControlPanel(GameController gameController) {
        this.gameController = gameController;
        initGUI();
    }

    private void initGUI() {
        JButton randomMoveButton = new JButton();
        ImageIcon randomIcon = new ImageIcon(getClass().getResource(DICE_ICON_PATH));
        randomMoveButton.setIcon(randomIcon);

        JToolBar toolBar = new JToolBar("Still draggable");
        toolBar.add(randomMoveButton);
        toolBar.addSeparator(); //añade un separador

        JButton smartMoveButton = new JButton("");
        ImageIcon nerdIcon = new ImageIcon(getClass().getResource(NERD_ICON_PATH));
        smartMoveButton.setIcon(nerdIcon);
        toolBar.add(smartMoveButton);
        toolBar.addSeparator(); //añade un separador

        JButton restartButton = new JButton("");
        ImageIcon restartIcon = new ImageIcon(getClass().getResource(RESTART_ICON_PATH));
        restartButton.setIcon(restartIcon);
        toolBar.add(restartButton);
        toolBar.addSeparator(); //añade un separador

        JButton exitButton = new JButton("");
        ImageIcon exitIcon = new ImageIcon(getClass().getResource(EXIT_ICON_PATH));
        exitButton.setIcon(exitIcon);
        toolBar.add(exitButton);

        toolBar.setFloatable(false); //impide que se pueda mover de su sitio
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        add(toolBar);
    }

    @Override
    public void update(GameState state) {

    }
}
