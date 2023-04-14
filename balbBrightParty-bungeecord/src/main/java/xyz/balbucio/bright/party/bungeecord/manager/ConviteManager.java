package xyz.balbucio.bright.party.bungeecord.manager;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import xyz.balbucio.bright.Bungee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class ConviteManager {

    public static ArrayList<Convite> convites = new ArrayList<>();

    public static void create(ProxiedPlayer player, ProxiedPlayer convidado) {
        for(Convite cn : convites){
            if(cn.getPlayer().equals(player) && cn.getConvidado().equals(convidado)){
                player.sendMessage("§aVocê já convidou esse Player! Aguarde o convite expirirar.");
                return;
            }
        }
        Convite con = new Convite(player, convidado);
        convites.add(con);
        ProxyServer.getInstance().getScheduler().schedule(Bungee.get(), new Runnable() {
            @Override
            public void run() {
                if(convites.contains(con)) {
                    con.getPlayer().sendMessage(new TextComponent("§cSeu convite para o §f"+con.getConvidado().getName()+" §cexpirou!"));
                    convites.remove(con);
                }
            }
        }, 60, TimeUnit.SECONDS);
    }

    public static boolean checkAndDelete(ProxiedPlayer player, ProxiedPlayer convidado) {
        for(Convite con : convites) {
            if (con.getPlayer().equals(player) && con.getConvidado().equals(convidado)) {
                convites.remove(con);
                return true;
            }
        }
        return false;
    }
    public static boolean check(ProxiedPlayer player, ProxiedPlayer convidado) {
        for(Convite con : convites) {
            if (con.getPlayer().equals(player) && con.getConvidado().equals(convidado)) {
                return true;
            }
        }
        return false;
    }
}
