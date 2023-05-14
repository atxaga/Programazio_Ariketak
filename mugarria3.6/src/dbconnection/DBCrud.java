package dbconnection;

import model.Fotos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCrud {
    private Connection conn;
    private static final String GET_ALL_PHOTOGRAPHER_NAMES = "SELECT name FROM Photographers";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER = "SELECT * FROM Pictures WHERE photographerId = ?";
    private static final String GET_PHOTOGRAPHER_ID = "SELECT photographerId FROM Photographers WHERE name = ?";
    private static final String GET_PICTURES_BY_PHOTOGRAPHER_AND_DATE = "SELECT * FROM Pictures WHERE photographerId = ? AND date > ?";
    private static final String UPDATE_PICTURE_VISITS = "UPDATE Pictures SET visits=(visits+1) WHERE pictureId = ?";

    public DBCrud(Connection conn){
        this.conn = conn;
    }


    public List<String> getPhotographerNames(){
        List<String> photographerNames = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_ALL_PHOTOGRAPHER_NAMES)){
            rs = st.executeQuery();

            while(rs.next()){
                photographerNames.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return photographerNames;
    }

    public List<Fotos> getPicuresByPhotographer(String photographerName){
        List<Fotos> pictures = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_PICTURES_BY_PHOTOGRAPHER)) {
            st.setInt(1, getPhotographerId(photographerName));
            rs = st.executeQuery();

            while(rs.next()){
                pictures.add(createPicture(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pictures;
    }

    public List<Fotos> getPicuresByPhotographerAndDate(String photographerName, String date){
        List<Fotos> pictures = new ArrayList<>();
        ResultSet rs = null;

        try(PreparedStatement st = conn.prepareStatement(GET_PICTURES_BY_PHOTOGRAPHER_AND_DATE)) {
            st.setInt(1, getPhotographerId(photographerName));
            st.setString(2,date);
            rs = st.executeQuery();

            while(rs.next()){
                pictures.add(createPicture(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pictures;
    }

    public int getPhotographerId(String photographerName){
        ResultSet rs = null;
        int photographerId = 0;

        try(PreparedStatement st = conn.prepareStatement(GET_PHOTOGRAPHER_ID)){
            st.setString(1, photographerName);
            rs = st.executeQuery();

            if(rs.next()){
                photographerId = rs.getInt("photographerId");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return photographerId;
    }

    public void updateVisits(int pictureId){
        try(PreparedStatement st = conn.prepareStatement(UPDATE_PICTURE_VISITS)) {
            st.setInt(1, pictureId);
            int updated = st.executeUpdate();
            System.out.println(updated+ " column updated");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private Fotos createPicture(ResultSet rs) throws SQLException {
        int pictureId = rs.getInt("pictureId");
        String title = rs.getString("title");
        Date date_ = rs.getDate("date");
        String file = rs.getString("file");
        int visits = rs.getInt("visits");
        int photographerId = rs.getInt("photographerId");

        return new Fotos(pictureId,title,date_,file,visits,photographerId);

    }
}
