package es.ucm.fdi.tp.view.ControlPanel;

import es.ucm.fdi.tp.mvc.PlayerType;

public interface ControlPanelObservable {
	void playerModeHasChange(PlayerType newPlayerMode, int playerId);
	void makeAutomaticMove(PlayerType playerType);
//	void stopSmartPlayerAction();
}
