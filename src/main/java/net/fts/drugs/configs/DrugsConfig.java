package net.fts.drugs.configs;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
            List<String> stringList = configuration.getStringList(result+".Effects");
            List<PotionEffect> potionsEffects = new ArrayList<>();

            boolean changedEffects = false;

            for (String effects : stringList) {
                String[] str = effects.split(":");
                String effectID = str[0];
                int effectDuration = Integer.parseInt(str[1]);
                int effectAmplifier = Integer.parseInt(str[2]);

                PotionEffectType type = PotionEffectType.getByName(effectID);

                if(type != null){
                    PotionEffect potionEffect = new PotionEffect(type, effectDuration, effectAmplifier);
                    potionsEffects.add(potionEffect);
                }else {
                    stringList.remove(effects);
                    changedEffects=true;
                }
            }

            if(changedEffects) {
                configuration.set(result + ".Effects", stringList);
                saveConfig();
            }

            Drug drug = new Drug(result, potionsEffects);
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
        if(configuration.contains(name)){
            configuration.set(name, null);
            saveConfig();
        }
    }

    public void addDrug(Drug drug){
        if(getDrug(drug.getName())!=null)
            return;

        List<String> effects = new ArrayList<>();
        for (PotionEffect effect : drug.getEffects()) {
            String str = effect.toString()+":"+effect.getDuration()+":"+effect.getAmplifier();
            effects.add(str);
        }

        configuration.set(drug.getName()+".Effects", effects);
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
