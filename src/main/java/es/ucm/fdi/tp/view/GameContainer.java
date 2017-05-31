package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import javax.swing.BoxLayout;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.ConcurrentAiPlayer;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.mvc.GameEvent;
import es.ucm.fdi.tp.mvc.GameObservable;
import es.ucm.fdi.tp.mvc.GameObserver;
import es.ucm.fdi.tp.mvc.PlayerType;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanel;
import es.ucm.fdi.tp.view.ControlPanel.ControlPanelObservable;
import es.ucm.fdi.tp.view.Controller.GameController;
import es.ucm.fdi.tp.view.InfoPanel.InfoView;
import es.ucm.fdi.tp.view.InfoPanel.MessageViewer;
import es.ucm.fdi.tp.view.InfoPanel.PlayerInfoObserver;

public class GameContainer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView<S, A> implements GameObserver<S, A>, PlayerInfoObserver, ControlPanelObservable {

    private static final long serialVersionUID = 2977574295953072934L;
    private GameController<S, A> gameController;
    private GamePlayer gamePlayer;
    private GamePlayer randPlayer;
    private ConcurrentAiPlayer concurrentAiPlayer;
    private InfoView<S, A> infoView;
    private List<GamePlayer> listaJugadores;
    private GUIView<S, A> rectBoardView;
    private Thread concurrentAIThread;
    private ControlPanel<S, A> controlPanel;

    public GameContainer(int idPlayer, GUIView<S, A> gameView, GameController<S, A> gameController, GameObservable<S, A> game, List<GamePlayer> jugadores) {
        this.listaJugadores = jugadores;
        this.gamePlayer = jugadores.get(idPlayer);
        this.randPlayer = new RandomPlayer("dummy");
        this.randPlayer.join(this.gamePlayer.getPlayerNumber());
        this.concurrentAiPlayer = new ConcurrentAiPlayer("Jugador: " + idPlayer);
        this.concurrentAiPlayer.join(this.gamePlayer.getPlayerNumber());

        this.setTitle("Jugador " + this.gamePlayer.getName());
        this.setLayout(new BorderLayout(5, 5));
        this.rectBoardView = gameView;
        ((RectBoardView<S, A>) rectBoardView).setListPlayers(this.listaJugadores, this.gamePlayer);
        ((RectBoardView<S, A>) rectBoardView).setPlayerInfoObserver(this);
        this.gameController = gameController;
        game.addObserver(this);
        initGUI();
    }

    @Override
    public void colorChanged(int player, Color color) {
        gameController.notifyInterfaceNeedBeUpdated();
    }

    @Override
    public Color getColorPlayer(int jugador) {
        return infoView.getColorPlayer(jugador);
    }

    public void initGUI() {
        controlPanel = new ControlPanel<S, A>(gameController, this.gamePlayer.getPlayerNumber());
        controlPanel.setBackground(Color.decode("#eeeeee"));
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.X_AXIS));
        controlPanel.addControlPanelObserver(this);
        this.add(controlPanel, BorderLayout.NORTH);
        this.add(rectBoardView, BorderLayout.CENTER);
        infoView = new InfoView<S, A>(listaJugadores, this);
        infoView.setOpaque(true);
        this.add(infoView, BorderLayout.EAST);
    }

    @Override
    public void notifyEvent(GameEvent<S, A> e) {
        switch (e.getType()) {
            case Start:
                infoView.setContent(e.toString());
                ((RectBoardView)rectBoardView).resetValidMoves();
                rectBoardView.update(e.getState());
                infoView.repaintPlayersInfoViewer();
                makeAutomaticMove();
                break;
            case Change:
                rectBoardView.update(e.getState());
                infoView.repaintPlayersInfoViewer();
                if (e.getState().getTurn() == this.gamePlayer.getPlayerNumber()) {
                    makeAutomaticMove();
                }else {
                    controlPanel.setUpSmartPlayerAction(false);
                }
                break;
            case Info:
                if (e.getState().getTurn() == this.gamePlayer.getPlayerNumber()) {
                    infoView.addContent("Tu turno");
                } else {
                    infoView.addContent("Turno del jugador " + e.getState().getTurn());
                }
                break;
            case Error:
                infoView.addContent(e.getError().getMessage());
                break;
            case Stop:
                infoView.addContent(e.toString());
                break;
            default:
                break;
        }
    }

    @Override
    public void playerModeHasChange(PlayerType newPlayerMode) {
        gameController.changePlayerMode(newPlayerMode);
        switch (newPlayerMode) {
            case MANUAL:
                rectBoardView.setEnabled(true);
                break;
            case SMART:
            case RANDOM:
                rectBoardView.setEnabled(false);
                makeAutomaticMove();
                break;
            default:
                break;
        }
    }

    private void makeAutomaticMove() {
        if (gameController.getPlayerMode() == PlayerType.RANDOM) {
            makeRandomMove();
        } else if (gameController.getPlayerMode() == PlayerType.SMART) {
            makeSmartMove();
        }
    }

    @Override
    public void makeAutomaticMove(PlayerType playerType) {
        if (playerType == PlayerType.RANDOM) {
            makeRandomMove();
        } else {
            makeSmartMove();
        }
    }

    @Override
    public void stopSmartPlayerAction() {
        concurrentAIThread.interrupt();
    }

    private void makeRandomMove() {
        gameController.makeRandomMove(randPlayer);
    }

    private void makeSmartMove() {
        controlPanel.setUpSmartPlayerAction(true);
        concurrentAiPlayer.setMaxThreads(controlPanel.getConcurrentPlayerThreads());
        concurrentAiPlayer.setTimeout(controlPanel.getConcurrentPlayerTimeOut());
        concurrentAIThread = new Thread() {
            public void run() {
                gameController.makeSmartMove(concurrentAiPlayer);
            }
        };
        concurrentAIThread.start();
    }

    @Override
    public void postMessage(String message) {
        infoView.addContent(message);
    }

    @Override
    public void setGameController(GameController<S, A> gameCtrl) {}

    @Override
    public void setMessageViewer(MessageViewer<S, A> messageViewer) {}

    @Override
    public void update(S state) {}
}
