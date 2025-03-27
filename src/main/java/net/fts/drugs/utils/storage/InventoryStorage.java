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
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
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
                lore.add(MiniMessage.miniMessage().deserialize("<dark_gray>» <gray>" + effect.getType().getName() +" "+ (effect.getAmplifier()+1) + "; " + (effect.getDuration()/10)/2 + " Sekunden"));
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

    public Inventory getWorkbench(Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9*5, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Rezept-Bearbeitung"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();


        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
        inventory.setItem(4, new ItemCreator(Material.SPRUCE_HANGING_SIGN).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).build());

        inventory.setItem(9, placeholder);
        inventory.setItem(10, placeholder);
        inventory.setItem(11, placeholder);

        inventory.setItem(15, placeholder);
        inventory.setItem(16, placeholder);
        inventory.setItem(17, placeholder);

        inventory.setItem(18, placeholder);
        inventory.setItem(19, placeholder);
        inventory.setItem(20, placeholder);

        inventory.setItem(24, placeholder);
        inventory.setItem(25, placeholder);
        inventory.setItem(26, placeholder);

        inventory.setItem(27, placeholder);
        inventory.setItem(28, placeholder);
        inventory.setItem(29, placeholder);

        inventory.setItem(33, placeholder);
        inventory.setItem(34, placeholder);
        inventory.setItem(35, placeholder);
        for(int i = 36; i <= 44; i++){
            inventory.setItem(i, placeholder);
        }

        String[] shape = drug.getReceipe().getShape();
        HashMap<String, ItemStack> ingridients = drug.getIngridients();

        int[] gridSlots = {
                12, 13, 14,
                21, 22, 23,
                30, 31, 32
        };

        int i = 0;

        for (String s : shape) {
            for (char c : s.toCharArray()) {
                if(ingridients.containsKey(String.valueOf(c))){
                    int slot = gridSlots[i];
                    inventory.setItem(slot, ingridients.get(String.valueOf(c)));
                }
                i++;
            }
        }

        inventory.setItem(36, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um zur Übersicht zu kommen")).build());
        inventory.setItem(44, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Speichern")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um das Rezept zu speichern")).build());
        return inventory;
    }

    public Inventory getResult(Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9*5, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Result-Bearbeitung"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 17; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(18, placeholder);
        inventory.setItem(19, placeholder);
        inventory.setItem(20, placeholder);
        inventory.setItem(21, placeholder);

        inventory.setItem(23, placeholder);
        inventory.setItem(24, placeholder);
        inventory.setItem(25, placeholder);
        inventory.setItem(26, placeholder);

        for(int i = 27; i <= 44; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(4, new ItemCreator(Material.SPRUCE_HANGING_SIGN).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).build());
        inventory.setItem(22, drug.getResult());

        inventory.setItem(36, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um zur Übersicht zu kommen")).build());
        inventory.setItem(44, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Speichern")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um das Result zu speichern")).build());

        return inventory;
    }

    public Inventory getPotionEffects(Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9*6, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Effekte-Bearbeitung"));

        List<PotionEffect> effects = drug.getEffects();

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();
        for (@NotNull PotionEffectType value : PotionEffectType.values()) {
            boolean done = false;
            for (PotionEffect effect : effects) {
                if(effect.getType().equals(value)){
                    inventory.addItem(new ItemCreator(Material.ENCHANTED_BOOK).displayName(MiniMessage.miniMessage().deserialize("<green>"+value.getName() + " " + (effect.getAmplifier()+1))).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Dauer: <white>" + ((effect.getDuration()/10)/2) + " Sekunden"), Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Bearbeiten"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Entfernen")).build());
                    done=true;
                }
            }
            if(!done)
                inventory.addItem(new ItemCreator(Material.BOOK).displayName(MiniMessage.miniMessage().deserialize(value.getName())).build());
        }

        for(int i = 45; i <= 53; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(45, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um eine Ebene zurück zu gehen")).build());
        inventory.setItem(49, new ItemCreator(Material.SPRUCE_HANGING_SIGN).displayName(MiniMessage.miniMessage().deserialize(drug.getName())).build());


        return inventory;
    }

    public Inventory getMultiplier(Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9*3, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Multiplier-Bearbeitung"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
        for(int i = 18; i <= 26; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(13, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("AddictionMultiplier: " + drug.getAddictionMultiplier())).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());

        inventory.setItem(26, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Speichern")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um die Änderung zu speichern")).addNamespacedKey("multiplier", drug.getName()).build());

        return inventory;
    }

    public Inventory getPotionManager(PotionEffectType type, Drug drug){
        Inventory inventory = Bukkit.createInventory(null, 9*3, MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Effektmodifizierung"));

        PotionEffect potionEffect = null;

        for (PotionEffect effect : drug.getEffects()) {
            if(effect.getType().equals(type))potionEffect=effect;
        }

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
        for(int i = 18; i <= 26; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(11, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("Amplifier: " + (potionEffect!=null?""+(potionEffect.getAmplifier()+1):"1"))).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());
        inventory.setItem(15, new ItemCreator(Material.COMPASS).displayName(MiniMessage.miniMessage().deserialize("Duration: "+ (potionEffect!=null?""+((potionEffect.getDuration()/10)/2):"1")+" Sekunden")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());

        inventory.setItem(4, new ItemCreator(Material.BOOK).displayName(MiniMessage.miniMessage().deserialize(type.getName())).addNamespacedKey("addeffect", drug.getName()).build());

        inventory.setItem(18, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("<gray>Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick um eine Ebene zurück zu gehen")).build());
        inventory.setItem(26, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Bestätigen")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um die Modifizierung zu bestätigen")).build());

        return inventory;
    }

}
