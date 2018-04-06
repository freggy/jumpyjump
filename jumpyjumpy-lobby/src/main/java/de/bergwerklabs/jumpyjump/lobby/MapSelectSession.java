package de.bergwerklabs.jumpyjump.lobby;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class MapSelectSession {

    public static final Map<UUID, MapSelectSession> SESSIONS = new HashMap<>();

    private Player challenger;
    private Player challenged;

    public MapSelectSession(Player challenger, Player challenged) {
        this.challenger = challenger;
        this.challenged = challenged;
    }

    public Player getChallenger() {
        return challenger;
    }

    public Player getChallenged() {
        return challenged;
    }

    public void requestMapServer(String mapHash) {

    }
}
