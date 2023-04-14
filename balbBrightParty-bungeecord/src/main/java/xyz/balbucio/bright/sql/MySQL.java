package xyz.balbucio.bright.sql;

import java.sql.SQLException;

public class MySQL {

    public static boolean isConnected(){
        if(SQL.connection != null){
            return true;
        }
        return false;
    }
    public static void connect(){
        if(SQL.connection == null){
            SQL.connect();
        }
    }
    public static void disconnect(){
        if(SQL.connection != null) {
            try {
                SQL.connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
