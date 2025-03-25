package net.fts.drugs.objects;

import org.bukkit.potion.PotionEffect;

import java.util.List;

public class Drug {

    private String name;
    private List<PotionEffect> effects;

    public Drug(String name, List<PotionEffect> effects) {
        this.name = name;
        this.effects = effects;
    }

    public String getName() {
        return name;
    }
    public List<PotionEffect> getEffects() {
        return effects;
    }
}
