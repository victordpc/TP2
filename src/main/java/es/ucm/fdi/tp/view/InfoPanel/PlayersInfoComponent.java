package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.extra.jcolor.ColorChooser;
import es.ucm.fdi.tp.mvc.PlayerModel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.*;
import java.util.List;

public class PlayersInfoComponent<S extends GameState<S, A>, A extends GameAction<S, A>> extends PlayersInfoViewer<S, A> {

    private PlayerModel playerModel;
    private Map<Integer, Color> colors; // Line -> Color
    private ColorChooser colorChooser;
    private PlayersInfoObserver playersInfoObserver;
    private List<GamePlayer> gamePlayers;

    public PlayersInfoComponent(List<GamePlayer> gamePlayers, PlayersInfoObserver playersInfoObserver) {
        this.playersInfoObserver = playersInfoObserver;
        this.gamePlayers = gamePlayers;
        List<String> playerNames = new ArrayList<>();
        for (GamePlayer gamePlayer : gamePlayers) {
            playerNames.add(gamePlayer.getName());
        }
        playerModel = new PlayerModel(playerNames);
        colors = new HashMap<>();
        loadColors();
        colorChooser = new ColorChooser(new JFrame(), "Choose Line Color", Color.BLACK);
        initGUI();
    }

    private void initGUI() {
        JTable table = new JTable(playerModel) {
            private static final long serialVersionUID = 1L;
            // THIS IS HOW WE CHANGE THE COLOR OF EACH ROW
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                // the color of row 'row' is taken from the colors table, if
                // 'null' setBackground will use the parent component color.
                if (col == 1) {
                    comp.setBackground(colors.get(row));
                } else {
                    comp.setBackground(Color.WHITE);
                    comp.setForeground(Color.BLACK);
                }
                return comp;
            }
        };

        table.setToolTipText("Click on a row to change the color of a player");
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 0) {
                    changeColor(row);
                }
            }

        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        Border borderLayout = new TitledBorder("Player Information") {
            private Insets customInsets = new Insets(20, 10, 10, 10);
            public Insets getBorderInsets(Component c) {
                return customInsets;
            }
        };
        scrollPane.setBorder(borderLayout);
        scrollPane.setBackground(null);
        add(scrollPane);
    }

    private void loadColors() {
        for(int i = 0; i < gamePlayers.size(); i++) {
            colors.put(i, gamePlayers.get(i).getPlayerColor());
        }
    }

    public void updateColors() {
        loadColors();
        repaint();
    }

    private void changeColor(int row) {
        colorChooser.setSelectedColorDialog(colors.get(row));
        colorChooser.openDialog();
        if (colorChooser.getColor() != null) {
            colors.put(row, colorChooser.getColor());
            repaint();
        }
        playersInfoObserver.colorChanged(row, colorChooser.getColor());
    }

    @Override
    public void setNumberOfPlayer(int i) {

    }

    @Override
    public Color getPlayerColor(int playerId) {
        return colors.get(playerId);
    }
}
