package es.ucm.fdi.tp.launcher;

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
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class Main {

    /**
     * Define el tipo de jugador por consola.
     */
    private static final String MANUAL = "MANUAL";
    /**
     * Define el tipo de interfaz de la partida.
     */
    private static final String GUI = "GUI";
    /**
     * Define el tipo de jugador por consola.
     */
    private static final String CONSOLE = "CONSOLE";
    /**
     * Scanner que se encargará de recoger los comandos del usuario.
     */
    private static Scanner scanner;
    /**
     * Define a un jugador controlado por IA.
     */
    private static final String SMART = "SMART";
    /**
     * Define el juego tres en rayas.
     */
    private static final String TTT = "TTT";
    /**
     * Define el juego WolfAndSheep
     */
    private static final String WAS = "WAS";

    /**
     * Inicializa el modelo.
     * Las vistas.
     * Los controladores.
     * controller.init();
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        System.out.println("Introduce nuevo juego: " + System.getProperty("line.separator"));
        String[] arguments = scanner.nextLine().trim().split(" ");

        if (arguments.length < 2) {
            System.err.println("El número de parámetros introducidos, es menor al número de parámetros mínimos requeridos para iniciar una partida.");
            System.exit(1);
        }

        GameTable gameTable = createGame(arguments[0]);
        if (gameTable == null) {
            System.err.println("Juego inválido");
            System.exit(1);
        }

        String[] otherArgs = Arrays.copyOfRange(arguments, 2, arguments.length);
        if (arguments[1].equalsIgnoreCase(CONSOLE)) {
            startConsoleMode(gameTable, otherArgs);
        }else if (arguments[1].equalsIgnoreCase(GUI)) {
            startGUIMode(arguments[0], gameTable, otherArgs);
        }else {
            System.err.println("Invalid view mode: " + arguments[1]);
            System.exit(1);
        }
        scanner.close();
    }

    private static GameTable<?, ?> createGame(String gameTye) {
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
        if (gameName.equalsIgnoreCase(TTT)) {
            initialState = new TttState(3);
        } else if (gameName.equalsIgnoreCase(WAS)) {
            initialState = new WolfAndSheepState(8);
        }
        return initialState;
    }

    private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startConsoleMode(GameTable<S, A> gameTable, String playerModes[]) {
        List<GamePlayer> players = loadPlayers(playerModes);
        new ConsoleView(gameTable);
        new ConsoleController(players, gameTable).run();
    }

    private static <S extends GameState<S, A>, A extends GameAction<S, A>> void startGUIMode(String gType, GameTable<S, A> game, String playerModes[]) {

    }

    private static GamePlayer createPlayer(String playerType, String playerName) {
        GamePlayer newGamePlayer;
        if (playerType.equalsIgnoreCase(MANUAL)) {
            newGamePlayer = new ConsolePlayer(playerName, new Scanner(System.in));
        } else if (playerType.equalsIgnoreCase(SMART)) {
            newGamePlayer = new SmartPlayer(playerName, 5);
        } else {
            newGamePlayer = new RandomPlayer(playerName);
        }
        return newGamePlayer;
    }

    /**
     * Crea los jugadores.
     * @param gameSettingsData commandos introducidos por el usuario.
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
