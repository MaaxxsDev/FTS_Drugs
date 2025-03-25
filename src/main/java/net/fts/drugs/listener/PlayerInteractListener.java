package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;


public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if(itemStack==null)return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta==null)return;
        if(itemMeta.displayName()==null)return;

        if(itemStack.getType().equals(Material.NAUTILUS_SHELL)&&itemMeta.displayName().equals(MiniMessage.miniMessage().deserialize("<yellow><b>Drogenset"))){
            if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)){
                Cache.drugset.add(event.getPlayer());
                for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
                    Recipe recipe = it.next();
                    if (recipe instanceof Keyed keyed) {
                        event.getPlayer().undiscoverRecipe(keyed.getKey());
                    }
                }
                event.getPlayer().openWorkbench(event.getPlayer().getLocation(), true);
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player player)){
            return;
        }
        Cache.drugset.remove(player);
        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
            Recipe recipe = it.next();
            if (recipe instanceof Keyed keyed) {
                event.getPlayer().discoverRecipe(keyed.getKey());
            }
        }
        for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
            event.getPlayer().undiscoverRecipe(drug.getReceipe().getKey())            ;
        }
    }


}
