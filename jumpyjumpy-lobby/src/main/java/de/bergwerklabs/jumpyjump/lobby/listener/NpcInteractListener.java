package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.util.NPCInteractEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class NpcInteractListener implements Listener {

    @EventHandler
    private void onNpcInteract(NPCInteractEvent event) {
        final String action = event.getAction();
        final Player player = event.getPlayer();
        final LobbyPlayer lobbyPlayer = Main.LOBBY_PLAYERS.get(player.getUniqueId());
        if (lobbyPlayer == null) return;
        switch (action) {
            case "INTERACT":
            case "ATTACK":
                lobbyPlayer.joinQuickJoinQueue();
                Main.MESSENGER.message("Du bist §aQuickJoin §7beigetreten.", player);
                player.playSound(player.getEyeLocation(), Sound.ITEM_PICKUP, 50, 50);
                break;
        }
    }
}
