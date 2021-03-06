/*
  Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

    None.
just the license. same to none
*/
package me.spike.mymoreuselessplugin;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class MyMoreUselessPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        System.out.println("[UselessPlugin] Plugin has started successfully.");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        System.out.println("[UselessPlugin] Deactivating...");
        System.out.println("[UselessPlugin] Goodbye! :)");
    }

    @EventHandler
    public void onSleep(PlayerBedLeaveEvent event) {
        Player sleeper = event.getPlayer();
        World world = sleeper.getWorld();
        if (world.getTime() >= 13000 && world.getTime() <= 23000) {
            world.setTime(23000);
            Bukkit.broadcastMessage(ChatColor.GOLD + sleeper.getName() + ChatColor.YELLOW + " đã ngủ. Chúc ngủ ngon!");
        }
    }

    @EventHandler
    public void onOpenInv(InventoryOpenEvent event) {
        Player victim = (Player) event.getPlayer();
        if (victim.getGameMode() == GameMode.ADVENTURE) {
            victim.setHealth(0);
            Bukkit.broadcastMessage(ChatColor.RED + victim.getName() + " đã không làm gì cả nên chúng tôi giết anh ta cho vui.");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String cmd = command.getName().toUpperCase(Locale.ROOT);
        if (cmd.equals("BLOW")) {
            try {
                blow(sender, args);
            } catch (Exception x) {
                ExceptionHandler(x, "BlowCommand", (Player) sender);
            }

        }
        if (cmd.equals("VODAU")) {
            try {
                crackHead(sender, args);
            } catch (Exception wtf) {
                ExceptionHandler(wtf, "CrackHeadCommand", (Player) sender);
            }
        }
        if (cmd.equals("MUP")) {
            try {
                    sender.sendMessage(ChatColor.GREEN + "[UselessPlugin] Debug mode started. -- MORE DEBUG.");
                    sender.sendMessage(ChatColor.GREEN + "[UselessPlugin] This mode is for the idiot who wants to debug his bullshit plugin.");
                    sender.sendMessage(ChatColor.GREEN + "[UselessPlugin] Debug Info:");
                    sender.sendMessage(ChatColor.GOLD + "Sender: " + sender.getName() + ", command: " + command.getName() + ", label: " + label);
                    sender.sendMessage(ChatColor.GOLD + "String [] args Array Value:");
                    for (String a : args) {
                        sender.sendMessage(a);
                    }
                    sender.sendMessage("Report Complete.");
            } catch (Exception z) {
                ExceptionHandler(z, "DEBUG", (Player) sender);
            }
        }
        if (cmd.equals("TRAP")) {
            try {
                trap(sender, args);
            } catch (Exception x) {
                ExceptionHandler(x, "TrapCommand", (Player) sender);
            }
        }

        return true;
    }

    public static void ExceptionHandler(Exception x, String pos, Player sender) {
        System.out.println("[UselessPlugin] " + ChatColor.RED + "An error occurred at " + pos);
        System.out.println("[UselessPlugin] " + ChatColor.YELLOW + x);
        sender.sendMessage(ChatColor.RED + "An error occurred: " + pos + x);
    }

    public static void blow(CommandSender sender, String [] a) {
        Player victim;
        if (!noArgs(a)) {
            victim = NameToPlayer(a[0]);

        } else {
            victim = (Player) sender;
        }
        victim.getWorld().spawnEntity(victim.getLocation(), EntityType.PRIMED_TNT);
    }

    public static void crackHead(CommandSender sender, String [] a) {
        Player victim;
        if (noArgs(a)) {
            victim = (Player) sender;
        } else {
            victim = NameToPlayer(a[0]);
        }
        if (
                victim.getWorld().getBlockAt(victim.getLocation().add(0,5,0)).isPassable() &&   //summoning falling block straightaway doesn't work at all
                victim.getWorld().getBlockAt(victim.getLocation().add(0,4,0)).isPassable() &&   //so, block - anvil contraption
                victim.getWorld().getBlockAt(victim.getLocation().add(0,3,0)).isPassable()      //hope this works
        ) {
            Location location = victim.getLocation().add(0,5,0);
            location.add(0,-1,0).getBlock().setType(Material.BEDROCK);
            location.getBlock().setType(Material.ANVIL,true);
            location.add(0,-1,0).getBlock().setType(Material.AIR,true);
        } else {
            sender.sendMessage(ChatColor.RED + "Chỗ này không đủ thông thoáng. Chỗ nào cao 5 block ấy!");
        }

    }

    public static void trap(CommandSender sender, String[] a) {
        Player victim;
        if (noArgs(a)) {
            victim = (Player) sender;
        } else {
            victim = NameToPlayer(a[0]);
        }
        Location victimFeet = victim.getLocation();
        Material BlockOccupied = victimFeet.add(0,-1,0).getBlock().getType();
        System.out.println(victim.getName() + "has been trapped by " + sender + " at " + victimFeet);
        victimFeet.add(0,-1,0).getBlock().setType(Material.BEDROCK);
        victimFeet.add(1, 0, 0).getBlock().setType(Material.BEDROCK);
        victimFeet.add(-1, 0, 0).getBlock().setType(Material.BEDROCK);
        victimFeet.add(0, 0, 1).getBlock().setType(Material.BEDROCK);
        victimFeet.add(0, 0, -1).getBlock().setType(Material.BEDROCK);
        victimFeet.add(0, 2, 0).getBlock().setType(Material.BEDROCK);   //TRAP VICTIM IN BEDROCK HOLE
        victim.sendMessage(ChatColor.RED + "Bạn đã bị bẫy trong bedrock bởi " + sender);
        for (int i = 0; i <= 5; i++) {
            victim.sendMessage(ChatColor.RED + "Bạn sẽ được thả sau" + i + "s");
            pause(1000);
        }
        victimFeet.add(0,-1,0).getBlock().setType(BlockOccupied);

    }
    public static Player NameToPlayer(String name) {
        Player victim = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(name)) {
                victim = player;
            }
        }
        return victim;
    }

    public static boolean noArgs(String[] a) {
        boolean hasNoArgs = false;
        try {
            Player player1 = NameToPlayer(a[0]);
        } catch (Exception x) {     //Exception happens when no parameters is used
            hasNoArgs = true;
        }
        return hasNoArgs;
    }

    public static void pause(long time) {
        long a = System.currentTimeMillis();
        while (a >= System.currentTimeMillis() - time);    //do nothing and wait ms.
    }
}
