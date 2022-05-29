package me.aroze.parkourthingy;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class TestGenerate implements CommandExecutor {

    HashMap<Player, Location> parkourLastJump = new HashMap<>();
    HashMap<Player, Location> parkourCurrentJump = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        Location startingBlock = player.getLocation().add(0, -1, 0);
        player.sendMessage("Starting block: " + startingBlock.getBlockX() + " " + startingBlock.getBlockY() + " " + startingBlock.getBlockZ());

        parkourCurrentJump.put(player, startingBlock);


        return true;
    }
}
