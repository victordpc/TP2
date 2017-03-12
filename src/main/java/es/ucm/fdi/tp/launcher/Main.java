package es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class Main {

	private static final String TTT = "TTT";
	private static final String WAS = "WAS";
	private static final String CONSOLE = "CONSOLE";
	private static final String RAND = "RAND";
	private static final String SMART = "SMART";
	private static List<GamePlayer> players;
	private static Scanner scanner;

	public static void main(String[] args) {

		boolean seguir = true;
		scanner = new Scanner(System.in);
        int partidasGanadasJugaor1 = 0;
        int partidasGanadasJugaor2 = 0;
        players = new ArrayList<GamePlayer>();

        while(seguir) {
			System.out.println("Introduce nuevo juego: " + System.getProperty("line.separator"));
			String[] newCommand = scanner.nextLine().trim().split(" ");
			if(checkGame(newCommand[0])){
				if(newCommand.length == 3) {
					if(checkPlayers(newCommand)) {
						loadPlayers(newCommand);
						GameState<?, ?> initialGameState = createInitialState(newCommand[0]);
						int winnerPlayerNumer = playGame(initialGameState, players);
						if (winnerPlayerNumer == 0){
                            partidasGanadasJugaor1++;
                        }else {
                            partidasGanadasJugaor2++;
                        }
					}
				}else {
					System.err.println("Error: demasiados jugadores para este juego "+ System.getProperty("line.separator"));
				}
			}

			System.out.println("¿Desea continuar? " + System.getProperty("line.separator") + " 1-. Sí " + System.getProperty("line.separator") + " 2-. No");
			String continueGames = scanner.nextLine().trim();
			if (!continueGames.equalsIgnoreCase("1")) {
				seguir = false;
			}
		}

        System.out.println("Result: " + partidasGanadasJugaor1 + " for " + players.get(0).getName()
                + " vs " + partidasGanadasJugaor2 + " for " + players.get(1).getName());
		scanner.close();
	}

	private static boolean checkGame(String gameName) {
		if(gameName.equalsIgnoreCase(TTT) || gameName.equalsIgnoreCase(WAS)) {
			return true;
		}
		System.err.println("Error: juego " +gameName+ " no definido"+ System.getProperty("line.separator"));
		return false;
	}

	private static boolean checkPlayers(String[] command) {
	    boolean success = true;
	    if (players.size() != 2) {
            for(int i = 1; i < command.length; i++) {
                if(!command[i].equalsIgnoreCase(CONSOLE) && !command[i].equalsIgnoreCase(RAND) && !command[i].equalsIgnoreCase(SMART)){
                    System.err.println("Error: jugador " +command[i]+ " no definido"+ System.getProperty("line.separator"));
                    return false;
                }
            }
        }
		return success;
	}

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
     * @param gameName
     * @return
     */
    public static GameState<?,?> createInitialState(String gameName) {
		GameState<?,?> initialState = null;
    	if(gameName.equalsIgnoreCase(TTT)) {
			initialState = new TttState(3);
    	}else if(gameName.equalsIgnoreCase(WAS)) {
			initialState = new WolfAndSheepState(8);
		}
		return initialState;
    }

    public static GamePlayer createPlayer(String gameName, String playerType, String playerName) {
    	GamePlayer newGamePlayer = null;
    	if(playerType.equalsIgnoreCase(CONSOLE)) {
    		newGamePlayer = new ConsolePlayer(playerName, new Scanner(System.in));
    	}else if(playerType.equalsIgnoreCase(SMART)) {
    		newGamePlayer = new SmartPlayer(playerName, 5);
    	}else {
            newGamePlayer = new RandomPlayer(playerName);
        }
    	return newGamePlayer;
    }

	public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState,
			List<GamePlayer> players) {
		int playerCount = 0;
		for (GamePlayer p : players) {
			p.join(playerCount++); // welcome each player, and assign playerNumber
		}
		@SuppressWarnings("unchecked")
		S currentState = (S) initialState;

		while (!currentState.isFinished()) {
			int currentStateTurn = currentState.getTurn();
			// request move
			A action = players.get(currentState.getTurn()).requestAction(currentState);
			// apply move
			currentState = action.applyTo(currentState);
			System.out.println("After action:\n" + currentState);

			if (currentState.isFinished()) {
				// game over
				String endText = "The game ended: ";
				int winner = currentState.getWinner();
				if (winner == -1) {
					endText += "draw!";
				} else {
					endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
				}
				System.out.println(endText);
			}
		}
		return currentState.getWinner();
	}    
}
