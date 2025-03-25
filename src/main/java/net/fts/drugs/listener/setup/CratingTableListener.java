package net.fts.drugs.listener.setup;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class CratingTableListener implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        System.out.println(PlainTextComponentSerializer.plainText().serialize(event.getView().title()));

        if(Cache.drugset.contains(event.getView().getPlayer())){
            for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
                Recipe recipe = it.next();
                if (recipe instanceof Keyed keyed) {
                    if(event.getInventory().getResult()!=null){
                        if(event.getInventory().getResult().getType().equals(recipe.getResult().getType())){
                            System.out.println(keyed.getKey());
                            if(!keyed.getKey().namespace().equals("fts_drugs")){
                                event.getInventory().setResult(null);
                            }
                        }
                    }

                }
            }
        }else {
            for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
                if(Objects.requireNonNull(event.getRecipe()).getResult().getType().equals(drug.getResult().getType()) && Objects.equals(event.getRecipe().getResult().getItemMeta().displayName(), drug.getReceipe().getResult().getItemMeta().displayName())){
                    event.getInventory().setResult(null);
                }
            }
        }
    }


}
