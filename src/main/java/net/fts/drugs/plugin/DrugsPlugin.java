package net.fts.drugs.plugin;

import net.fts.drugs.commands.drugsCommand;
import net.fts.drugs.configs.DrugsConfig;
import net.fts.drugs.utils.StorageManager;
import net.fts.drugs.utils.storage.InventoryStorage;
import org.bukkit.plugin.java.JavaPlugin;

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

        getCommand("drugs").setExecutor(new drugsCommand());
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
