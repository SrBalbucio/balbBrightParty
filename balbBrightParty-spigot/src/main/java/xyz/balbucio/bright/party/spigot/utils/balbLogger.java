package xyz.balbucio.bright.party.spigot.utils;

import org.bukkit.Bukkit;

public class balbLogger {

    public static void sendLoad(String message){
        Bukkit.getConsoleSender().sendMessage("§a[balbParty] §f[LOAD] §a"+message);
    }
    public static void sendError(String message){
        Bukkit.getConsoleSender().sendMessage("§a[balbParty] §c[ERROR] §4"+message);
    }
    public static void sendParty(String message){
        Bukkit.getConsoleSender().sendMessage("§a[balbParty] §f[Party] §a"+message);
    }
}
