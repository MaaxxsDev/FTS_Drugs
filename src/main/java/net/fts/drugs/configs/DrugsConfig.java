package net.fts.drugs.configs;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DrugsConfig {

    private final DrugsPlugin plugin;
    private final File file;
    private final YamlConfiguration configuration;

    public DrugsConfig() {
        this.plugin = DrugsPlugin.getInstance();
        this.file = new File(plugin.getDataFolder().getPath(), "drugs.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        this.configuration = YamlConfiguration.loadConfiguration(file);
        this.configuration.options().copyDefaults(true);
        saveConfig();
    }
    
    public List<Drug> getDrugs(){
        List<Drug> drugs = new ArrayList<>();
        for (String result : configuration.getConfigurationSection("").getKeys(false)) {
            Material material = Material.valueOf(configuration.getString(result+".Material"));
            List<String> stringList = configuration.getStringList(result+".Effects");
            List<String> shape = configuration.getStringList(result+".Shape");
            HashMap<String, ItemStack> ingridients = new HashMap<>();
            List<PotionEffect> potionsEffects = new ArrayList<>();
            ItemStack result_item = configuration.getItemStack(result+".Result");
            double addictionMultiplier = configuration.getDouble(result+".AddictionMultiplier");

            boolean changedEffects = false;

            Iterator<String> iterator = stringList.iterator();
            while (iterator.hasNext()) {
                String effects = iterator.next();
                String[] str = effects.split(":");
                String effectID = str[0];
                int effectDuration = Integer.parseInt(str[1]);
                int effectAmplifier = Integer.parseInt(str[2]);

                PotionEffectType type = PotionEffectType.getByName(effectID);

                if (type != null) {
                    PotionEffect potionEffect = new PotionEffect(type, (effectDuration * 2), effectAmplifier - 1);
                    potionsEffects.add(potionEffect);
                } else {
                    iterator.remove(); // ✅ erlaubt!
                    changedEffects = true;
                }
            }

            if(changedEffects) {
                configuration.set(result + ".Effects", stringList);
                saveConfig();
            }

            for (String ingridient : configuration.getConfigurationSection(result + ".Ingridients").getKeys(false)) {
                ItemStack itemStack = configuration.getItemStack(result+".Ingridients."+ingridient);
                ingridients.put(ingridient, itemStack);
            }

            Drug drug = new Drug(result, material, potionsEffects, shape, ingridients, result_item, addictionMultiplier);
            drugs.add(drug);
        }
        return drugs;
    }

    public Drug getDrug(String name){
        for (Drug drug : getDrugs()) {
            if(drug.getName().equals(name))return drug;
        }
        return null;
    }

    public void removeDrug(String name){
        configuration.set(name, null);
        saveConfig();
        DrugsPlugin.getInstance().unloadRecipe();
        DrugsPlugin.getInstance().loadReceipe();
    }

    public void addDrug(Drug drug){
        configuration.set(drug.getName(), null);
        configuration.set(drug.getName()+".Material", drug.getMaterial().toString());

        List<String> effects = new ArrayList<>();
        for (PotionEffect effect : drug.getEffects()) {
            String str = effect.getType().getName()+":"+effect.getDuration()+":"+effect.getAmplifier();
            effects.add(str);
        }
        configuration.set(drug.getName()+".Effects", effects);
        configuration.set(drug.getName()+".Shape", drug.getShape());
        configuration.set(drug.getName()+".Result", drug.getResult());
        configuration.set(drug.getName()+".AddictionMultiplier", drug.getAddictionMultiplier());
        drug.getIngridients().forEach((s, itemStack) -> {
            configuration.set(drug.getName()+".Ingridients."+s, itemStack);
        });

        saveConfig();
    }

    private void saveConfig(){
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
