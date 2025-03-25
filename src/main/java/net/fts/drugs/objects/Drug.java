package net.fts.drugs.objects;

import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
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

    public Drug(String name, Material material,  List<PotionEffect> effects, List<String> shape, HashMap<String, ItemStack> ingridients, ItemStack result) {
        this.name = name;
        this.material = material;
        this.effects = effects;
        this.shape = shape;
        this.ingridients = ingridients;
        this.result = result;
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
        NamespacedKey key = new NamespacedKey(DrugsPlugin.getInstance(), name);
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
        return result;
    }
}
