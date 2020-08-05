package me.neuriong.multitool.commands;

import me.neuriong.multitool.MultiTool;
import me.neuriong.multitool.item.Item;
import me.neuriong.multitool.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class multitool implements CommandExecutor {

    private MultiTool main;
    private Item h = new Item();

    public multitool(MultiTool main) {
        this.main = main;
    }

    public void createUpgradeMenu(Player p) {
        Inventory iv = Bukkit.createInventory(null, main.getConfig().getInt("gui.size"), Utils.chat(main.getConfig().getString("gui.title")));

        /*
        - - - - - - - - -
        - - - - P - - - -
        - - G - E - U - -
        - - - - - - - - -
        G = Golden Ingot (Fortune + 1)
        E = Diamond Pick (Efficiency + 1)
        U = Diamond Armor (Unbreaking + 1)
        P = Preview
         */

        ItemStack fortune = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta fortuneMeta = fortune.getItemMeta();
        fortuneMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.fortune.name")));
        fortuneMeta.setLore(getFortuneLore(p.getItemInHand()));
        fortuneMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        fortune.setItemMeta(fortuneMeta);

        ItemStack efficiency = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta efficiencyMeta = efficiency.getItemMeta();
        efficiencyMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.efficiency.name")));
        efficiencyMeta.setLore(getEfficiencyLore(p.getItemInHand()));
        efficiencyMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        efficiency.setItemMeta(efficiencyMeta);

        ItemStack unbreaking = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta unbreakingMeta = unbreaking.getItemMeta();
        unbreakingMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.unbreaking.name")));
        unbreakingMeta.setLore(getUnbreakingLore(p.getItemInHand()));
        unbreakingMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        unbreaking.setItemMeta(unbreakingMeta);

        ItemStack haste = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta hasteMeta = haste.getItemMeta();
        hasteMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.haste.name")));
        hasteMeta.setLore(getHasteLore(p.getItemInHand()));
        hasteMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        haste.setItemMeta(hasteMeta);

        ItemStack repair = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta repairMeta = repair.getItemMeta();
        repairMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.repair.name")));
        repairMeta.setLore(getRepairLore(p.getItemInHand()));
        repairMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        repair.setItemMeta(repairMeta);


        ItemStack side1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.YELLOW.getData());
        ItemMeta side1Meta = side1.getItemMeta();
        side1Meta.setDisplayName(" ");
        side1Meta.setLore(null);
        side1Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        side1.setItemMeta(side1Meta);

        ItemStack side = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.GRAY.getData());
        ItemMeta sideMeta = side.getItemMeta();
        sideMeta.setDisplayName(" ");
        sideMeta.setLore(null);
        sideMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        side.setItemMeta(sideMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.setDisplayName(Utils.chat(main.getConfig().getString("gui.exit.name")));
        exitMeta.setLore(getExitLore());
        exit.setItemMeta(exitMeta);

        iv.setItem(0, side1);
        iv.setItem(8, side1);
        iv.setItem(9, side1);
        iv.setItem(17, side1);
        iv.setItem(18, side1);
        iv.setItem(26, side1);

        iv.setItem(1, side);
        iv.setItem(2, side);
        iv.setItem(3, side);
        iv.setItem(4, side);
        iv.setItem(5, side);
        iv.setItem(6, side);
        iv.setItem(7, side);
        iv.setItem(10, side);
        iv.setItem(16, side);
        iv.setItem(19, side);
        iv.setItem(20, side);
        iv.setItem(21, side);
        iv.setItem(23, side);
        iv.setItem(24, side);
        iv.setItem(25, side);


        iv.setItem(11, fortune);
        iv.setItem(12, efficiency);
        iv.setItem(13, unbreaking);
        iv.setItem(14, haste);
        iv.setItem(15, repair);

        iv.setItem(22, exit);

        p.openInventory(iv);
    }

    private List<String> getExitLore() {
        List<String> newLore = new ArrayList<>();
        for (String s : main.getConfig().getStringList("gui.exit.lores")) {
            newLore.add(Utils.chat(s));
        }
        return newLore;
    }

    private List<String> getRepairLore(ItemStack i) {
        List<String> newLore = new ArrayList<>();
        for (String loreString : main.getConfig().getStringList("gui.repair.lores")) {
            if (loreString.contains("<price>")) {
                int cost = main.getConfig().getInt("gui.repair.cost");
                loreString = loreString.replace("<price>", Integer.toString(cost));
            }
            newLore.add(Utils.chat(loreString));
        }
        return newLore;
    }

    private List<String> getUnbreakingLore(ItemStack itemStack) {
        List<String> lore = main.getConfig().getStringList("gui.unbreaking.lores");
        List<String> newLore = new ArrayList<>();
        int level = 0;
        for (String f : lore) {
            if (f.contains("<level>")) {
                if (itemStack.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                    level = itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
                }
                f = f.replace("<level>", Integer.toString(level));
            } else if (f.contains("<price>")) {
                int cost = main.getConfig().getInt("gui.unbreaking.cost");
                int newLevel = itemStack.getEnchantmentLevel(Enchantment.DURABILITY);
                if (newLevel == Enchantment.DURABILITY.getMaxLevel()) {
                    f = f.replace("<price>", ChatColor.GOLD + "" + ChatColor.BOLD + "MAX");
                } else {
                    f = f.replace("<price>", Integer.toString(cost * (itemStack.getEnchantmentLevel(Enchantment.DURABILITY) + 1) * (itemStack.getEnchantmentLevel(Enchantment.DURABILITY) + 1)));
                }
            }
            newLore.add(Utils.chat(ChatColor.GRAY + f));
        }
        return newLore;
    }

    private List<String> getHasteLore(ItemStack i) {
        List<String> lore = main.getConfig().getStringList("gui.haste.lores");
        List<String> newLore = new ArrayList<>();
        int level = 0;
        for (String f : lore) {
            if (f.contains("<level>")) {
                if (i.getEnchantments().containsKey(Enchantment.getByName("Haste"))) {
                    level = i.getEnchantmentLevel(Enchantment.getByName("Haste"));
                }
                f = f.replace("<level>", Integer.toString(level));
            } else if (f.contains("<price>")) {
                int cost = main.getConfig().getInt("gui.haste.cost");
                int newLevel = i.getEnchantmentLevel(Enchantment.getByName("Haste"));
                if (newLevel == Enchantment.getByName("Haste").getMaxLevel()) {
                    f = f.replace("<price>", ChatColor.GOLD + "" + ChatColor.BOLD + "MAX");
                } else {
                    f = f.replace("<price>", Integer.toString(cost * (i.getEnchantmentLevel(Enchantment.getByName("Haste")) + 1) * (i.getEnchantmentLevel(Enchantment.getByName("Haste")) + 1)));
                }
            }
            newLore.add(Utils.chat(ChatColor.GRAY + f));
        }
        return newLore;
    }

    private List<String> getEfficiencyLore(ItemStack i) {
        List<String> lore = main.getConfig().getStringList("gui.efficiency.lores");
        List<String> newLore = new ArrayList<>();
        int level = 0;
        for (String f : lore) {
            if (f.contains("<level>")) {
                if (i.getEnchantments().containsKey(Enchantment.DIG_SPEED)) {
                    level = i.getEnchantmentLevel(Enchantment.DIG_SPEED);
                }
                f = f.replace("<level>", Integer.toString(level));
            } else if (f.contains("<price>")) {
                int cost = main.getConfig().getInt("gui.efficiency.cost");
                int newLevel = i.getEnchantmentLevel(Enchantment.DIG_SPEED);
                if (newLevel == Enchantment.DIG_SPEED.getMaxLevel()) {
                    f = f.replace("<price>", ChatColor.GOLD + "" + ChatColor.BOLD + "MAX");
                } else {
                    f = f.replace("<price>", Integer.toString(cost * (i.getEnchantmentLevel(Enchantment.DIG_SPEED) + 1) * (i.getEnchantmentLevel(Enchantment.DIG_SPEED) + 1)));
                }
            }
            newLore.add(Utils.chat(ChatColor.GRAY + f));
        }
        return newLore;
    }

    private List<String> getFortuneLore(ItemStack i) {
        List<String> lore = main.getConfig().getStringList("gui.fortune.lores");
        List<String> newLore = new ArrayList<>();
        int level = 0;
        for (String f : lore) {
            if (f.contains("<level>")) {
                if (i.getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
                    level = i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                }
                f = f.replace("<level>", Integer.toString(level));
            } else if (f.contains("<price>")) {
                int cost = main.getConfig().getInt("gui.fortune.cost");
                int newLevel = i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
                if (newLevel == Enchantment.LOOT_BONUS_BLOCKS.getMaxLevel()) {
                    f = f.replace("<price>", ChatColor.GOLD + "" + ChatColor.BOLD + "MAX");
                } else {
                    f = f.replace("<price>", Integer.toString(cost * (i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1) * (i.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) + 1)));
                }
            }
            newLore.add(Utils.chat(ChatColor.GRAY + f));
        }
        return newLore;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        Item i = new Item();
        ItemStack tool = i.getMultiTool();

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 2) {
                if (p.hasPermission(main.getConfig().getString("permission_give"))) {
                    if (args[0].equalsIgnoreCase("give")) {
                        give(sender, args, tool);
                    } else {
                        p.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
                        return false;
                    }
                } else {
                    p.sendMessage(Utils.chat(main.getConfig().getString("no_permission_message")));
                    return false;
                }
            } else if (args.length == 1) {
                if (main.getConfig().getBoolean("upgrade_menu_enabled")) {
                    if (p.hasPermission(main.getConfig().getString("permission_upgrade"))) {
                        if (args[0].equalsIgnoreCase("upgrade")) {
                            upgrade(p);
                        } else {
                            p.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
                            return false;
                        }
                    } else {
                        p.sendMessage(Utils.chat(main.getConfig().getString("no_permission_message")));
                        return false;
                    }
                } else {
                    p.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
                    return false;
                }
            } else {
                p.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
                return false;
            }
        } else {
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("give")) {
                    give(sender, args, tool);
                } else {
                    sender.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
                }
            } else {
                sender.sendMessage(Utils.chat(main.getConfig().getString("invalid_arguments_message")));
            }
        }
        return false;
    }


    private void upgrade(Player plr) {
        if (plr.getItemInHand() != null && plr.getItemInHand().getType() != Material.AIR) {
            if (h.compare(plr.getItemInHand().getItemMeta().getLore(), h.getLore())) {
                createUpgradeMenu(plr);
            } else {
                plr.sendMessage(Utils.chat(main.getConfig().getString("no_item_message")));
            }
        } else {
            plr.sendMessage(Utils.chat(main.getConfig().getString("no_item_message")));
        }
    }

    private void give(CommandSender p, String[] args, ItemStack tool) {
        if (Bukkit.getPlayerExact(args[1]) != null) {
            Player target = Bukkit.getPlayerExact(args[1]);
            Inventory targetInv = target.getInventory();
            if (targetInv.firstEmpty() != -1) {
                p.sendMessage(Utils.chat(main.getConfig().getString("give_message").replace("<player>", target.getDisplayName())));
                targetInv.addItem(tool);
            } else {
                p.sendMessage(Utils.chat(main.getConfig().getString("inventory_full_message")));
            }
        } else {
            p.sendMessage(Utils.chat(main.getConfig().getString("player_invalid_message").replace("<player>", args[1])));
        }
    }
}
