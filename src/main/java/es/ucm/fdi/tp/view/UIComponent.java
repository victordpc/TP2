package es.ucm.fdi.tp.view;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;

import javax.swing.*;

public abstract class UIComponent extends JPanel {
    public abstract void setEnable();
    public abstract void update(GameState state);
}