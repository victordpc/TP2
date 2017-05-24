package es.ucm.fdi.tp.view.ControlPanel;

import java.awt.Component;
import java.awt.Insets;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;

public class ControlPanel<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A>
		implements ActionListener {
	private static final long serialVersionUID = -7821939007784233719L;

	private enum ActionType {
		RandomMove, Restart, SmartMove, Stop
	}

	private enum EventType {
		ButtonEvent, ComboBoxEvent
	}

	private List<ControlPanelObservable> controlPanelObservables;
	private final String DICE_ICON_PATH = "/dice.png";
	private final String EXIT_ICON_PATH = "/exit.png";
	private final String NERD_ICON_PATH = "/nerd.png";
	private final String RESTART_ICON_PATH = "/restart.png";
	private final String STOP_ICON_PATH = "/stop.png";
	private final String BRAIN_ICON_PATH = "/brain.png";
	private final String TIMER_ICON_PATH = "/timer.png";

	private GameController<S, A> gameController;

	private JButton randomMoveButton;
	private JButton smartMoveButton;
	private RandomPlayer randPlayer;
	private SmartPlayer smartPlayer;

    private final String RESTART_ICON_PATH = "/restart.png";
    private final String STOP_ICON_PATH = "/stop.png";
    private final String TIMER_ICON_PATH = "/timer.png";

	// Buttons
	private JButton randomMoveButton;
    private ConcurrentAiPlayer concurrentAiPlayer;
	private RandomPlayer randPlayer;

	private JButton smartMoveButton;

	public ControlPanel(GameController<S, A> gameController, int idJugador) {
		this.gameController = gameController;
		this.controlPanelObservables = new ArrayList<>();
		this.randPlayer = new RandomPlayer("dummy");
		this.randPlayer.join(idJugador);
		this.smartPlayer = new SmartPlayer("dummy", 5);
		this.smartPlayer.join(idJugador);
		initGUI();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == EventType.ComboBoxEvent.toString()) {
			JComboBox<PlayerType> cb = (JComboBox<PlayerType>) e.getSource();
			PlayerType playerType = (PlayerType) cb.getSelectedItem();
			notifyPlayerModeHasChanged(playerType);
			setUpButtons(playerType);
		} else {
			switch (ActionType.valueOf(e.getActionCommand())) {
			case RandomMove:
				gameController.makeRandomMove(this.randPlayer);
				break;
			case SmartMove:
				gameController.makeSmartMove(this.smartPlayer);
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

	public void addControlPanelObserver(ControlPanelObservable newObserver) {
		controlPanelObservables.add(newObserver);
	}

	private void createThreadsSpinner(JToolBar smartMovesToolBar) {
		ImageIcon smartIcon = new ImageIcon(getClass().getResource(BRAIN_ICON_PATH));
		JLabel smartIconLabel = new JLabel(smartIcon);
		smartMovesToolBar.add(smartIconLabel);
		JLabel titleLabel = new JLabel("threads");
		int processors = Runtime.getRuntime().availableProcessors();
		SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, processors, 1);
		JSpinner spinner = new JSpinner(spinnerModel);
		smartMovesToolBar.add(spinner);
		smartMovesToolBar.add(titleLabel);
	}

	private void createTimerSpinner(JToolBar smartMovesToolBar) {
		ImageIcon timerIcon = new ImageIcon(getClass().getResource(TIMER_ICON_PATH));
		JLabel timerIconLabel = new JLabel(timerIcon);
		smartMovesToolBar.add(timerIconLabel);
		JLabel titleLabel = new JLabel("ms.");
		double min = 500;
		double max = 5000;
		double step = 500;
		SpinnerModel spinnerModel = new SpinnerNumberModel(min, min, max, step);
		JSpinner spinner = new JSpinner(spinnerModel);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner);
		spinner.setEditor(editor);
		smartMovesToolBar.add(spinner);
		smartMovesToolBar.add(titleLabel);
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
		JComboBox<PlayerType> playerModeList = new JComboBox<>(PlayerType.values());
		playerModeList.setActionCommand("ComboBoxEvent");
		playerModeList.addActionListener(this);
		manualMovesToolBar.add(playerModeList);
		manualMovesToolBar.addSeparator(); // añade un separador

		manualMovesToolBar.setFloatable(false); // impide que se pueda mover de
												// su sitio
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
		JButton stopButton = new JButton();
		stopButton.setEnabled(false);
		ImageIcon stopIcon = new ImageIcon(getClass().getResource(STOP_ICON_PATH));
		stopButton.setIcon(stopIcon);

		stopButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ControlPanel.this.stopThreads();
			}
		});

		smartMovesToolBar.add(stopButton);
		add(smartMovesToolBar);
	}

	protected void stopThreads() {
		// TODO Auto-generated method stub

	}

	private void notifyPlayerModeHasChanged(PlayerType playerType) {
		for (ControlPanelObservable controlPanelObservable : controlPanelObservables) {
			controlPanelObservable.playerModeHasChange(playerType);
		}
	}

	private boolean preguntaCerrar() {
		int res = JOptionPane.showConfirmDialog(this, "¿Desea cerrar el juego?", "Confirme cierre",
				JOptionPane.YES_NO_OPTION);
		return res == JOptionPane.YES_OPTION;
	}

	@Override
	public void setGameController(GameController<S, A> gameCtrl) {
	}

	@Override
	public void setMessageViewer(MessageViewer<S, A> infoViewer) {
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
			gameController.makeSmartMove(this.concurrentAiPlayer);
			break;
		case RANDOM:
			smartMoveButton.setEnabled(false);
			randomMoveButton.setEnabled(false);
			gameController.makeRandomMove(this.randPlayer);
			break;
		default:
			break;
		}
	}

	@Override
	public void update(S state) {
	}
}
