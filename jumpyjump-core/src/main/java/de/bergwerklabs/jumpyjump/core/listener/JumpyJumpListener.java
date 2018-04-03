package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.core.JumpyJump;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
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

    JumpyJumpListener(JumpyJumpSession session) {
        this.map = session.getMapManager().getMap();
        this.game = (JumpyJump) session.getGame();
    }
}
