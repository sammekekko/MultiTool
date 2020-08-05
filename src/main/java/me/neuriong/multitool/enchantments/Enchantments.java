package me.neuriong.multitool.enchantments;

import me.neuriong.multitool.MultiTool;
import org.bukkit.enchantments.Enchantment;

import java.util.HashMap;

public class Enchantments {

    public HashMap<Enchantment, String> hash = new HashMap<>();

    private void registerEnchantments() {
        hash.put(Enchantment.DURABILITY, "Unbreaking");
        hash.put(Enchantment.DIG_SPEED, "Efficiency");
        hash.put(Enchantment.LOOT_BONUS_BLOCKS, "Fortune");
    }

    public Enchantments() {
        registerEnchantments();
    }

}
