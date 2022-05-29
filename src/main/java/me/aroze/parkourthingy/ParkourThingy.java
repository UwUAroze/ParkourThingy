package me.aroze.parkourthingy;

import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ParkourThingy extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void addCommand(String name, CommandExecutor executor) {
        getCommand(name).setExecutor(executor);
    }

    private void addListener(Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }


}
