package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.launcher.Main.TypePlayer;

public class windowView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JFrame {
	private static final long serialVersionUID = -8296742231165949155L;
	private GUIController<S, A> controller;
	private S gameState;
	private GameWindow<S, A> padre;
	private JTextArea infoArea;
	JPanel mainPanel;

	public windowView(String string, GameWindow<S, A> father) {
		super(string);
		padre = father;
		this.setSize(new Dimension(500, 500));
	}

	public void drawGUI(GUIController<S, A> controlador) {
		this.controller = controlador;

		/* Añadimos los paneles en las regiones */

		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		JPanel pNorth = new JPanel();
		pNorth.setLayout(new FlowLayout(FlowLayout.LEFT));
		mainPanel.add(pNorth, BorderLayout.NORTH);

		JPanel pEast = new JPanel();
		pEast.setLayout(new GridLayout(2, 1));
		mainPanel.add(pEast, BorderLayout.EAST);

		JPanel pCenter = new JPanel();
		mainPanel.add(pCenter, BorderLayout.CENTER);

		this.add(mainPanel);

		/* Rellenamos panel norte */

		JToolBar toolBar = new JToolBar();
		toolBar.setOrientation(JToolBar.HORIZONTAL);
		pNorth.add(toolBar);

		JButton randGame = new JButton();
		randGame.setIcon(new ImageIcon(this.getClass().getResource("dice.png")));
		randGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doRandMove(gameState);
			}
		});
		toolBar.add(randGame);

		JButton nerdGame = new JButton();
		nerdGame.setIcon(new ImageIcon(this.getClass().getResource("nerd.png")));
		nerdGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.doSmartMove(gameState);
			}
		});
		toolBar.add(nerdGame);

		JButton restartGame = new JButton();
		restartGame.setIcon(new ImageIcon(this.getClass().getResource("restart.png")));
		restartGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.startGame();
			}
		});
		toolBar.add(restartGame);

		JButton closeGame = new JButton();
		closeGame.setIcon(new ImageIcon(this.getClass().getResource("exit.png")));
		closeGame.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				int res = JOptionPane.showConfirmDialog(windowView.this,
						"Esta acción cerrará la aplicación, ¿desea continuar?", "Confirmación de cierre",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (res == JOptionPane.YES_OPTION)
					System.exit(0);
			}
		});
		toolBar.add(closeGame);

		toolBar.addSeparator();

		JLabel tipoJugador = new JLabel("Tipo de jugador:");
		toolBar.add(tipoJugador);

		JComboBox<TypePlayer> typePlayerChooser = new JComboBox<TypePlayer>(TypePlayer.values());
		typePlayerChooser.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				padre.changePlayerType((TypePlayer) typePlayerChooser.getSelectedItem());
			}
		});
		toolBar.add(typePlayerChooser);

		toolBar.addSeparator();

		/* Rellenamos panel norte */

		/* Rellenamos panel este */

		JPanel pnlInfoArea = new JPanel();
		pnlInfoArea.setBorder(BorderFactory.createTitledBorder("Status Messages"));
		pEast.add(pnlInfoArea);

		infoArea = new JTextArea(10,0);
		infoArea.setEditable(true);
		infoArea.setLineWrap(true);
		infoArea.setWrapStyleWord(true);

		JScrollPane area = new JScrollPane(infoArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		pnlInfoArea.add(area);

		//	pnlInfoArea.add(infoArea);

		String story = "The Internet Foundation Classes (IFC) were a graphics "
				+ "library for Java originally developed by Netscape Communications "
				+ "Corporation and first released on December 16, 1996.\n\n"
				+ "On April 2, 1997, Sun Microsystems and Netscape Communications"
				+ " Corporation announced their intention to combine IFC with other"
				+ " technologies to form the Java Foundation Classes. In addition "
				+ "to the components originally provided by IFC, Swing introduced "
				+ "a mechanism that allowed the look and feel of every component "
				+ "in an application to be altered without making substantial "
				+ "changes to the application code. The introduction of support "
				+ "for a pluggable look and feel allowed Swing components to "
				+ "emulate the appearance of native components while still "
				+ "retaining the benefits of platform independence. This feature "
				+ "also makes it easy to have an individual application's appearance "
				+ "look very different from other native programs.\n\n"
				+ "Originally distributed as a separately downloadable library, "
				+ "Swing has been included as part of the Java Standard Edition "
				+ "since release 1.2. The Swing classes are contained in the " + "javax.swing package hierarchy.\n\n";
		//	infoArea.setText(story);

		JPanel pnlPlayerInfo = new JPanel();
		pnlPlayerInfo.setBorder(BorderFactory.createTitledBorder("Player Information"));
		pEast.add(pnlPlayerInfo);
		// pnlInfoArea.add(infoArea);

		/* Rellenamos panel este */

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// this.setSize(350, 300);

	}

	public void refreshBoard(S state) {
		this.gameState = state;
	}

	public void turno(boolean b) {
		mainPanel.setEnabled(b);
	}

}
