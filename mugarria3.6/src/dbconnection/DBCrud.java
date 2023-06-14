package dbconnection;

import model.Fotos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCrud {
    private Connection conn;

    private static final String GET_PHOTOGRAPHERS = "select name from photographers";
    private static final String GET_PHOTOGRAPHERID = "select photographerId from photographers where name = ?";
    private static final String GET_PICTURES = "select * from pictures where photographerId = ?";
    private static final String GET_PICTURES_DATE = "select * from pictures where photographerId = ? and date > ?";
    private static final String UPDATE_VISITS = "update pictures set visits = (visits+1) where pictureId = ?";



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
}
