package net.fts.drugs.listener;

import net.fts.drugs.objects.FTSUser;
import net.fts.drugs.objects.User;
import net.fts.drugs.plugin.Cache;
import net.fts.drugs.plugin.DrugsPlugin;
import org.apache.logging.log4j.util.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = DrugsPlugin.getInstance().getUserManager().getUser(event.getPlayer().getUniqueId());

        if(user==null){
            DrugsPlugin.getInstance().getUserManager().updateUser(new User(event.getPlayer().getUniqueId(), event.getPlayer().getName()));
        }else {
            if(!user.getName().equals(player.getName())){
                user.setName(player.getName());
                user.update();
            }
        }

        FTSUser ftsUser = new FTSUser(player.getUniqueId());
        Cache.ftsPlayers.add(ftsUser);

        if(!ftsUser.hasRequiredSkill()){
            for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
                Recipe recipe = it.next();
                if (recipe instanceof Keyed keyed) {
                    if(keyed.getKey().namespace().equalsIgnoreCase("fts_drugs")){
                        player.undiscoverRecipe(keyed.getKey());
                    }
                }
            }
        }else {
            for (@NotNull Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext(); ) {
                Recipe recipe = it.next();
                if (recipe instanceof Keyed keyed) {
                    if(keyed.getKey().namespace().equalsIgnoreCase("fts_drugs")){
                        NamespacedKey namespacedKey = keyed.getKey();
                        if(namespacedKey.getKey().equals("drugset")||namespacedKey.getKey().equals("gegengift")||namespacedKey.getKey().equals("tester")){
                            if(!player.hasDiscoveredRecipe(keyed.getKey()))
                                player.discoverRecipe(namespacedKey);
                        }
                    }
                }
            }
        }

        player.sendMessage("Du hast die Skills: " + Strings.join(ftsUser.getSkills(), ','));
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerQuit(PlayerQuitEvent event) {
        FTSUser ftsUser = Cache.getFTSUser(event.getPlayer().getUniqueId());
        if(ftsUser!=null){
            Cache.ftsPlayers.remove(ftsUser);
        }
    }


}
