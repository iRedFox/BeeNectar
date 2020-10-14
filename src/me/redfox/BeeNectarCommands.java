package me.redfox;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import org.bukkit.inventory.ItemStack;

public class BeeNectarCommands implements CommandExecutor
{
    private final Main plugin;

    public BeeNectarCommands(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player))
        {
            System.out.println("BEENECTAR ERROR => IN-GAME COMMAND.");
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length == 1) {
            if (!(player.hasPermission("BeeNectar.admin"))) return false;
            if (args[0].equalsIgnoreCase("reload")){
                plugin.reloadConfig();
                player.sendMessage(ChatColor.RED + "-> Config reloaded.");
                return true;
            }
            if (args[0].equalsIgnoreCase("summon")){
                World world = player.getWorld();
                //The location, where you want to spawn it.
                Location playerLoc = player.getLocation();
                Location location = new Location(world, playerLoc.getX(), playerLoc.getY(), playerLoc.getZ());

                Bee beeNectar = (Bee) world.spawnEntity(location, EntityType.BEE);
                beeNectar.setHasNectar(true);
                beeNectar.setGlowing(true);

                player.sendMessage(ChatColor.RED + "-> Spawned BeeNectar.");
                return true;
            }
        }
        else
        {
            if (!(player.hasPermission("BeeNectar.potion"))) return false;

            BeeNectar beeItem = new BeeNectar(plugin);
            player.sendMessage(ChatColor.YELLOW + "-> Nectar potion added to your inventory.");

            ItemStack potion = beeItem.createPotion();
            player.getInventory().addItem(potion);
        }

        return true;
    }
}
