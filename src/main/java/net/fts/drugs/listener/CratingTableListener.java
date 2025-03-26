package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.FTSUser;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Iterator;
import java.util.Objects;

public class CratingTableListener implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        if(Cache.drugset.contains(event.getView().getPlayer())){
            if(event.getRecipe()==null) {
                event.getInventory().setResult(null);
                return;
            }
            if(event.getRecipe().getResult().getItemMeta().displayName()==null) {
                event.getInventory().setResult(null);
                return;
            };

            if(event.getRecipe().getResult().hasItemMeta()){
                for (NamespacedKey key : event.getRecipe().getResult().getItemMeta().getPersistentDataContainer().getKeys()) {
                    if(!isDrug(key)){
                        System.out.println("Ist keine Droge");
                        event.getInventory().setResult(null);
                        return;
                    }
                    FTSUser ftsUser = Cache.getFTSUser(event.getView().getPlayer().getUniqueId());
                    if(ftsUser==null){
                        event.getInventory().setResult(null);
                    }else{
                        if(!ftsUser.hasRequiredSkill()){
                            event.getInventory().setResult(null);
                            return;
                        }
                        return;
                    }
                }
                event.getInventory().setResult(null);
            }else {
                event.getInventory().setResult(null);
            }
            
        }else {
            for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
                if(event.getRecipe()==null)return;
                if(event.getRecipe().getResult()==null||event.getRecipe().getResult().getItemMeta().displayName()==null)return;
                if(Objects.requireNonNull(event.getRecipe()).getResult().getType().equals(drug.getResult().getType()) && Objects.equals(event.getRecipe().getResult().getItemMeta().displayName(), drug.getReceipe().getResult().getItemMeta().displayName())){
                    event.getInventory().setResult(null);
                }
            }
            if(event.getRecipe()==null)return;
            if(event.getRecipe().getResult().getItemMeta().displayName()==null)return;
            for (NamespacedKey key : event.getRecipe().getResult().getItemMeta().getPersistentDataContainer().getKeys()) {
                if(key.getKey().equalsIgnoreCase("fts_drugs_items")){
                    FTSUser ftsUser = Cache.getFTSUser(event.getView().getPlayer().getUniqueId());
                    if(ftsUser==null){
                        event.getInventory().setResult(null);
                    }else{
                        if(!ftsUser.hasRequiredSkill())
                            event.getInventory().setResult(null);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if(Cache.drugset.contains(event.getView().getPlayer())){
            Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if(event.getRecipe() instanceof Keyed keyed){
                        event.getView().getPlayer().undiscoverRecipe(keyed.getKey());
                    }
                }
            },1);
        }
    }

    public boolean isDrug(NamespacedKey key){
        for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
            for (NamespacedKey namespacedKey : drug.getResult().getPersistentDataContainer().getKeys()) {
                if(namespacedKey.namespace().equals(key.namespace())&&namespacedKey.getKey().equals(key.getKey()))return true;
            }
        }
        return false;
    }


}
