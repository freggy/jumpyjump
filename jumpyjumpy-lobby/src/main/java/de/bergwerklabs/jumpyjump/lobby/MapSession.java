package de.bergwerklabs.jumpyjump.lobby;

import de.bergwerklabs.framework.commons.misc.Tuple;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class MapSession {

    public static final Map<UUID, MapSession> SESSIONS        = new HashMap<>();
    public static final Map<UUID, Tuple<UUID, Long>> REQUESTS = new ConcurrentHashMap<>();

    public int getPriority() {
        return priority;
    }

    private Inventory openInventory;
    private LobbyPlayer challenger;
    private LobbyPlayer challenged;
    private String requestedMapId;
    private int priority;

    public MapSession(LobbyPlayer challenger, LobbyPlayer challenged) {
        MapSession.SESSIONS.put(challenger.getPlayer().getUniqueId(), this);
        this.challenger = challenger;
        this.challenged = challenged;
        this.priority = this.challenger.getPriority() + this.challenged.getPriority();
    }

    public LobbyPlayer getChallenger() {
        return challenger;
    }

    public LobbyPlayer getChallenged() {
        return challenged;
    }

    public String getRequestedMapId() {
        return requestedMapId;
    }

    public Inventory getOpenInventory() {
        return openInventory;
    }

    public void requestMapServer(String mapHash) {
        this.requestedMapId = mapHash;
        Main.SERVER_MANAGER.addToQueue(this);
    }

    public void setOpenInventory(Inventory openInventory) {
        this.openInventory = openInventory;
    }

    public static boolean isInSession(UUID uuid) {
        return MapSession.SESSIONS
                .values()
                .stream()
                .anyMatch(session -> session.getChallenged().getPlayer().getUniqueId().equals(uuid));
    }
}
