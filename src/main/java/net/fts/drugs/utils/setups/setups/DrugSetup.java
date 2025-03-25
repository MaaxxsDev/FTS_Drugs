package net.fts.drugs.utils.setups.setups;

import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DrugSetup implements Setup{

    private final Player player;
    private final Inventory craftingTable;
    private final Inventory addictionMultiplierInv;
    private final Inventory resultInv;
    private List<String> shape;
    private HashMap<String, ItemStack> ingridients;
    private ItemStack result;
    private List<PotionEffect> effects;
    private double addictionMultiplier;
    String name;

    public DrugSetup(Player player) {
        this.player = player;
        this.craftingTable = DrugsPlugin.getInstance().getStorageManager().getSetupInventoryStorage().getWorkbench();
        this.resultInv = DrugsPlugin.getInstance().getStorageManager().getSetupInventoryStorage().getResult();
        this.addictionMultiplierInv = DrugsPlugin.getInstance().getStorageManager().getSetupInventoryStorage().getMultiplier();
        this.effects = new ArrayList<>();

        DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.add(this);

        player.openInventory(this.craftingTable);
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    public Inventory getCraftingTable() {
        return craftingTable;
    }
    public Inventory getResultInv() {
        return resultInv;
    }
    public Inventory getPotionsInv() {
        return DrugsPlugin.getInstance().getStorageManager().getSetupInventoryStorage().getPotionEffects(getEffects());
    }

    public void setShape(List<String> shape) {
        this.shape = shape;
    }
    public List<String> getShape() {
        return shape;
    }

    public void setIngridients(HashMap<String, ItemStack> ingridients) {
        this.ingridients = ingridients;
    }
    public HashMap<String, ItemStack> getIngridients() {
        return ingridients;
    }

    public void setResult(ItemStack result) {
        this.result = result;
    }
    public ItemStack getResult() {
        return result;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setAddictionMultiplier(double addictionMultiplier) {
        this.addictionMultiplier = addictionMultiplier;
    }
    public double getAddictionMultiplier() {
        return addictionMultiplier;
    }

    public Inventory getAddictionMultiplierInv() {
        return addictionMultiplierInv;
    }
}
