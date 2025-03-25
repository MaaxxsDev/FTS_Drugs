package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.setups.setups.DrugSetup;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null || itemStack.getType().equals(Material.AIR))
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null || itemMeta.displayName()==null)
            return;

        if(event.getView().title().equals(MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Admin Interface"))){
            event.setCancelled(true);

            if(event.getRawSlot()>=0 && event.getRawSlot()<=44){
                String itemName = PlainTextComponentSerializer.plainText().serialize(itemMeta.displayName());
                for (Drug drug : DrugsPlugin.getInstance().getDrugsManager().getDrugs()) {
                    if(drug.getName().equals(itemName)){
                        player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
                    }
                }
            }

            switch (event.getRawSlot()){
                case 49:
                    new DrugSetup(player);
                    break;
            }
        }


        if(event.getView().title().equals(MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Bearbeitung"))) {
            event.setCancelled(true);
            String drugName = PlainTextComponentSerializer.plainText().serialize(event.getView().getTopInventory().getItem(4).getItemMeta().displayName());
            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(drugName);

            if(drug==null)return;

            if(event.getRawSlot()==0){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI());
            }
            if(event.getRawSlot()==8){
                DrugsPlugin.getInstance().getDrugsManager().removeDrug(drug.getName());
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI());
            }
        }
    }


}
