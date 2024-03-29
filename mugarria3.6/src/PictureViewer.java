import model.Fotos;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;


import dbconnection.*;

public class PictureViewer extends JFrame {

    private JComboBox<String> comboBox;

    private JXDatePicker datePicker;
    private JLabel imageLabel;
    private JList imageList;
    private ImageIcon imagePicture;
    List<Fotos> images;

    public PictureViewer() {
        setTitle("Argazkiak");
        setSize(800, 400);
        setLayout(new GridLayout(2, 2));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //Argazkilariekin dauden combobox panela
        JPanel comboboxPanela = new JPanel();
        add(comboboxPanela);
        this.comboBox  = new JComboBox<>();
        this.comboBox.addItem(null);
        comboboxPanela.add(new JLabel("Argazkilariak"));
        comboboxPanela.add(this.comboBox);
        this.comboBox.addActionListener(e -> loadPhotographersCombobox((String) comboBox.getSelectedItem()));

        //Data aukeratzeko (datePicker-en bidez) panela
        JPanel datepickerPanela = new JPanel();
        add(datepickerPanela);
        this.datePicker = new JXDatePicker();
        datepickerPanela.add(new JLabel("Data: "));
        datepickerPanela.add(this.datePicker);
        this.datePicker.addActionListener(e -> loadPictureList((String) comboBox.getSelectedItem()));

        //Argazkilarien argazkien Jlista
        JPanel jlistPanela = new JPanel();
        add(jlistPanela);
        this.imageList = new JList<>();
        jlistPanela.add(imageList);
        imageList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() ==  1){
                    for(Fotos f: images){
                        if(imageList.getSelectedValue().equals(f.getTitle())){
                            loadPictureImage(f.getFile());
                            new DBCrud(DBconnection.getConnection()).updateVisits(f.getPictureId());
                        }
                    }
                }
            }
        });

        //Argazkiak ikusko diren panela
        JPanel imagePanel = new JPanel();
        this.imageLabel = new JLabel();
        imagePanel.add(this.imageLabel);
        jlistPanela.add(new JScrollPane(imageList));
        add(imagePanel);


        setVisible(true);
        //DBCrud c = new DBCrud(DBconnection.getConnection());
    }

    // Argazkilarien izenak combobox-era gehitu
    public void loadPhotographersCombobox(String selectedItem){
        DBCrud c = new DBCrud(DBconnection.getConnection());
        List<String> argazkilarienLista;
        argazkilarienLista = c.getPhotographerNames();
        for (String s: argazkilarienLista) {
            this.comboBox.addItem(s);
        }
    }
    //Argazkilarien irudiak hautatzeko listan iruadiak gehitu
    private void loadPictureList(String photographerName){
        DBCrud c = new DBCrud(DBconnection.getConnection());
        DefaultListModel<String> pictureTitles = new DefaultListModel<>();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        String newDate = formatDate.format(datePicker.getDate());
        if(newDate == null){
            this.images = c.getPictures(photographerName);
        }else{
            this.images = c.getPicturesByDate(photographerName, newDate);
        }

        for (Fotos f: this.images){
            pictureTitles.addElement(f.getTitle());
        }
        this.imageList.setModel(pictureTitles);
    }

    //irudiak gehitu
    private void loadPictureImage(String imagePath){
        this.imagePicture = new ImageIcon("src/Fotos/"+imagePath);
        this.imageLabel.setIcon(this.imagePicture);
    }
    public static void main(String[] args) {
        new PictureViewer();

    }
}

