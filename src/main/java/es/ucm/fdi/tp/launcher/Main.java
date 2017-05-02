package es.ucm.fdi.tp.launcher;

import java.awt.*;
import java.io.Console;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.extra.jboard.BoardExample;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

import javax.swing.*;

public class Main {

	public static enum TypePlayer {
		Manual, Random, Smart;
	}

	public static enum TypeGame {
		Ttt, WaS;
	}

	public static enum TypeInterface {
		Gui, Console;
	}

	/**
	 * Scanner que se encargará de recoger los comandos del usuario.
	 */
	private static Scanner scanner;

	/**
	 * Inicializa el modelo. Las vistas. Los controladores. controller.init();
	 */
	public static void main(String[] args) {

		if (args.length < 2) {
			System.err.println(
					"El número de parámetros introducidos, es menor al número de parámetros mínimos requeridos para iniciar una partida.");
			System.exit(1);
		}

		GameTable gameTable = createGame(args[0]);
		if (gameTable == null) {
			System.err.println("Juego inválido");
			System.exit(1);
		}

		String[] otherArgs = Arrays.copyOfRange(args, 2, args.length);
		if (args[1].equalsIgnoreCase(TypeInterface.Console.name())) {
			startConsoleMode(gameTable, otherArgs);
		} else if (args[1].equalsIgnoreCase(TypeInterface.Gui.name())) {
			startGUIMode(args[0], gameTable);
		} else {
			System.err.println("Invalid view mode: " + args[1]);
			System.exit(1);
		}
	}

	private static GameTable<?, ?> createGame(String gameTye) {
		GameState<?, ?> initialGameState = createInitialState(gameTye);
		return new GameTable(initialGameState);
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
		if (gameName.equalsIgnoreCase(TypeGame.Ttt.name())) {
			initialState = new TttState(3);
		} else if (gameName.equalsIgnoreCase(TypeGame.WaS.name())) {
			initialState = new WolfAndSheepState(8);
		}
		return initialState;
	}

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(
			GameTable<S, A> gameTable, String playerModes[]) {
		List<GamePlayer> players = loadPlayers(playerModes);
		new ConsoleView(gameTable);
		new ConsoleController(players, gameTable).run();
	}

	private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(String game,
			GameTable<S, A> gameTable) {
		List<GamePlayer> players = new LinkedList<GamePlayer>();

		for (int i = gameTable.getState().getPlayerCount(); i > 0; i--) {
			GamePlayer jugador = createPlayer(TypePlayer.Manual.name(), "jugador" + i);
			jugador.join(i);
			players.add(jugador);
		}

		/* Creamos el controlador */
		// final GUIController gameController = new GUIController(players,
		// gameTable);
		/* Creamos el controlador */
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					for (GamePlayer gamePlayer : players) {

						GUIController controller = new GUIController(gamePlayer, gameTable);

						/* Creamos la vista */
						GameWindow<?, ?> gameView = null;
						if (game.equalsIgnoreCase(TypeGame.Ttt.name())) {
							gameView = new GameWindow<TttState, TttAction>(game, controller);
						} else if (game.equalsIgnoreCase(TypeGame.WaS.name())) {
							gameView = new GameWindow<WolfAndSheepState, WolfAndSheepAction>(game, controller);
						}

						/* Registramos la vista en el modelo */
						gameTable.addObserver((GameObserver<S, A>) gameView);

						controller.run();
					}
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.out.println("Some error occurred while creating the view...");
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * SwingUtilities.invokeLater(new Runnable() { public void run() {
		 * gameController.run(); } });
		 */
	}

	private static GamePlayer createPlayer(String playerType, String playerName) {
		GamePlayer newGamePlayer;
		if (playerType.equalsIgnoreCase(TypePlayer.Manual.name())) {
			newGamePlayer = new ConsolePlayer(playerName, new Scanner(System.in));
		} else if (playerType.equalsIgnoreCase(TypePlayer.Smart.name())) {
			newGamePlayer = new SmartPlayer(playerName, 5);
		} else {
			newGamePlayer = new RandomPlayer(playerName);
		}
		return newGamePlayer;
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
			players.add(createPlayer(gameSettingsData[1], playerName));
		}
		return players;
	}
}
