package net.fts.drugs.utils.items;

import net.fts.drugs.plugin.DrugsPlugin;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class ItemCreator {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemCreator(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemCreator(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemCreator displayName(Component displayname){
        this.itemMeta.displayName(displayname);
        return this;
    }

    public ItemCreator amount(Integer amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemCreator unbreakable(Boolean value){
        this.itemMeta.setUnbreakable(value);
        return this;
    }

    public ItemCreator addNamespacedKey(String keyName, String message){
        NamespacedKey namespacedKey = new NamespacedKey(DrugsPlugin.getInstance(), keyName);
        this.itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, message);
        return this;
    }

    public ItemCreator enchant(Enchantment enchantment, int amplifier){
        this.itemMeta.addEnchant(enchantment, amplifier, true);
        return this;
    }

    public ItemCreator lore(Component... lore){
        this.itemMeta.lore(Arrays.stream(lore).toList());
        return this;
    }

    public ItemCreator lore(List<Component> lore){
        this.itemMeta.lore(lore);
        return this;
    }

    public ItemStack build(){
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
