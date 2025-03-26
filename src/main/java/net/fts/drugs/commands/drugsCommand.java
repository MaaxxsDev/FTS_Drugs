package net.fts.drugs.commands;

import net.fts.drugs.enums.Tools;
import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class drugsCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player player))
            return true;

        if (args.length == 0) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
        } else if (args.length == 1) {
            switch (args[0]) {
                case "help":
                    if (player.hasPermission("fts.commands.drugs.openGUI")) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs openGUI <dark_gray>» <gray>Öffne das Admin Interface"));
                    }
                    if (player.hasPermission("fts.commands.drugs.give")) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs give <Name> <Drug> [Amount] <dark_gray>» <gray>Gebe dir eine Droge"));
                    }
                    player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs sucht <dark_gray>» <gray>Sehe deine Suchtwerte ein"));
                    break;
                case "openGUI":
                    Inventory inventory = DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI();
                    player.openInventory(inventory);
                    break;
                case "sucht":
                    User user = DrugsPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
                    if (user.getAddictions().isEmpty()) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Du bist gegen keine Droge süchtig"));
                        return true;
                    }
                    player.sendMessage(MiniMessage.miniMessage().deserialize("Du bist süchtig gegen:"));
                    for (Addiction addiction : user.getAddictions()) {
                        player.sendMessage(Component.empty());
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>»</dark_gray> " + addiction.getName()));
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>➥</dark_gray> Suchtentwicklung: " + addiction.getStrength()));
                    }
                    break;
                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
                    break;
            }
        } else if (args.length == 2) {
            switch (args[0]) {
                case "sucht":
                    if (!player.hasPermission("fts.command.drugs.sucht"))
                        return true;
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>" + args[1] + " ist nicht Online"));
                        return true;
                    }
                    User user = DrugsPlugin.getInstance().getUserManager().getUser(target.getUniqueId());
                    if (user.getAddictions().isEmpty()) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>" + target.getName() + " ist gegen keine Droge süchtig"));
                        return true;
                    }
                    player.sendMessage(Component.empty());
                    player.sendMessage(MiniMessage.miniMessage().deserialize(target.getName() + " ist süchtig gegen:"));
                    player.sendMessage(Component.empty());
                    for (Addiction addiction : user.getAddictions()) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>»</dark_gray> " + addiction.getName()));
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>➥</dark_gray> Suchtentwicklung: " + addiction.getStrength()));
                    }
                    break;
                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
                    break;
            }
        } else if (args.length == 3) {
            switch (args[0]) {
                case "give":
                    if (!player.hasPermission("fts.command.drugs.give"))
                        return true;
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>" + args[1] + " ist nicht Online"));
                        return true;
                    }
                    String drogeStr = args[2];
                    Drug droge = DrugsPlugin.getInstance().getDrugsManager().getDrug(drogeStr);
                    ItemStack give = null;
                    if(droge!=null){
                        give=droge.getResult();
                    }else{
                        if(drogeStr.equalsIgnoreCase("drugkit")){
                            give = Tools.DRUGKIT.getItemStack();
                        }else if(drogeStr.equalsIgnoreCase("drugtest")){
                            give = Tools.DRUGTEST.getItemStack();
                        }else if(drogeStr.equalsIgnoreCase("antidote")){
                            give = Tools.ANTIDOTE.getItemStack();
                        }
                    }
                    if(give==null){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Warte einen Moment!"));
                        return true;
                    }
                    target.getInventory().addItem(give);
                    player.sendMessage(MiniMessage.miniMessage().deserialize("Der Spieler " + target.getName() + " hat <i>" + 1 + "x " + args[2] + "</i> erhalten"));
                    break;
                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
                    break;
            }
        } else if (args.length == 4) {
            switch (args[0]) {
                case "give":
                    if (!player.hasPermission("fts.command.drugs.give"))
                        return true;
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target == null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>" + args[1] + " ist nicht Online"));
                        return true;
                    }
                    String drogeStr = args[2];
                    Drug droge = DrugsPlugin.getInstance().getDrugsManager().getDrug(drogeStr);
                    ItemStack give = null;
                    if(droge!=null){
                        give=droge.getResult();
                    }else{
                        if(drogeStr.equalsIgnoreCase("drugkit")){
                            give = Tools.DRUGKIT.getItemStack();
                        }else if(drogeStr.equalsIgnoreCase("drugtest")){
                            give = Tools.DRUGTEST.getItemStack();
                        }else if(drogeStr.equalsIgnoreCase("antidote")){
                            give = Tools.ANTIDOTE.getItemStack();
                        }
                    }

                    try {
                        int amount = Integer.parseInt(args[3]);
                        player.sendMessage(MiniMessage.miniMessage().deserialize("Der Spieler " + target.getName() + " hat <i>" + amount + "x " +args[2]+ "</i> erhalten"));

                        if (amount <= 0) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Der Amount muss größer als 0 sein"));
                            return true;
                        }
                        while (amount > 0) {
                            amount -= 1;
                            target.getInventory().addItem(give);
                        }
                    } catch (NumberFormatException exception) {
                        exception.printStackTrace();
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Der Amount muss eine Zahl sein"));
                    }

                    break;
                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
                    break;
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if (!(commandSender instanceof Player player)) return List.of();

        if (args.length == 1) {
            List<String> tab = new ArrayList<>();
            tab.add("help");
            tab.add("sucht");
            if (player.hasPermission("fts.command.drugs.give")) {
                tab.add("give");
            }
            if (player.hasPermission("fts.command.drugs.openGUI")) {
                tab.add("openGUI");
            }
            return tab.stream().filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("sucht")) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            } else if (args[0].equalsIgnoreCase("give")) {
                return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                        .collect(Collectors.toList());
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("give")) {
                List<String> tab = new ArrayList<>(DrugsPlugin.getInstance().getDrugsManager().getDrugs().stream().map(Drug::getName).toList());
                tab.add("Drugkit");
                tab.add("Drugtest");
                tab.add("Antidote");
                return tab.stream().filter(name -> name.toLowerCase().startsWith(args[2].toLowerCase())).collect(Collectors.toList());
            }
        }
        return List.of();
    }
}
