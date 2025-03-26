package net.fts.drugs.enums;

import net.fts.drugs.utils.items.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public enum Tools {

    DRUGKIT(new ItemCreator(Material.NAUTILUS_SHELL).displayName(MiniMessage.miniMessage().deserialize("<red><b>Drugset")).addNamespacedKey("fts_drugs_items", "drugkit").build()),
    ANTIDOTE(new ItemCreator(Material.POISONOUS_POTATO).displayName(MiniMessage.miniMessage().deserialize("<yellow><b>Gegengift")).addNamespacedKey("fts_drugs_items", "gegengift").build()),
    DRUGTEST(new ItemCreator(Material.STICK).enchant(Enchantment.UNBREAKING, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).displayName(MiniMessage.miniMessage().deserialize("<yellow>Drogentest")).addNamespacedKey("fts_drugs_items", "drogentest").lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick auf einen Spieler, um den Test durchzuführen")).build());

    private final ItemStack itemStack;

    Tools(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
