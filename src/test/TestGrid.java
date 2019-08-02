package test;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.levelsgenerator.Block;
import model.levelsgenerator.BlockImpl;
import model.levelsgenerator.Grid;
import model.levelsgenerator.GridImpl;

import javax.swing.JFrame;

/**
 * This is the test for the level creation grid and block occupation controls, where the user can place manually blocks, represented by colored buttons.
 */
public class TestGrid extends JFrame {

    private static final long serialVersionUID = 1886141971648582402L;
    private final Map<JButton, Point> buttons = new HashMap<>();

    /**
     * The empty constructor for the view of the test.
     * @param size 
     *     is the size of the grind in the view and the matrix in the model part of this test.
     */
    public TestGrid(final int size) {

        super();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 1000);

        final JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(BorderLayout.CENTER, panel);
        final Grid logic = new GridImpl(size);
        //final BlockFactory bf = new BlockFactoryImpl();
        final Block b = new BlockImpl(new Point(1, 1));
        for (int i = 0; i < 4; i++) {
            b.addPoint(new Point(1, i)); 
        }

        final ActionListener al = (e) -> {
               final JButton bt = (JButton) e.getSource();
               final Point spawnPoint = new Point(this.buttons.get(bt).x, this.buttons.get(bt).y);

               logic.tryToPlace(spawnPoint, b);
               this.redraw(logic);
        };

        for (int i = size - 1; i >= 0; i--) {
            for (int j = 0; j < size; j++) {
                final String buttonText = "X:" + j + " Y:" + i;
                final JButton jb = new JButton(buttonText);
                jb.addActionListener(al);
                this.buttons.put(jb, new Point(j, i));
                panel.add(jb);
            }

        }
        this.setVisible(true);
    }

    /**
    * Redraw the buttons grid coherently with the logic.
    * @param logic
    *         is the model instance of the test
    */
    private void redraw(final Grid logic) {
        for (final JButton jb : this.buttons.keySet()) {
            final Point currentPoint = new Point(this.buttons.get(jb).x, this.buttons.get(jb).y);
            jb.setBackground(logic.getElement(currentPoint).equals(Optional.of(1)) ? Color.yellow : jb.getBackground());
        }
    }
}
