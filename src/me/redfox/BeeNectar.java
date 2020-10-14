package me.redfox;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class BeeNectar
{
    private final Main plugin;
    private long PotionDelay;
    private String PotionEffect;

    public BeeNectar(Main plugin) {
        this.plugin = plugin;

    }

    public ItemStack createPotion() {
        ItemStack item = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        PotionEffect = plugin.getConfig().getString("Potion.EffectType");
        PotionDelay = plugin.getConfig().getLong("Potion.EffectDelay");

        meta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(PotionEffect), (int) (PotionDelay * 20L), 2), true);

        meta.setColor(Color.WHITE);

        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_POTION_EFFECTS});
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_PLACED_ON});

        List<String> loreList = new ArrayList<>();
        loreList.add("BzzZzZzZ zz.");
        loreList.add("Obtained from pure nectar.");

        meta.setLore(loreList);
        meta.setDisplayName("§f§gBee Nectar");

        item.setItemMeta(meta);

        return item;
    }
}
