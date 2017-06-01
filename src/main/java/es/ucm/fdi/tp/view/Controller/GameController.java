package es.ucm.fdi.tp.view.Controller;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;

public interface GameController<S extends GameState<S, A>, A extends GameAction<S, A>> {

    /**
     * Cambia el modo del jugador.
     * @param playerMode Nuevo modo.
     */
    void changePlayerMode(PlayerType playerMode);

    /**
     * Devuelve el tipo de jugador seleccionado por el jugador.
     * @return Tipo de jugador selecciondo.
     */
    PlayerType getPlayerMode();

    /**
     * Realiza un movimiento manual.
     */
    void makeManualMove(A a);

    /**
     * Realiza un movimiento aleatorio
     * @param jugador Jugador.
     */
    void makeRandomMove(GamePlayer jugador);

    /**
     * Realiza un movimiento inteligente
     * @param jugador Jugador.
     */
    void makeSmartMove(GamePlayer jugador);

    /**
     * Notifica de que la interfaz ha cambiado.
     */
    void notifyInterfaceNeedBeUpdated();

    /**
     * Reinicia el modelo.
     */
    void restartGame();

    /**
     * ïndica al modelo d eque pare el juego.
     */
    void stopGame();

    void setPlayerInfoObserver(PlayerInfoObserver playerInfoObserver);

     int getPlayerIdTurn();

}