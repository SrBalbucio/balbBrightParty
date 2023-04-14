package xyz.balbucio.bright.amigo.spigot.sql;

import me.vagdedes.mysql.database.SQL;

public class SQLDep {


    public static String table = "balbAmigo";
    public static void onCheck(){
        if(!SQL.tableExists("balbAmigo")){
            SQL.createTable(table, "nome VARCHAR(255), amigos VARCHAR(255), pedidos VARCHAR(255)");
        }
    }
}
