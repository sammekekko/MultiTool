package me.neuriong.multitool.item;

import me.neuriong.multitool.MultiTool;
import me.neuriong.multitool.enchantments.Enchantments;
import me.neuriong.multitool.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Item {

    private HashMap<Enchantment, Integer> enchantments = new HashMap<>();

    private Enchantments enchs = new Enchantments();

    private void registerEnchantments() {
        enchantments.put(Enchantment.DIG_SPEED, 5);
        enchantments.put(Enchantment.DURABILITY, 5);
        enchantments.put(Enchantment.LOOT_BONUS_BLOCKS, 3);
    }

    public Item() {
        registerEnchantments();
    }

    public ItemStack getMultiTool() {
        ItemStack mTool = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta mMeta = mTool.getItemMeta();
        mMeta.setDisplayName(Utils.chat(MultiTool.main.getConfig().getString("toolname")));
        mMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        if (!MultiTool.main.getConfig().getBoolean("upgrade_menu_enabled")) {
            mMeta.setLore(getProcessedLore());
            mTool.setItemMeta(mMeta);
            for (Enchantment ench : enchantments.keySet()) {
                mTool.addUnsafeEnchantment(ench, enchantments.get(ench));
            }
        } else {
            mMeta.setLore(getLore());
            mTool.setItemMeta(mMeta);
        }
        return mTool;
    }

    private String getRoman(int s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s; i++) {
            sb.append("I");
        }
        return sb.toString()
                .replace("IIIII", "V")
                .replace("IIII", "IV");
    }

    private List<String> getProcessedLore() {
        final List<String> lore = getLore();
        for (Enchantment ench : enchantments.keySet()) {
            lore.add(Utils.chat(ChatColor.AQUA + enchs.hash.get(ench) + ChatColor.GRAY + " " + getRoman(enchantments.get(ench))));
        }
        lore.add(Utils.chat(ChatColor.AQUA + "Unbreakble"));
        return lore;
    }

    public List<String> getLore() {
        List<String> lore = MultiTool.main.getConfig().getStringList("lores");
        List<String> newLore = new ArrayList<>();
        for (String f : lore) {
            newLore.add(Utils.chat(ChatColor.GRAY + f));
        }
        return newLore;
    }

    public boolean compare(List<String> s, List<String> e) {
        if (s == null || e == null) {
            return false;
        }

        Collections.sort(s);
        Collections.sort(e);

        String firstS = null;
        String firstE = null;

        for (String se : s) {
            firstS = se;
            break;
        }
        for (String ee : e) {
            firstE = ee;
            break;
        }

        if (firstE == null || firstS == null) {
            return false;
        }

        return firstE.equals(firstS);
    }

}
