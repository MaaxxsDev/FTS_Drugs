package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.FTSUser;
import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.objects.user.Rausch;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.StorageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if(itemStack==null)return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta==null)return;
        if(itemMeta.displayName()==null)return;

        NamespacedKey namespacedKey = new NamespacedKey(DrugsPlugin.getInstance(), "fts_drugs_items");

        if(itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey)){
            if(itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING).equals("drugkit")) {

                FTSUser ftsUser = Cache.getFTSUser(event.getPlayer().getUniqueId());
                if(ftsUser==null) {
                    return;
                }
                if(ftsUser.hasRequiredSkill()){
                    if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                        Cache.drugset.add(event.getPlayer());
                        Cache.recepies.put(event.getPlayer(), event.getPlayer().getDiscoveredRecipes());
                        for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
                            Recipe recipe = it.next();
                            if (recipe instanceof Keyed keyed) {
                                event.getPlayer().undiscoverRecipe(keyed.getKey());
                            }
                        }
                        event.getPlayer().openWorkbench(event.getPlayer().getLocation(), true);
                        event.getPlayer().getOpenInventory().setTitle("§e§lDrugkit");
                    }
                }
            }

            if(itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING).equals("gegengift")) {
                event.setCancelled(true);
                User user = DrugsPlugin.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());
                user.getAddictions().clear();
                for (Rausch rausch : user.getRausch()) {
                    for (PotionEffect effect : DrugsPlugin.getInstance().getDrugsManager().getDrug(rausch.getName()).getEffects()) {
                       if(event.getPlayer().hasPotionEffect(effect.getType())){
                           event.getPlayer().removePotionEffect(effect.getType());
                       }
                    }
                }
                user.getRausch().clear();
                user.update();
                itemStack.setAmount(itemStack.getAmount()-1);
                event.getPlayer().updateInventory();
            }
        }

        if(itemStack.getType().equals(Material.SHORT_GRASS)){
            event.getPlayer().swingMainHand();
        }

    }

    private List<Player> cooldown = new ArrayList<>();

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        if(!(event.getRightClicked() instanceof Player target))return;


        ItemStack itemStack = event.getPlayer().getInventory().getItemInMainHand();
        if(itemStack==null)return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta==null)return;
        if(itemMeta.displayName()==null)return;

        NamespacedKey namespacedKey = new NamespacedKey(DrugsPlugin.getInstance(), "fts_drugs_items");

        if(itemStack.getItemMeta().getPersistentDataContainer().has(namespacedKey)){
            if(itemStack.getItemMeta().getPersistentDataContainer().get(namespacedKey, PersistentDataType.STRING).equals("drogentest")) {
                if(cooldown.contains(player))return;

                cooldown.add(player);
                Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        cooldown.remove(player);
                    }
                }, 2);

                itemStack.setAmount(itemStack.getAmount()-1);

                player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Ergebniss wird jetzt ausgewertet, bitte warte kurz..."));

                Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        User user = DrugsPlugin.getInstance().getUserManager().getUser(target.getUniqueId());

                        if(user.getAddictions().isEmpty()){
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<green>"+target.getName()+" ist gegen keine Droge süchtig"));
                            return;
                        }

                        if(user.getStrongestAddiction().getStrength()<=20){
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<green>"+target.getName()+" ist gegen keine Droge süchtig"));
                            return;
                        }

                        player.sendMessage(Component.empty());
                        player.sendMessage(MiniMessage.miniMessage().deserialize(target.getName() + " ist süchtig gegen:"));
                        for (Addiction addiction : user.getAddictions()) {
                            player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>»</dark_gray> " + (addiction.getStrength()>=80?"<dark_red>":addiction.getStrength()>60?"<red>":addiction.getStrength()>=40?"<yellow>":"<gray>") + addiction.getName()));
//                            player.sendMessage(MiniMessage.miniMessage().deserialize("<dark_gray>➥</dark_gray> Suchtentwicklung: " + addiction.getStrength()));
                        }
                    }
                }, 20*4);


            }
        }
    }


}
