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
            lore.add(MiniMessage.miniMessage().deserialize("<red>Suchtentwicklung: <white>" + drug.getAddictionMultiplier()));
            lore.add(MiniMessage.miniMessage().deserialize("<red>Effekte:"));
            for (PotionEffect effect : drug.getEffects()) {
                lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>» <gray>" + effect.getType().getName() +" "+ effect.getAmplifier() + "; " + (effect.getDuration()/10)/2 + " Sekunden"));
            };

            inventory.addItem(new ItemCreator(drug.getMaterial()).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).lore(lore).build());
        }

        return inventory;
    }

    public Inventory manageDrug(Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Bearbeitung"));

        inventory.setItem(0, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um zur Übersicht zu kommen")).build());
        inventory.setItem(2, new ItemCreator(Material.CRAFTING_TABLE).displayName(MiniMessage.miniMessage().deserialize("Rezept bearbeiten")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick das Rezept zu bearbeiten")).build());
        inventory.setItem(3, new ItemCreator(Material.ANVIL).displayName(MiniMessage.miniMessage().deserialize("Result bearbeiten")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick das Result zu bearbeiten")).build());
        inventory.setItem(4, new ItemCreator(Material.SPRUCE_HANGING_SIGN).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).build());
        inventory.setItem(5, new ItemCreator(Material.ENCHANTED_BOOK).displayName(MiniMessage.miniMessage().deserialize("Effekte bearbeiten")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um die Effekte zu bearbeiten")).build());
        inventory.setItem(6, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("Suchtentwicklung bearbeiten")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um die Suchtentwicklung zu bearbeiten")).build());
        inventory.setItem(8, new ItemCreator(Material.BARRIER).displayName(MiniMessage.miniMessage().deserialize("<red>Löschen")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um die Droge zu löschen")).build());

        return inventory;
    }

}
