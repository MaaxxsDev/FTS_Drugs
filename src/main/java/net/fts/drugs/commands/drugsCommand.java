package net.fts.drugs.commands;

import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class drugsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if(!(commandSender instanceof Player player))
            return true;

        if(args.length == 0){
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
        }else if(args.length == 1){
            switch (args[0]){
                case "help":
                    if(player.hasPermission("fts.commands.drugs.openGUI")){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs openGUI <dark_gray>» <gray>Öffne das Admin Interface"));
                    }
                    player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs sucht <dark_gray>» <gray>Sehe deine Suchtwerte ein"));
                    break;
                case "openGUI":
                    Inventory inventory = DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI();
                    player.openInventory(inventory);
                    break;
                case "sucht":
                    User user = DrugsPlugin.getInstance().getUserManager().getUser(player.getUniqueId());
                    if(user.getAddictions().isEmpty()){
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
        }else if(args.length == 2){
            switch (args[0]){
                case "sucht":
                    if(!player.hasPermission("fts.command.drugs.sucht"))
                        return true;
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target==null){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>" + args[1] + " ist nicht Online"));
                        return true;
                    }
                    User user = DrugsPlugin.getInstance().getUserManager().getUser(target.getUniqueId());
                    if(user.getAddictions().isEmpty()){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>"+target.getName()+" ist gegen keine Droge süchtig"));
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
        }

        return true;
    }

}
