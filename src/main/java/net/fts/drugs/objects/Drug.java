package net.fts.drugs.objects;

import org.bukkit.Material;
import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Drug {

    private String name;
    private Material material;
    private List<PotionEffect> effects;

    public Drug(String name, Material material,  List<PotionEffect> effects) {
        this.name = name;
        this.material = material;
        this.effects = effects;
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
}
