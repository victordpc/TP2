package es.ucm.fdi.tp.launcher;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;

//import es.ucm.fdi.tp.ataxx.AtaxxAction;
//import es.ucm.fdi.tp.ataxx.AtaxxState;
import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
//import es.ucm.fdi.tp.chess.ChessAction;
//import es.ucm.fdi.tp.chess.ChessBoard;
//import es.ucm.fdi.tp.chess.ChessState;
import es.ucm.fdi.tp.mvc.GameName;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.mvc.GameType;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;
import es.ucm.fdi.tp.view.Controller.ConsoleController;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.Controller.UIController;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class Main {

	/**
	 * Scanner que se encargará de recoger los comandos del usuario.
	 */
	private static Scanner scanner;

	private static GameTable<?, ?> createGameModel(String gameType) {
		GameState initialGameState = createInitialState(gameType);
		return new GameTable(initialGameState);
	}

	// private static GameTable<?, ?> createGameModel(String gameName, String
	// args, String args2) {
	// GameState initialGameState = createInitialState(gameName,
	// Integer.parseInt(args), Integer.parseInt(args2));
	// return new GameTable(initialGameState);
	// }

	@SuppressWarnings("unchecked")
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> GUIView<S, A> createGUIGame(String gameName,
			GameController<S, A> gameController, GameState<S, A> gameState) {
		if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
			return (GUIView<S, A>) new TttView((GameController<TttState, TttAction>) gameController,
					(TttState) gameState);
		} else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
			return (GUIView<S, A>) new WasView((GameController<WolfAndSheepState, WolfAndSheepAction>) gameController,
					(WolfAndSheepState) gameState);
			// }else //if (gameName.equalsIgnoreCase(GameName.CHESS.toString()))
			// {
			// return (GUIView<S, A>) new ChessView((GameController<ChessState,
			// ChessAction>) gameController, (ChessState) gameState);
			// } else if (gameName.equalsIgnoreCase(GameName.ATAXX.toString()))
			// {
			// return (GUIView<S, A>) new AtaxxView((GameController<AtaxxState,
			// AtaxxAction>) gameController,
			// (AtaxxState) gameState);
		}
		return null;
	}

	/**
	 * Crea el estado inicial para el juego que quiere jugar el usuario.
	 *
	 * @param gameName
	 *            Nombre el juego.
	 * @return Devuelve un juego en su estado inicial si el parámetro
	 *         introducido es correcto, devuelve nulo en caso contrario.
	 */
	private static GameState<?, ?> createInitialState(String gameName) {
		GameState<?, ?> initialState = null;
		if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
			initialState = new TttState(3);
		} else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
			initialState = new WolfAndSheepState(8);
			// }else if (gameName.equalsIgnoreCase(GameName.CHESS.toString())) {
			// initialState = new ChessState();
		}
		return initialState;
	}

	// /**
	// * Crea el estado inicial para el juego que quiere jugar el usuario.
	// *
	// * @param gameName
	// * Nombre el juego.
	// * @param dimension
	// * dimensiones del tablero [[dimension]x[dimension]]
	// * @param numPlayers
	// * numero de jugadores en el rango {1-4}
	// * @return Devuelve un juego en su estado inicial si el parámetro
	// * introducido es correcto, devuelve nulo en caso contrario.
	// */
	// private static GameState<?, ?> createInitialState(String gameName, int
	// dimension, int numPlayers) {
	// GameState<?, ?> initialState = null;
	// if (gameName.equalsIgnoreCase(GameName.ATAXX.toString())) {
	// initialState = new AtaxxState(dimension, numPlayers);
	// }
	// return initialState;
	// }

	private static GamePlayer createPlayer(String playerType, String playerName) {
		GamePlayer newGamePlayer;
		if (playerType.equalsIgnoreCase(PlayerType.MANUAL.toString())) {
			newGamePlayer = new ConsolePlayer(playerName, new Scanner(System.in));
		} else if (playerType.equalsIgnoreCase(PlayerType.SMART.toString())) {
			newGamePlayer = new SmartPlayer(playerName, 5);
		} else {
			newGamePlayer = new RandomPlayer(playerName);
		}
		return newGamePlayer;
	}

	/**
	 * Crea los jugadores.
	 *
	 * @param gameTable
	 *            commandos introducidos por el usuario.
	 */
	private static List<GamePlayer> loadGuiPlayers(GameTable gameTable) {
		List<GamePlayer> players = new ArrayList<>();
		for (int i = 0; i < gameTable.getState().getPlayerCount(); i++) {
			String playerName = "Jugador" + i;
			GamePlayer newPlayer = createPlayer(PlayerType.MANUAL.name(), playerName);
			newPlayer.join(i);
			players.add(newPlayer);
		}
		return players;
	}

	/**
	 * Crea los jugadores.
	 *
	 * @param gameSettingsData
	 *            commandos introducidos por el usuario.
	 */
	private static List<GamePlayer> loadPlayers(String[] gameSettingsData) {
		List<GamePlayer> players = new ArrayList<>();
		for (int i = 0; i < gameSettingsData.length; i++) {
			System.out.println(System.getProperty("line.separator") + "Jugador " + (i + 1) + " Introduce tu nombre:");
			String playerName = scanner.nextLine();
			GamePlayer newPlayer = createPlayer(gameSettingsData[i], playerName);
			newPlayer.join(i);
			players.add(newPlayer);
		}
		return players;
	}

	/**
	 * Inicializa el modelo. Las vistas. Los controladores. controller.init();
	 */
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println(
					"El número de parámetros introducidos, es menor al número de parámetros mínimos requeridos para iniciar una partida.");
			System.exit(1);
		}

		GameTable<?, ?> gameTable = null;
		if (args.length == 2) {
			gameTable = createGameModel(args[0]);
			// } else {
			// gameTable = createGameModel(args[0], args[2], args[3]);
		}

		if (gameTable == null) {
			System.err.println("Juego inválido");
			System.exit(1);
		}

		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		if (args[1].equalsIgnoreCase(GameType.CONSOLE.toString())) {
			startConsoleMode(gameTable, otherArgs);
		} else if (args[1].equalsIgnoreCase(GameType.GUI.toString())) {
			startGUIMode(args[0], gameTable);
		} else {
			System.err.println("Invalid view mode: " + args[1]);
			System.exit(1);
		}
	}

	/**
	 * Inicializa un juego por consola.
	 * 
	 * @param gameTable
	 *            Model del juego.
	 * @param playerModes
	 *            Modo de los jugadores.
	 * @param <S>
	 *            EStado genérico.
	 * @param <A>
	 *            Acción genérica.
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(
			GameTable<S, A> gameTable, String playerModes[]) {
		List<GamePlayer> players = loadPlayers(playerModes);
		gameTable.setGamePlayers(players);
		new ConsoleView<S, A>(gameTable);
		new ConsoleController<S, A>(players, gameTable).run();
	}

	/**
	 * Inicia un juego en modo interfaz.
	 * 
	 * @param gameName
	 *            Nombre del juego.
	 * @param gameTable
	 *            Modelo del juego.
	 * @param <S>
	 * @param <A>
	 */
	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(String gameName,
			GameTable<S, A> gameTable) {
		List<GamePlayer> players = loadGuiPlayers(gameTable);
		if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
			players.get(0).setPlayerColor(Color.decode("#FFEB3B"));
			players.get(1).setPlayerColor(Color.decode("#F44336"));
		} else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
			players.get(0).setPlayerColor(Color.decode("#424242"));
			players.get(1).setPlayerColor(Color.decode("#ECEFF1"));
		} else {
			if (players.size() >= 2) {
				players.get(0).setPlayerColor(Color.decode("#3CBCEF"));
				players.get(1).setPlayerColor(Color.decode("#A71F5D"));
			}
			if (players.size() >= 3) {
				players.get(2).setPlayerColor(Color.decode("#424242"));
			}
			if (players.size() >= 4) {
				players.get(3).setPlayerColor(Color.decode("#ECEFF1"));
			}
		}
		gameTable.setGamePlayers(players);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < players.size(); i++) {
						UIController<S, A> gameController = new UIController<S, A>(gameTable);
						GUIView<S, A> guiViewPlayer = createGUIGame(gameName, gameController, gameTable.getState());
						GUIView<S, A> containerViewPlayer = new GameContainer<S, A>(i, guiViewPlayer, gameController,
								gameTable, players);
						gameController.setPlayerInfoObserver((PlayerInfoObserver) containerViewPlayer);
						containerViewPlayer.enableWindowMode();
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Some error occurred while creating the view..." + e.getMessage() + " --- ");
		} catch (InvocationTargetException e) {
			System.out.println("Some error occurred while creating the view..." + e.getMessage());
		}

		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					gameTable.start();
				}
			});
		} catch (

		InterruptedException e) {
			e.printStackTrace();
			System.out.println("Some error occurred while creating the view..." + e.getMessage() + " --- ");
		} catch (InvocationTargetException e) {
			System.out.println("Some error occurred while creating the view..." + e.getCause());
		}
	}
}

// import java.awt.GridLayout;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;
// import java.util.List;
// import java.util.concurrent.CancellationException;
// import java.util.concurrent.ExecutionException;
//
// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JProgressBar;
// import javax.swing.SwingUtilities;
// import javax.swing.SwingWorker;
//
//// Ejemplo de Swing Worker
// public class EjemploWorkerPrimos extends JFrame implements ActionListener {
// public static class HebraPrimosWorker extends SwingWorker<Integer, Integer> {
// private int desde;
// private int hasta;
// private JProgressBar pb;
//
// public HebraPrimosWorker(JProgressBar pb, int desde, int hasta) {
// this.pb = pb;
// this.desde = desde;
// this.hasta = hasta;
// }
//
// @Override
// protected Integer doInBackground() throws Exception {
// int ultimo = 0;
// for (int i = desde; i < hasta; i++) {
// if (esPrimo(i)) {
// // System.out.println("Primo: "+i);
// ultimo = i;
// }
// if ((i - desde) % (NUM / 100) == 0)
// publish(100 * (i - desde) / NUM + 1);
// }
// return ultimo;
// }
//
// @Override
// public void done() {
// try {
// Integer ultimo = get();
// pb.setString("El Ãºltimo primo es " + ultimo);
// } catch (CancellationException | InterruptedException | ExecutionException e)
// {
// pb.setString("Error!");
// }
// }
//
// private boolean esPrimo(int n) {
// if (n <= 3) {
// return true;
// } else {
// if (n % 2 == 0) {
// return false;
// } else {
// boolean primo = true;
// for (int i = 3; i * i < n && primo; i += 2)
// if (n % i == 0)
// primo = false;
// return primo;
// }
// }
// }
//
// @Override
// protected void process(List<Integer> porcentajes) {
// int ultimo = porcentajes.get(porcentajes.size() - 1);
// pb.setString(Integer.toString(ultimo) + " %");
// pb.setValue(ultimo);
// }
// }
//
// public final static int NUM = 5000000;
//
// public static void main(String[] args) throws InterruptedException {
// SwingUtilities.invokeLater(new Runnable() {
// @Override
// public void run() {
// EjemploWorkerPrimos v = new EjemploWorkerPrimos();
// v.setVisible(true);
// }
// });
// }
//
// JButton btnStart;
// HebraPrimosWorker hebra1;
// HebraPrimosWorker hebra2;
// HebraPrimosWorker hebra3;
// private JProgressBar pb1;
//
// private JProgressBar pb2;
//
// private JProgressBar pb3;
//
// public EjemploWorkerPrimos() {
// super("Ejemplo de SwingWorker");
// this.getContentPane().setLayout(new GridLayout(4, 1, 5, 5));
// this.setDefaultCloseOperation(EXIT_ON_CLOSE);
// pb1 = new JProgressBar(0, 100);
// pb1.setStringPainted(true);
// pb2 = new JProgressBar(0, 100);
// pb2.setStringPainted(true);
// pb3 = new JProgressBar(0, 100);
// pb3.setStringPainted(true);
// btnStart = new JButton("Empezar");
//
// this.getContentPane().add(pb1);
// this.getContentPane().add(pb2);
// this.getContentPane().add(pb3);
// this.getContentPane().add(btnStart);
// this.setSize(300, 200);
//
// btnStart.addActionListener(this);
// }
//
// @Override
// public void actionPerformed(ActionEvent e) {
// hebra1 = new HebraPrimosWorker(pb1, 1, NUM);
// hebra2 = new HebraPrimosWorker(pb2, NUM + 1, 2 * NUM);
// hebra3 = new HebraPrimosWorker(pb3, 2 * NUM + 1, 3 * NUM);
// hebra1.execute();
// hebra2.execute();
// hebra3.execute();
// }
// }
