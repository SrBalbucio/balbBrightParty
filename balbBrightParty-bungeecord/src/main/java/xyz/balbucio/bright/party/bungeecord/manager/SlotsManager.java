package xyz.balbucio.bright.party.bungeecord.manager;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class SlotsManager {

    public static int get(ProxiedPlayer player){
        if(player.hasPermission("party.simples")){
            return 4;
        } else if(player.hasPermission("party.vip")){
            return 12;
        } else if(player.hasPermission("party.midia")){
            return 24;
        } else{
            return 4;
        }
    }
}
