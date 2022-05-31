package me.aroze.parkourthingy;

import me.aroze.parkourthingy.util.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

import static me.aroze.parkourthingy.TestGenerate.playingParkour;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!(playingParkour.contains(e.getPlayer())) || e.getTo() == null) return;

        List<Block> blocksBelow = getNonAirBlocksBelow(e.getPlayer().getLocation());

        double playerY = e.getTo().getY(); // player.getLocation is inaccurate.
        int nextJumpY = TestGenerate.parkourNextJump.get(e.getPlayer()).getY();
        int lastJumpY = TestGenerate.parkourLastJump.get(e.getPlayer()).getY();

        Block nextJump = TestGenerate.parkourNextJump.get(e.getPlayer());
        Block nextNextJump = TestGenerate.parkourNextNext.get(e.getPlayer());
        Block nextNextNextJump = TestGenerate.parkourNextNextNext.get(e.getPlayer());

        if (playerY < nextJumpY && playerY < lastJumpY) {
            Bukkit.broadcastMessage(ChatUtils.color("&7You fell after &c" + TestGenerate.parkourJumps.get(e.getPlayer()) + " &7jumps!"));
            playingParkour.remove(e.getPlayer());
            TestGenerate.parkourJumps.remove(e.getPlayer());
            nextJump.setType(Material.RED_CONCRETE);
            nextNextJump.setType(Material.AIR);
            nextNextNextJump.setType(Material.AIR);
            return;
        }

        if (!(blocksBelow.contains(TestGenerate.parkourNextJump.get(e.getPlayer())))) return;

        TestGenerate.parkourJumps.put(e.getPlayer(), TestGenerate.parkourJumps.get(e.getPlayer()) + 1);

        Block newNextNextNextJump = nextNextNextJump.getLocation().add(randInt(2, 3), randInt(-1, 1), randInt(2, 3)).getBlock();

        TestGenerate.parkourLastJump.put(e.getPlayer(), nextJump);
        TestGenerate.parkourNextJump.put(e.getPlayer(), nextNextJump); // New next jump
        TestGenerate.parkourNextNext.put(e.getPlayer(), nextNextNextJump); // New next next jump
        TestGenerate.parkourNextNextNext.put(e.getPlayer(), newNextNextNextJump);

        if (nextJump.getType() == Material.PINK_CONCRETE) nextJump.setType(Material.GRAY_CONCRETE);

        if ((TestGenerate.parkourJumps.get(e.getPlayer()) + 3) % 15 == 0) {
            newNextNextNextJump.setType(Material.LIGHT_BLUE_CONCRETE);
            return;
        }

        newNextNextNextJump.setType(Material.PINK_CONCRETE);


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
