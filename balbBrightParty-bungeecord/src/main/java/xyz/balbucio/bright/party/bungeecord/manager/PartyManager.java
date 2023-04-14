package xyz.balbucio.bright.party.bungeecord.manager;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PartyManager {

    public static HashMap<ProxiedPlayer, Party> partys = new HashMap<>();
    public static HashMap<ProxiedPlayer, Party> partys_convidado = new HashMap<>();
    public static ArrayList<ProxiedPlayer> partyMembers = new ArrayList<>();

    public static void convidar(ProxiedPlayer player, ProxiedPlayer convidado){
        if(partys.containsKey(player)) {
            if (!partys.containsKey(convidado) && !partys_convidado.containsKey(convidado)) {
                Party party = partys.get(player);
                if(party.getSlots() >= (party.getPlayers().size() + 1)) {
                    partys_convidado.put(convidado, party);
                    partyMembers.add(convidado);
                    party.add(convidado);
                } else {
                    player.sendMessage("§cSua Party não tem slots suficientes para adicionar o §f"+convidado.getName()+"§c!");
                }
            } else {
                convidado.sendMessage(new TextComponent("§4Você já está em uma Party!"));
            }
        } else {
            if (!partys_convidado.containsKey(player)) {
                if (!partys.containsKey(convidado) && !partys_convidado.containsKey(convidado)) {
                    Party party = new Party(player, convidado, UUID.randomUUID());
                    partys.put(player, party);
                    partys_convidado.put(convidado, party);
                    partyMembers.add(convidado);
                    partyMembers.add(player);
                    convidado.sendMessage(new TextComponent("§d[§bPARTY§d] §aVocê entrou na Party!"));
                    player.sendMessage("§d[§bPARTY§d] §aSua Party foi criada com sucesso!");
                    player.sendMessage("§d[§bPARTY§d] §aO §f"+convidado.getDisplayName()+" §aaceitou seu convite e entrou na Party!");
                } else {
                    convidado.sendMessage(new TextComponent("§4Você já está em uma Party!"));
                }
            } else{
                player.sendMessage(new TextComponent("§4Você não é o Líder dessa Party!"));
            }
        }
    }
    public static boolean entrar(ProxiedPlayer convidado, String name){
        for(Party party : partys.values()){
            if(party.getName().equals(name) && party.isPblc()){
                party.add(convidado);
                partys_convidado.put(convidado, party);
                partyMembers.add(convidado);
                return true;
            }
        }
        return false;
    }
    public static void remover(ProxiedPlayer player, ProxiedPlayer convidado){
        if(partys.containsKey(player)){
            Party party = partys.get(player);
            PartyManager.partyMembers.remove(convidado);
            PartyManager.partys_convidado.remove(convidado);
            party.kick(convidado);
        } else {
            player.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
        }
    }
}
