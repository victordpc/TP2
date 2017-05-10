package es.ucm.fdi.tp.view.ControlPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
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
	private final String[] playerModes = { "Manual", "Random", "Smart" };
	private List<ControlPanelObservable> controlPanelObservables;

	// Buttons
	private JButton randomMoveButton;
	private JButton smartMoveButton;

	private RandomPlayer randPlayer;

	private SmartPlayer smartPlayer;

	private enum EventType {
		ComboBoxEvent, ButtonEvent
	}

	private enum ActionType {
		RandomMove, SmartMove, Restart, Stop
	}

	public ControlPanel(GameController gameController, int idJugador) {
		this.gameController = gameController;
		this.controlPanelObservables = new ArrayList<>();
		this.randPlayer = new RandomPlayer("dummy");
		this.randPlayer.join(idJugador);
		this.smartPlayer = new SmartPlayer("dummy", 5);
		this.smartPlayer.join(idJugador);
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
		toolBar.addSeparator(); // añade un separador

		smartMoveButton = new JButton();
		ImageIcon nerdIcon = new ImageIcon(getClass().getResource(NERD_ICON_PATH));
		smartMoveButton.setIcon(nerdIcon);
		smartMoveButton.setActionCommand("SmartMove");
		smartMoveButton.addActionListener(this);
		toolBar.add(smartMoveButton);
		toolBar.addSeparator(); // añade un separador

		JButton restartButton = new JButton();
		ImageIcon restartIcon = new ImageIcon(getClass().getResource(RESTART_ICON_PATH));
		restartButton.setIcon(restartIcon);
		restartButton.setActionCommand("Restart");
		restartButton.addActionListener(this);
		toolBar.add(restartButton);
		toolBar.addSeparator(); // añade un separador

		JButton exitButton = new JButton();
		ImageIcon exitIcon = new ImageIcon(getClass().getResource(EXIT_ICON_PATH));
		exitButton.setIcon(exitIcon);
		exitButton.setActionCommand("Stop");
		exitButton.addActionListener(this);
		toolBar.add(exitButton);
		toolBar.addSeparator(); // añade un separador

		JLabel playerModeLabel = new JLabel("Player Mode: ");
		toolBar.add(playerModeLabel);
		JComboBox playerModeList = new JComboBox(playerModes);
		playerModeList.setActionCommand("ComboBoxEvent");
		playerModeList.addActionListener(this);
		toolBar.add(playerModeList);
		toolBar.addSeparator(); // añade un separador

		toolBar.setFloatable(false); // impide que se pueda mover de su sitio
		toolBar.setOrientation(SwingConstants.HORIZONTAL);
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
			gameController.makeSmartMove(this.smartPlayer);
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
}
