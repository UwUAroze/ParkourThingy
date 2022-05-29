package me.aroze.parkourthingy;

import jdk.incubator.vector.VectorOperators;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (!e.getPlayer().hasPermission("parkourthingy.admin")) return;
        if (!e.getPlayer().isOnGround()) return;

        ArrayList<Location> blocksBelow = getNonAirBlocksBelow(e.getPlayer().getLocation());



        for (Location block : blocksBelow) {
            if (TestGenerate.parkourNextJump.get(e.getPlayer()) == block) {

                block.getBlock().setType(Material.DIRT);
                TestGenerate.parkourLastJump.put(e.getPlayer(), block);

                Location nextJump = block.clone().add(2,0,0);
                TestGenerate.parkourNextJump.put(e.getPlayer(), nextJump);
                nextJump.getBlock().setType(Material.WHITE_CONCRETE);


            }
        }

    }

    public ArrayList<Location> getBlocksBelow(Location loc) {

        ArrayList<Location> blocksBelow = new ArrayList<Location>();

        Location blockBelow = loc.add(0, -1, 0);

        blocksBelow.add(blockBelow);
        blocksBelow.add(blockBelow.clone().add(0,0,1));
        blocksBelow.add(blockBelow.clone().add(0,0,-1));

        blocksBelow.add(blockBelow.clone().add(1,0,1));
        blocksBelow.add(blockBelow.clone().add(1,0,-1));
        blocksBelow.add(blockBelow.clone().add(1,0,0));

        blocksBelow.add(blockBelow.clone().add(-1,0,1));
        blocksBelow.add(blockBelow.clone().add(-1,0,-1));
        blocksBelow.add(blockBelow.clone().add(-1,0,0));

        return blocksBelow;
    }

    public ArrayList<Location> getNonAirBlocksBelow(Location loc) {

        ArrayList<Location> blocksBelow = getBlocksBelow(loc);
        for (Location block : blocksBelow) {
            if (block.getBlock().getType().equals(org.bukkit.Material.AIR)) {
                blocksBelow.remove(block);
            }
        }

        return blocksBelow;
    }


}
