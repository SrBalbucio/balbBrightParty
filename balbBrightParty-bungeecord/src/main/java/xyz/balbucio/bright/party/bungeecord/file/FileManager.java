package xyz.balbucio.bright.party.bungeecord.file;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import xyz.balbucio.bright.party.bungeecord.utils.balbLogger;
import xyz.balbucio.bright.sql.SQL;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {
    public static ArrayList<ServerInfo> blocked = null;
    static File file = new File("plugins/balbParty", "servers.yml");
    static File sql = new File("plugins/balbParty", "servers.yml");
    static File folder = new File("plugins/balbParty");
    public static void load(){
        try {
            if(!file.exists() || !folder.exists()) {
                folder.mkdir();
                file.createNewFile();
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
                ArrayList<String> list = new ArrayList<>();
                list.add("Login");
                list.add("Login2");
                config.set("ServidoresBloqueados", list);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, file);
            }
            if(!sql.exists() || !folder.exists()) {
                folder.mkdir();
                sql.createNewFile();
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(sql);
                config.set("database", "s1_Bungee");
                config.set("user", "admin");
                config.set("senha", "BrightCloud**");
                config.set("host", "34.95.176.229");
                config.set("porta", 3306);
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, sql);
            }
            Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            for(String sv : config.getStringList("ServidoresBloqueados")){
                try{
                    ServerInfo info = ProxyServer.getInstance().getServerInfo(sv);
                    if(info != null) {
                        blocked.add(info);
                    }
                } catch(Exception e){

                }
            }
            config =ConfigurationProvider.getProvider(YamlConfiguration.class).load(sql);
            SQL.database = config.getString("database");
            SQL.host = config.getString("host");
            SQL.username = config.getString("user");
            SQL.password = config.getString("senha");
            SQL.port = config.getInt("porta");
            balbLogger.sendFile("Config carregada com sucesso!");
        }catch (Exception e){
            balbLogger.sendFile("§cOcorreu um erro ao carregar a config, por favor aguarde a nova tentativa.");
        }
    }
    public static Configuration get() throws IOException {
        return ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }
}
