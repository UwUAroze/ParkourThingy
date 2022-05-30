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

import static me.aroze.parkourthingy.TestGenerate.playingParkour;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (!(playingParkour.contains(e.getPlayer()))) return;

        ArrayList<Block> blocksBelow = getNonAirBlocksBelow(e.getPlayer().getLocation());

            double playerY = e.getPlayer().getLocation().getY();
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

            Block newNextJump = nextNextJump;
            Block newNextNextJump = nextNextNextJump;
            Block newNextNextNextJump = newNextNextJump.getLocation().add(randInt(2,3),randInt(-1,1),randInt(2,3)).getBlock();


            TestGenerate.parkourLastJump.put(e.getPlayer(), nextJump);
            TestGenerate.parkourNextJump.put(e.getPlayer(), newNextJump);
            TestGenerate.parkourNextNext.put(e.getPlayer(), newNextNextJump);
            TestGenerate.parkourNextNextNext.put(e.getPlayer(), newNextNextNextJump);

            nextJump.setType(Material.GRAY_CONCRETE);

            if ((TestGenerate.parkourJumps.get(e.getPlayer()) + 3) % 15 == 0) {
                newNextNextNextJump.setType(Material.LIGHT_BLUE_CONCRETE);
                return;
            }
            newNextNextNextJump.setType(Material.PINK_CONCRETE);


    }

    public static int randInt(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }

    public ArrayList<Block> getBlocksBelow(Location loc) {

        ArrayList<Block> blocksBelow = new ArrayList<>();

        Location blockBelow = loc.add(0, -1, 0);

        blocksBelow.add(blockBelow.getBlock());
        blocksBelow.add(blockBelow.clone().add(0,0,0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(0,0,-0.3).getBlock());

        blocksBelow.add(blockBelow.clone().add(0.3,0,0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(0.3,0,-0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3,0,0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3,0,-0.3).getBlock());
        blocksBelow.add(blockBelow.clone().add(-0.3,0,0.3).getBlock());

        return blocksBelow;
    }

    public ArrayList<Block> getNonAirBlocksBelow(Location loc) {

        ArrayList<Block> blocksBelow = getBlocksBelow(loc);
        ArrayList<Block> valuesToRemove = new ArrayList<>();

        for (Block block : blocksBelow) {
            if (block.getType() == org.bukkit.Material.AIR) {
                valuesToRemove.add(block);
            }
        }

        blocksBelow.removeAll(valuesToRemove);
        return blocksBelow;
    }


}
