package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;

public interface PlayersInfoObserver {
	void colorChanged(int player, Color color);

	void postMessage(String message);
}
