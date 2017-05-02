package es.ucm.fdi.tp.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.*;
import es.ucm.fdi.tp.view.Controller.ConsoleController;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.Controller.UIController;
import es.ucm.fdi.tp.was.WolfAndSheepState;

import javax.swing.*;

public class Main {

    /**
     * Scanner que se encargará de recoger los comandos del usuario.
     */
    private static Scanner scanner;


    /**
     * Inicializa el modelo.
     * Las vistas.
     * Los controladores.
     * controller.init();
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Introduce nuevo juego: " + System.getProperty("line.separator"));
        String[] arguments = scanner.nextLine().trim().split(" "); // {"was", "gui", "manual", "manual"};

        if (arguments.length < 2) {
            System.err.println("El número de parámetros introducidos, es menor al número de parámetros mínimos requeridos para iniciar una partida.");
            System.exit(1);
        }

        GameTable gameTable = createGameModel(arguments[0]);
        if (gameTable == null) {
            System.err.println("Juego inválido");
            System.exit(1);
        }

        String[] otherArgs = Arrays.copyOfRange(arguments, 2, arguments.length);
        if (arguments[1].equalsIgnoreCase(GameType.CONSOLE.toString())) {
            startConsoleMode(gameTable, otherArgs);
        } else if (arguments[1].equalsIgnoreCase(GameType.GUI.toString())) {
            startGUIMode(arguments[0], gameTable, otherArgs);
        } else {
            System.err.println("Invalid view mode: " + arguments[1]);
            System.exit(1);
        }
        scanner.close();
    }

    private static GameTable<?, ?> createGameModel(String gameTye) {
        GameState<?, ?> initialGameState = createInitialState(gameTye);
        return new GameTable(initialGameState);
    }

    /**
     * Crea el estado inicial para el juego que quiere jugar el usuario.
     *
     * @param gameName Nombre el juego.
     * @return Devuelve un juego en su estado inicial si el parámetro
     * introducido es correcto, devuelve nulo en caso contrario.
     */
    private static GameState<?, ?> createInitialState(String gameName) {
        GameState<?, ?> initialState = null;
        if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
            initialState = new TttState(3);
        } else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
            initialState = new WolfAndSheepState(8);
        }
        return initialState;
    }

    private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(GameTable<S, A> gameTable, String playerModes[]) {
        List<GamePlayer> players = loadPlayers(playerModes);
        new ConsoleView(gameTable);
        new ConsoleController(players, gameTable).run();
    }

    private static <S extends GameState<S, A>, A extends GameAction<S, A>> GUIView<S, A> createGUIGame(String gameName, GameController<S, A> gameController, GameState<S, A> gameState) {
        if (gameName.equalsIgnoreCase(GameName.TTT.toString())) {
            return (GUIView<S, A>) new TttView(gameController, (TttState) gameState);
        } else if (gameName.equalsIgnoreCase(GameName.WAS.toString())) {
            return (GUIView<S, A>) new WasView(gameController, (WolfAndSheepState) gameState);
        }
        return (GUIView<S, A>) new TttView(gameController, (TttState) gameState);
    }

    private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(String gameName, GameTable<S, A> gameTable, String playerModes[]) {
        List<GamePlayer> players = loadPlayers(playerModes);
        gameTable.setGamePlayers(players);
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    UIController gameControllerPlayer0 = new UIController(0, null, null, gameTable);
                    GUIView<S, A> guiViewPlayer0 = (GUIView<S, A>) createGUIGame(gameName, gameControllerPlayer0, gameTable.getState());
                    GUIView<S, A> containerViewPlayer0 = new GameContainer<>(guiViewPlayer0, gameControllerPlayer0, gameTable);
                    containerViewPlayer0.enableWindowMode();

                    UIController gameControllerPlayer1 = new UIController(1, null, null, gameTable);
                    GUIView<S, A> guiViewPlayer1 = (GUIView<S, A>) createGUIGame(gameName, gameControllerPlayer1, gameTable.getState());
                    GUIView<S, A> containerViewPlayer1 = new GameContainer<>(guiViewPlayer1, gameControllerPlayer1, gameTable);
                    containerViewPlayer1.enableWindowMode();
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
     * @param gameSettingsData commandos introducidos por el usuario.
     */
    private static List<GamePlayer> loadPlayers(String[] gameSettingsData) {
        List<GamePlayer> players = new ArrayList<>();
//        players.add(createPlayer("MANUAL", "Jugador 0"));
//        players.add(createPlayer("MANUAL", "Jugador 1"));
        for (int i = 0; i < gameSettingsData.length; i++) {
            System.out.println(System.getProperty("line.separator") + "Jugador " + (i + 1) + " Introduce tu nombre:");
            String playerName = scanner.nextLine();
            GamePlayer newPlayer = createPlayer(gameSettingsData[i], playerName);
            newPlayer.join(i);
            players.add(newPlayer);
        }
        return players;
    }
}
