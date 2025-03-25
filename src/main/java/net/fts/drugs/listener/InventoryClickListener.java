package net.fts.drugs.listener;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null || itemStack.getType().equals(Material.AIR))
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null || itemMeta.displayName()==null)
            return;

        if(event.getView().title().equals(MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Admin Interface"))){
            event.setCancelled(true);
        }
    }


}
