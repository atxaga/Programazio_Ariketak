package dbconnection;

import model.Fotos;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBCrud {
    private Connection conn;

    private static final String GET_PHOTOGRAPHERS = "select name from photographers";
    private static final String GET_PHOTOGRAPHERID = "select photographerId from photographers where name = ?";
    private static final String GET_PICTURES = "select * from pictures where photographerId = ?";
    private static final String GET_PICTURES_DATE = "select * from pictures where photographerId = ? and date > ?";
    private static final String UPDATE_VISITS = "update pictures set visits = (visits+1) where pictureId = ?";
    private static final String GET_PICTURES_TABLE = "select * from pictures";
    private static  final String UPDATE_AWARDED = "update photographers set awarded = (awarded+1) where photographerId = ?";
    private static final String GET_NON_PHOTOS = "select pictureId from pictures where visits = 0";
    private  static final String REMOVE_PICTURE = "delete from pictures where pictureId = ?";
    private static final String GET_PHOTOGRAPHER_ID_WITH_PICTUREID = "select photographerId from pictures where pictureId = ?";
    private static final String GET_PHOTOGRAPHER_NO_PICTURES = "select count(photographerId) from pictures where photographerId = ?";
    private static final String DELETE_PHOTOGRAPHER_NO_PICTURE = "delete from photographers where photographerId = ?";



    public DBCrud(Connection conn){
        this.conn = conn;
    }

    public List<String> getPhotographerNames() {
        List<String> photographerNames = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(GET_PHOTOGRAPHERS);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                photographerNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return photographerNames;
    }

    public List<Fotos> getPictures(String photographerName) {
        List<Fotos> pictures = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(GET_PICTURES)) {
            st.setInt(1, getPhotographerId(photographerName));

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pictures.add(createPicture(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pictures;
    }

    public List<Fotos> getPicturesByDate(String photographerName, String date) {
        List<Fotos> pictures = new ArrayList<>();

        try (PreparedStatement st = conn.prepareStatement(GET_PICTURES_DATE)) {
            st.setInt(1, getPhotographerId(photographerName));
            st.setString(2, date);

            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    pictures.add(createPicture(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return pictures;
    }

    public int getPhotographerId(String photographerName) {
        int photographerId = 0;

        try (PreparedStatement st = conn.prepareStatement(GET_PHOTOGRAPHERID)) {
            st.setString(1, photographerName);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    photographerId = rs.getInt("photographerId");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return photographerId;
    }

    public void updateVisits(int pictureId) {
        try (PreparedStatement st = conn.prepareStatement(UPDATE_VISITS)) {
            st.setInt(1, pictureId);
            int updated = st.executeUpdate();
            System.out.println(updated + " Eguneratuta");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Fotos createPicture(ResultSet rs) throws SQLException {
        int pictureId = rs.getInt("pictureId");
        String title = rs.getString("title");
        Date date = rs.getDate("date");
        String file = rs.getString("file");
        int visits = rs.getInt("visits");
        int photographerId = rs.getInt("photographerId");

        return new Fotos(pictureId, title, date, file, visits, photographerId);
    }

    public Map<Integer, Integer> createVisitMap() throws SQLException {
        Map<Integer,Integer>visitsHashmap = new HashMap<>();
        DBCrud c = new DBCrud(DBconnection.getConnection());
        PreparedStatement st = c.conn.prepareStatement(GET_PICTURES_TABLE);
        ResultSet rs = st.executeQuery();
        while (rs.next()){
            if (visitsHashmap.containsKey(rs.getInt("photographerId"))){
                visitsHashmap.replace(rs.getInt("photographerId"),rs.getInt("visits"));
            }else {
                visitsHashmap.put(rs.getInt("photographerId"),rs.getInt("visits"));
            }

        }
        return visitsHashmap;
    }
    public void updateAwarded(Integer photographerId) throws SQLException {
       DBCrud c = new DBCrud(DBconnection.getConnection());
       PreparedStatement st = c.conn.prepareStatement(UPDATE_AWARDED);
       st.setString(1, String.valueOf(photographerId));
       st.executeUpdate();
    }

    public List<Integer> getNonPhotos() throws SQLException {
        List<Integer> photosList = new ArrayList<>();
        DBCrud c = new DBCrud(DBconnection.getConnection());
        PreparedStatement st = c.conn.prepareStatement(GET_NON_PHOTOS);
        ResultSet rs = st.executeQuery();
        while(rs.next()){
            photosList.add(rs.getInt("pictureId"));
        }
        return photosList;
    }

    public void ezabatuIrudia(int pictureId) {
        DBCrud c = new DBCrud(DBconnection.getConnection());
        PreparedStatement st = null;
        int photographerId = getPhotographersIdNoPictures(pictureId);
        try {
            st = c.conn.prepareStatement(REMOVE_PICTURE);
            st.setInt(1, pictureId);
            st.executeUpdate();
            System.out.println("Irudia ezabatu da");
            deletePhotographerNoPic(photographerId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public int getPhotographersIdNoPictures(int pictureId){

        DBCrud c = new DBCrud(DBconnection.getConnection());
        int photographerId = 0;
        try {
            PreparedStatement getPhotographerId = c.conn.prepareStatement(GET_PHOTOGRAPHER_ID_WITH_PICTUREID);
            getPhotographerId.setInt(1, pictureId);
            ResultSet rs1 = getPhotographerId.executeQuery();
            while (rs1.next()) {
                photographerId = rs1.getInt("photographerId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return  photographerId;
    }

    public void deletePhotographerNoPic(int photographerId) throws SQLException {
        DBCrud c = new DBCrud(DBconnection.getConnection());
        int idkopurua = 0;
        PreparedStatement getPhotographerNoPic = c.conn.prepareStatement(GET_PHOTOGRAPHER_NO_PICTURES);
        getPhotographerNoPic.setInt(1, photographerId);
        ResultSet rs2 = getPhotographerNoPic.executeQuery();
        while (rs2.next()){
            idkopurua = rs2.getInt(1);
            if (idkopurua == 0){
                PreparedStatement delete = c.conn.prepareStatement(DELETE_PHOTOGRAPHER_NO_PICTURE);
                delete.setInt(1, photographerId);
                delete.executeUpdate();
                System.out.println("Argazkilaria ezabatu da");
            }
        }
    }




}
