package net.fts.drugs.utils;

import net.fts.drugs.utils.setups.SetupManager;
import net.fts.drugs.utils.storage.InventoryStorage;
import net.fts.drugs.utils.storage.SetupInventoryStorage;

public class StorageManager {
    private final InventoryStorage inventoryStorage;
    private final SetupInventoryStorage setupInventoryStorage;
    private final SetupManager setupManager;

    public StorageManager() {
        this.inventoryStorage = new InventoryStorage();
        this.setupInventoryStorage = new SetupInventoryStorage();
        this.setupManager = new SetupManager();
    }

    public InventoryStorage getInventoryStorage() {
        return inventoryStorage;
    }
    public SetupInventoryStorage getSetupInventoryStorage() {
        return setupInventoryStorage;
    }
    public SetupManager getSetupManager() {
        return setupManager;
    }
}
