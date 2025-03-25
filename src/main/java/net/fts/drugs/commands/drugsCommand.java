package net.fts.drugs.commands;

import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
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

        if(!player.hasPermission("fts.command.drugs"))
            return true;

        if(args.length == 0){
            player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
        }else if(args.length == 1){
            switch (args[0]){
                case "help":
                    player.sendMessage(MiniMessage.miniMessage().deserialize("/drugs openGUI <dark_gray>» <gray>Öffne das Admin Interface"));
                    break;
                case "openGUI":
                    Inventory inventory = DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI();
                    player.openInventory(inventory);
                    break;
                default:
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Falsche Benutzung! <gray><i>/drugs help</i> für Hilfe"));
                    break;
            }
        }

        return true;
    }

}
