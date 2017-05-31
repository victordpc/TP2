package es.ucm.fdi.tp.view.ControlPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> implements ActionListener {

    private static final long serialVersionUID = -7821939007784233719L;

    private enum ActionType {
        RandomMove, Restart, SmartMove, Stop
    }

    private enum EventType {
        ButtonEvent, ComboBoxEvent
    }

    private GameController<S, A> gameController;
    private List<ControlPanelObservable> controlPanelObservables;
    private JButton randomMoveButton;
    private JButton smartMoveButton;
    private String RESOURCES_PATH = "src/main/resources/";
    private int playerId;

    public ControlPanel(GameController<S, A> gameController, int idJugador) {
        this.gameController = gameController;
        this.controlPanelObservables = new ArrayList<>();
        this.playerId = idJugador;
        initGUI();
    }

    private void initGUI() {
        randomMoveButton = new JButton();
        ImageIcon randomIcon = new ImageIcon(RESOURCES_PATH+"dice.png");
        randomMoveButton.setIcon(randomIcon);
        randomMoveButton.setActionCommand("RandomMove");
        randomMoveButton.addActionListener(this);
        JToolBar manualMovesToolBar = new JToolBar();
        manualMovesToolBar.add(randomMoveButton);
        manualMovesToolBar.addSeparator(); // añade un separador

        smartMoveButton = new JButton();
        ImageIcon nerdIcon = new ImageIcon(RESOURCES_PATH+"/nerd.png");
        smartMoveButton.setIcon(nerdIcon);
        smartMoveButton.setActionCommand("SmartMove");
        smartMoveButton.addActionListener(this);
        manualMovesToolBar.add(smartMoveButton);
        manualMovesToolBar.addSeparator(); // añade un separador

        JButton restartButton = new JButton();
        ImageIcon restartIcon = new ImageIcon(RESOURCES_PATH+"/restart.png");
        restartButton.setIcon(restartIcon);
        restartButton.setActionCommand("Restart");
        restartButton.addActionListener(this);
        manualMovesToolBar.add(restartButton);
        manualMovesToolBar.addSeparator(); // añade un separador

        JButton exitButton = new JButton();
        ImageIcon exitIcon = new ImageIcon(RESOURCES_PATH+"/exit.png");
        exitButton.setIcon(exitIcon);
        exitButton.setActionCommand("Stop");
        exitButton.addActionListener(this);
        manualMovesToolBar.add(exitButton);
        manualMovesToolBar.addSeparator(); // añade un separador

        JLabel playerModeLabel = new JLabel("Player Mode: ");
        manualMovesToolBar.add(playerModeLabel);
        JComboBox<PlayerType> playerModeList = new JComboBox<>(PlayerType.values());
        playerModeList.setActionCommand("ComboBoxEvent");
        playerModeList.addActionListener(this);
        manualMovesToolBar.add(playerModeList);
        manualMovesToolBar.addSeparator(); // añade un separador

        manualMovesToolBar.setFloatable(false); // impide que se pueda mover de su sitio
        manualMovesToolBar.setOrientation(SwingConstants.HORIZONTAL);
        add(manualMovesToolBar);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == EventType.ComboBoxEvent.toString()) {
            JComboBox<PlayerType> cb = (JComboBox<PlayerType>) e.getSource();
            PlayerType playerType = (PlayerType) cb.getSelectedItem();
            notifyPlayerModeHasChanged(playerType);
        } else {
            switch (ActionType.valueOf(e.getActionCommand())) {
                case RandomMove:
                    this.notifyMakeAutomaticMove(PlayerType.RANDOM);
                    break;
                case SmartMove:
                    this.notifyMakeAutomaticMove(PlayerType.SMART);
                    break;
                case Restart:
                    gameController.restartGame();
                    break;
                case Stop:
                    if (preguntaCerrar()) {
                        gameController.stopGame();
                    }
                    break;
            }
        }
    }

    private boolean preguntaCerrar() {
        int res = JOptionPane.showConfirmDialog(this, "¿Desea cerrar el juego?", "Confirme cierre", JOptionPane.YES_NO_OPTION);
        return res == JOptionPane.YES_OPTION;
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
                notifyMakeAutomaticMove(playerType);
                break;
            default:
                break;
        }
    }

    public void addControlPanelObserver(ControlPanelObservable newObserver) {
        controlPanelObservables.add(newObserver);
    }

    private void notifyPlayerModeHasChanged(PlayerType playerType) {
        for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
            controlPanelObservable.playerModeHasChange(playerType, playerId);
        }
    }

    private void notifyMakeAutomaticMove(PlayerType playerType) {
        for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
            controlPanelObservable.makeAutomaticMove(playerType);
        }
    }

    @Override
    public void update(S state) {}

    @Override
    public void setGameController(GameController<S, A> gameCtrl) {}

    @Override
    public void setMessageViewer(MessageViewer<S, A> infoViewer) {}
}
