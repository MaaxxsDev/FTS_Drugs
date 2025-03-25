package net.fts.drugs.utils.storage;

import net.fts.drugs.utils.items.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SetupInventoryStorage {

    public Inventory getWorkbench(){
        Inventory inventory = Bukkit.createInventory(null, 9*5, MiniMessage.miniMessage().deserialize("Definiere dein Crafting Rezept"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
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

        inventory.setItem(44, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Weiter")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um den nächsten Schritt zu beginnen")).build());
        return inventory;
    }

    public Inventory getResult(){
        Inventory inventory = Bukkit.createInventory(null, 9*5, MiniMessage.miniMessage().deserialize("Setze das Result Item"));

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

        inventory.setItem(44, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Weiter")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um den nächsten Schritt zu beginnen")).build());

        return inventory;
    }

    public Inventory getPotionEffects(List<PotionEffect> effects){
        Inventory inventory = Bukkit.createInventory(null, 9*6, MiniMessage.miniMessage().deserialize("Wähle die Effekte"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for (@NotNull PotionEffectType value : PotionEffectType.values()) {
            boolean done = false;
            for (PotionEffect effect : effects) {
                if(effect.getType().equals(value)){
                    inventory.addItem(new ItemCreator(Material.ENCHANTED_BOOK).displayName(MiniMessage.miniMessage().deserialize("<green>"+value.getName() + " " + effect.getAmplifier())).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Dauer: <white>" + (effect.getDuration()/10) + " Sekunden")).build());
                    done=true;
                }
            }
            if(!done)
                inventory.addItem(new ItemCreator(Material.BOOK).displayName(MiniMessage.miniMessage().deserialize(value.getName())).build());
        }

        for(int i = 45; i <= 53; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(53, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Weiter")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um den nächsten Schritt zu beginnen")).build());

        return inventory;
    }

    public Inventory getMultiplier(){
        Inventory inventory = Bukkit.createInventory(null, 9*3, MiniMessage.miniMessage().deserialize("Stelle die Stärke der Suchtentwicklung ein"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
        for(int i = 18; i <= 26; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(13, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("AddictionMultiplier: 1.00")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());

        inventory.setItem(26, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Weiter")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um den nächsten Schritt zu beginnen")).build());

        return inventory;
    }

    public Inventory getPotionManager(PotionEffectType type){
        Inventory inventory = Bukkit.createInventory(null, 9*3, MiniMessage.miniMessage().deserialize("Modifiziere den PotionEffect"));

        ItemStack placeholder = new ItemCreator(Material.GRAY_STAINED_GLASS_PANE).displayName(Component.empty()).build();

        for(int i = 0; i <= 8; i++){
            inventory.setItem(i, placeholder);
        }
        for(int i = 18; i <= 26; i++){
            inventory.setItem(i, placeholder);
        }

        inventory.setItem(11, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("Amplifier: 1")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());
        inventory.setItem(15, new ItemCreator(Material.COMPASS).displayName(MiniMessage.miniMessage().deserialize("Duration: 1 Sekunden")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());

        inventory.setItem(4, new ItemCreator(Material.BOOK).displayName(MiniMessage.miniMessage().deserialize(type.getName())).build());

        inventory.setItem(18, new ItemCreator(Material.ARROW).displayName(MiniMessage.miniMessage().deserialize("<gray>Zurück")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um zur Übersicht zu kommen")).build());
        inventory.setItem(26, new ItemCreator(Material.GREEN_DYE).displayName(MiniMessage.miniMessage().deserialize("<green>Bestätigen")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Klicke um die Modifizierung zu bestätigen")).build());

        return inventory;
    }

}
