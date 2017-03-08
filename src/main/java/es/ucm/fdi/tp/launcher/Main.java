package es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.ConsolePlayer;
import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.ttt.TttState;

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
		while(seguir) {
			System.out.println("Introduce nuevo juego: " + System.getProperty("line.separator"));
			String[] newCommand = scanner.nextLine().trim().split(" ");
			if(checkGame(newCommand[0])){
				if(newCommand.length == 3) {
					if(checkPlayers(newCommand)) {
						loadPlayers(newCommand);
						GameState<?, ?> initialGameState = createInitialState(newCommand[0]);
						int result = playGame(initialGameState, players);
						System.out.println("Result: partida finalizad" + result);
					}
				}else {
					System.err.println("Error: demasiados jugadores para este juego "+ System.getProperty("line.separator"));
				}
			}	
		}
		scanner.close();
	}
	
	private static boolean checkGame(String gameName) {
		if(gameName.equalsIgnoreCase(TTT) || gameName.equals(WAS)) {
			return true;
		}
		System.err.println("Error: juego " +gameName+ " no definido"+ System.getProperty("line.separator"));
		return false;
	}

	private static boolean checkPlayers(String[] command) {
		for(int i = 1; i < command.length; i++) {
			if(!command[i].equalsIgnoreCase(CONSOLE) && !command[i].equalsIgnoreCase(RAND) && !command[i].equalsIgnoreCase(SMART)){
				System.err.println("Error: jugador " +command[i]+ " no definido"+ System.getProperty("line.separator"));
				return false;
			}
		}
		return true;
	}
	
	private static void loadPlayers(String[] gameSettingsData) {
		players = new ArrayList<GamePlayer>();
		System.out.println("Jugador 1 Introduce tu nombre:" + System.getProperty("line.separator"));
		String playerName = scanner.nextLine();
		players.add(createPlayer(gameSettingsData[0], gameSettingsData[1], playerName));
		System.out.println("Jugador 2 Introduce tu nombre:" + System.getProperty("line.separator"));
		playerName = scanner.nextLine();
		players.add(createPlayer(gameSettingsData[0], gameSettingsData[2], playerName));
	}

    /**
     * @param gameName
     * @return
     */
    public static GameState<?,?> createInitialState(String gameName) {
    	if(gameName.equalsIgnoreCase(TTT)) {
    		return new TttState(3);
    	}
		return null;
    }
    
    public static GamePlayer createPlayer(String gameName, String playerType, String playerName) {
    	GamePlayer newGamePlayer = null;
    	int randNumber = 0;
    	Random ran;
    	if(!playerType.equalsIgnoreCase(CONSOLE) && !playerType.equalsIgnoreCase(SMART)) {
    		ran = new Random();
    		randNumber = ran.nextInt(10);
    	}
    	if(playerType.equalsIgnoreCase(CONSOLE) && randNumber%2 == 0) {
    		newGamePlayer = new ConsolePlayer(playerName, new Scanner(System.in));
    	}else {
    		newGamePlayer = new SmartPlayer(playerName, 5);
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
