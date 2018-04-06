package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class InventoryCloseListener implements Listener {

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        MapSelectSession.SESSIONS.remove(event.getPlayer().getUniqueId());
    }

}
