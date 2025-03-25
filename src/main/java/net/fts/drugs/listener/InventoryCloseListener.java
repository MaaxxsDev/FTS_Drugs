package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class InventoryCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player player)){
            return;
        }

        if(event.getInventory().getType().equals(InventoryType.WORKBENCH)){
            Cache.drugset.remove(player);

            Set<NamespacedKey> recipe = Cache.recepies.get(player);

            if(recipe!=null){
                for (NamespacedKey namespacedKey : recipe) {
                    if(!namespacedKey.namespace().equals("fts_drugs")) {
                        player.discoverRecipe(namespacedKey);
                    }else {
                        if(namespacedKey.getKey().equals("drugset")||namespacedKey.getKey().equals("gegengift")||namespacedKey.getKey().equals("tester")){
                            player.discoverRecipe(namespacedKey);
                        }
                    }
                }
                Cache.recepies.remove(player);
            }

        }


    }

}
