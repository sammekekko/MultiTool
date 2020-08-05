package me.neuriong.multitool.tabcomplete;

import com.google.common.collect.Lists;
import me.neuriong.multitool.MultiTool;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class MultiToolTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> arguments = new java.util.ArrayList<>(Collections.singletonList("give"));
        if (MultiTool.main.getConfig().getBoolean("upgrade_menu_enabled")) {
            arguments.add("upgrade");
        }
        List<String> flist = Lists.newArrayList();
        if (args.length == 1) {
            for (String s : arguments) {
                if (s.toLowerCase().startsWith(args[0].toLowerCase()))flist.add(s);
            }
            return flist;
        }
        return null;
    }
}
