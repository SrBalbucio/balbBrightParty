package xyz.balbucio.bright.party.bungeecord.utils;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;

public class balbLogger {

    public static void sendLoad(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a[balbPartyAndFriends] §f[LOAD] §a"+message));
    }
    public static void sendError(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a[balbPartyAndFriends] §c[ERROR] §4"+message));
    }
    public static void sendParty(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a[balbPartyAndFriends] §f[Party] §a"+message));
    }
    public static void sendFile(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a[balbPartyAndFriends] §f[File] §a"+message));
    }
    public static void sendSQL(String message){
        ProxyServer.getInstance().getConsole().sendMessage(new TextComponent("§a[balbPartyAndFriends] §f[SQL] §a"+message));
    }
}
