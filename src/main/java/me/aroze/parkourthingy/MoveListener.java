package me.aroze.parkourthingy;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (e.getPlayer().hasPermission("parkourthingy.admin")) {

            ArrayList<Location> blocksBelow = getBlocksBelow(e.getPlayer().getLocation());

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


}
