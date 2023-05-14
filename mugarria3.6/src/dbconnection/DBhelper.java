package dbconnection;

import model.Fotografo;
import model.Fotos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBhelper {

    private static Connection conexioa;
    private static final String url = "jdbc:mysql://localhost:3306/mugarria3_6";
    private static final String usuario = "root";
    private static final String contraseña = "";

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
