package net.fts.drugs.listener.setup;

import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.setups.setups.DrugSetup;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class SetupSneakListener implements Listener {

    @EventHandler
    public void onAsyncChat(PlayerToggleSneakEvent event) {
        Player player = event.getPlayer();

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) == null)
            return;

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) instanceof DrugSetup setup){
            DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
            player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
        }

    }

}
