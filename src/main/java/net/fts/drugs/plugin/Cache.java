package net.fts.drugs.plugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Cache {

    public static List<Player> drugset = new ArrayList<>();
    public static HashMap<Player, Set<NamespacedKey>> recepies = new HashMap<>();


}
