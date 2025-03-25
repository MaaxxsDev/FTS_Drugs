package net.fts.drugs.plugin;

import net.fts.drugs.commands.drugsCommand;
import net.fts.drugs.configs.DrugsConfig;
import net.fts.drugs.listener.InventoryClickListener;
import net.fts.drugs.listener.setup.SetupChatListener;
import net.fts.drugs.listener.setup.SetupInventoryClickListener;
import net.fts.drugs.listener.setup.SetupSneakListener;
import net.fts.drugs.utils.StorageManager;
import net.fts.drugs.utils.storage.InventoryStorage;
import org.bukkit.Bukkit;
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

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new SetupInventoryClickListener(), this);
        pluginManager.registerEvents(new SetupChatListener(), this);
        pluginManager.registerEvents(new SetupSneakListener(), this);

        Objects.requireNonNull(getCommand("drugs")).setExecutor(new drugsCommand());
    }

    @Override
    public void onDisable() {
        super.onDisable();
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
