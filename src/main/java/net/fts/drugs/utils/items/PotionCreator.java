package net.fts.drugs.utils.items;

import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.items.enums.PotionType;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;

import java.util.Arrays;
import java.util.List;

public class PotionCreator {

    private final ItemStack itemStack;
    private final PotionMeta itemMeta;

    public PotionCreator(PotionType potionType) {
        if(potionType.equals(PotionType.SPLASH)){
            this.itemStack = new ItemStack(Material.SPLASH_POTION);
        }else if(potionType.equals(PotionType.LINGERING)){
            this.itemStack = new ItemStack(Material.LINGERING_POTION);
        }else {
            this.itemStack = new ItemStack(Material.POTION);
        }
        this.itemMeta = (PotionMeta) itemStack.getItemMeta();
    }

    public PotionCreator displayName(Component displayname){
        this.itemMeta.displayName(displayname);
        return this;
    }

    public PotionCreator amount(Integer amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public PotionCreator unbreakable(Boolean value){
        this.itemMeta.setUnbreakable(value);
        return this;
    }

    public PotionCreator addEffect(PotionEffect potionEffect){
        this.itemMeta.addCustomEffect(potionEffect, true);
        return this;
    }

    public PotionCreator addNamespacedKey(String keyName, String message){
        NamespacedKey namespacedKey = new NamespacedKey(DrugsPlugin.getInstance(), keyName);
        this.itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, message);
        return this;
    }

    public PotionCreator enchant(Enchantment enchantment, int amplifier){
        this.itemMeta.addEnchant(enchantment, amplifier, true);
        return this;
    }

    public PotionCreator lore(Component... lore){
        this.itemMeta.lore(Arrays.stream(lore).toList());
        return this;
    }

    public PotionCreator lore(List<Component> lore){
        this.itemMeta.lore(lore);
        return this;
    }

    public ItemStack build(){
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }

}
