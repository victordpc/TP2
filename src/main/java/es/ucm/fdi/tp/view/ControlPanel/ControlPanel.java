package es.ucm.fdi.tp.view.ControlPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView
		implements ActionListener {

	private GameController<S, A> gameController;

	private final String DICE_ICON_PATH = "/dice.png";
	private final String EXIT_ICON_PATH = "/exit.png";
	private final String NERD_ICON_PATH = "/nerd.png";
	private final String RESTART_ICON_PATH = "/restart.png";
    private final String BRAIN_ICON_PATH = "/brain.png";
    private final String TIMER_ICON_PATH = "/timer.png";
    private final String STOP_ICON_PATH = "/stop.png";

	private final String[] playerModes = { "Manual", "Random", "Smart" };
	private List<ControlPanelObservable> controlPanelObservables;

	// Buttons
	private JButton randomMoveButton;
	private JButton smartMoveButton;

	private enum EventType {
		ComboBoxEvent, ButtonEvent
	}

	private enum ActionType {
		RandomMove, SmartMove, Restart, Stop
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
		JToolBar manualMovesToolBar = new JToolBar();
		manualMovesToolBar.add(randomMoveButton);
		manualMovesToolBar.addSeparator(); // añade un separador

		smartMoveButton = new JButton();
		ImageIcon nerdIcon = new ImageIcon(getClass().getResource(NERD_ICON_PATH));
		smartMoveButton.setIcon(nerdIcon);
		smartMoveButton.setActionCommand("SmartMove");
		smartMoveButton.addActionListener(this);
		manualMovesToolBar.add(smartMoveButton);
		manualMovesToolBar.addSeparator(); // añade un separador

		JButton restartButton = new JButton();
		ImageIcon restartIcon = new ImageIcon(getClass().getResource(RESTART_ICON_PATH));
		restartButton.setIcon(restartIcon);
		restartButton.setActionCommand("Restart");
		restartButton.addActionListener(this);
		manualMovesToolBar.add(restartButton);
		manualMovesToolBar.addSeparator(); // añade un separador

		JButton exitButton = new JButton();
		ImageIcon exitIcon = new ImageIcon(getClass().getResource(EXIT_ICON_PATH));
		exitButton.setIcon(exitIcon);
		exitButton.setActionCommand("Stop");
		exitButton.addActionListener(this);
		manualMovesToolBar.add(exitButton);
		manualMovesToolBar.addSeparator(); // añade un separador

		JLabel playerModeLabel = new JLabel("Player Mode: ");
		manualMovesToolBar.add(playerModeLabel);
		JComboBox playerModeList = new JComboBox(playerModes);
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
            private Insets customInsets = new Insets(20, 10, 10, 10);

            @Override
            public Insets getBorderInsets(Component c) {
                return customInsets;
            }
        };
        smartMovesToolBar.setBorder(borderLayout);

        createThreadsSpinner(smartMovesToolBar);
        createTimerSpinner(smartMovesToolBar);
        JButton stopButton = new JButton();
        ImageIcon stopIcon = new ImageIcon(getClass().getResource(STOP_ICON_PATH));
        stopButton.setIcon(stopIcon);
        smartMovesToolBar.add(stopButton);
        add(smartMovesToolBar);
    }

	private void createThreadsSpinner(JToolBar smartMovesToolBar) {
        ImageIcon smartIcon = new ImageIcon(getClass().getResource(BRAIN_ICON_PATH));
        JLabel smartIconLabel = new JLabel(smartIcon);
        smartMovesToolBar.add(smartIconLabel);
        JLabel titleLabel = new JLabel("threads");
        SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, null, 1); //default value,lower bound,upper bound,increment by
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                textField.setText(spinner.getValue().toString());
            }
        });
        smartMovesToolBar.add(spinner);
        smartMovesToolBar.add(titleLabel);
    }

    private void createTimerSpinner(JToolBar smartMovesToolBar) {
        ImageIcon timerIcon = new ImageIcon(getClass().getResource(TIMER_ICON_PATH));
        JLabel timerIconLabel = new JLabel(timerIcon);
        smartMovesToolBar.add(timerIconLabel);
        JLabel titleLabel = new JLabel("ms.");
        double min = 1.0;
        double step = 0.1;
        SpinnerModel spinnerModel = new SpinnerNumberModel(min, min, null, step);
        JSpinner spinner = new JSpinner(spinnerModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
        spinner.setEditor(editor);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
//                textField.setText(spinner.getValue().toString());
            }
        });
        smartMovesToolBar.add(spinner);
        smartMovesToolBar.add(titleLabel);
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
		int res = 0;
		res = JOptionPane.showConfirmDialog(this, "¿Desea cerrar el juego?", "Confirme cierre",
				JOptionPane.YES_NO_OPTION);
		return res == JOptionPane.YES_OPTION;
	}

	private void setUpButtons(PlayerType playerType) {
		switch (playerType) {
		case MANUAL:
			smartMoveButton.setEnabled(true);
			randomMoveButton.setEnabled(true);
			break;
		case SMART:
			smartMoveButton.setEnabled(false);
			randomMoveButton.setEnabled(false);
			gameController.makeSmartMove();
			break;
		case RANDOM:
			smartMoveButton.setEnabled(false);
			randomMoveButton.setEnabled(false);
			gameController.makeRandomMove();
			break;
		default:
			break;
		}
	}
}
