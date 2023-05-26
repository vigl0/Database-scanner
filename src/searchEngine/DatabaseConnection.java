package searchEngine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class DatabaseConnection {
    private static Connection mysqlConnect;
    public static Connection dbConnection() throws SQLException {
        if(mysqlConnect==null){
            try {
                String DB="jdbc:mysql://localhost:3306/MyDataBase";
                String user="root";
                String pass="";
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
                mysqlConnect = (Connection) DriverManager.getConnection(DB,user,pass);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"Connection Fail !");
            }
        }
        return mysqlConnect;
    }
}
