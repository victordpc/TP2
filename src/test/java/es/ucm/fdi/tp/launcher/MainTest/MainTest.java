package es.ucm.fdi.tp.launcher.MainTest;

import static org.junit.Assert.*;

import org.junit.Test;

import es.ucm.fdi.tp.launcher.Main;

public class MainTest {

	  /**
     * Test que evalua: Proporcionar menos de 3 argumentos ó demasiados argumentos
     * (más jugadores de los que acepta el juego) resulta en un error.
     */
    @Test
    public void errorTooMuchArguments() {
        String[]command = {"WAS", "SMART", "SMART", "EXTRAARGUMENT"};
        boolean success = Main.checkCommand(command);
        assertFalse(success);
    }

    /**
     * Test evalua: Proporcionar un juego inválido como primer argumento resulta en un error.
     */
    @Test
    public void invalidGameReturnError() {
       boolean success = Main.checkGame("JuegoIncorrecto");
       assertFalse(success);
    }

}
