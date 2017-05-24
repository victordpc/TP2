package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;

public interface PlayerInfoObserver {
	void colorChanged(int player, Color color);

	Color getColorPlayer(int jugador);

	void postMessage(String message);
}
