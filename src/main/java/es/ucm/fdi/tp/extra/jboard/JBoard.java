package es.ucm.fdi.tp.extra.jboard;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public abstract class JBoard extends JComponent {

    public enum Shape {
        CIRCLE, RECTANGLE, IMAGE
    }

    /**
     *
     */
    private static final long serialVersionUID = -4518722262994516431L;
    private int _CELL_HEIGHT = 50;
    private int _CELL_WIDTH = 50;

    private int _SEPARATOR = -2;

    public JBoard() {
        initGUI();
    }

    private void drawCell(int row, int col, Graphics g) {
        int x = col * _CELL_WIDTH;
        int y = row * _CELL_HEIGHT;

        g.setColor(getBackground(row, col));
        g.fillRect(x + _SEPARATOR, y + _SEPARATOR, _CELL_WIDTH - 2 * _SEPARATOR, _CELL_HEIGHT - 2 * _SEPARATOR);

        Integer p = getPosition(row, col);

        if (p != null) {
            Color c = getColor(p);
            Shape s = getShape(p);
            g.setColor(c);
            switch (s) {
                case CIRCLE:
                    g.fillOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    g.setColor(Color.black);
                    g.drawOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    break;
                case RECTANGLE:
                    g.fillRect(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    g.setColor(Color.black);
                    g.drawRect(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    break;
                case IMAGE:
                    g.fillOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    g.setColor(Color.black);
                    g.drawOval(x + _SEPARATOR + 2, y + _SEPARATOR + 2, _CELL_WIDTH - 2 * _SEPARATOR - 4, _CELL_HEIGHT - 2 * _SEPARATOR - 4);
                    Image pieceImage = getImage(row, col);
                    if (pieceImage != null) {
                        int positionX = (x + _SEPARATOR + 10);
                        int positiony = (y + _SEPARATOR + 10);
                        g.drawImage(pieceImage, positionX, positiony, _CELL_WIDTH - 25, _CELL_HEIGHT - 25, null);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    private void fillBoard(Graphics g) {
        int numCols = getNumCols();
        int numRows = getNumRows();

        if (numCols <= 0 || numRows <= 0) {
            g.setColor(Color.red);
            g.drawString("Waiting for game to start!", 20, this.getHeight() / 2);
            return;
        }

        _CELL_WIDTH = this.getWidth() / numCols;
        _CELL_HEIGHT = this.getHeight() / numRows;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                drawCell(i, j, g);
            }
        }
    }

    protected abstract Color getBackground(int row, int col);

    protected abstract Color getColor(int player);

    protected abstract int getNumCols();

    protected abstract int getNumRows();

    protected abstract Integer getPosition(int row, int col);

    protected int getSepPixels() {
        return 2;
    }

    protected abstract Shape getShape(int player);

    protected abstract Image getImage(int row, int col);

    private void initGUI() {
        setBorder(BorderFactory.createRaisedBevelBorder());

        addKeyListener(new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }

            @Override
            public void keyTyped(KeyEvent e) {
                JBoard.this.keyTyped(e.getExtendedKeyCode());
            }
        });
        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int col = (e.getX() / _CELL_WIDTH);
                int row = (e.getY() / _CELL_HEIGHT);

                int mouseButton = 0;

                if (SwingUtilities.isLeftMouseButton(e))
                    mouseButton = 1;
                else if (SwingUtilities.isMiddleMouseButton(e))
                    mouseButton = 2;
                else if (SwingUtilities.isRightMouseButton(e))
                    mouseButton = 3;

                if (mouseButton == 0)
                    return; // Unknown button, don't know if it is possible!

                JBoard.this.mouseClicked(row, col, e.getClickCount(), mouseButton);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JBoard.this.requestFocus();
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

        _SEPARATOR = getSepPixels();
        if (_SEPARATOR < 0)
            _SEPARATOR = 0;

        this.setPreferredSize(new Dimension(400, 400));
        repaint();
    }

    protected abstract void keyTyped(int keyCode);

    protected abstract void mouseClicked(int row, int col, int clickCount, int mouseButton);

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        fillBoard(g);
    }

}
