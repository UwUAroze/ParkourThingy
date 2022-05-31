package me.aroze.parkourthingy;

import me.aroze.parkourthingy.util.ChatUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.aroze.parkourthingy.MoveListener.randInt;
import static me.aroze.parkourthingy.ParkourThingy.blockPallet;

public class TestGenerate implements CommandExecutor {

    public static Map<Player, Block> parkourLastJump = new HashMap<>();
    public static Map<Player, Block> parkourNextJump = new HashMap<>();
    public static Map<Player, Block> parkourNextNext = new HashMap<>();
    public static Map<Player, Block> parkourNextNextNext = new HashMap<>();

    public static List<Player> playingParkour = new ArrayList<>();
    public static Map<Player, Integer> parkourJumps = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("parkourthingy.startcommand")) {
            sender.sendMessage(ChatUtils.color("&#ff6e6e⚠ &#ff7f6eYou aren't allowed to do this! smh!"));
            return true;
        }

        Player player = (Player) sender;

        if (playingParkour.contains(player)) {
            sender.sendMessage(ChatUtils.color("&#ff6e6e⚠ &#ff7f6eYou're already playing! smh!"));
            return true;
        }

        playingParkour.add(player);

        Block startingBlock = player.getLocation().add(0, -1, 0).getBlock();
        startingBlock.setType(Material.WHITE_CONCRETE);
        player.sendMessage("Starting block: " + startingBlock.getX() + " " + startingBlock.getY() + " " + startingBlock.getZ());

        parkourNextJump.put(player, startingBlock);
        parkourNextNext.put(player, startingBlock);
        parkourNextNextNext.put(player, startingBlock);

        Block nextJump = startingBlock.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextJump = nextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();
        Block nextNextNextJump = nextNextJump.getLocation().clone().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();

        parkourLastJump.put(player, startingBlock);
        parkourNextJump.put(player, nextJump);
        parkourNextNext.put(player, nextNextJump);
        parkourNextNextNext.put(player, nextNextNextJump);

        nextJump.setType(blockPallet.get(randInt(0, blockPallet.size() - 1))));
        nextNextJump.setType(blockPallet.get(randInt(0, blockPallet.size() - 1)));
        nextNextNextJump.setType(blockPallet.get(randInt(0, blockPallet.size() - 1)));

        parkourJumps.put(player, 0);

        return true;
    }
}
