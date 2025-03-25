package net.fts.drugs.utils.storage;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.items.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;

public class InventoryStorage {

    public Inventory getGUI(){
        Inventory inventory = Bukkit.createInventory(null, 9*6, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Admin Interface"));

        for(int i = 45; i <= 53; i++){
            inventory.setItem(i, new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build());
        }

        inventory.setItem(49, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Neue Droge erstellen")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um zu beginnen")).build());

        for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
            List<Component> lore = new ArrayList<>();
            lore.add(Component.empty());
            lore.add(MiniMessage.miniMessage().deserialize("<red>Effekte:"));
            for (PotionEffect effect : drug.getEffects()) {
                lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>» <gray>" + effect.getType().getName() +" "+ effect.getAmplifier() + "; " + effect.getDuration() + " Sekunden"));
            }

            inventory.addItem(new ItemCreator(drug.getMaterial()).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).lore(lore).build());
        }

        return inventory;
    }

}
