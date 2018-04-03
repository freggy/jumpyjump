package de.bergwerklabs.jumpyjump.core;

import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.framework.bedrock.api.session.MinigameSession;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;

import java.util.UUID;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Main class for the JumpyJump game.
 *
 * @author Yannic Rieger
 */
public class JumpyJumpSession extends MinigameSession<JumpyJumpPlayer> {

    public static JumpyJumpSession getInstance() {
        return instance;
    }

    /**
     * Gets the {@link MapManager}.
     */
    public MapManager getMapManager() {
        return mapManager;
    }

    public LabsGame getGame() {
        return new JumpyJump("JumpyJump");
    }

    private static JumpyJumpSession instance;
    private MapManager mapManager;

    public void prepare() {
        instance = this;
        this.mapManager = new MapManager(this);
        mapManager.loadMap();
    }

    /**
     * Gets a {@link JumpyJumpPlayer} based on their {@link UUID}
     *
     * @param uuid {@link UUID} of the player.
     */
    public JumpyJumpPlayer getPlayer(UUID uuid) {
        return (JumpyJumpPlayer)this.getGame().getPlayerRegistry().getPlayer(uuid);
    }
}
