import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Ventana extends JFrame implements ActionListener {
    private JComboBox<String> comboBox;
    private JTextArea textArea;
    private JButton btnBorrar;
    private JButton btnCerrar;
    private String[] nombresArchivos = {"./fitxategiak/Phyton.txt", "./fitxategiak/C.txt", "./fitxategiak/Java.txt"};

    public Ventana() {
        // Configuración de la ventana principal
        this.setTitle("programazio hizkuntzen informazioa");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel nagusia
        JPanel panel = new JPanel(new BorderLayout());

        // JComboBox
        comboBox = new JComboBox<String>(nombresArchivos);
        comboBox.addActionListener(this);
        panel.add(comboBox, BorderLayout.NORTH);

        //  JTextArea
        textArea = new JTextArea();
        panel.add(textArea, BorderLayout.CENTER);

        // "Ezabatu" botoia
        btnBorrar = new JButton("Ezabatu");
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });
        panel.add(btnBorrar, BorderLayout.SOUTH);

        // "Cerrar" Botoia
        btnCerrar = new JButton("Itxi");
        btnCerrar.addActionListener(this);
        panel.add(btnCerrar, BorderLayout.EAST);


        this.add(panel);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.comboBox)){
            String file = (String) this.comboBox.getSelectedItem();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String text = "";
                String line = reader.readLine();
                while (line != null) {
                    text += line + "\n";
                    line = reader.readLine();
                }
                reader.close();
                this.textArea.setText(text);
            }
            catch (IOException exception){
                System.out.println("");
                exception.printStackTrace();
            }
        }
        if(e.getSource().equals(this.btnCerrar)) {
            dispose();
        }
        if(e.getSource().equals(this.btnBorrar)){
            this.textArea.setText("");
        }
    }

    public static void main(String[] args) {
        new Ventana();
    }
}

