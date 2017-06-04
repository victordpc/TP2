package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Color;

public interface PlayerInfoObserver {

	/** Notifica a los observadores que el color ha cambiado*/
	void colorChanged(int player, Color color);

	/** Obtiene el jugador según el id pasado por parámetro. */
	Color getColorPlayer(int jugador);

	/**
	 * Envía un mensasje a los observadores.
	 * @param message Mensaje a enviar.
	 */
	void postMessage(String message);
}
