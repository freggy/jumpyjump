package de.bergwerklabs.jumpyjump.core.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Yannic Rieger on 05.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerJoinListener implements Listener {

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        player.setLevel(0);
        player.setExp(0);
        player.getInventory().clear();
    }

}
