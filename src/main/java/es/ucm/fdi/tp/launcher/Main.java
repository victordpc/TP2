package es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import es.ucm.fdi.tp.base.console.ConsolePlayer;
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
    private static final String CONSOLE = "CONSOLE";
    /**
     * Listado de jugadores.
     */
    private static List<GamePlayer> players;
    /**
     * Define a un jugador random.
     */
    private static final String RAND = "RAND";
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
     * Evalua que el número de parámetros introducidos por el usuario son los
     * necesarios.
     *
     * @param command array de comandos introducidos
     * @return Devuelve true si el número de parámetros introducidos es
     * correcto, false en caso contrario.
     */
    public static boolean checkCommand(String[] command) {
        return command.length == 3 || (command.length == 1 && players.size() == 2);
    }

    /**
     * Evalua que el juego introducido está definido
     *
     * @param gameName Nombre del juego.
     * @return Devuelve true en caso de que el juego introducido esté definido.
     */
    public static boolean checkGame(String gameName) {
        if (gameName.equalsIgnoreCase(TTT) || gameName.equalsIgnoreCase(WAS)) {
            return true;
        }
        System.err.println("Error: juego " + gameName + " no definido" + System.getProperty("line.separator"));
        return false;
    }

    /**
     * Evalua que los jugadores introducidos están definidos
     *
     * @param command Parámetros introducidos por el usuario
     * @return Devuelve true en caso de que los jugadores introducidos estén
     * definidos.
     */
    private static boolean checkPlayers(String[] command) {
        boolean success = true;
        if (players.size() != 2) {
            for (int i = 1; i < command.length; i++) {
                if (!command[i].equalsIgnoreCase(CONSOLE) && !command[i].equalsIgnoreCase(RAND)
                        && !command[i].equalsIgnoreCase(SMART)) {
                    System.err.println(
                            "Error: jugador " + command[i] + " no definido" + System.getProperty("line.separator"));
                    return false;
                }
            }
        }
        return success;
    }

    /**
     * Crea el estado inicial para el juego que quiere jugar el usuario.
     *
     * @param gameName Nombre el juego.
     * @return Devuelve un juego en su estado inicial si el parámetro
     * introducido es correcto, devuelve nulo en caso contrario.
     */
    public static GameState<?, ?> createInitialState(String gameName) {
        GameState<?, ?> initialState = null;
        if (gameName.equalsIgnoreCase(TTT)) {
            initialState = new TttState(3);
        } else if (gameName.equalsIgnoreCase(WAS)) {
            initialState = new WolfAndSheepState(8);
        }
        return initialState;
    }

    public static GamePlayer createPlayer(String gameName, String playerType, String playerName) {
        GamePlayer newGamePlayer = null;
        if (playerType.equalsIgnoreCase(CONSOLE)) {
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
     *
     * @param gameSettingsData commandos introducidos por el usuario.
     */
    private static void loadPlayers(String[] gameSettingsData) {
        if (players.size() != 2) {
            System.out.println(System.getProperty("line.separator") + "Jugador 1 Introduce tu nombre:");
            String playerName = scanner.nextLine();
            players.add(createPlayer(gameSettingsData[0], gameSettingsData[1], playerName));
            System.out.println(System.getProperty("line.separator") + "Jugador 2 Introduce tu nombre:");
            playerName = scanner.nextLine();
            players.add(createPlayer(gameSettingsData[0], gameSettingsData[2], playerName));
            System.out.println(System.getProperty("line.separator"));
        }
    }

    /**
     * Inicializa el modelo.
     * Las vistas.
     * Los controladores.
     * controller.init();
     */
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        players = new ArrayList<>();

        System.out.println("Introduce nuevo juego: " + System.getProperty("line.separator"));
        String[] newCommand = scanner.nextLine().trim().split(" ");
        if (checkGame(newCommand[0])) {
            if (checkCommand(newCommand)) {
                if (checkPlayers(newCommand)) {
                    loadPlayers(newCommand);
                    GameState<?, ?> initialGameState = createInitialState(newCommand[0]);
                    GameTable gameTable = new GameTable(initialGameState);
                    ConsoleController consoleController = new ConsoleController(players, gameTable);
                    ConsoleView view = new ConsoleView(gameTable);
                    consoleController.run();
                }
            } else {
                System.err.println("Error: demasiados jugadores para este juego " + System.getProperty("line.separator"));
            }
        }
        scanner.close();
    }
}
