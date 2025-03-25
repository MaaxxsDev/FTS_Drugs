package net.fts.drugs.utils;

import net.fts.drugs.utils.storage.InventoryStorage;

public class StorageManager {
    private InventoryStorage inventoryStorage;

    public StorageManager() {
        this.inventoryStorage = new InventoryStorage();
    }

    public InventoryStorage getInventoryStorage() {
        return inventoryStorage;
    }
}
