package xyz.balbucio.bright.party.spigot.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.balbucio.bright.party.spigot.utils.Item;

import java.util.Arrays;
import java.util.List;

public class PartyGUI {

    public static void open(Player player, String partyName, String owner, String open, List<String> players, String type, int slots, String id){
        try {
            Inventory inv = Bukkit.createInventory(null, 9 * 1, "§7Party: §f" + partyName);
            ItemStack info = new Item(Material.BOOK, 1, (short) 0)
                    .setName("§aInformações")
                    .setLore(Arrays.asList("§7Nome da Party: §f" + partyName, "§7Dono da Party: §f" + owner, "§7Slots: §f" + slots))
                    .getItemStack();
            ItemStack membros = new Item(Material.COMPASS, 1, (short) 0)
                    .setName("§aMembros")
                    .setLore(players)
                    .getItemStack();
            ItemStack config = new Item(Material.REDSTONE_COMPARATOR, 1, (short) 0)
                    .setName("§aConfigurações")
                    .setLore(Arrays.asList("§7Tipo da Party§: §f" + type, "§7Está aberta? §f" + open, "§7ID: §f" + id))
                    .getItemStack();

            inv.setItem(0, info);
            inv.setItem(4, config);
            inv.setItem(8, membros);
            player.openInventory(inv);
        }catch (Exception e){
            e.printStackTrace();
            player.sendMessage("§a§lParty Info: §r§f"+partyName);
            player.sendMessage("§7Dono da Party: §f"+owner);
            player.sendMessage("§7Slots: §f"+slots);
            player.sendMessage("§7ID: §f"+id);
            player.sendMessage("§7Tipo: §f"+type);
            player.sendMessage("§7Está aberta? §f"+open);
            String messageFlat = null;
            for(String member : players){
                if(messageFlat == null){
                    messageFlat = member;
                } else{
                    messageFlat = messageFlat+", "+member;
                }
            }
            player.sendMessage("§7Membros da Party: §f"+messageFlat);
        }
    }
}
