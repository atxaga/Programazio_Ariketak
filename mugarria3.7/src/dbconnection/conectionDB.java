package dbconnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conectionDB {

    private static Connection conexioa;
    private static final String url = "jdbc:mysql://localhost:3306/mugarria3_6";
    private static final String usuario = "root";
    private static final String contraseña = "zubiri";

    public static Connection getConnection(){
        try{
            conexioa = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("conectado a la base de datos");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexioa;
    }
}
