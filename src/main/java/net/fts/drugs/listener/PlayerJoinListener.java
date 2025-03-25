package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.User;
import net.fts.drugs.plugin.DrugsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
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
    }


}
