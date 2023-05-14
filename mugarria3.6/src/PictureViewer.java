import model.Fotos;

import org.jdesktop.swingx.JXDatePicker;

import javax.swing.*;
import java.awt.*;

import java.text.SimpleDateFormat;
import java.util.List;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import dbconnection.*;

public class PictureViewer extends JFrame {

    private JComboBox<String> comboBox;

    private JXDatePicker datePicker;
    private JLabel imageLabel;
    private JList imagelist;
    private ImageIcon imagePicture;
    List<Fotos> images;

    public PictureViewer() {
        setTitle("Argazkiak");
        setLayout(new GridLayout(2,2));
        setSize(800,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        this.comboBox = new JComboBox<>();
        this.datePicker = new JXDatePicker();
        this.imagelist = new JList<>();
        this.imageLabel = new JLabel();




        JPanel photographerPanel = new JPanel();
        comboBox.setPreferredSize(new Dimension(200,20));
        comboBox.addItem(null);
        comboBox.addActionListener(e -> loadPictureList((String) comboBox.getSelectedItem()));
        this.loadPhotographersComboBox();

        photographerPanel.add(new JLabel("Argazkilaria: "));
        photographerPanel.add(comboBox);


        JPanel datePanel = new JPanel();
        datePanel.add(new JLabel("Data ondoren: "));
        datePicker.addActionListener(e -> loadPictureList((String) comboBox.getSelectedItem()));
        datePanel.add(datePicker);


        JScrollPane pictureListPanel = new JScrollPane(imagelist);
        pictureListPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        imagelist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() ==  2){
                    for(Fotos p: images){
                        if(imagelist.getSelectedValue().equals(p.getTitle())){
                            loadPictureImage(p.getFile());
                            new DBCrud(DBhelper.getConnection()).updateVisits(p.getPictureId());
                        }
                    }
                }
            }
        });

        JPanel picturePanel = new JPanel();
        picturePanel.add(imageLabel);


        add(photographerPanel);
        add(datePanel);
        add(pictureListPanel);
        add(picturePanel);

        pack();
        setVisible(true);
    }

    private void loadPhotographersComboBox(){
        DBCrud c = new DBCrud(DBhelper.getConnection());
        List<String> photographerNames = c.getPhotographerNames();

        for (String p: photographerNames){
            this.comboBox.addItem(p);
        }
    }

    private void loadPictureList(String photographerName){
        DBCrud c = new DBCrud(DBhelper.getConnection());
        DefaultListModel<String> pictureTitles = new DefaultListModel<>();
        if(datePicker.getDate() == null){
            this.images = c.getPicuresByPhotographer(photographerName);
        }else{
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
            String newDate = formatDate.format(datePicker.getDate());

            this.images = c.getPicuresByPhotographerAndDate(photographerName, newDate);
        }

        for (Fotos p: this.images){
            pictureTitles.addElement(p.getTitle());
        }
        this.imagelist.setModel(pictureTitles);
    }

    private void loadPictureImage(String imagePath){
        this.imagePicture = new ImageIcon("src/Fotos/"+imagePath);
        Image image = this.imagePicture.getImage().getScaledInstance(400,400,Image.SCALE_SMOOTH);
        this.imagePicture = new ImageIcon(image);
        this.imageLabel.setIcon(this.imagePicture);
    }

    public static void main(String[] args) {
        new PictureViewer();
    }
}

