package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerJoinListener extends LobbyListener {

    public PlayerJoinListener(Config config, LobbyMapManager mapManager) {
        super(config, mapManager);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        final Inventory inventory = player.getInventory();
        inventory.setItem(2, this.config.getChallengeItem());
        inventory.setItem(4, this.config.getQuickJoinItem());
        inventory.setItem(6, this.config.getLobbyItem());
    }
}
