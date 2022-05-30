package me.aroze.parkourthingy;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static me.aroze.parkourthingy.MoveListener.randInt;

public class TestGenerate implements CommandExecutor {

    public static HashMap<Player, Block> parkourLastJump = new HashMap<>();
    public static HashMap<Player, Block> parkourNextJump = new HashMap<>();
    public static HashMap<Player, Block> parkourNextNext = new HashMap<>();
    public static HashMap<Player, Block> parkourNextNextNext = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        Block startingBlock = player.getLocation().add(0, -1, 0).getBlock();
        startingBlock.setType(Material.WHITE_CONCRETE);
        player.sendMessage("Starting block: " + startingBlock.getX() + " " + startingBlock.getY() + " " + startingBlock.getZ());

        parkourNextJump.put(player, startingBlock);
        parkourNextNext.put(player, startingBlock);
        parkourNextNextNext.put(player, startingBlock);

        Block nextJump = startingBlock.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextJump = nextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextNextJump = nextNextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();

        parkourNextJump.put(player, nextJump);
        parkourNextNext.put(player, nextNextJump);
        parkourNextNextNext.put(player, nextNextNextJump);

        nextJump.setType(Material.PINK_CONCRETE);
        nextNextJump.setType(Material.MAGENTA_CONCRETE);
        nextNextNextJump.setType(Material.PURPLE_CONCRETE);

        return true;
    }
}
