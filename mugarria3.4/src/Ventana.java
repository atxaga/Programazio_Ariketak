import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Ventana extends JFrame {

    private JComboBox <String> aukerak;
    private ImageIcon imageIcon;
    private JLabel labelArgazkia;
    private JPanel panelMain;
    private JCheckBox checkBox;
    private JButton save;
    public Ventana() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mugarria 4");
        setSize(500, 500);

        this.panelMain = new JPanel();
        this.panelMain.setLayout(new BoxLayout(this.panelMain, BoxLayout.Y_AXIS));

        // ComboBox
        this.aukerak = new JComboBox<String>();
        this.aukerak.addItem("charlesleclerc.jpg");
        this.aukerak.addItem("fernandoalonso.jpg");
        this.aukerak.addItem("sebastianvettel.jpg");
        this.panelMain.add(this.aukerak);

        // Argazkia kargatu
        String imageName = "charlesleclerc.jpg";
        this.imageIcon = new ImageIcon("./fotos/" + imageName);
        Image foto = this.imageIcon.getImage();
        Image newfoto = foto.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon newimageIcon = new ImageIcon(newfoto);
        this.labelArgazkia = new JLabel(newimageIcon);
        this.panelMain.add(this.labelArgazkia);



        this.aukerak.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String imageName = (String) aukerak.getSelectedItem();
                imageIcon = new ImageIcon("./fotos/" + imageName);
                Image foto = imageIcon.getImage();
                Image newfoto = foto.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                ImageIcon newimageIcon = new ImageIcon(newfoto);
                labelArgazkia.setIcon(newimageIcon);
            }
        });


        // Checkbox y TextArea
        this.checkBox = new JCheckBox("Gorde iruzkinak", true);
        this.panelMain.add(this.checkBox);

        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setPreferredSize(new Dimension(10, 5));
        this.panelMain.add(textAreaPanel);

        JLabel label = new JLabel("Iruzkinak: ");
        textAreaPanel.add(label, BorderLayout.WEST);

        JTextArea textAreaSmall = new JTextArea();
        textAreaSmall.setPreferredSize(new Dimension(10, 5));
        textAreaPanel.add(textAreaSmall, BorderLayout.EAST);

        JTextArea textArea = new JTextArea();
        textAreaPanel.add(textArea, BorderLayout.CENTER);

        // Boton Guardar
        this.save = new JButton("GORDE");
        this.panelMain.add(this.save);

        // Eventos
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedImage = (String) aukerak.getSelectedItem();
                String comment = textArea.getText();
                OutputStream outputStream = null;

                if (checkBox.isSelected()) {
                    String fileName = selectedImage.substring(0, selectedImage.lastIndexOf(".")) + ".txt";
                    try {
                        outputStream = new FileOutputStream(fileName, true);
                        outputStream.write(comment.getBytes());
                        outputStream.write("\n".getBytes());

                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    } finally {
                        try {
                            outputStream.close();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }
            }
        });

        this.add(panelMain);

    }


    public static void main(String[] args) {

        // Preguntar por la contraseña
        String input = JOptionPane.showInputDialog(null, "Sartu pasahitza");

        // Comprobar la contraseña
        if (input == null || !input.equals("damocles")) {
            JOptionPane.showMessageDialog(null, "Pasahitza okerra da", "Errorea", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        //Contraseña correcta
        Ventana ventana = new Ventana();
        ventana.setVisible(true);

        ventana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(null, "Agur!");
            }
        });
    }
}