package me.aroze.parkourthingy;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        if (e.getPlayer().hasPermission("parkourthingy.admin")) {

            //

        }

    }

}
