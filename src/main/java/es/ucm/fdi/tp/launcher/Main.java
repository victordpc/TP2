package es.ucm.fdi.tp.launcher;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
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

	private static GameTable<?, ?> createGameModel(String gameTye) {
		GameState initialGameState = createInitialState(gameTye);
		return new GameTable(initialGameState);
	}

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> GUIView<S, A> createGUIGame(String gameName, GameController<S, A> gameController, GameState<S, A> gameState) {
		if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
			return (GUIView<S, A>) new TttView((GameController<TttState, TttAction>) gameController, (TttState) gameState);
		} else /**if (gameName.equalsIgnoreCase(GameName.WAS.toString()))*/ {
			return (GUIView<S, A>) new WasView((GameController<WolfAndSheepState, WolfAndSheepAction>) gameController, (WolfAndSheepState) gameState);
		}
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
		}else {
//			initialState = new ChessState();
		}
		return initialState;
	}

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

		GameTable<?, ?> gameTable = createGameModel(args[0]);
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

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(
			GameTable<S, A> gameTable, String playerModes[]) {
		List<GamePlayer> players = loadPlayers(playerModes);
		gameTable.setGamePlayers(players);
		new ConsoleView<S, A>(gameTable);
		new ConsoleController<S, A>(players, gameTable).run();
	}

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(String gameName, GameTable<S, A> gameTable) {
		List<GamePlayer> players = loadGuiPlayers(gameTable);
		if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
			players.get(0).setPlayerColor(Color.decode("#FFEB3B"));
			players.get(1).setPlayerColor(Color.decode("#F44336"));
		} else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
			players.get(0).setPlayerColor(Color.decode("#424242"));
			players.get(1).setPlayerColor(Color.decode("#ECEFF1"));
		}else {
			players.get(0).setPlayerColor(Color.decode("#3CBCEF"));
			players.get(1).setPlayerColor(Color.decode("#A71F5D"));
		}
		gameTable.setGamePlayers(players);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					for (int i = 0; i < players.size(); i++) {
						UIController<S, A> gameControllerPlayer = new UIController<S, A>(gameTable);
						GUIView<S, A> guiViewPlayer = createGUIGame(gameName, gameControllerPlayer, gameTable.getState());
						GUIView<S, A> containerViewPlayer = new GameContainer<S, A>(i, guiViewPlayer, gameControllerPlayer, gameTable, players);
						gameControllerPlayer.setPlayerInfoObserver((PlayerInfoObserver)containerViewPlayer);
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
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("Some error occurred while creating the view..." + e.getMessage() + " --- ");
		} catch (InvocationTargetException e) {
			System.out.println("Some error occurred while creating the view..." + e.getCause());
		}
	}
}
