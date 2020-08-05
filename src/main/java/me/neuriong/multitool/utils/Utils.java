package me.neuriong.multitool.utils;

import org.bukkit.ChatColor;

public class Utils {
    public static String chat(String s) {
        if (s != null) {
            return ChatColor.translateAlternateColorCodes('&', s);
        }
        return ChatColor.RED + "Something went wrong!";
    }
}
