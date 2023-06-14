package dbconnection;

import java.sql.*;

public class DBconnection {

    private static Connection conexioa;
    private static final String url = "jdbc:mysql://localhost:3306/mugarria3_6";
    private static final String usuario = "root";
    private static final String contraseña = "";

    //Datu baserako konexioa
    public static Connection getConnection(){
        try{
            conexioa = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Datu basera konektatzen");
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conexioa;
    }
}
