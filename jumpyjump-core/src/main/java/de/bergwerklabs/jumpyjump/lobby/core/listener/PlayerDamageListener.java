package de.bergwerklabs.jumpyjump.lobby.core.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Created by Yannic Rieger on 04.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerDamageListener implements Listener {

    @EventHandler
    private void onPlayerDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }
}
