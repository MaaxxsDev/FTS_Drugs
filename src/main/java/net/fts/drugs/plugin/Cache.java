package net.fts.drugs.plugin;

import net.fts.drugs.objects.FTSUser;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.*;

public class Cache {

    public static List<FTSUser> ftsPlayers = new ArrayList<>();
    public static List<Player> drugset = new ArrayList<>();
    public static HashMap<Player, Set<NamespacedKey>> recepies = new HashMap<>();

    public static FTSUser getFTSUser(UUID uuid){
        for (FTSUser ftsPlayer : ftsPlayers) {
            if(ftsPlayer.getUniqueId().equals(uuid)){
                return ftsPlayer;
            }
        }
        return null;
    }


}
