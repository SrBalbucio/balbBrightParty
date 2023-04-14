package xyz.balbucio.bright.party.spigot.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.balbucio.bright.party.spigot.manager.PartyListener;
import xyz.balbucio.bright.party.spigot.utils.balbLogger;

import java.util.UUID;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onDisconnect(PlayerQuitEvent evt){
        Player player = evt.getPlayer();
        try {
            if (PartyListener.partybyOwner.containsKey(player)) {
                UUID id = PartyListener.partybyOwner.get(player).getId();
                PartyListener.partybyOwner.remove(player);
                if(PartyListener.party.containsKey(id)) {
                    PartyListener.party.remove(id);
                }
            }
        }catch(Exception e){
            balbLogger.sendParty("§cOcorreu um erro ao retirar a Party do "+evt.getPlayer().getName()+" da Lista!");
        }
    }
}
