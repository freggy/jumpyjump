package de.bergwerklabs.jumpyjump.lobby;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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

    private Inventory openInventory;
    private Player challenger;
    private Player challenged;
    private String requestedMapId;

    public MapSelectSession(Player challenger, Player challenged) {
        MapSelectSession.SESSIONS.put(challenger.getUniqueId(), this);
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
        this.requestedMapId = mapHash;
    }

    public Inventory getOpenInventory() {
        return openInventory;
    }

    public void setOpenInventory(Inventory openInventory) {
        this.openInventory = openInventory;
    }

    public String getRequestedMapId() {
        return requestedMapId;
    }
}
