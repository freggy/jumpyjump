package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.event.Listener;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public abstract class LobbyListener implements Listener {

    protected Config config;
    protected LobbyMapManager mapManager;

    public LobbyListener(Config config, LobbyMapManager mapManager) {
        this.config = config;
        this.mapManager = mapManager;
    }
}
