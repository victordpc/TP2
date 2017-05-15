package es.ucm.fdi.tp.view.ControlPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class JSpinnerExample {
    /**
     * Ejemplo de uso de JSpinner.
     * Se crea una ventana con un JSpinner y un JTextField. El valor
     * seleccionado en el JSpinner pasa automáticamente al JTextField.
     * @author chuidiang
     */

        /** El JTextField */
        private JTextField tf;

        /** El JSpinner */
        private JSpinner spinner;

        /** La ventana */
        private JFrame v;

        /**
         * Crea una instancia de esta clase.
         * @param args
         */
        public static void main(String[] args) {
            new JSpinnerExample();
        }

        /**
         * Crea la ventana con el JSpinner y el JTextField. La visualiza.
         */
        public JSpinnerExample()
        {
            // Creacion del JTextField
            tf = new JTextField(20);

            // Creacion del JSpinner y valor incial.
            spinner = new JSpinner();
            spinner.setValue(30);

            // Nos suscribimos a cambios en el JSpinner
            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    // Ponemos el valor del JSpinner en el JTextField
                    tf.setText(spinner.getValue().toString());
                }

            });


            // Creacion de la ventana con los componentes
            v = new JFrame();
            v.getContentPane().setLayout(new FlowLayout());
            v.getContentPane().add(spinner);
            v.getContentPane().add(tf);
            v.pack();
            v.setVisible(true);
            v.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
