package de.bergwerklabs.jumpyjump.lobby.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.core.JumpyJump;
import de.bergwerklabs.jumpyjump.lobby.core.JumpyJumpSession;
import org.bukkit.event.Listener;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class JumpyJumpListener implements Listener {

    protected JumpyJumpMap map;
    protected JumpyJump game;
    protected JumpyJumpSession session;

    public JumpyJumpListener(JumpyJumpSession session) {
        this.map = session.getMapManager().getMap();
        this.game = (JumpyJump) session.getGame();
        this.session = session;
    }
}
