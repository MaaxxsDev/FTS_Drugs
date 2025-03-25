package net.fts.drugs.objects;

import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.objects.user.Rausch;
import net.fts.drugs.plugin.DrugsPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private String name;
    private final List<Addiction> addictions;
    private final List<Rausch> rausch;

    public User(UUID uuid, String name, List<Addiction> addictions, List<Rausch> rausch) {
        this.uuid = uuid;
        this.addictions = addictions;
        this.name = name;
        this.rausch = rausch;
    }

    public User(UUID uuid, String name) {
        this.uuid = uuid;
        this.addictions = new ArrayList<>();
        this.rausch = new ArrayList<>();
        this.name = name;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public List<Addiction> getAddictions() {
        return addictions;
    }

    public Addiction getStrongestAddiction(){
        Addiction strongest = null;

        for (Addiction addiction : getAddictions()) {
            if(strongest==null){
                strongest=addiction;
            }else {
                if(addiction.getStrength()>strongest.getStrength()){
                    strongest = addiction;
                }
            }
        }

        return strongest;
    }

    public List<Rausch> getRausch() {
        return rausch;
    }

    public void update(){
        DrugsPlugin.getInstance().getUserManager().updateUser(this);
    }

}
