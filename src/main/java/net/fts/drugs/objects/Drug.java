package net.fts.drugs.objects;

import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class Drug {

    private String name;
    private Material material;
    private List<PotionEffect> effects;
    private List<String> shape;
    private HashMap<String, ItemStack> ingridients;
    private ItemStack result;
    private double addictionMultiplier;
    NamespacedKey key;


    public Drug(String name, Material material,  List<PotionEffect> effects, List<String> shape, HashMap<String, ItemStack> ingridients, ItemStack result, double addictionMultiplier) {
        this.name = name;
        this.material = material;

        String keyname = name;
        keyname = keyname.replace(" ", "");

        key = new NamespacedKey(DrugsPlugin.getInstance(), "fts_drugs_"+keyname);

        this.effects = effects;
        this.shape = shape;
        this.ingridients = ingridients;
        this.result = result;
        this.addictionMultiplier = addictionMultiplier;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<PotionEffect> getEffects() {
        return effects;
    }

    public ShapedRecipe getReceipe(){
        String key_name = name;
        key_name=key_name.replace(" ", "");
        NamespacedKey key = new NamespacedKey(DrugsPlugin.getInstance(), key_name);
        ShapedRecipe shapedRecipe = new ShapedRecipe(key, getResult());

        if(shape.size()<3)return null;
        shapedRecipe.shape(shape.get(0),shape.get(1),shape.get(2));

        ingridients.forEach((string, itemstack) -> {
            shapedRecipe.setIngredient(string.charAt(0), itemstack);
        });
        return shapedRecipe;
    }

    public HashMap<String, ItemStack> getIngridients() {
        return ingridients;
    }

    public List<String> getShape() {
        return shape;
    }

    public ItemStack getResult() {
        ItemStack item = result;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(getKey(), PersistentDataType.STRING, getName());
        item.setItemMeta(meta);
        return item;
    }

    public double getAddictionMultiplier() {
        return addictionMultiplier;
    }
    public void setAddictionMultiplier(double addictionMultiplier) {
        this.addictionMultiplier = addictionMultiplier;
    }

    public NamespacedKey getKey() {
        return key;
    }
}
