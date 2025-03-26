package net.fts.drugs.listener.setup;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.setups.setups.DrugSetup;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class SetupChatListener implements Listener {

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {
        Player player = event.getPlayer();

        Bukkit.getScheduler().runTask(DrugsPlugin.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) == null)
                    return;

                if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) instanceof DrugSetup setup){
                    event.setCancelled(true);
                    String name = PlainTextComponentSerializer.plainText().serialize(event.message());

                    if(!name.matches("^\\S+$")){
                        player.sendMessage("<red>Die Drogenbezeichnung darf mehr als ein Wort beinhalten");
                        return;
                    }

                    if(DrugsPlugin.getInstance().getDrugsManager().getDrug(name)!=null){
                        player.sendMessage("<red>Diese Droge existiert schon");
                        return;
                    }

                    setup.setName(name);

                    if(setup.getName()!=null&&!setup.getEffects().isEmpty()&&!setup.getIngridients().isEmpty()&&!setup.getShape().isEmpty()&&setup.getResult()!=null) {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Droge wurde erstellt"));
                        DrugsPlugin.getInstance().getDrugsManager().addDrug(new Drug(setup.getName(), setup.getResult().getType(),  setup.getEffects(), setup.getShape(), setup.getIngridients(), setup.getResult(), setup.getAddictionMultiplier()));
                        DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                        player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI());
                    }else {
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Es ist ein Fehler aufgetreten, versuche es bitte erneut!"));
                    }
                }
            }
        });



    }


}
