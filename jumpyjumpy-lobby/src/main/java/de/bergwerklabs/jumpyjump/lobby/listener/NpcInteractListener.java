package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.util.NPCInteractEvent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.concurrent.TimeUnit;

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

        // If should see == null then we know it's the quick join npc.
        if (event.getInteracted().getShouldSee() != null) return;

        switch (action) {
            case "INTERACT":
            case "ATTACK":

                if (lobbyPlayer.isQueued()) {
                    Main.MESSENGER.message("§cDu bist bereits in einer Warteschlange.", player);
                    player.playSound(player.getEyeLocation(), Sound.NOTE_BASS, 50, 50);
                    return;
                }

                final long lastQueueLeave = lobbyPlayer.getLastQueueLeave();

                if (lastQueueLeave != 0 && System.currentTimeMillis() - lastQueueLeave <= TimeUnit.SECONDS.toMillis(5)) {
                    Main.MESSENGER.message("Warte §b5 Sekunden §7vor erneutem joinen.", player);
                    player.playSound(player.getEyeLocation(), Sound.NOTE_BASS, 50, 50);
                    return;
                }

                lobbyPlayer.joinQuickJoinQueue();
                Main.MESSENGER.message("Du bist §aQuickJoin §7beigetreten.", player);
                player.playSound(player.getEyeLocation(), Sound.ITEM_PICKUP, 50, 50);
                break;
        }
    }
}
