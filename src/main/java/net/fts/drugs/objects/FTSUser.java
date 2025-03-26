package net.fts.drugs.objects;

import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class FTSUser {

    UUID uuid;
    List<String> skills;
    private final String skillPath;
    private final String skillName;

    private final File file;
    private YamlConfiguration configuration;

    public FTSUser(UUID uuid) {
        this.uuid = uuid;

        skillPath = DrugsPlugin.getInstance().getConfig().getString("skillsPath");
        skillName = DrugsPlugin.getInstance().getConfig().getString("skillName");
        file = new File(skillPath, uuid.toString()+".yml");

        if(file != null){
            if(file.exists()){
                configuration = YamlConfiguration.loadConfiguration(file);
                if(configuration.contains("skills")){
                    this.skills = configuration.getStringList("skills");
                }
            }
        }
    }

    public List<String> getSkills() {
        return skills;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public void update(){
        if(file != null){
            if(file.exists()){
                configuration = YamlConfiguration.loadConfiguration(file);
                if(configuration.contains("skills")){
                    this.skills = configuration.getStringList("skills");
                }
            }
        }
    }

    public boolean hasRequiredSkill(){
        if(getSkills()!=null){
            if(getSkills().contains(skillName)){
                return true;
            }
        }
        return false;
    }
}
