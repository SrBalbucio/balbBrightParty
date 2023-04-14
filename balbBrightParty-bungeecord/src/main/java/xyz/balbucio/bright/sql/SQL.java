package xyz.balbucio.bright.sql;

import com.mysql.cj.jdbc.MysqlDataSource;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.balbucio.bright.party.bungeecord.utils.balbLogger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQL {
    public static Connection connection;
    public static  String host, database, username, password;
    public static int port;

    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            MysqlDataSource dataSource = new MysqlDataSource();
            dataSource.setServerName(host);
            dataSource.setPort(port);
            dataSource.setDatabaseName(database);
            dataSource.setUser(username);
            dataSource.setPassword(password);
            connection = dataSource.getConnection();
            balbLogger.sendSQL("§aConectado com sucesso!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            balbLogger.sendSQL("§4Não foi possível conectar via SQL!");
        }
    }
    public static String getIdioma(ProxiedPlayer player) {
        String idioma = "";
        try {
            connect();
        PreparedStatement stat = connection.prepareStatement("SELECT idioma FROM balbProfilePlayer WHERE nome = ?");
            stat.setString(1, player.getName());
        ResultSet result = stat.executeQuery();
        while(result.next()){
            idioma = result.getString("idioma");
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idioma;
    }

    public static void setBIO(String name, String bio){
        try {
            PreparedStatement st = connection.prepareStatement("UPDATE balbProfilePerfil SET BIO = ? WHERE nome = ?");
            st.setString(2, name);
            st.setString(1, bio);
            st.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
