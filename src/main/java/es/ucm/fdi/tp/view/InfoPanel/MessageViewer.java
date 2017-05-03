package es.ucm.fdi.tp.view.InfoPanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.view.GUIView;

public abstract class MessageViewer<S extends GameState<S, A>, A extends GameAction<S, A>> extends GUIView {

	protected JTextArea textArea;

	public MessageViewer() {
		initGUI();
	}

	private void initGUI() {
		textArea = new JTextArea(15, 20);
		textArea.setLineWrap(true);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		Border borderLayout = new TitledBorder("Status Messages") {
			private Insets customInsets = new Insets(20, 10, 10, 10);

			@Override
			public Insets getBorderInsets(Component c) {
				return customInsets;
			}
		};
		scrollPane.setBorder(borderLayout);
		scrollPane.setBackground(null);
		add(scrollPane);
	}

	abstract public void addContent(String msg);

	abstract public void setContent(String msg);
}
