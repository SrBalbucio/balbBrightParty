package xyz.balbucio.bright.party.bungeecord.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import xyz.balbucio.bright.party.bungeecord.file.FileManager;
import xyz.balbucio.bright.party.bungeecord.manager.ConviteManager;
import xyz.balbucio.bright.party.bungeecord.manager.Party;
import xyz.balbucio.bright.party.bungeecord.manager.PartyManager;
import xyz.balbucio.bright.party.bungeecord.manager.PartyType;

public class PartyCommand extends Command {
    public PartyCommand() {
        super("party");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer senderPlayer = (ProxiedPlayer) sender;
            try {
                if (args.length == 0) {
                    sender.sendMessage(new TextComponent("§a§lAJUDA §f- §aParty Commands:"));
                    sender.sendMessage(new TextComponent("§a/party §f- §7Envia essa mensagem"));
                    sender.sendMessage(new TextComponent("§a/party convidar §f- §7Convida um amigo para Party"));
                    sender.sendMessage(new TextComponent("§a/party entrar §f- §7Entra numa Party"));
                    sender.sendMessage(new TextComponent("§a/party remover §f- §7Remove um membro da Party"));
                    sender.sendMessage(new TextComponent("§a/party excluir §f- §7Delete sua Party"));
                    sender.sendMessage(new TextComponent("§a/party sair §f- §7Sai de uma Party"));
                    sender.sendMessage(new TextComponent("§a/party abrir/fechar §f- §7Torna a Privada/Pública"));
                    sender.sendMessage(new TextComponent("§a/party transferir §f- §7Transfere a Liderança da Party para outro Player"));
                    sender.sendMessage(new TextComponent("§a/party type §f- §7Troca o Tipo da Party §f(CHAT/JOGO)"));
                    sender.sendMessage(new TextComponent("§a/party setname §f- §7Troca o nome da Party"));
                    sender.sendMessage(new TextComponent("§a/party info §f- §7Mostra as informações da Party"));
                    sender.sendMessage(new TextComponent("§a/party puxar §f- §7Puxa os membros da Party"));
                } else if (args.length > 0 && args[0].equalsIgnoreCase("entrar")) {
                    String name = args[1];
                    if (!PartyManager.partyMembers.contains(senderPlayer)) {
                        ProxiedPlayer owner = ProxyServer.getInstance().getPlayer(name);
                        if (owner != null && !FileManager.get().getStringList("ServidoresBloqueados").contains(senderPlayer.getServer().getInfo().getName())) {
                            if (ConviteManager.checkAndDelete(owner, senderPlayer)) {
                                PartyManager.convidar(owner, senderPlayer);
                            } else if (!PartyManager.entrar(senderPlayer, name)) {
                                sender.sendMessage("§cVocê não foi convidado por esse player §cou §cesta §cParty §cestá §cprivada!");
                            }
                        } else if (!PartyManager.entrar(senderPlayer, name)) {
                            sender.sendMessage("§cVocê não foi convidado por esse player §cou §cesta §cParty §cestá §cprivada!");
                        }
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê já está em uma Party!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("convidar")) {
                    String name = args[1];
                    if(!name.equals(senderPlayer.getName())) {
                        ProxiedPlayer convidado = ProxyServer.getInstance().getPlayer(name);
                        if (convidado != null && !FileManager.get().getStringList("ServidoresBloqueados").contains(convidado.getServer().getInfo().getName())) {
                            if (!PartyManager.partyMembers.contains(convidado)) {
                                if (!ConviteManager.checkAndDelete(convidado, senderPlayer)) {
                                    if (!ConviteManager.check(senderPlayer, convidado)) {
                                        ConviteManager.create(senderPlayer, convidado);
                                        TextComponent message1 = new TextComponent("§aO §f" + senderPlayer.getDisplayName() + "§a convidou você para uma Party!");
                                        if (PartyManager.partys.containsKey(senderPlayer)) {
                                            Party party = PartyManager.partys.get(senderPlayer);
                                            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Nome da Party: §f" + party.getName() + "\n§7Dono: §f" + party.getOwner().getName() + "\n§7Tipo: §f" + party.getType() + "\n§aVocê tem §c20 Segundos §apara aceitar!")));
                                        } else {
                                            message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7A party ainda não foi criada!\n§aVocê tem §c60 Segundos §apara aceitar!")));
                                        }
                                        TextComponent message2 = new TextComponent("§aClique §e§lAQUI §apara aceitar! Ou apenas ignore para §crejeitar§a.");
                                        message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aVocê tem §c60 Segundos §apara aceitar!")));
                                        message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party entrar " + sender.getName()));
                                        convidado.sendMessage(message1);
                                        convidado.sendMessage(message2);
                                        sender.sendMessage(new TextComponent("§aO player foi convidado!"));
                                    } else {
                                        sender.sendMessage(new TextComponent("§aVocê já convidou esse Player! Aguarde o convite expirirar."));
                                    }
                                } else {
                                    sender.sendMessage("§aEsse player já te convidou antes, por isso o pedido foi aceito automaticamente!");
                                    PartyManager.convidar(convidado, senderPlayer);
                                }
                            } else {
                                sender.sendMessage(new TextComponent("§cEsse player já está em uma Party!"));
                            }
                        } else {
                            sender.sendMessage(new TextComponent("§cEsse player não está Online!"));
                        }
                    } else {
                        sender.sendMessage("§cVocê não pode convidar você mesmo!");
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("remover")) {
                    String name = args[1];
                    if (PartyManager.partys.containsKey(senderPlayer)) {
                        ProxiedPlayer convidado = ProxyServer.getInstance().getPlayer(name);
                        if (convidado != null) {
                            PartyManager.remover(senderPlayer, convidado);
                        } else {
                            sender.sendMessage(new TextComponent("§cEsse player não está Online!"));
                        }
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("abrir") || args[0].equalsIgnoreCase("fechar")) {
                    if (PartyManager.partys.containsKey(senderPlayer)) {
                        PartyManager.partys.get(senderPlayer).setPublic();
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("excluir") || args[0].equalsIgnoreCase("delete")) {
                    if (PartyManager.partys.containsKey(senderPlayer)) {
                        Party party = PartyManager.partys.get(senderPlayer);
                        for (ProxiedPlayer membro : party.getPlayers()) {
                            PartyManager.partyMembers.remove(membro);
                            PartyManager.partys_convidado.remove(membro);
                        }
                        party.broadcast("§cA Party em que você estava foi deletada!");
                        party.broadcast("§cUse /party <membro> para criar a sua!");
                        PartyManager.partys.remove(senderPlayer);
                        PartyManager.partyMembers.remove(senderPlayer);
                        sender.sendMessage(new TextComponent("§cSua Party foi deletada com sucesso!"));
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("setname") || args[0].equalsIgnoreCase("mudarnome")) {
                    String newName = args[1];
                    if (PartyManager.partys.containsKey(senderPlayer)) {
                        PartyManager.partys.get(senderPlayer).setName(newName);
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("transferir")) {
                    String name = args[1];
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
                    if (player != null) {
                        if (PartyManager.partys.containsKey(senderPlayer)) {
                            PartyManager.partys.get(senderPlayer).setOwner(player);
                        } else {
                            sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                        }
                    } else {
                        sender.sendMessage(new TextComponent("§cEsse player não está Online!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("type") || args[0].equalsIgnoreCase("tipo")) {
                    String type = args[1];
                    try {
                        if (PartyManager.partys.containsKey(senderPlayer)) {
                            PartyManager.partys.get(senderPlayer).setType(PartyType.valueOf(type));
                        } else {
                            sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                        }
                    } catch (Exception e) {
                        sender.sendMessage(new TextComponent("§cUse os seguintes tipos: JOGO, CHAT."));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("info") || args[0].equalsIgnoreCase("information")) {
                    try {
                        if (PartyManager.partys.containsKey(senderPlayer)) {
                            PartyManager.partys.get(senderPlayer).open(senderPlayer);
                        } else if (PartyManager.partys_convidado.containsKey(senderPlayer)) {
                            PartyManager.partys_convidado.get(senderPlayer).open(senderPlayer);
                        } else {
                            sender.sendMessage(new TextComponent("§cVocê não está em uma Party!"));
                        }
                    } catch (Exception e) {
                        sender.sendMessage(new TextComponent("§cOcorreu um problema ao abrir o Menu!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("sair") || args[0].equalsIgnoreCase("quit")) {
                    if (PartyManager.partys_convidado.containsKey(senderPlayer)) {
                        PartyManager.partys_convidado.get(senderPlayer).saiu(senderPlayer);
                    } else if (PartyManager.partys.containsKey(senderPlayer)) {
                        PartyManager.partyMembers.remove(senderPlayer);
                        PartyManager.partys.remove(senderPlayer);
                        PartyManager.partys.get(senderPlayer).setRandomOwner();
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party!"));
                    }
                } else if (args.length > 0 && args[0].equalsIgnoreCase("puxar") || args[0].equalsIgnoreCase("warp")) {
                  if (PartyManager.partys.containsKey(senderPlayer)) {
                        for(ProxiedPlayer player : PartyManager.partys.get(senderPlayer).getPlayers()){
                            player.connect(senderPlayer.getServer().getInfo());
                            player.sendMessage(new TextComponent("§d[§bP§d] §aConectando..."));
                        }
                        sender.sendMessage(new TextComponent("§d[§bPARTY§d] §aTodos membros da Party foram puxados!"));
                    } else {
                        sender.sendMessage(new TextComponent("§cVocê não está em uma Party ou não é Líder dela!"));
                    }
                }else {
                    String name = args[0];
                    ProxiedPlayer player = ProxyServer.getInstance().getPlayer(name);
                    if (player != null && !FileManager.get().getStringList("ServidoresBloqueados").contains(senderPlayer.getServer().getInfo().getName())) {
                        if (ConviteManager.checkAndDelete(player, senderPlayer)) {
                            PartyManager.convidar(player, senderPlayer);
                        } else {
                            ConviteManager.create(senderPlayer, player);
                            TextComponent message1 = new TextComponent("§aO " + ((ProxiedPlayer) sender).getDisplayName() + "§a convidou você para uma Party!");
                            if (PartyManager.partys.containsKey(senderPlayer)) {
                                Party party = PartyManager.partys.get(senderPlayer);
                                message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7Nome da Party: §f" + party.getName() + "\n§7Dono: §f" + party.getOwner().getName() + "\n§7Tipo: §f" + party.getType() + "\n§aVocê tem §c20 Segundos §apara aceitar!")));
                            } else {
                                message1.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§7A party ainda não foi criada!\n§aVocê tem §c60 Segundos §apara aceitar!")));
                            }
                            TextComponent message2 = new TextComponent(" §aClique §e§lAQUI §apara aceitar! Ou apenas ignore para §crejeitar§a.");
                            message2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aVocê tem §c60 Segundos §apara aceitar!")));
                            message2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party entrar " + sender.getName()));
                            player.sendMessage(message1);
                            player.sendMessage(message2);
                            sender.sendMessage(new TextComponent("§aO player foi convidado!"));
                        }
                    } else {
                        sender.sendMessage(new TextComponent("§cEsse player não existe!"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                sender.sendMessage(new TextComponent("§cAlgo está errado com o comando que você executou!"));
                sender.sendMessage(new TextComponent("§cUse §f/party §cpara saber mais sobre os comandos."));
            }
        } else{
            sender.sendMessage(new TextComponent("§cSomente players podem usar esse comando!"));
        }
    }
}
