package xyz.balbucio.bright;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import xyz.balbucio.bright.amigo.spigot.Manager;
import xyz.balbucio.bright.amigo.spigot.commands.AmigoCommand;
import xyz.balbucio.bright.amigo.spigot.sql.SQLDep;
import xyz.balbucio.bright.party.spigot.commands.PartySpigotAdminCommand;
import xyz.balbucio.bright.party.spigot.events.InventoryEvent;
import xyz.balbucio.bright.party.spigot.events.PlayerEvents;
import xyz.balbucio.bright.party.spigot.gui.PartyGUI;
import xyz.balbucio.bright.party.spigot.manager.PartyListener;
import xyz.balbucio.bright.party.spigot.manager.PartySpigot;
import xyz.balbucio.bright.party.spigot.utils.balbLogger;
import java.util.ArrayList;
import java.util.UUID;

public final class Spigot extends JavaPlugin implements PluginMessageListener {

    @Override
    public void onEnable() {
        // PARTY
        Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(), this);
        this.getCommand("partyspigotadm").setExecutor(new PartySpigotAdminCommand());
        getServer().getMessenger().registerIncomingPluginChannel(this, "balbparty:channel", this);
        //PARTY

        // AMIGO
        SQLDep.onCheck();
        this.getCommand("amigo").setExecutor(new AmigoCommand());
        // AMIGO
        balbLogger.sendLoad("O Plugin foi carregado com sucesso!");
    }

    @Override
    public void onDisable() {

    }

    public void onPluginMessageReceived(String channel, Player player, byte[] bytes)
    {
        if ( !channel.equalsIgnoreCase( "balbparty:channel" ) )
        {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();
        if ( subChannel.equalsIgnoreCase( "balbParty" ) )
        {
            //correto

            String name = in.readUTF();
            String players = in.readUTF();
            String type = in.readUTF();
            int slots = in.readInt();
            String owner = in.readUTF();
            String id = in.readUTF();
            PartyListener.create(name, players, type, slots, owner, UUID.fromString(id));

            //correto
        } else if(subChannel.equalsIgnoreCase("balbPartyMenu")){
            String vdd = in.readUTF();
            String name = in.readUTF();
            String owner = in.readUTF();
            String players = in.readUTF();
            String open = in.readUTF();
            String type = in.readUTF();
            int slots = in.readInt();
            String id = in.readUTF();
            String[] splited = players.split("/");
            ArrayList<String> rPlayer = new ArrayList<>();
            rPlayer.add("§b"+owner);
            for(String s : splited) {
                rPlayer.add("§7"+s);
            }
            Player vddPlayer = Bukkit.getPlayer(vdd);
            if(vddPlayer == player) {
                player.sendMessage("§aAbrindo...");
                PartyGUI.open(player, name, owner, open, rPlayer, type, slots, id);
            } else {
                vddPlayer.sendMessage("§aAbrindo...");
                PartyGUI.open(vddPlayer, name, owner, open, rPlayer, type, slots, id);
            }
        } else if(subChannel.equalsIgnoreCase("balbPartyDelete")) {
            String id = in.readUTF();
            PartySpigot party = PartyListener.party.get(UUID.fromString(id));
            PartyListener.partybyOwner.remove(party.getOwner());
            PartyListener.party.remove(UUID.fromString(id));
        }
    }
}
