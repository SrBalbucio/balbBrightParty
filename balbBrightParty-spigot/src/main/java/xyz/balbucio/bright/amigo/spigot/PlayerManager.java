package xyz.balbucio.bright.amigo.spigot;

import me.vagdedes.mysql.database.SQL;
import xyz.balbucio.bright.amigo.spigot.sql.SQLDep;

import java.util.ArrayList;

public class PlayerManager {

    public static void check(String name){
        if(!SQL.exists("nome", name, SQLDep.table)){
            SQL.insertData("nome, pedidos, amigos", "'"+name+"', 'null', 'null'", SQLDep.table);
        }
    }

    public static ArrayList<String> getPedidos(String amigo){
        check(amigo);
        String pedidoString = (String) SQL.get("pedidos", "nome", "=", amigo, SQLDep.table);
        ArrayList<String> pedidos = new ArrayList<>();
        if(!pedidoString.equalsIgnoreCase("null")) {
            for (String name : pedidoString.split("/")) {
                pedidos.add(name);
            }
        }
        return pedidos;
    }

    public static boolean negarPedido(String amigo, String player){
        ArrayList<String> pedidos = getPedidos(amigo);
        if(pedidos.contains(player)){
            pedidos.remove(player);
            return true;
        }
        return false;
    }

    public static boolean addPedidos(String amigo, String player) {
        ArrayList<String> playerPedidos = getPedidos(player);
        if (playerPedidos.contains(amigo)) {
            playerPedidos.remove(amigo);
            String listaString = "null";
            for (String amg : playerPedidos) {
                if (listaString.equalsIgnoreCase("null")) {
                    listaString = amg;
                } else {
                    listaString = listaString + "/" + amg;
                }
            }
            SQL.set("pedidos", listaString, "nome", "=", player, SQLDep.table);
            addAmigos(amigo, player);
            return true;
        } else {
            ArrayList<String> pedidos = getPedidos(amigo);
            pedidos.add(player);
            String listaString = "null";
            for (String amg : pedidos) {
                if (listaString.equalsIgnoreCase("null")) {
                    listaString = amg;
                } else {
                    listaString = listaString + "/" + amg;
                }
            }
            SQL.set("pedidos", listaString, "nome", "=", amigo, SQLDep.table);
            return false;
        }
    }
    public static ArrayList<String> getAmigos(String amigo){
        check(amigo);
        String amigoString = (String) SQL.get("amigos", "nome", "=", amigo, SQLDep.table);
        ArrayList<String> pedidos = new ArrayList<>();
        if(!amigoString.equalsIgnoreCase("null")) {
            for (String name : amigoString.split("/")) {
                pedidos.add(name);
            }
        }
        return pedidos;
    }

    public static void addAmigos(String amigo, String player){
        ArrayList<String> amigos = getAmigos(amigo);
        amigos.add(player);
        String listaString = "null";
        for(String amg : amigos){
            if(listaString.equalsIgnoreCase("null")){
                listaString = amg;
            } else {
                listaString = listaString+"/"+amg;
            }
        }
        SQL.set("amigos", listaString, "nome", "=", amigo, SQLDep.table);

        amigos = getAmigos(player);
        listaString = "null";
        for(String amg : amigos){
            if(listaString.equalsIgnoreCase("null")){
                listaString = amg;
            } else {
                listaString = listaString+"/"+amg;
            }
        }
        SQL.set("amigos", listaString, "nome", "=", player, SQLDep.table);
    }

}
