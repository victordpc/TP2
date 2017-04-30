package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import javax.swing.*;

public abstract class UIComponent<S extends GameState<S,A>, A extends GameAction<S,A>> extends JPanel {
    public abstract void update(S state);
}
