package net.fts.drugs.plugin;

import net.fts.drugs.configs.DrugsConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class DrugsPlugin extends JavaPlugin {

    private static DrugsPlugin instance;

    private DrugsConfig drugsConfig;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }

        drugsConfig = new DrugsConfig();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public static DrugsPlugin getInstance() {
        return instance;
    }

    public DrugsConfig getDrugs() {
        return drugsConfig;
    }
}
