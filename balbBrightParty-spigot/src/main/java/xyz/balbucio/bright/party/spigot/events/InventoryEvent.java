package xyz.balbucio.bright.party.spigot.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClickEvent(InventoryClickEvent evt){
        if(evt.getView().getTitle().contains("Party")){
            evt.setCancelled(true);
        }
    }
}
