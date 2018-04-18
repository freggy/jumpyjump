package session;

import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;

/**
 * Created by Yannic Rieger on 08.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class QuickJoinSession {

  public LobbyPlayer getLobbyPlayer() {
    return lobbyPlayer;
  }

  private LobbyPlayer lobbyPlayer;

  public QuickJoinSession(LobbyPlayer lobbyPlayer) {
    this.lobbyPlayer = lobbyPlayer;
  }

  public void requestServer() {
    Main.SERVER_MANAGER.addToQueue(this);
  }
}
