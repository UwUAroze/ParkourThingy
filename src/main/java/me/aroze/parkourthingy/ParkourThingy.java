package me.aroze.parkourthingy;

import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class ParkourThingy extends JavaPlugin {

    public static List<Material> blockPallet = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        blockPallet.add(Material.BLACK_GLAZED_TERRACOTTA);
        blockPallet.add(Material.BLUE_GLAZED_TERRACOTTA);
        blockPallet.add(Material.BROWN_GLAZED_TERRACOTTA);
        blockPallet.add(Material.CYAN_GLAZED_TERRACOTTA);
        blockPallet.add(Material.GRAY_GLAZED_TERRACOTTA);
        blockPallet.add(Material.GREEN_GLAZED_TERRACOTTA);
        blockPallet.add(Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        blockPallet.add(Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        blockPallet.add(Material.LIME_GLAZED_TERRACOTTA);
        blockPallet.add(Material.MAGENTA_GLAZED_TERRACOTTA);
        blockPallet.add(Material.ORANGE_GLAZED_TERRACOTTA);
        blockPallet.add(Material.PINK_GLAZED_TERRACOTTA);
        blockPallet.add(Material.PURPLE_GLAZED_TERRACOTTA);
        blockPallet.add(Material.RED_GLAZED_TERRACOTTA);
        blockPallet.add(Material.WHITE_GLAZED_TERRACOTTA);
        blockPallet.add(Material.YELLOW_GLAZED_TERRACOTTA);

        addCommand("testgenerate", new TestGenerate());
        addListener(new MoveListener());

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

    public static ParkourThingy getInstance() {
        return getPlugin(ParkourThingy.class);
    }


}
