package es.ucm.fdi.tp.view.InfoPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.Controller.GameController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoView<S extends GameState<S, A>, A extends GameAction<S, A>> extends JPanel {

    private S state;
    private JTextArea textArea;

    public InfoView(S state) {
        this.state = state;
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initGUI();
    }

    private void initGUI() {
        setUpStatusMessagesView();

    }

    private void setUpStatusMessagesView() {
        textArea = new JTextArea("Status Message");
        textArea.setLineWrap(true);
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(150, 150));
        Border borderLayout = new TitledBorder("") {
            private Insets customInsets = new Insets(20, 10, 10, 10);

            public Insets getCustomInsets() {
                return customInsets;
            }
        };
        scrollPane.setBorder(borderLayout);
        scrollPane.setBackground(null);
        add(scrollPane);
    }

}
