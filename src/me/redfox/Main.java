package me.redfox;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin
{
    public static Main instance;

    private File file;

    public void onEnable()
    {
        instance = this;
        createConfig();

        YamlConfiguration.loadConfiguration(file);

        this.getCommand("BeeNectar").setExecutor(new BeeNectarCommands(this));
        getServer().getPluginManager().registerEvents(new BeeNectarEventListener(this), this);
    }

    public void onDisable()
    {
        System.out.println("DISABLED.");
        this.saveDefaultConfig();
    }
    private void createConfig() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                getLogger().info("Config.yml not found, creating!");
                saveDefaultConfig();
            } else {
                getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
