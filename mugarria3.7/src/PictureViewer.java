import org.jdesktop.swingx.JXDatePicker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import dbconnection.*;

public class PictureViewer extends JFrame {

    private JComboBox<String> comboBox;

    private JXDatePicker datePicker;
    private JLabel jLabel;
    private JLabel imageLabel;
    private JList list;
    private String[] nombreFotografos;

    public PictureViewer() {

        //configuracion de ventana
        this.setTitle("Photografhy");
        this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2,2 ));


        //Panel combobox
        JPanel conboBoxPanel = new JPanel();


        //Configuracion del combobox
        this.comboBox = new JComboBox<String>(this.nombreFotografos);
        this.jLabel = new JLabel("model.Fotografo: ");
        conboBoxPanel.add(this.jLabel);
        conboBoxPanel.add(this.comboBox);
        add(conboBoxPanel);

        //Panel JXDatePicker
        JPanel jdate = new JPanel();


        //Configuracion del JXDatePicker
        this.datePicker = new JXDatePicker();
        jdate.add(this.datePicker);
        add(jdate);

        //Panel JList
        JPanel pjlist = new JPanel();


        //COnfiguracion del Jlist
        this.list = new JList<>();
        pjlist.add(this.list);
        add(pjlist);

        //Panel de label
        JPanel panelabel = new JPanel(new BorderLayout());


        //Configuracion del JLabel
        this.imageLabel = new JLabel();
        panelabel.add(this.imageLabel);
        add(panelabel);




        this.setVisible(true);
    }

    private void loadPhotographersComboBox(){
        DBCrud c = new DBCrud(DBhelper.getConnection());
        List<String> photographerNames = c.getPhotographerNames();

        for (String p: photographerNames){
            this.comboBox.addItem(p);
        }
    }

    private void loadPictureList(String photographerName){
        DBCrud c = new DBCrud(DBHelper.getConnection());

        DefaultListModel<String> pictureTitles = new DefaultListModel<>();

        if(datePicker.getDate() != null){
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = formatDate.format(datePicker.getDate());

            pictures = c.getPicuresByPhotographerAndDate(photographerName, newDate);
        }else{
            pictures = c.getPicuresByPhotographer(photographerName);
        }

        for (Picture p: pictures){
            pictureTitles.addElement(p.getTitle());
        }
        this.pictureList.setModel(pictureTitles);
    }

    private void loadPictureImage(String imagePath){
        pictureImage = new ImageIcon("src/"+imagePath);
        Image image = pictureImage.getImage().getScaledInstance(200,200,Image.SCALE_SMOOTH);
        pictureImage = new ImageIcon(image);
        imageLabel.setIcon(pictureImage);
    }




    public static void main(String[] args) {
        new PictureViewer();
    }
}

