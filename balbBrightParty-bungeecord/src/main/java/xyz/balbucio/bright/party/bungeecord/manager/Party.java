package xyz.balbucio.bright.party.bungeecord.manager;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Party {

    private String name;
    private ProxiedPlayer owner;
    private ArrayList<ProxiedPlayer> players;
    private int slots;
    private boolean pblc;
    private PartyType type;
    private UUID id;

    public Party(ProxiedPlayer owner, ProxiedPlayer convidado, UUID id) {
        this.name = "Party do "+owner.getName();
        this.owner = owner;
        ArrayList<ProxiedPlayer> pla = new ArrayList<>();
        pla.add(convidado);
        this.players = pla;
        this.slots = SlotsManager.get(owner);
        this.pblc = false;
        this.type = PartyType.JOGO;
        this.id = id;
        send();
    }
    public void add(ProxiedPlayer convidado){
        if(!this.players.contains(convidado)) {
            this.players.add(convidado);
            this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO "+convidado.getDisplayName()+" entrou com sucesso na sua Party!"));
            broadcast("§aO "+convidado.getDisplayName()+" entrou na Party!");
            send();
        } else {
            convidado.sendMessage("§cVocê já está nessa party!");
        }
    }
    public void kick(ProxiedPlayer convidado){
        if(this.players.contains(convidado)) {
            if((this.players.size() - 1) > 0) {
                PartyManager.partyMembers.remove(convidado);
                PartyManager.partys_convidado.containsKey(convidado);
                this.players.remove(convidado);
                broadcast("§cO " + convidado.getDisplayName() + " foi removido da Party!");
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §cO " + convidado.getDisplayName() + " foi removido da Party!"));
                send();
            } else{
                for (ProxiedPlayer membro : this.getPlayers()) {
                    PartyManager.partyMembers.remove(membro);
                    PartyManager.partys_convidado.remove(membro);
                }
                broadcast("§cO " + convidado.getDisplayName() + " foi removido da Party!");
                this.broadcast("§cA Party em que você estava foi deletada por falta de Players!");
                this.broadcast("§cUse /party <membro> para criar a sua!");
                PartyManager.partys.remove(this.owner);
                PartyManager.partyMembers.remove(this.owner);
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §cO " + convidado.getDisplayName() + " foi removido da Party!"));
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §cSua Party foi deletada por falta de membros!"));
                sendDelete();
            }
        }
    }

    public void saiu(ProxiedPlayer convidado){
        if(this.players.contains(convidado)) {
            if((this.players.size() - 1) > 0) {
                convidado.sendMessage("§d[§bPARTY§d] Você saiu da Party com sucesso!");
                PartyManager.partyMembers.remove(convidado);
                PartyManager.partys_convidado.remove(convidado);
                this.players.remove(convidado);
                broadcast("§cO " + convidado.getDisplayName() + " saiu da Party!");
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO " + convidado.getDisplayName() + " saiu da Party!"));
                send();
            } else{
                for (ProxiedPlayer membro : this.getPlayers()) {
                    PartyManager.partyMembers.remove(membro);
                    PartyManager.partys_convidado.remove(membro);
                }
                broadcast("§cO " + convidado.getDisplayName() + " saiu da Party!");
                this.broadcast("§cA Party em que você estava foi deletada por falta de Players!");
                this.broadcast("§cUse /party <membro> para criar a sua!");
                PartyManager.partys.remove(this.owner);
                PartyManager.partyMembers.remove(this.owner);
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO " + convidado.getDisplayName() + " saiu da Party!"));
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §cSua Party foi deletada por falta de membros!"));
                sendDelete();
            }
        }
    }
    public void disconnect(ProxiedPlayer convidado){
        if(this.players.contains(convidado)) {
            if((this.players.size() - 1) > 0) {
                PartyManager.partyMembers.remove(convidado);
                PartyManager.partys_convidado.remove(convidado);
                this.players.remove(convidado);
                broadcast("§cO " + convidado.getDisplayName() + " saiu do Servidor!");
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO " + convidado.getDisplayName() + " saiu do Servidor!"));
                send();
            } else{
                for (ProxiedPlayer membro : this.getPlayers()) {
                    PartyManager.partyMembers.remove(membro);
                    PartyManager.partys_convidado.remove(membro);
                }
                broadcast("§cO " + convidado.getDisplayName() + " saiu do Servidor!");
                this.broadcast("§cA Party em que você estava foi deletada por falta de Players!");
                this.broadcast("§cUse /party <membro> para criar a sua!");
                PartyManager.partys.remove(this.owner);
                PartyManager.partyMembers.remove(this.owner);
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO " + convidado.getDisplayName() + " saiu do Servidor!"));
                this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §cSua Party foi deletada por falta de membros!"));
                sendDelete();
            }
        }
    }

    public void broadcast(String message){
        for(ProxiedPlayer membro : this.players){
            membro.sendMessage(new TextComponent("§d[§bPARTY§d] §f"+message));
        }
    }

    public void chat(String message){
        for(ProxiedPlayer membro : this.players){
            membro.sendMessage(new TextComponent("§d[§bCHAT§d] "+message));
        }
        this.owner.sendMessage("§d[§bP§d] §f"+message);
    }

    public void setName(String name){
        this.name = name;
        broadcast("§aA party trocou de nome, agora é §f"+name+"§a!");
        this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aO nome da Party foi trocado com sucesso!"));
        send();
    }

    public void setOwner(ProxiedPlayer newOwner){
        if(this.players.contains(newOwner)){
            PartyManager.partys.remove(this.owner);
            PartyManager.partys.put(newOwner, this);
            PartyManager.partys_convidado.remove(newOwner);
            PartyManager.partys_convidado.put(this.owner, this);
            this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aVocê transferiu a Party com sucesso!"));
            this.players.add(this.owner);
            this.players.remove(newOwner);
            this.owner = newOwner;
            broadcast("§cO Líder da Party agora é o §f"+newOwner.getDisplayName()+"§c!");
            newOwner.sendMessage(new TextComponent("§d[§bPARTY§d] §aAgora você é o Líder da Party!"));
            send();
        } else {
            this.owner.sendMessage("§d[§bPARTY§d] §cEsse player não está em sua Party!");
        }
    }
    public void setRandomOwner() {
        if((this.players.size() - 1) > 0) {
            ProxiedPlayer newOwner = this.players.get(new Random().nextInt(this.players.size()));
            PartyManager.partys.remove(this.owner);
            PartyManager.partys.put(newOwner, this);
            PartyManager.partys_convidado.remove(newOwner);
            this.players.remove(newOwner);
            this.owner = newOwner;
            broadcast("§cO Líder da Party agora é o §f" + newOwner.getDisplayName() + "§c!");
            broadcast("§cO Antigo Líder da Party saiu!");
            newOwner.sendMessage(new TextComponent("§d[§bPARTY§d] §cO Antigo Líder da Party saiu!"));
            newOwner.sendMessage(new TextComponent("§d[§bPARTY§d] §cAgora você é o líder da Party!"));
            send();
        } else{
            for (ProxiedPlayer membro : this.getPlayers()) {
                PartyManager.partyMembers.remove(membro);
                PartyManager.partys_convidado.remove(membro);
            }
            broadcast("§cO Líder da Party saiu do servidor!");
            this.broadcast("§cA Party em que você estava foi deletada por falta de Players!");
            this.broadcast("§cUse /party <membro> para criar a sua!");
            PartyManager.partys.remove(this.owner);
            PartyManager.partyMembers.remove(this.owner);
            sendDelete();
        }
    }
    public void setPublic(){
        if(this.pblc){
            this.pblc = false;
            broadcast("§aA Party agora é §4PRIVADA!");
            this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aA Party agora é §4PRIVADA!"));
        } else {
            this.pblc = true;
            broadcast("§aA Party agora é §4PÚBLICA!");
            this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aA Party agora é §4PÚBLICA!"));
        }
        send();
    }
    public void setType(PartyType type){
        this.type = type;
        broadcast("§aAgora a Party é do tipo "+type.toString());
        this.owner.sendMessage(new TextComponent("§d[§bPARTY§d] §aVocê trocou o tipo de Party!"));
        send();
    }

    public String getName() {
        return name;
    }

    public ProxiedPlayer getOwner() {
        return owner;
    }

    public ArrayList<ProxiedPlayer> getPlayers() {
        return players;
    }

    public int getSlots() {
        return slots;
    }

    public boolean isPblc() {
        return pblc;
    }

    public PartyType getType() {
        return type;
    }

    public UUID getId() {
        return id;
    }

    public void send(){
        String message = "null";
        for (ProxiedPlayer convidado : this.getPlayers()) {
            if (message.equalsIgnoreCase("null")) {
                message = convidado.getName();
            } else {
                message = message + "/" + convidado.getName();
            }
        }
        if(!(BungeeCord.getInstance().getPlayers().size() == 0)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("balbParty");
            out.writeUTF(this.getName());
            out.writeUTF(message);
            out.writeUTF(this.getType().toString());
            out.writeInt(this.getSlots());
            out.writeUTF(this.getOwner().toString());
            out.writeUTF(this.id.toString());
            this.owner.getServer().getInfo().sendData("balbparty:channel", out.toByteArray());
        }
    }
    public void sendDelete(){
        if(!(BungeeCord.getInstance().getPlayers().size() == 0)) {
            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("balbPartyDelete");
            out.writeUTF(this.id.toString());
            this.owner.getServer().getInfo().sendData("balbparty:channel", out.toByteArray());

            for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
                server.sendData("balbparty:channel", out.toByteArray());
            }
        }
    }
    public void open(ProxiedPlayer membro){
        if(this.players.contains(membro)) {
            String message = "null";
            for (ProxiedPlayer convidado : this.getPlayers()) {
                if (message.equalsIgnoreCase("null")) {
                    message = convidado.getName();
                } else {
                    message = message + "/" + convidado.getName();
                }
            }
            if (!(BungeeCord.getInstance().getPlayers().size() == 0)) {
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("balbPartyMenu");
                out.writeUTF(membro.getName());
                out.writeUTF(this.getName());
                out.writeUTF(this.getOwner().toString());
                out.writeUTF(message);
                out.writeUTF(pblc ? "Sim" : "Não");
                out.writeUTF(this.getType().toString());
                out.writeInt(this.getSlots());
                out.writeUTF(this.id.toString());
                membro.getServer().getInfo().sendData("balbparty:channel", out.toByteArray());
            }
        }
    }
}
