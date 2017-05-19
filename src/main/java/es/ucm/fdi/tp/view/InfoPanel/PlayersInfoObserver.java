package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;

public interface PlayersInfoObserver {
	void colorChanged(int player, Color color);

	public Color getColorPlayer(int jugador);

	void postMessage(String message);
}
