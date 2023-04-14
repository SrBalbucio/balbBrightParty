package xyz.balbucio.bright.party.bungeecord.eventos;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import xyz.balbucio.bright.party.bungeecord.manager.Party;
import xyz.balbucio.bright.party.bungeecord.manager.PartyManager;
import xyz.balbucio.bright.party.bungeecord.manager.PartyType;

public class ServerSwitch implements Listener {

    @EventHandler
    public void serverSwitch(ServerSwitchEvent evt){
        ProxiedPlayer player = evt.getPlayer();
        String message = "";
        if(PartyManager.partys.containsKey(player)){
            Party party = PartyManager.partys.get(player);
            if(party.getType() == PartyType.JOGO) {
                for (ProxiedPlayer convidado : PartyManager.partys.get(player).getPlayers()) {
                    if(message.length() == 0){
                        message = convidado.getName();
                    } else {
                        message = message +"/"+convidado.getName();
                    }
                    convidado.connect(player.getServer().getInfo());
                    convidado.sendMessage(new TextComponent("§d[§bP§d] §aConectando..."));
                }
            }
            if(!(BungeeCord.getInstance().getPlayers().size() == 0)) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("balbParty");
                out.writeUTF(party.getName());
                out.writeUTF(message);
                out.writeUTF(party.getType().toString());
                out.writeInt(party.getSlots());
                out.writeUTF(party.getOwner().toString());
                out.writeUTF(party.getId().toString());
                player.sendData("balbParty:channel", out.toByteArray());
            }
        } else if(PartyManager.partys_convidado.containsKey(player)){
            Party party = PartyManager.partys_convidado.get(player);
            if(party.getType() == PartyType.JOGO) {
                if (!(party.getOwner().getServer() == player.getServer())) {
                    player.connect(party.getOwner().getServer().getInfo());
                    player.sendMessage("§cVocê não pode se mover pela Network!");
                    player.sendMessage("§cVocê está em uma Party do Tipo Jogo, somente o Líder pode puxar!");
                }
            }
        }
    }
}
