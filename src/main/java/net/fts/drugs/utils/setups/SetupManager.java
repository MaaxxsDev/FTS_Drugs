package net.fts.drugs.utils.setups;

import net.fts.drugs.utils.setups.setups.Setup;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SetupManager {

    public List<Setup> setups;

    public SetupManager() {
        this.setups = new ArrayList<>();
    }

    public Setup getSetup(Player player){
        for (Setup setup : setups) {
           if(setup.getPlayer().equals(player))return setup;
        }
        return null;
    }

}
