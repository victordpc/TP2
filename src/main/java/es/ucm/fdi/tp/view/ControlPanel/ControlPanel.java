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
    private ConcurrentAiPlayer concurrentAiPlayer;
    private JSpinner threadsSpinner;
    private JSpinner timeOutSpinner;
    private JButton stopSmartMoveButton;
    private JLabel smartIconLabel;
    private String RESOURCES_PATH = "src/main/resources/";

    public ControlPanel(GameController<S, A> gameController, int idJugador) {
        this.gameController = gameController;
        this.controlPanelObservables = new ArrayList<>();
        this.concurrentAiPlayer = new ConcurrentAiPlayer("Jugador " + idJugador);
        this.concurrentAiPlayer.join(idJugador);
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

        JToolBar smartMovesToolBar = new JToolBar();
        smartMovesToolBar.setFloatable(false);
        Border borderLayout = new TitledBorder("Smart Moves") {
            private static final long serialVersionUID = 5519939568486362218L;
            private Insets customInsets = new Insets(20, 10, 10, 10);

            @Override
            public Insets getBorderInsets(Component c) {
                return customInsets;
            }
        };
        smartMovesToolBar.setBorder(borderLayout);

        createThreadsSpinner(smartMovesToolBar);
        createTimerSpinner(smartMovesToolBar);
        stopSmartMoveButton = new JButton();
        stopSmartMoveButton.setEnabled(false);
        ImageIcon stopIcon = new ImageIcon(RESOURCES_PATH+"/stop.png");
        stopSmartMoveButton.setIcon(stopIcon);

        stopSmartMoveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControlPanel.this.notifyStopSmartPlayerMove();
                ControlPanel.this.setUpSmartPlayerAction(false);
            }
        });

        smartMovesToolBar.add(stopSmartMoveButton);
        add(smartMovesToolBar);
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

    private void createThreadsSpinner(JToolBar smartMovesToolBar) {
        ImageIcon smartIcon = new ImageIcon(RESOURCES_PATH+"/brain.png");
        smartIconLabel = new JLabel(smartIcon);
        smartIconLabel.setOpaque(true);
        smartMovesToolBar.add(smartIconLabel);
        JLabel titleLabel = new JLabel("threads");
        int processors = Runtime.getRuntime().availableProcessors();
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, processors, 1);
        threadsSpinner = new JSpinner(spinnerModel);
        smartMovesToolBar.add(threadsSpinner);
        smartMovesToolBar.add(titleLabel);
    }

    private void createTimerSpinner(JToolBar smartMovesToolBar) {
        ImageIcon timerIcon = new ImageIcon(RESOURCES_PATH+"/timer.png");
        JLabel timerIconLabel = new JLabel(timerIcon);
        smartMovesToolBar.add(timerIconLabel);
        JLabel titleLabel = new JLabel("ms.");
        double min = 500;
        double max = 5000;
        double step = 500;
        SpinnerModel spinnerModel = new SpinnerNumberModel(min, min, max, step);
        timeOutSpinner = new JSpinner(spinnerModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(timeOutSpinner);
        timeOutSpinner.setEditor(editor);
        smartMovesToolBar.add(timeOutSpinner);
        smartMovesToolBar.add(titleLabel);
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
            controlPanelObservable.playerModeHasChange(playerType);
        }
    }

    private void notifyMakeAutomaticMove(PlayerType playerType) {
        for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
            controlPanelObservable.makeAutomaticMove(playerType);
        }
    }

    protected void notifyStopSmartPlayerMove() {
        for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
            controlPanelObservable.stopSmartPlayerAction();
        }
    }

    public int getConcurrentPlayerThreads() {
        return Integer.parseInt(threadsSpinner.getValue().toString());
    }

    public int getConcurrentPlayerTimeOut() {
        return (int) Double.parseDouble(timeOutSpinner.getValue().toString());
    }

    public void setUpSmartPlayerAction(Boolean isThinking) {
        stopSmartMoveButton.setEnabled(isThinking);
        if (isThinking) {
            smartIconLabel.setBackground(Color.yellow);
        }
        smartIconLabel.setOpaque(isThinking);
        smartIconLabel.repaint();
    }



    @Override
    public void update(S state) {}

    @Override
    public void setGameController(GameController<S, A> gameCtrl) {}

    @Override
    public void setMessageViewer(MessageViewer<S, A> infoViewer) {}
}
