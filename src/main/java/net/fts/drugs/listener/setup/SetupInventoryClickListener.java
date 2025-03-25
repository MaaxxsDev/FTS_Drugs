package net.fts.drugs.listener.setup;

import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.items.ItemCreator;
import net.fts.drugs.utils.setups.setups.DrugSetup;
import net.fts.drugs.utils.setups.setups.Setup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SetupInventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player player))
            return;

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) == null)
            return;

        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null || itemStack.getType().equals(Material.AIR))
            return;

        ItemMeta itemMeta = itemStack.getItemMeta();
        if(itemMeta == null || itemMeta.displayName()==null)
            return;

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) instanceof DrugSetup setup){

            if(Objects.equals(event.getClickedInventory(), (setup).getCraftingTable())){
                List<Integer> slots = List.of(12, 13, 14, 21, 22, 23, 30, 31, 32);

                if(!slots.contains(event.getRawSlot())){
                    event.setCancelled(true);
                }

                if(event.getRawSlot()==44){
                    HashMap<String, ItemStack> ingridients = new HashMap<>();

                    String shapeTop = "";
                    if(event.getInventory().getItem(12)==null||event.getInventory().getItem(12).getType().equals(Material.AIR)){
                        shapeTop = " ";
                    }else {
                        shapeTop = "A";
                        ingridients.put("A", event.getInventory().getItem(12));
                    }
                    if(event.getInventory().getItem(13)==null||event.getInventory().getItem(13).getType().equals(Material.AIR)){
                        shapeTop = shapeTop+" ";
                    }else {
                        shapeTop = shapeTop+"B";
                        ingridients.put("B", event.getInventory().getItem(13));
                    }
                    if(event.getInventory().getItem(14)==null||event.getInventory().getItem(14).getType().equals(Material.AIR)){
                        shapeTop = shapeTop+" ";
                    }else {
                        shapeTop = shapeTop+"C";
                        ingridients.put("C", event.getInventory().getItem(14));
                    }

                    String shapeMid = "";
                    if(event.getInventory().getItem(21)==null||event.getInventory().getItem(21).getType().equals(Material.AIR)){
                        shapeMid = " ";
                    }else {
                        shapeMid = "D";
                        ingridients.put("D", event.getInventory().getItem(21));
                    }
                    if(event.getInventory().getItem(22)==null||event.getInventory().getItem(22).getType().equals(Material.AIR)){
                        shapeMid = shapeMid+" ";
                    }else {
                        shapeMid = shapeMid+"E";
                        ingridients.put("E", event.getInventory().getItem(22));
                    }
                    if(event.getInventory().getItem(23)==null||event.getInventory().getItem(23).getType().equals(Material.AIR)){
                        shapeMid = shapeMid+" ";
                    }else {
                        shapeMid = shapeMid+"F";
                        ingridients.put("F", event.getInventory().getItem(23));
                    }


                    String shapeBottom = "";
                    if(event.getInventory().getItem(30)==null||event.getInventory().getItem(30).getType().equals(Material.AIR)){
                        shapeBottom = " ";
                    }else {
                        shapeBottom = "G";
                        ingridients.put("G", event.getInventory().getItem(30));
                    }
                    if(event.getInventory().getItem(31)==null||event.getInventory().getItem(31).getType().equals(Material.AIR)){
                        shapeBottom = shapeBottom+" ";
                    }else {
                        shapeBottom = shapeBottom+"H";
                        ingridients.put("H", event.getInventory().getItem(31));
                    }
                    if(event.getInventory().getItem(32)==null||event.getInventory().getItem(32).getType().equals(Material.AIR)){
                        shapeBottom = shapeBottom+" ";
                    }else {
                        shapeBottom = shapeBottom+"I";
                        ingridients.put("I", event.getInventory().getItem(32));
                    }

                    ArrayList<String> shape = new ArrayList<>();
                    shape.add(shapeTop);
                    shape.add(shapeMid);
                    shape.add(shapeBottom);

                    if(ingridients.isEmpty()){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Du musst ein Craftig-Rezept festlegen!"));
                        return;
                    }

                    setup.setShape(shape);
                    setup.setIngridients(ingridients);
                    player.openInventory(setup.getResultInv());
                }

            }

            if(Objects.equals(event.getClickedInventory(), (setup).getResultInv())){
                if(event.getRawSlot()!=22){
                    event.setCancelled(true);
                }

                if(event.getRawSlot()==44){
                    ItemStack result = event.getClickedInventory().getItem(22);
                    if(result==null||result.getType().equals(Material.AIR)){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Du musst ein Result-Item festlegen!"));
                        return;
                    }

                    setup.setResult(result);
                    player.openInventory(setup.getPotionsInv());
                }
            }

            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Wähle die Effekte"))){
                event.setCancelled(true);

                if(event.getCurrentItem().getType().equals(Material.BOOK)){
                    PotionEffectType type = PotionEffectType.getByName(PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()));
                    if(type!=null){
                        player.openInventory(DrugsPlugin.getInstance().getStorageManager().getSetupInventoryStorage().getPotionManager(type));
                    }
                }

                if(event.getRawSlot()==53){
                    if(setup.getEffects().isEmpty()){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Du musst mindestens ein Effekt auswählen!"));
                        return;
                    }
                    player.openInventory(setup.getAddictionMultiplierInv());
                }
            }

            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Modifiziere den PotionEffect"))){
                event.setCancelled(true);

                if(event.getRawSlot()==11){
                    String text = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName());
                    String[] str = text.split(" ");
                    int ampifier = Integer.parseInt(str[1]);
                    if(event.isLeftClick()){
                        ampifier+=1;
                    }else if(event.isRightClick()){
                        if(ampifier>1){
                            ampifier-=1;
                        }
                    }
                    event.getClickedInventory().setItem(11, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("Amplifier: "+ampifier)).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());
                    player.updateInventory();
                }
                if(event.getRawSlot()==15){
                    String text = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName());
                    String[] str = text.split(" ");
                    int duration = Integer.parseInt(str[1]);
                    if(event.isLeftClick()){
                        duration+=1;
                    }else if(event.isRightClick()){
                        if(duration>1){
                            duration-=1;
                        }
                    }
                    event.getClickedInventory().setItem(15, new ItemCreator(Material.COMPASS).displayName(MiniMessage.miniMessage().deserialize("Duration: "+duration+" Sekunden")).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());
                    player.updateInventory();
                }
                if(event.getRawSlot()==18){
                    player.openInventory(setup.getPotionsInv());
                }
                if(event.getRawSlot()==26){
                    PotionEffectType type = PotionEffectType.getByName(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(4).getItemMeta().displayName()));
                    int amplifier = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(11).getItemMeta().displayName()).split(" ")[1]);
                    int duration = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(15).getItemMeta().displayName()).split(" ")[1]);

                    PotionEffect potionEffect = new PotionEffect(type, duration*10, amplifier);
                    setup.getEffects().add(potionEffect);
                    player.openInventory(setup.getPotionsInv());
                }
            }

            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Stelle die Stärke der Suchtentwicklung ein"))){
                event.setCancelled(true);

                if(event.getRawSlot()==13){
                    String text = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName());
                    String[] str = text.split(" ");
                    double duration = Double.parseDouble(str[1]);
                    if(event.isLeftClick()){
                        duration+=0.25;
                    }else if(event.isRightClick()){
                        if(duration>0.25){
                            duration-=0.25;
                        }
                    }
                    event.getClickedInventory().setItem(13, new ItemCreator(Material.PAPER).displayName(MiniMessage.miniMessage().deserialize("AddictionMultiplier: "+duration)).lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Linksklick zum Erhöhen"), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick zum Verringern")).build());
                    player.updateInventory();
                }
                if(event.getRawSlot()==26){
                    double multiplier = Double.parseDouble(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(13).getItemMeta().displayName()).split(" ")[1]);
                    setup.setAddictionMultiplier(multiplier);

                    if(setup.getEffects().isEmpty()){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Du musst mindestens ein Effekt auswählen!"));
                        return;
                    }
                    player.closeInventory(InventoryCloseEvent.Reason.OPEN_NEW);
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Schreibe den Namen, den die Drogen haben soll, in den Chat"));
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray><i>Sneake, wenn das Setup abgebrochen werden soll"));
                }
            }

        }

    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if(!(event.getPlayer() instanceof Player player))
            return;

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) == null)
            return;

        if(DrugsPlugin.getInstance().getStorageManager().getSetupManager().getSetup(player) instanceof DrugSetup setup) {
            if(Objects.equals(event.getInventory(), (setup).getCraftingTable())){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
            if(Objects.equals(event.getInventory(), (setup).getResultInv())){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Setze das Result Item"))){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Wähle die Effekte"))){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Modifiziere den PotionEffect"))){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
            if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("Stelle die Stärke der Suchtentwicklung ein"))){
                if(!event.getReason().equals(InventoryCloseEvent.Reason.OPEN_NEW)) {
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<gray>Das Setup wurde abgebrochen"));
                    DrugsPlugin.getInstance().getStorageManager().getSetupManager().setups.remove(setup);
                }
            }
        }

    }

}
