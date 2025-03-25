package net.fts.drugs.plugin;

import net.fts.drugs.commands.drugsCommand;
import net.fts.drugs.configs.DrugsConfig;
import net.fts.drugs.configs.UserConfig;
import net.fts.drugs.listener.*;
import net.fts.drugs.listener.consume.PlayerConsumeDrugListener;
import net.fts.drugs.listener.setup.SetupChatListener;
import net.fts.drugs.listener.setup.SetupInventoryClickListener;
import net.fts.drugs.listener.setup.SetupSneakListener;
import net.fts.drugs.objects.Drug;
import net.fts.drugs.objects.User;
import net.fts.drugs.objects.user.Addiction;
import net.fts.drugs.objects.user.Rausch;
import net.fts.drugs.utils.StorageManager;
import net.fts.drugs.utils.items.ItemCreator;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Keyed;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class DrugsPlugin extends JavaPlugin {

    private static DrugsPlugin instance;

    private DrugsConfig drugsConfig;
    private UserConfig usersConfig;
    private StorageManager storageManager;

    @Override
    public void onEnable() {
        instance = this;

        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }


        drugsConfig = new DrugsConfig();
        usersConfig = new UserConfig();
        storageManager = new StorageManager();

        loadReceipe();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new InventoryClickListener(), this);
        pluginManager.registerEvents(new SetupInventoryClickListener(), this);
        pluginManager.registerEvents(new SetupChatListener(), this);
        pluginManager.registerEvents(new SetupSneakListener(), this);
        pluginManager.registerEvents(new CratingTableListener(), this);
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerInteractListener(), this);
        pluginManager.registerEvents(new PlayerConsumeDrugListener(), this);
        pluginManager.registerEvents(new InventoryCloseListener(), this);

        Objects.requireNonNull(getCommand("drugs")).setExecutor(new drugsCommand());

        Bukkit.getOnlinePlayers().forEach(player -> {
            User user = DrugsPlugin.getInstance().getUserManager().getUser(player.getUniqueId());

            if(user==null){
                DrugsPlugin.getInstance().getUserManager().updateUser(new User(player.getUniqueId(), player.getName()));
            }else {
                if(!user.getName().equals(player.getName())){
                    user.setName(player.getName());
                    user.update();
                }
            }
        });

        initRepatableTasks();
    }

    @Override
    public void onDisable() {
        Cache.recepies.forEach((player, namespacedKeys) -> {
            for (NamespacedKey namespacedKey : namespacedKeys) {
                player.discoverRecipe(namespacedKey);
            }
        });
    }

    public void loadReceipe(){
        ShapedRecipe Drugkit = new ShapedRecipe(new NamespacedKey(this, "drugset"), new ItemCreator(Material.NAUTILUS_SHELL).displayName(MiniMessage.miniMessage().deserialize("<red><b>Drugset")).addNamespacedKey("fts_drugs_items", "drugkit").build());
        Drugkit.shape(" A ", "BBB", "CCC");
        Drugkit.setIngredient('A', Material.BREWING_STAND);
        Drugkit.setIngredient('B', Material.DIAMOND);
        Drugkit.setIngredient('C', Material.EMERALD_BLOCK);

        ShapedRecipe Gegengift = new ShapedRecipe(new NamespacedKey(this, "gegengift"), new ItemCreator(Material.POISONOUS_POTATO).displayName(MiniMessage.miniMessage().deserialize("<yellow><b>Gegengift")).addNamespacedKey("fts_drugs_items", "gegengift").build());
        Gegengift.shape("AAA", "BAB", " C ");
        Gegengift.setIngredient('A', Material.PUFFERFISH);
        Gegengift.setIngredient('B', Material.POISONOUS_POTATO);
        Gegengift.setIngredient('C', Material.WATER_BUCKET);

        ShapedRecipe Tester = new ShapedRecipe(new NamespacedKey(this, "tester"), new ItemCreator(Material.STICK).enchant(Enchantment.UNBREAKING, 1).addItemFlag(ItemFlag.HIDE_ENCHANTS).displayName(MiniMessage.miniMessage().deserialize("<yellow>Drogentest")).addNamespacedKey("fts_drugs_items", "drogentest").lore(Component.empty(), MiniMessage.miniMessage().deserialize("<gray>Rechtsklick auf einen Spieler, um den Test durchzuführen")).build());
        Tester.shape("AAA", "BAB", " C ");
        Tester.setIngredient('A', Material.LAPIS_LAZULI);
        Tester.setIngredient('B', Material.REDSTONE);
        Tester.setIngredient('C', Material.WATER_BUCKET);

        int counter = 0;
        Bukkit.addRecipe(Drugkit);
        Bukkit.getConsoleSender().sendMessage("Rezept " + Drugkit.getKey().getKey() + " wurde geladen");
        counter+=1;
        Bukkit.addRecipe(Gegengift);
        Bukkit.getConsoleSender().sendMessage("Rezept " + Gegengift.getKey().getKey() + " wurde geladen");
        counter+=1;
        Bukkit.addRecipe(Tester);
        Bukkit.getConsoleSender().sendMessage("Rezept " + Tester.getKey().getKey() + " wurde geladen");
        counter+=1;

        for (Drug drug : getDrugsManager().getDrugs()) {
            Bukkit.addRecipe(drug.getReceipe());
            Bukkit.getConsoleSender().sendMessage("Rezept " + drug.getReceipe().getKey().getKey() + " wurde geladen");
            counter+=1;
        }

        Bukkit.getConsoleSender().sendMessage("Es wurden "+counter+" Rezepte geladen");
    }

    public void initRepatableTasks(){
        Bukkit.getScheduler().runTaskTimer(this, new Runnable() {
            @Override
            public void run() {
                for (User user : getUserManager().getUsers()) {
                    if(Bukkit.getPlayer(user.getUniqueId())!=null){
                        Player player = Bukkit.getPlayer(user.getUniqueId());

                        List<Rausch> toRemove = new ArrayList<>();
                        List<Addiction> toRemoveAddic = new ArrayList<>();
                        List<Addiction> addictions = new ArrayList<>(user.getAddictions());

                        user.getRausch().forEach(rausch -> {
                            addictions.forEach(addiction -> {
                                if(addiction.getName().equals(rausch.getName())){
                                   toRemoveAddic.add(addiction);
                                }
                            });

                            long startedAt = rausch.getStartedAt();
                            long now = System.currentTimeMillis();

                            long elapsedMillis = now-startedAt;
                            long elapsedSecouds = elapsedMillis/1000;


                            if(elapsedSecouds>=rausch.getDuration()){
                                toRemove.add(rausch);
                                player.sendActionBar(MiniMessage.miniMessage().deserialize("Der Rausch von <i>" + rausch.getName() + "</i> lässt nach"));
                            }
                        });

                        for (Rausch rausch : toRemove) {
                            user.getRausch().remove(rausch);
                            user.update();
                        }

                        for (Addiction addiction : toRemoveAddic) {
                            addictions.remove(addiction);
                        }

                        //Sucht

                        double strongestAddiction = 0.00;
                        for (Addiction addiction : addictions) {
                            if(addiction.getStrength()>strongestAddiction){
                                strongestAddiction=addiction.getStrength();
                            }
                        }

                        if(strongestAddiction >= 80){
                            if(player.getWalkSpeed()>=0.15f){
                                player.setWalkSpeed(0.10f);
                            }
                            if(!player.hasPotionEffect(PotionEffectType.NAUSEA)){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, -1, 1));
                            }
                            if(!player.hasPotionEffect(PotionEffectType.WEAKNESS)){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, -1, 1));
                            }
                            if(player.getPotionEffect(PotionEffectType.HUNGER)==null){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, -1, 0));
                            }else {
                                if(Objects.requireNonNull(player.getPotionEffect(PotionEffectType.HUNGER)).getAmplifier()==0){
                                    player.removePotionEffect(PotionEffectType.HUNGER);
                                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, -1, 1));
                                }
                            }
                        }else if(strongestAddiction > 60){
                            if(player.getWalkSpeed()>=0.15f){
                                player.setWalkSpeed(0.10f);
                            }
                            if(player.hasPotionEffect(PotionEffectType.NAUSEA)){
                                player.removePotionEffect(PotionEffectType.NAUSEA);
                            }
                            if(!player.hasPotionEffect(PotionEffectType.WEAKNESS)){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, -1, 1));
                            }
                            if(player.getPotionEffect(PotionEffectType.HUNGER)==null){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, -1, 0));
                            }
                        }else if(strongestAddiction > 50){
                            if(player.getWalkSpeed()>=0.15f){
                                player.setWalkSpeed(0.10f);
                            }
                            if(player.hasPotionEffect(PotionEffectType.NAUSEA)){
                                player.removePotionEffect(PotionEffectType.NAUSEA);
                            }
                            if(!player.hasPotionEffect(PotionEffectType.WEAKNESS)){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, -1, 1));
                            }
                            if(player.getPotionEffect(PotionEffectType.HUNGER)==null){
                                player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, -1, 0));
                            }
                        }else if(strongestAddiction > 30){
                            if(player.getWalkSpeed()>=0.2f){
                                player.setWalkSpeed(0.15f);
                            }
                            if(player.hasPotionEffect(PotionEffectType.NAUSEA)){
                                player.removePotionEffect(PotionEffectType.NAUSEA);
                            }
                            if(player.hasPotionEffect(PotionEffectType.WEAKNESS)){
                                player.removePotionEffect(PotionEffectType.WEAKNESS);
                            }
                        }else if(strongestAddiction<30){
                            if(player.getWalkSpeed()<0.2f){
                                player.setWalkSpeed(0.2f);
                            }
                            if(player.hasPotionEffect(PotionEffectType.NAUSEA)){
                                player.removePotionEffect(PotionEffectType.NAUSEA);
                            }
                            if(player.hasPotionEffect(PotionEffectType.WEAKNESS)){
                                player.removePotionEffect(PotionEffectType.WEAKNESS);
                            }
                            if(player.hasPotionEffect(PotionEffectType.HUNGER)){
                                player.removePotionEffect(PotionEffectType.HUNGER);
                            }
                        }
                    }
                }
            }
        }, 20, 20);
    }

    public static DrugsPlugin getInstance() {
        return instance;
    }
    public DrugsConfig getDrugsManager() {
        return drugsConfig;
    }
    public UserConfig getUserManager() {
        return usersConfig;
    }
    public StorageManager getStorageManager() {
        return storageManager;
    }
}
