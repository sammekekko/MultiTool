package me.neuriong.multitool;

import me.neuriong.multitool.cenchantments.Haste;
import me.neuriong.multitool.commands.multitool;
import me.neuriong.multitool.item.Item;
import me.neuriong.multitool.listeners.EnchantListener;
import me.neuriong.multitool.listeners.GuiListener;
import me.neuriong.multitool.listeners.InteractListener;
import me.neuriong.multitool.tabcomplete.MultiToolTabComplete;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class MultiTool extends JavaPlugin {

    public static Economy economy = null;

    private Haste haste = new Haste(101);

    private Item it = new Item();

    private ArrayList<UUID> hasHaste = new ArrayList<>();

    public static MultiTool main;

    @Override
    public void onEnable() {
        main = this;
        saveDefaultConfig();
        if (!setupEconomy()) {
            Bukkit.shutdown();
        }
        loadEnchantments();
        loadEvents();
        checkHand();
        getCommand("multitool").setTabCompleter(new MultiToolTabComplete());
        getCommand("multitool").setExecutor(new multitool(this));
    }

    private void checkHand() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(MultiTool.main, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                ItemStack itemInHand = p.getItemInHand();
                if (itemInHand != null && itemInHand.getType() != Material.AIR) {
                    if (itemInHand.getItemMeta().getLore() != null && it.compare(itemInHand.getItemMeta().getLore(), it.getLore()) && itemInHand.getItemMeta().getEnchants().containsKey(Enchantment.getByName("Haste"))) {
                        if (!hasHaste.contains(p.getUniqueId())) {
                            p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                            final int level = p.getItemInHand().getEnchantmentLevel(Enchantment.getByName("Haste"));
                            final PotionEffect hasteEffect = PotionEffectType.FAST_DIGGING.createEffect(999999999, level - 1);
                            p.addPotionEffect(hasteEffect);
                            hasHaste.add(p.getUniqueId());
                        }
                    } else if (hasHaste.contains(p.getUniqueId())) {
                        hasHaste.remove(p.getUniqueId());
                        p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                    }
                } else if (hasHaste.contains(p.getUniqueId())) {
                    hasHaste.remove(p.getUniqueId());
                    p.removePotionEffect(PotionEffectType.FAST_DIGGING);
                }
            }
        }, 0L, 20L);
    }

    private void loadEvents() {
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new GuiListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EnchantListener(), this);
    }

    private void loadEnchantments() {
         try {
             try {
                 Field f = Enchantment.class.getDeclaredField("acceptingNew");
                 f.setAccessible(true);
                 f.set(null, true);
             } catch (IllegalAccessException e) {
                 e.printStackTrace();
             } catch (NoSuchFieldException e) {
                 e.printStackTrace();
             }
             try {
                 Enchantment.registerEnchantment(haste);
             } catch (IllegalArgumentException e){
                 e.printStackTrace();
             }
         } catch (Exception e) {
             e.printStackTrace();
         }
    }

    private boolean setupEconomy()
    {
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    @Override
    public void onDisable() {
        try {
            Field byIdField = Enchantment.class.getDeclaredField("byId");
            Field byNameField = Enchantment.class.getDeclaredField("byName");
            byIdField.setAccessible(true);
            byNameField.setAccessible(true);
            HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
            HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);
            byId.remove(haste.getId());
            byName.remove(haste.getName());
        } catch (Exception ignored) {
        }
        hasHaste.clear();
        main = null;
    }
}
