package xyz.balbucio.bright.party.bungeecord.eventos;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.balbucio.bright.party.bungeecord.manager.PartyManager;

public class PlayerEntry implements Listener {

    @EventHandler
    public void onJoin(PostLoginEvent evt){
        ProxiedPlayer player = evt.getPlayer();
    }

    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent evt){
        ProxiedPlayer player = evt.getPlayer();
        if(PartyManager.partyMembers.contains(player)){
            if(PartyManager.partys.containsKey(player)){
                PartyManager.partys.remove(player);
                PartyManager.partyMembers.remove(player);
                PartyManager.partys.get(player).setRandomOwner();
            } else if(PartyManager.partys_convidado.containsKey(player)){
                PartyManager.partys_convidado.remove(player);
                PartyManager.partyMembers.remove(player);
                PartyManager.partys_convidado.get(player).disconnect(player);
            }
        }
    }
}
