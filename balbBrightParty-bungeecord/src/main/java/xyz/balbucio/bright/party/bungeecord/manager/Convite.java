package xyz.balbucio.bright.party.bungeecord.manager;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Convite {

    private ProxiedPlayer player;
    private ProxiedPlayer convidado;

    public Convite(ProxiedPlayer player, ProxiedPlayer convidado) {
        this.player = player;
        this.convidado = convidado;
    }

    public ProxiedPlayer getPlayer() {
        return player;
    }

    public ProxiedPlayer getConvidado() {
        return convidado;
    }
}
