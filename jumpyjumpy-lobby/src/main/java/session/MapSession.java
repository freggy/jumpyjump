package session;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Yannic Rieger on 06.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class MapSession {

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

  public void close() {
    final Player challengedPlayer = this.challenged.getPlayer();
    final Player challengerPlayer = this.challenger.getPlayer();

    Main.MESSENGER.message("Die Session wurde §cbeendet§7.", challengedPlayer);
    Main.MESSENGER.message("Die Session wurde §cbeendet§7.", challengerPlayer);

    challengedPlayer.playSound(challengedPlayer.getEyeLocation(), Sound.NOTE_BASS, 50, 50);
    challengerPlayer
        .getPlayer()
        .playSound(challengerPlayer.getEyeLocation(), Sound.NOTE_BASS, 50, 50);

    this.challenged.setMapSession(null);
    this.challenger.setMapSession(null);

    challengerPlayer.closeInventory();
  }
}
