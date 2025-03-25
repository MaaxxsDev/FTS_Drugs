package net.fts.drugs.utils.storage;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class InventoryStorage {

    public Inventory getGUI(){
        Inventory inventory = Bukkit.createInventory(null, 9*6, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Admin Interface"));

        for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
            ItemStack itemStack = new ItemStack(drug.getMaterial());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.displayName(MiniMessage.miniMessage().deserialize(drug.getName()));
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty());
            lore.add(MiniMessage.miniMessage().deserialize("<red>Effekte:"));
            for (PotionEffect effect : drug.getEffects()) {
                lore.add(MiniMessage.miniMessage().deserialize(effect.getType().toString() + effect.getAmplifier() + "; " + effect.getDuration() + " Ticks"));
            }
            itemMeta.lore(lore);
            itemStack.setItemMeta(itemMeta);

            inventory.addItem(itemStack);
        }

        return inventory;
    }

}
