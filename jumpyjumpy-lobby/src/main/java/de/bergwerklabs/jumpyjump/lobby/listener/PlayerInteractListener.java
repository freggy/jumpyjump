package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Yannic Rieger on 08.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerInteractListener extends LobbyListener {

  public PlayerInteractListener(Config config, LobbyMapManager mapManager) {
    super(config, mapManager);
  }

  @EventHandler
  private void onPlayerInteract(PlayerInteractEvent event) {
    final Action action = event.getAction();
    if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) return;
    final Player player = event.getPlayer();
    if (player.getItemInHand().getType() != this.config.getLeaveQuickJoin().getType()) return;
    Main.LOBBY_PLAYERS.get(player.getUniqueId()).leaveQuickJoinQueue();
    Main.MESSENGER.message("Du hast §aQuickJoin §7verlassen.", player);
    player.playSound(player.getEyeLocation(), Sound.NOTE_BASS, 50, 50);
    event.setCancelled(true);
  }
}
