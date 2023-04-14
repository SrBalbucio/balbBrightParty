package xyz.balbucio.bright.party.spigot.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PartySpigot {

    private Player owner;
    private String name;
    private ArrayList<Player> players;
    private int slots;
    private PartyType type;
    private UUID id;

    public PartySpigot(Player owner, String name, ArrayList<Player> players, int slots, PartyType type, UUID id) {
        this.owner = owner;
        this.name = name;
        this.players = players;
        this.slots = slots;
        this.type = type;
        this.id = id;
    }

    public Player getOwner() {
        return owner;
    }

    public UUID getId() {
        return id;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getSlots() {
        return slots;
    }

    public void setSlots(int slots) {
        this.slots = slots;
    }

    public PartyType getType() {
        return type;
    }

    public void setType(PartyType type) {
        this.type = type;
    }
}
