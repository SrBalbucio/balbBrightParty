package xyz.balbucio.bright;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import xyz.balbucio.bright.party.bungeecord.commands.PartyAdminCommand;
import xyz.balbucio.bright.party.bungeecord.commands.PartyChatCommand;
import xyz.balbucio.bright.party.bungeecord.commands.PartyCommand;
import xyz.balbucio.bright.party.bungeecord.eventos.PlayerEntry;
import xyz.balbucio.bright.party.bungeecord.eventos.ServerSwitch;
import xyz.balbucio.bright.party.bungeecord.file.FileManager;
import xyz.balbucio.bright.party.bungeecord.utils.balbLogger;
import xyz.balbucio.bright.sql.SQL;

public final class Bungee extends Plugin {

    public static Bungee INSTANCE= null;
    @Override
    public void onEnable() {
        setInstance(this);
        loadCommandsAndEvents();
        getProxy().registerChannel("balbparty:channel");
        SQL.connect();
        balbLogger.sendLoad("O plugin foi carregado com sucesso!");
    }

    @Override
    public void onLoad(){
        FileManager.load();
    }
    public void setInstance(final Bungee bng){
        INSTANCE = bng;
    }
    public static Bungee get() {
        return INSTANCE;
    }

    @Override
    public void onDisable() {
    }

    private void loadCommandsAndEvents(){
        balbLogger.sendLoad("Comandos e Eventos carregados com sucesso!");
        ProxyServer.getInstance().getPluginManager().registerListener(this, new ServerSwitch());
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerEntry());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PartyCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PartyChatCommand());
        ProxyServer.getInstance().getPluginManager().registerCommand(this, new PartyAdminCommand());
    }

}
