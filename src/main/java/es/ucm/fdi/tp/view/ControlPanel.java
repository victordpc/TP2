package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView implements ActionListener {

    private GameController<S, A> gameController;

    private final String DICE_ICON_PATH = "/dice.png";
    private final String EXIT_ICON_PATH = "/exit.png";
    private final String NERD_ICON_PATH = "/nerd.png";
    private final String RESTART_ICON_PATH = "/restart.png";
    private final String[] playerModes = {"Manual", "Random", "Automatic"};

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
        toolBar.addSeparator(); //añade un separador

        JComboBox playerModeList = new JComboBox(playerModes);
        playerModeList.addActionListener(this);
        toolBar.add(playerModeList);
        toolBar.addSeparator(); //añade un separador

        toolBar.setFloatable(false); //impide que se pueda mover de su sitio
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        add(toolBar);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String playerMode = (String)cb.getSelectedItem();
       System.out.println("Mode selected " +playerMode);
    }
}
