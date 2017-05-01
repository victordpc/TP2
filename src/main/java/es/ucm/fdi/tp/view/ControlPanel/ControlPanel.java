package es.ucm.fdi.tp.view.ControlPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView implements ActionListener {

    private GameController<S, A> gameController;

    private final String DICE_ICON_PATH = "/dice.png";
    private final String EXIT_ICON_PATH = "/exit.png";
    private final String NERD_ICON_PATH = "/nerd.png";
    private final String RESTART_ICON_PATH = "/restart.png";
    private final String[] playerModes = {"Manual", "Random", "Smart"};
    private List<ControlPanelObservable> controlPanelObservables;

    //Buttons
    private JButton randomMoveButton;
    private JButton smartMoveButton;

    private enum EventType {
        ComboBoxEvent,
        ButtonEvent
    }

    private enum ActionType {
        RandomMove,
        SmartMove,
        Restart,
        Exit
    }

    public ControlPanel(GameController gameController) {
        this.gameController = gameController;
        this.controlPanelObservables = new ArrayList<>();
        initGUI();
    }

    private void initGUI() {
        randomMoveButton = new JButton();
        ImageIcon randomIcon = new ImageIcon(getClass().getResource(DICE_ICON_PATH));
        randomMoveButton.setIcon(randomIcon);
        randomMoveButton.setActionCommand("RandomMove");
        randomMoveButton.addActionListener(this);
        JToolBar toolBar = new JToolBar("Still draggable");
        toolBar.add(randomMoveButton);
        toolBar.addSeparator(); //añade un separador

        smartMoveButton = new JButton();
        ImageIcon nerdIcon = new ImageIcon(getClass().getResource(NERD_ICON_PATH));
        smartMoveButton.setIcon(nerdIcon);
        smartMoveButton.setActionCommand("SmartMove");
        smartMoveButton.addActionListener(this);
        toolBar.add(smartMoveButton);
        toolBar.addSeparator(); //añade un separador

        JButton restartButton = new JButton();
        ImageIcon restartIcon = new ImageIcon(getClass().getResource(RESTART_ICON_PATH));
        restartButton.setIcon(restartIcon);
        restartButton.setActionCommand("Restart");
        restartButton.addActionListener(this);
        toolBar.add(restartButton);
        toolBar.addSeparator(); //añade un separador

        JButton exitButton = new JButton();
        ImageIcon exitIcon = new ImageIcon(getClass().getResource(EXIT_ICON_PATH));
        exitButton.setIcon(exitIcon);
        exitButton.setActionCommand("Exit");
        exitButton.addActionListener(this);
        toolBar.add(exitButton);
        toolBar.addSeparator(); //añade un separador

        JComboBox playerModeList = new JComboBox(playerModes);
        playerModeList.setActionCommand("ComboBoxEvent");
        playerModeList.addActionListener(this);
        toolBar.add(playerModeList);
        toolBar.addSeparator(); //añade un separador

        toolBar.setFloatable(false); //impide que se pueda mover de su sitio
        toolBar.setOrientation(JToolBar.HORIZONTAL);
        add(toolBar);
    }

    public void addControlPanelObserver(ControlPanelObservable newObserver) {
        controlPanelObservables.add(newObserver);
    }

    private void notifyPlayerModeHasChanged(PlayerType playerType) {
        for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
            controlPanelObservable.playerModeHasChange(playerType);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == EventType.ComboBoxEvent.toString()) {
            JComboBox cb = (JComboBox) e.getSource();
            String playerMode = (String) cb.getSelectedItem();
            PlayerType playerType = PlayerType.valueOf(playerMode.toUpperCase());
            notifyPlayerModeHasChanged(playerType);
            setUpButtons(playerType);
        } else {
            switch (ActionType.valueOf(e.getActionCommand())) {
                case RandomMove:
                    gameController.makeRandomMove();
                    break;
                case SmartMove:
                    gameController.makeSmartMove();
                    break;
                case Restart:
                    break;
                case Exit:
                    break;
            }
        }
    }

    private void setUpButtons(PlayerType playerType) {
        switch (playerType) {
            case MANUAL:
                smartMoveButton.setEnabled(true);
                randomMoveButton.setEnabled(true);
                break;
            case SMART:
            case RANDOM:
                smartMoveButton.setEnabled(false);
                randomMoveButton.setEnabled(false);
                break;
        }
    }
}
