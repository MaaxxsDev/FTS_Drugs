package net.fts.drugs.configs;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.objects.user.Rausch;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserConfig {

    private final DrugsPlugin plugin;
    private final File file;
    private final YamlConfiguration configuration;

    public UserConfig() {
        this.plugin = DrugsPlugin.getInstance();
        this.file = new File(plugin.getDataFolder().getPath(), "users.yml");

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
    
    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        for (String result : configuration.getConfigurationSection("").getKeys(false)) {
            UUID uuid = UUID.fromString(result);
            String name = configuration.getString(result+".Name");
            List<Addiction> addictions = new ArrayList<>();
            if(configuration.contains(result+".Addiction")){
                for (String addiction : configuration.getConfigurationSection(result + ".Addiction").getKeys(false)) {
                    double strength = configuration.getDouble(result+".Addiction."+addiction);
                    Addiction addict = new Addiction(strength, addiction);
                    addictions.add(addict);
                }
            }
            List<Rausch> rauschAll = new ArrayList<>();
            if(configuration.contains(result+".Rausch")){
                for (String rausch : configuration.getConfigurationSection(result + ".Rausch").getKeys(false)) {
                    int duration = configuration.getInt(result+".Rausch."+rausch+".Duration");
                    long startedat = configuration.getLong(result+".Rausch."+rausch+".StartedAt");
                    Rausch rau = new Rausch(startedat, duration, rausch);
                    rauschAll.add(rau);
                }
            }
            users.add(new User(uuid, name, addictions, rauschAll));
        }
        return users;
    }

    public User getUser(UUID uuid){
        for (User user : getUsers()) {
            if(user.getUniqueId().equals(uuid)){
                return user;
            }
        }
        return null;
    }

    public void updateUser(User user){
        configuration.set(user.getUniqueId().toString()+".Name", user.getName());
        configuration.set(user.getUniqueId().toString()+".Addiction", null);
        configuration.set(user.getUniqueId().toString()+".Rausch", null);


        for (Addiction addiction : user.getAddictions()) {
            configuration.set(user.getUniqueId().toString()+".Addiction."+addiction.getName(), addiction.getStrength());
        }
        for (Rausch rausch : user.getRausch()) {
            configuration.set(user.getUniqueId().toString()+".Rausch."+rausch.getName()+".StartedAt", rausch.getStartedAt());
            configuration.set(user.getUniqueId().toString()+".Rausch."+rausch.getName()+".Duration", rausch.getDuration());
        }
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
