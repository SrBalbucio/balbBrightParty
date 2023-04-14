package xyz.balbucio.bright.party.spigot.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.balbucio.bright.party.spigot.manager.PartyListener;
import xyz.balbucio.bright.party.spigot.manager.PartySpigot;

public class PartySpigotAdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission("party.adm")){
            if(args.length == 0){
                sender.sendMessage("§a§lAJUDA §f- §aParty Admin:");
                sender.sendMessage("§a/partyspigotadm §f- §7Envia essa mensagem");
                sender.sendMessage("§a/partyspigotadm listar §f- §7Lista todas as Partys atuais");
            } else if(args.length > 0 && args[0].equalsIgnoreCase("listar")){
                sender.sendMessage("§a§lPartys Ativas§f: §7(Lista Completa)");
                for (PartySpigot party : PartyListener.party.values()) {
                    TextComponent partyMsg = new TextComponent("§7Nome: §e" + party.getName() + " §7UUID: " + party.getId());
                    String messageFlat = null;
                    for(Player member : party.getPlayers()){
                        if(messageFlat == null){
                            messageFlat = member.getName();
                        } else{
                            messageFlat = messageFlat+", "+member.getName();
                        }
                    }
                    partyMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Nome da Party: §f"+party.getName()+"\n§7Dono da Party: §f"+party.getOwner().getName()+"\n§7Players: §f"+messageFlat+"\n§7Slots: §f"+party.getSlots()+"\n§7Tipo: §f"+party.getType().toString()+"\n§7ID: §f"+party.getId().toString()).create()));
                    ((Player) sender).spigot().sendMessage(partyMsg);
                }
            }
        }
        return false;
    }
}
