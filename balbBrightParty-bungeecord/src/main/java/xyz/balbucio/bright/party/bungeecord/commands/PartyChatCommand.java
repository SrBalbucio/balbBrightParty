package xyz.balbucio.bright.party.bungeecord.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.balbucio.bright.party.bungeecord.manager.PartyManager;

public class PartyChatCommand extends Command {
    public PartyChatCommand() {
        super("p");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer senderPlayer = (ProxiedPlayer) sender;
            if (args.length == 0) {
                sender.sendMessage(new TextComponent("§cUse: /p <mensagem>"));
            } else {
                if (PartyManager.partyMembers.contains((ProxiedPlayer) sender)) {
                    String message = " ";
                    for (int i = 0; i < args.length; i++) {
                        message = message + " " + args[i];
                    }
                    if (PartyManager.partys.containsKey((ProxiedPlayer) sender)) {
                        PartyManager.partys.get(senderPlayer).chat(senderPlayer.getDisplayName()+":§f"+message);
                    } else {
                        PartyManager.partys_convidado.get(senderPlayer).chat(senderPlayer.getDisplayName()+":§f"+message);
                    }
                }
            }
        } else{
            sender.sendMessage(new TextComponent("§cSomente players podem usar esse comando!"));
        }
    }
}
