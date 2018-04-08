package de.bergwerklabs.jumpyjump.lobby;

import java.util.UUID;

/**
 * Created by Yannic Rieger on 08.04.2018.
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
