package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

//        for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
//            player.discoverRecipe(drug.getReceipe().getKey());
//        }
    }


}
