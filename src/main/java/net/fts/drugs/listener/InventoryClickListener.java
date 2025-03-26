package net.fts.drugs.listener;

import net.fts.drugs.objects.Drug;
import net.fts.drugs.plugin.DrugsPlugin;
import net.fts.drugs.utils.items.ItemCreator;
import net.fts.drugs.utils.setups.setups.DrugSetup;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
            if(event.getRawSlot()==2){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getWorkbench(drug));
            }
            if(event.getRawSlot()==3){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getResult(drug));
            }
            if(event.getRawSlot()==5){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionEffects(drug));
            }
            if(event.getRawSlot()==6){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getMultiplier(drug));
            }
            if(event.getRawSlot()==8){
                DrugsPlugin.getInstance().getDrugsManager().removeDrug(drug.getName());
                DrugsPlugin.getInstance().unloadRecipe();
                DrugsPlugin.getInstance().loadReceipe();
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getGUI());
            }
        }


        if(event.getView().title().equals(MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Rezept-Bearbeitung"))) {
            event.setCancelled(true);
            String drugName = PlainTextComponentSerializer.plainText().serialize(event.getView().getTopInventory().getItem(4).getItemMeta().displayName());
            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(drugName);

            if(drug==null)return;

            List<Integer> slots = List.of(12, 13, 14, 21, 22, 23, 30, 31, 32);

            if(!slots.contains(event.getRawSlot())){
                event.setCancelled(true);
            }

            if(event.getRawSlot()==36){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
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

                drug.setShape(shape);
                drug.setIngridients(ingridients);
                DrugsPlugin.getInstance().getDrugsManager().addDrug(drug);
                DrugsPlugin.getInstance().unloadRecipe();
                DrugsPlugin.getInstance().loadReceipe();
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Das Rezept wurde gespeichert"));
            }
        }

        if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Result-Bearbeitung"))){
            String drugName = PlainTextComponentSerializer.plainText().serialize(event.getView().getTopInventory().getItem(4).getItemMeta().displayName());
            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(drugName);

            if(drug==null)return;
            if(event.getRawSlot()!=22){
                event.setCancelled(true);
            }

            if(event.getRawSlot()==36){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
            }

            if(event.getRawSlot()==44){
                ItemStack result = event.getClickedInventory().getItem(22);
                if(result==null||result.getType().equals(Material.AIR)){
                    player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Du musst ein Result-Item festlegen!"));
                    return;
                }

                drug.setResult(result);
                DrugsPlugin.getInstance().getDrugsManager().addDrug(drug);
                DrugsPlugin.getInstance().unloadRecipe();
                DrugsPlugin.getInstance().loadReceipe();
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Das Result wurde gespeichert"));
            }
        }

        if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Multiplier-Bearbeitung"))){
            event.setCancelled(true);

            int i = 0;
            String drugName = null;
            for (NamespacedKey key : event.getView().getTopInventory().getItem(26).getPersistentDataContainer().getKeys()) {
                String name =  event.getView().getTopInventory().getItem(26).getPersistentDataContainer().get(key, PersistentDataType.STRING);
                if(drugName == null){
                    drugName = name;
                }
            }

            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(drugName);
            if(drug==null)return;

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
                drug.setAddictionMultiplier(multiplier);
                DrugsPlugin.getInstance().getDrugsManager().addDrug(drug);
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
                player.sendMessage(MiniMessage.miniMessage().deserialize("<green>Der Multiplier wurde gespeichert"));
            }
        }

        if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Effekte-Bearbeitung"))){
            event.setCancelled(true);

            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(PlainTextComponentSerializer.plainText().serialize(event.getView().getTopInventory().getItem(49).getItemMeta().displayName()));
            if(drug==null)return;
            
            if(event.getCurrentItem().getType().equals(Material.BOOK)){
                PotionEffectType type = PotionEffectType.getByName(PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()));
                if(type!=null){
                    player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionManager(type, drug));
                }
            }else if(event.getCurrentItem().getType().equals(Material.ENCHANTED_BOOK)){
                if(event.isRightClick()){
                    if(drug.getEffects().size()<=1){
                        player.sendMessage(MiniMessage.miniMessage().deserialize("<red>Es muss mindestens 1 Effekt bestehen bleiben"));
                        return;
                    }
                    String[] str = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()).split(" ");
                    PotionEffectType type = PotionEffectType.getByName(str[0]);
                    PotionEffect potionEffect = null;
                    for (PotionEffect effect : drug.getEffects()) {
                        if(effect.getType().equals(type)){
                            potionEffect = effect;
                        }
                    }
                    if(potionEffect!=null){
                        drug.getEffects().remove(potionEffect);
                        DrugsPlugin.getInstance().getDrugsManager().addDrug(drug);
                        player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionEffects(drug));
                    }
                }else if(event.isLeftClick()){
                    String[] str = PlainTextComponentSerializer.plainText().serialize(event.getCurrentItem().getItemMeta().displayName()).split(" ");
                    PotionEffectType type = PotionEffectType.getByName(str[0]);
                    if(type!=null) {
                        player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionManager(type, drug));
                    }
                }
            }

            if(event.getRawSlot()==45){
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().manageDrug(drug));
            }

        }

        if(Objects.equals(event.getView().title(), MiniMessage.miniMessage().deserialize("<red><b>DRUGS</b> <dark_gray>» <white>Effektmodifizierung"))){
            event.setCancelled(true);

            int i = 0;
            String drugName = null;
            for (NamespacedKey key : event.getView().getTopInventory().getItem(4).getPersistentDataContainer().getKeys()) {
                String name =  event.getView().getTopInventory().getItem(4).getPersistentDataContainer().get(key, PersistentDataType.STRING);
                if(drugName == null){
                    drugName = name;
                }
            }

            Drug drug = DrugsPlugin.getInstance().getDrugsManager().getDrug(drugName);
            if(drug==null)return;

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
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionEffects(drug));
            }
            if(event.getRawSlot()==26){
                PotionEffectType type = PotionEffectType.getByName(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(4).getItemMeta().displayName()));
                int amplifier = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(11).getItemMeta().displayName()).split(" ")[1]);
                int duration = Integer.parseInt(PlainTextComponentSerializer.plainText().serialize(event.getClickedInventory().getItem(15).getItemMeta().displayName()).split(" ")[1]);

                PotionEffect exists = null;
                for (PotionEffect effect : drug.getEffects()) {
                    if(effect.getType().equals(type)){
                        exists = effect;
                    }
                }

                if(exists != null){
                    drug.getEffects().remove(exists);
                }

                PotionEffect potionEffect = new PotionEffect(type, duration*10, amplifier);
                drug.getEffects().add(potionEffect);
                DrugsPlugin.getInstance().getDrugsManager().addDrug(drug);
                player.openInventory(DrugsPlugin.getInstance().getStorageManager().getInventoryStorage().getPotionEffects(drug));
            }
        }
    }


}
