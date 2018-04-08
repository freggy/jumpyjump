package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerQuitListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    private void onPlayerQuit(PlayerQuitEvent event) {
        LobbyPlayer player = Main.LOBBY_PLAYERS.remove(event.getPlayer().getUniqueId());
    }
}
