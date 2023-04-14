package xyz.balbucio.bright.party.spigot.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import xyz.balbucio.bright.party.spigot.utils.balbLogger;

import javax.print.attribute.IntegerSyntax;
import java.lang.reflect.Executable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PartyListener {

    public static HashMap<UUID, PartySpigot> party = new HashMap<>();
    public static HashMap<Player, PartySpigot> partybyOwner = new HashMap<>();
    public static void create(String name, String members, String type, int slots, String ownerString, UUID id){
        try {
            // Owner
            Player owner = Bukkit.getPlayer(ownerString);
            if (owner == null) {
                return;
            }
            // Owner
            // PLAYERS AQUI
            String[] splited = members.split("/");
            ArrayList<Player> players = new ArrayList<>();
            for (String s : splited) {
                Player p = Bukkit.getPlayer(s);
                if (p != null) {
                    players.add(p);
                }
            }
            // PLAYERS AQUI
            if (party.containsKey(id)) {
                PartySpigot part = party.get(id);
                partybyOwner.remove(part.getOwner());
                part.setName(name);
                part.setOwner(owner);
                part.setPlayers(players);
                part.setType(PartyType.valueOf(type));
                part.setSlots(slots);
                party.remove(id);
                party.put(id, part);
                partybyOwner.put(owner, part);
            } else {
                PartySpigot part = new PartySpigot(owner, name, players, slots, PartyType.valueOf(type), id);
                party.put(id, part);
                partybyOwner.put(owner, part);
            }
            balbLogger.sendParty("A party do §f"+owner+" §afoi carregada com sucesso!");
        }catch (Exception e){
            e.printStackTrace();
            balbLogger.sendError("§cNão foi possível carregar a Party do "+ownerString);
        }
    }
}
