package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.spigot.entity.PlayerSkin;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import de.bergwerklabs.util.NPC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Yannic Rieger on 06.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerJoinListener extends LobbyListener {

  public PlayerJoinListener(Config config, LobbyMapManager mapManager) {
    super(config, mapManager);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  private void onPlayerJoin(PlayerJoinEvent event) {
    final Player player = event.getPlayer();
    final Inventory inventory = player.getInventory();
    inventory.setItem(2, this.config.getChallengeItem());
    inventory.setItem(4, this.config.getQuickJoinItem());
    inventory.setItem(6, this.config.getLobbyItem());
    player.setGameMode(GameMode.ADVENTURE);
    event.setJoinMessage("§b" + player.getDisplayName() + " §7hat die Lobby §abetreten.");
    Main.LOBBY_PLAYERS.putIfAbsent(player.getUniqueId(), new LobbyPlayer(player));

    try {
      PlayerSkin skin = PlayerSkin.fromPlayer(player).get();
      this.config.getQuickJoinNpc().spawn(player);
      NPC statsNpc = new NPC("", "", false, false, this.config.getStatsNpcLocation());
      // TODO: add stats above NPC
      statsNpc.setSinglePlayerNpc(true);
      statsNpc.setShouldSee(player);
      statsNpc.spawn(player);
      statsNpc.updateSkin(skin.getValue(), skin.getSignature());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
