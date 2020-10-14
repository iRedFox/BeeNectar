package me.redfox;

import org.bukkit.*;
import org.bukkit.entity.Bee;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Random;

public class BeeNectarEventListener implements Listener {
    private final Main plugin;
    private long Chance;
    private int AngerDelay;
    private int FlyDelay;

    private Random rnd = new Random();

    public BeeNectarEventListener(Main plugin) {
        this.plugin = plugin;
        //plugin.map.put("Drop-Chance", Chance);
        //plugin.map.put("AngerDelay", AngerDelay);
    }

    @EventHandler
    public void on(PlayerInteractEntityEvent evt) {
        Player player = evt.getPlayer();
        if (!(evt.getRightClicked() instanceof Bee)) return;
        if (evt.getHand() == EquipmentSlot.OFF_HAND) return;

        Bee beeObject = (Bee) evt.getRightClicked();
        if (!(beeObject.hasNectar())) return;

        Chance = plugin.getConfig().getLong("Potion.DropChance");
        AngerDelay = plugin.getConfig().getInt("Bee.AngerLastsFor");

        World beeLocation = beeObject.getLocation().getWorld();
        ItemStack playerHeldItem = player.getInventory().getItemInMainHand();

        beeLocation.playSound(beeObject.getLocation(), Sound.BLOCK_BEEHIVE_WORK, 3.0F, 1.0F);
        beeObject.setHasNectar(false);

        if (plugin.getConfig().getBoolean("Bee.Death")) {
            beeObject.setHealth(0);
        } else {
            beeObject.setAnger(AngerDelay);
        }

        playerHeldItem.setAmount(playerHeldItem.getAmount()-1);
        Material itemMaterial = player.getInventory().getItemInMainHand().getType();

        if (player.hasPermission("BeeNectar.milk") && itemMaterial == Material.GLASS_BOTTLE
                && (rnd).nextInt(100) <= Chance) {
            // create potion and drops it.
            BeeNectar beeItem = new BeeNectar(plugin);
            ItemStack potion = beeItem.createPotion();
            beeObject.getLocation().getWorld().dropItemNaturally(beeObject.getLocation(), potion);
        }

    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent evt) {
        System.out.println((plugin.getConfig().getBoolean("Potion.FlyPower")));
        if (!(plugin.getConfig().getBoolean("Potion.FlyPower"))) return;
        Player player = evt.getPlayer();

        FlyDelay = plugin.getConfig().getInt("Potion.FlyDelay");
        //System.out.println("I'm here now..");
        if (evt.getItem().getType() == Material.POTION) {
            //System.out.println("It's a potion!..");
            if (player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("§f§gBee Nectar")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&'
                        , "&eYou can fly for ")  + FlyDelay + " secs.");
                //System.out.println("It's name is BeeNectar.");
                player.setAllowFlight(true);
                player.setFlying(true);

                plugin.getServer().getScheduler().scheduleSyncDelayedTask((plugin), () -> {
                    player.setFlying(false);
                    player.setAllowFlight(false);
                }, FlyDelay * 20L);
            }
        }
    }

}