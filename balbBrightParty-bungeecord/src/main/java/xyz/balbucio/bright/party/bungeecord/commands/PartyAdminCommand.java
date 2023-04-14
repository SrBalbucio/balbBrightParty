package xyz.balbucio.bright.party.bungeecord.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.balbucio.bright.Bungee;
import xyz.balbucio.bright.party.bungeecord.manager.Party;
import xyz.balbucio.bright.party.bungeecord.manager.PartyManager;

import java.util.concurrent.TimeUnit;

public class PartyAdminCommand extends Command {
    public PartyAdminCommand() {
        super("partyadm", "party.adm", "partyadmin");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender.hasPermission("party.adm")) {
            if (args.length == 0) {
                sender.sendMessage("§a§lAJUDA §f- §aParty Admin:");
                sender.sendMessage("§a/partyadm §f- §7Envia essa mensagem");
                sender.sendMessage("§a/partyadm listar §f- §7Lista todas as Partys atuais");
                sender.sendMessage("§a/partyadm clear §f- §7Deleta todas as Partys criadas e reseta todas as configs");
            } else if (args.length > 0 && args[0].equalsIgnoreCase("listar")) {
                sender.sendMessage("§a§lPartys Ativas§f: §7(Lista Completa)");
                for (Party party : PartyManager.partys.values()) {
                    TextComponent partyMsg = new TextComponent("§7Nome: §e" + party.getName() + " §7UUID: " + party.getId());
                    String messageFlat = null;
                    for(ProxiedPlayer member : party.getPlayers()){
                        if(messageFlat == null){
                            messageFlat = member.getName();
                        } else{
                            messageFlat = messageFlat+", "+member.getName();
                        }
                    }
                    partyMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Nome da Party: §f"+party.getName()+"\n§7Dono da Party: §f"+party.getOwner().getName()+"\n§7Players: §f"+messageFlat+"\n§7Slots: §f"+party.getSlots()+"\n§7Tipo: §f"+party.getType().toString()+"\n§7ID: §f"+party.getId().toString()+"\n\n§cClique para deletar!")));
                    partyMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/partyadm deletar "+party.getOwner().toString()));
                    sender.sendMessage(partyMsg);
                }
            } else if (args.length > 0 && args[0].equalsIgnoreCase("clear")) {
                sender.sendMessage("§4§lAVISO: §cOs Players não serão avisados de que suas Partys foram apagadas, elas apenas sumiram! O Comando será executado dentro dos próximos segundos...");
                for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()){
                    if(all.hasPermission("party.adm")){
                        all.sendMessage("§cO §f"+sender.getName()+" §ciniciou um processo de Clear das Partys, dentro de alguns segundos o comando deve ser executado.");
                    }
                }
                ProxyServer.getInstance().getScheduler().schedule(Bungee.get(), new Runnable() {
                    @Override
                    public void run() {
                        sender.sendMessage("§cAs Partys estão sendo deletadas...");
                        PartyManager.partyMembers.clear();
                        PartyManager.partys.clear();
                        PartyManager.partys_convidado.clear();
                        sender.sendMessage("§cPartys deletadas com sucesso!");
                    }
                }, 30, TimeUnit.SECONDS);
            } else if(args.length > 0 && args[0].equalsIgnoreCase("deletar")){
                String name = args[1];
                ProxiedPlayer owner = ProxyServer.getInstance().getPlayer(name);
                if(owner != null){
                    owner.sendMessage("§cSua Party foi deletada por um Administrador!");
                    Party party = PartyManager.partys.get(owner);
                    for (ProxiedPlayer membro : party.getPlayers()) {
                        PartyManager.partyMembers.remove(membro);
                        PartyManager.partys_convidado.remove(membro);
                    }
                    party.broadcast("§cA Party em que você estava foi deletada por um Administrador");
                    PartyManager.partys.remove(owner);
                    PartyManager.partyMembers.remove(owner);
                    party.sendDelete();
                }
            }
        }
    }
}
