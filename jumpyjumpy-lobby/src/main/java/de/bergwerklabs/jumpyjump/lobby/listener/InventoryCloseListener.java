package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import session.MapSession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.UUID;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class InventoryCloseListener implements Listener {

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {
        final Player player = (Player)event.getPlayer();
        final LobbyPlayer lobbyPlayer = Main.LOBBY_PLAYERS.get(player.getUniqueId());
        MapSession session = lobbyPlayer.getMapSession();
        if (session == null) return;

        // If session.getOpenInventory().getTitle() equals the title of the inventory that has been closed,
        // it means that the player closed the current inventory. This check needs to be done because if we open a
        // different inventory to the player, the currently open one will be closed and this event will be fired.
        // That's why we can't just set them to null because then the map session would be
        // removed every time.
        if (!session.getOpenInventory().getTitle().equals(event.getInventory().getTitle())) return;

        // Since they requested a server do not remove the session.
        if (session.getRequestedMapId() != null) return;
        session.close();
    }
}
