package me.aroze.parkourthingy;

import me.aroze.parkourthingy.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static me.aroze.parkourthingy.ParkourThingy.blockPallet;
import static me.aroze.parkourthingy.TestGenerate.playingParkour;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!(playingParkour.contains(e.getPlayer())) || e.getTo() == null) return;

        double playerY = e.getTo().getY(); // player.getLocation is inaccurate.
        int nextJumpY = TestGenerate.parkourNextJump.get(e.getPlayer()).getY();
        int lastJumpY = TestGenerate.parkourLastJump.get(e.getPlayer()).getY();

        List<Block> blocksBelow = getNonAirBlocksBelow(e.getPlayer().getLocation());

        Block nextJump = TestGenerate.parkourNextJump.get(e.getPlayer());
        Block nextNextJump = TestGenerate.parkourNextNext.get(e.getPlayer());
        Block nextNextNextJump = TestGenerate.parkourNextNextNext.get(e.getPlayer());

        if (playerY < nextJumpY + 1 && playerY < lastJumpY + 1) {
            Bukkit.broadcastMessage(ChatUtils.color("&7You fell after &c" + TestGenerate.parkourJumps.get(e.getPlayer()) + " &7jumps!"));
            playingParkour.remove(e.getPlayer());
            TestGenerate.parkourJumps.remove(e.getPlayer());
            nextJump.setType(Material.RED_CONCRETE);
            nextNextJump.setType(Material.AIR);
            nextNextNextJump.setType(Material.AIR);
            return;
        }

        if (!(blocksBelow.contains(TestGenerate.parkourNextJump.get(e.getPlayer())))) return;

        generateNewBlock(e.getPlayer());

    }

    public static void generateNewBlock(Player player) {

        Block nextJump = TestGenerate.parkourNextJump.get(player);
        Block nextNextJump = TestGenerate.parkourNextNext.get(player);
        Block nextNextNextJump = TestGenerate.parkourNextNextNext.get(player);

        TestGenerate.parkourJumps.put(player, TestGenerate.parkourJumps.get(player) + 1);

        //Pathfinding

        Map<Integer,Integer> maxZ = new HashMap<>();
        Map<Integer,Integer> maxX = new HashMap<>();

        //Default values (x, z)
        maxZ.put(0, 0);
        maxZ.put(1, 0);
        maxZ.put(2, 0);
        maxZ.put(3, 0);
        maxZ.put(4, 0);
        maxZ.put(5, 0);

        //Default values (z, x)
        maxX.put(0, 0);
        maxX.put(1, 0);
        maxX.put(2, 0);
        maxX.put(3, 0);
        maxX.put(4, 0);
        maxX.put(5, 0);

        //Max Z from X
        for (int x = 0; x <= 5; x++) {
            zAxis:
            for (int z = 0; z <= 5; z++) {
                if (x == 0 && z == 0) continue zAxis; // Skip the starting block.
                if (nextNextNextJump.getLocation().add(x,0,z).getBlock().getType() == Material.AIR) { //Checking if block is air
                    if (nextNextNextJump.getLocation().add(x, 1, z).getBlock().getType() == Material.AIR) { //Checking if block above is air (so player can fit)
                        if (nextNextNextJump.getLocation().add(x, 2, z).getBlock().getType() == Material.AIR) { //Checking if block 2 above is air (so player can fit)
                            maxZ.put(x, z); // if all three are air, set the max distance to this block.
                            continue zAxis; // continue to the next block.
                        } break zAxis; // if the block 2 above is not air, break the loop.
                    } break zAxis; // if the block above is not air, break the loop.
                } break zAxis; // if the block is not air, break the loop.
            }
        }

        //Max X from Z
        for (int z = 0; z <= 5; z++) {
            xAxis:
            for (int x = 0; x <= 5; x++) {
                if (x == 0 && z == 0) continue xAxis; // Skip the starting block.
                if (nextNextNextJump.getLocation().add(x,0,z).getBlock().getType() == Material.AIR) { //Checking if block is air
                    if (nextNextNextJump.getLocation().add(x, 1, z).getBlock().getType() == Material.AIR) { //Checking if block above is air (so player can fit)
                        if (nextNextNextJump.getLocation().add(x, 2, z).getBlock().getType() == Material.AIR) { //Checking if block 2 above is air (so player can fit)
                            maxX.put(z, x); // if all three are air, set the max distance to this block.
                            continue xAxis; // continue to the next block.
                        } break xAxis; // if the block 2 above is not air, break the loop.
                    } break xAxis; // if the block above is not air, break the loop.
                } break xAxis; // if the block is not air, break the loop.
            }
        }

        Block newNextNextNextJump = null;

        int lowestMaxX = 5;
        for (int max : maxX.values()) {
            player.sendMessage(max + "");
            lowestMaxX = Math.min(lowestMaxX, max);
        }

        int addX = randInt(0, maxX.get(0));
        int addY = randInt(0, 1);
        int addZ = randInt(0,maxZ.get(addX));

        // Patches for hard/impossible jumps

        if (addX >= 4 || addZ >= 4) addY = -1;

        if (addX == 5) addZ = 0;
        if (addZ == 5) addX = 0;

        if (addX == 0 && addZ == 0) {
            if (maxZ.get(0) >= 1) addZ = 1;
            if (maxX.get(0) >= 1) addX = 1;
        }

        if (addX == 1) {
            if (maxX.get(addZ) > 1) addX++;
        }

        if (addZ == 1) {
            if (maxZ.get(addX) > 1) addZ++;
        }

        newNextNextNextJump = nextNextNextJump.getLocation().add(addX, addY, addZ).getBlock();


        TestGenerate.parkourLastJump.put(player, nextJump);
        TestGenerate.parkourNextJump.put(player, nextNextJump); // New next jump
        TestGenerate.parkourNextNext.put(player, nextNextNextJump); // New next next jump
        TestGenerate.parkourNextNextNext.put(player, newNextNextNextJump);

        if (blockPallet.contains(nextJump.getType())) nextJump.setType(Material.GRAY_CONCRETE);

        if ((TestGenerate.parkourJumps.get(player) + 3) % 20 == 0) {
            newNextNextNextJump.setType(Material.PINK_CONCRETE);
            return;
        }

        newNextNextNextJump.setType(blockPallet.get(randInt(0, blockPallet.size() - 1)));
        player.playSound(player.getLocation(), Sound.BLOCK_WET_GRASS_PLACE, 1, 1);


    }

    public static int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public List<Block> getBlocksBelow(Location loc) {

        List<Block> blocksBelow = new ArrayList<>();

        Location blockBelow = loc.add(0, -1, 0);

        blocksBelow.add(blockBelow.getBlock());
        blocksBelow.add(blockBelow.clone().add(0, 0, 0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(0, 0, -0.3).getBlock());

        blocksBelow.add(blockBelow.clone().add(0.3, 0, 0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(0.3, 0, -0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3, 0, 0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3, 0, -0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3, 0, 0.3).getBlock());

        return blocksBelow;
    }

    public List<Block> getNonAirBlocksBelow(Location loc) {
        List<Block> blocksBelow = getBlocksBelow(loc);
        List<Block> valuesToRemove = new ArrayList<>();

        for (Block block : blocksBelow) {
            if (block.getType() == org.bukkit.Material.AIR) {
                valuesToRemove.add(block);
            }
        }

        blocksBelow.removeAll(valuesToRemove);
        return blocksBelow;
    }


}
