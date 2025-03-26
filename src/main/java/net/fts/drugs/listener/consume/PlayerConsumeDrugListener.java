package net.fts.drugs.listener.consume;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.objects.user.Rausch;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class PlayerConsumeDrugListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        if(itemStack==null)return;
        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta==null)return;
        if(itemMeta.displayName()==null)return;

        if(event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (!itemStack.getItemMeta().getPersistentDataContainer().getKeys().isEmpty()) {

                for (NamespacedKey namespacedKey : itemStack.getItemMeta().getPersistentDataContainer().getKeys()) {
                    for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
                        NamespacedKey key = drug.getKey();
                        if (namespacedKey.namespace().equals(key.namespace()) && namespacedKey.getKey().equals(key.getKey())) {
                            event.getItem().setAmount(event.getItem().getAmount()-1);
                            Objects.requireNonNull(event.getPlayer().getAttribute(Attribute.ATTACK_SPEED)).setBaseValue(4);
                            int longestDuration = 0;
                            for (PotionEffect effect : drug.getEffects()) {
                                if(effect.getDuration()>longestDuration){
                                    longestDuration=effect.getDuration();
                                }
                                event.getPlayer().addPotionEffect(effect);
                            }

                            longestDuration = (longestDuration/10)/2;

                            User user = DrugsPlugin.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());
                            boolean hasAddiction = false;
                            for (Addiction addiction : user.getAddictions()) {
                                if(addiction.getName().equals(drug.getName())){
                                    hasAddiction=true;
                                    addiction.setStrength(addiction.getStrength()+drug.getAddictionMultiplier());
                                }
                            }
                            if(!hasAddiction){
                                Addiction addiction = new Addiction(drug.getAddictionMultiplier(), drug.getName());
                                user.getAddictions().add(addiction);
                            }

                            boolean hasRausch = false;
                            for (Rausch rausch : user.getRausch()) {
                                if(rausch.getName().equals(drug.getName())){
                                    hasRausch=true;
                                    rausch.setStartedAt(System.currentTimeMillis());
                                    rausch.setDurations(longestDuration);
                                }
                            }
                            if(!hasRausch){
                                Rausch rausch = new Rausch(System.currentTimeMillis(), longestDuration, drug.getName());
                                user.getRausch().add(rausch);
                            }
                            user.update();
                        }
                    }
                }
            }
        }

    }


}
