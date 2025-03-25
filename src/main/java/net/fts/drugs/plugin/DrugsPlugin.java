package net.fts.drugs.plugin;

import net.fts.drugs.commands.drugsCommand;
import net.fts.drugs.configs.DrugsConfig;
import net.fts.drugs.listener.InventoryClickListener;
import net.fts.drugs.listener.PlayerInteractListener;
import net.fts.drugs.listener.PlayerJoinListener;
import net.fts.drugs.listener.setup.CratingTableListener;
import net.fts.drugs.listener.setup.SetupChatListener;
import net.fts.drugs.listener.setup.SetupInventoryClickListener;
import net.fts.drugs.listener.setup.SetupSneakListener;
import net.fts.drugs.objects.Drug;
import net.fts.drugs.utils.StorageManager;
import net.fts.drugs.utils.items.ItemCreator;
import net.fts.drugs.utils.storage.InventoryStorage;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class DrugsPlugin extends JavaPlugin {

    private static DrugsPlugin instance;

    private DrugsConfig drugsConfig;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }


        drugsConfig = new DrugsConfig();
        storageManager = new StorageManager();

        loadReceipe();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new SetupInventoryClickListener(), this);
        pluginManager.registerEvents(new SetupChatListener(), this);
        pluginManager.registerEvents(new SetupSneakListener(), this);
        pluginManager.registerEvents(new CratingTableListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);

        Objects.requireNonNull(getCommand("drugs")).setExecutor(new drugsCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public void loadReceipe(){
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(this, "drugset"), new ItemCreator(Material.NAUTILUS_SHELL).displayName(MiniMessage.miniMessage().deserialize("<yellow><b>Drogenset")).build());
        shapedRecipe.shape(" A ", "BBB", "CCC");
        shapedRecipe.setIngredient('A', Material.BREWING_STAND);
        shapedRecipe.setIngredient('B', Material.DIAMOND);
        shapedRecipe.setIngredient('C', Material.EMERALD_BLOCK);

        int counter = 0;
        Bukkit.addRecipe(shapedRecipe);
        Bukkit.getConsoleSender().sendMessage("Rezept " + shapedRecipe.getKey().getKey() + " wurde geladen");
        counter+=1;

        for (Drug drug : getDrugsManager().getDrugs()) {
            Bukkit.addRecipe(drug.getReceipe());
            Bukkit.getConsoleSender().sendMessage("Rezept " + drug.getReceipe().getKey().getKey() + " wurde geladen");
            counter+=1;
        }

        Bukkit.getConsoleSender().sendMessage("Es wurden "+counter+" Rezepte geladen");
    }

    public static DrugsPlugin getInstance() {
        return instance;
    }
    public DrugsConfig getDrugsManager() {
        return drugsConfig;
    }
    public StorageManager getStorageManager() {
        return storageManager;
    }
}
