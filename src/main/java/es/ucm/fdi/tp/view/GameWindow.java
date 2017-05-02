package es.ucm.fdi.tp.view;

import javax.swing.JFrame;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.launcher.Main.TypePlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObserver;

public class GameWindow<S extends GameState<S, A>, A extends GameAction<S, A>> implements GameObserver<S, A> {
	private GUIController<S, A> controller = null;
	private TypePlayer playerTypeActive;
	private windowView<S, A> ventana;

	public GameWindow(String gameType, GUIController<S, A> controller) {
		this.playerTypeActive = TypePlayer.Manual;
		this.controller = controller;

		ventana = new windowView<S, A>("Ventana jugador " + controller.getPlayer().getPlayerNumber() + " : "
				+ controller.getPlayer().getName(), this);
		ventana.drawGUI(this.controller);
		ventana.setVisible(true);
	}

	@Override
	public void notifyEvent(GameEvent<S, A> e) {
		switch (e.getType()) {
		case Start:
			pintarTablero(e);
			addMessage(e.toString());
			play(e);
			break;
		case Change:
			pintarTablero(e);
			addMessage(e.toString());
			play(e);
			break;
		case Error:
			addMessage(e.getError().toString());
			break;
		case Stop:

			break;
		case Info:
			addMessage(e.toString());
			break;
		default:
			break;
		}

	}

	private void play(GameEvent<S, A> e) {
		if (controller.getPlayer().getPlayerNumber() == e.getState().getTurn()) {
			if (this.playerTypeActive == TypePlayer.Random) {
				controller.doRandMove(e.getState());
			} else if (this.playerTypeActive == TypePlayer.Smart) {
				controller.doSmartMove(e.getState());
			} else {
				ventana.turno(true);
			}
		} else {
			ventana.turno(false);
		}
	}

	private void addMessage(String string) {
		// TODO Auto-generated method stub

	}

	private void pintarTablero(GameEvent<S, A> e) {
		ventana.refreshBoard(e.getState());
	}

	public void setController(GUIController<S, A> controller) {
		this.controller = controller;
	}

	public void changePlayerType(TypePlayer newType) {
		this.playerTypeActive = newType;
	}

	private void stopGame() {
		controller.stopGame();
	}

	private void restartGame() {
		controller.startGame();
	}

}
