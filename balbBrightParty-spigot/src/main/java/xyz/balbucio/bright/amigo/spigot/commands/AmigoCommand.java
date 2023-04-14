package xyz.balbucio.bright.amigo.spigot.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.w3c.dom.Text;
import xyz.balbucio.bright.amigo.spigot.PlayerManager;

public class AmigoCommand implements CommandExecutor {
    static String prefix = "§a[§eA§a]";
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0){
            sender.sendMessage("§a§lAJUDA §f- §aAmigo:");
            sender.sendMessage("§a/amigo §f- §7Envia essa mensagem");
            sender.sendMessage("§a/amigo add §f- §7Adicionar um amigo");
            sender.sendMessage("§a/amigo negar §f- §7Nega um pedido de amizade");
            sender.sendMessage("§a/amigo pedidos §f- §7Lista seus pedidos de amizade");
            sender.sendMessage("§a/amigo listar §f- §7Lista seus pedidos de amizade");
        } else if(args.length > 0 && args[0].equalsIgnoreCase("add")){
            String name = args[1];
            if(name.equalsIgnoreCase(sender.getName())) {
                if (PlayerManager.getAmigos(sender.getName()).contains(name)) {
                    if (PlayerManager.addPedidos(name, sender.getName())) {
                        sender.sendMessage(prefix + " §aVocê e §f" + name + " §aagora são amigos!");
                    } else {
                        sender.sendMessage(prefix + " §aPedido de Amizade enviado com sucesso!");
                        Player amigo = Bukkit.getPlayer(name);
                        if (amigo != null) {
                            TextComponent msg = new TextComponent(prefix + " §f" + sender.getName() + " §ate enviou um §ePedido de Amizade§a!\n§aClique Aqui para Aceitar!");
                            msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo add " + sender.getName()));
                            amigo.spigot().sendMessage(msg);
                        }
                    }
                } else {
                    sender.sendMessage(prefix+" §cVocê não pode enviar amizade para você mesmo!");
                }
            } else {
                sender.sendMessage(prefix+" §cVocê já é amigo desse Player!");
            }
        } else if(args.length > 0 && args[0].equalsIgnoreCase("negar")){
            String amigo = args[1];
            if(PlayerManager.negarPedido(sender.getName(), amigo)){
                sender.sendMessage(prefix+" §cPedido removido com sucesso!");
            } else {
                sender.sendMessage(prefix+" §cEsse player não te enviou um Pedido de Amizade!");
            }
        } else if(args.length > 0 && args[0].equalsIgnoreCase("pedidos")){
            sender.sendMessage("§a§lPedidos de Amizade: §7(Lista Completa)");
            for(String name : PlayerManager.getPedidos(sender.getName())){
                TextComponent aceitar = new TextComponent("§a[ACEITAR]");
                aceitar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo add "+name));
                TextComponent negar = new TextComponent(" §c[NEGAR]");
                negar.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/amigo negar "+name));
                ((Player) sender).spigot().sendMessage(new TextComponent("§7"+name+" "+aceitar+negar));
            }
        } else if(args.length > 0 && args[0].equalsIgnoreCase("listar")){

        }
        return false;
    }
}
