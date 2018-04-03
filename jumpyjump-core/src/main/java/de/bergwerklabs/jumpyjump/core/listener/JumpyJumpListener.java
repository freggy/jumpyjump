package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import org.bukkit.event.Listener;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class JumpyJumpListener implements Listener {

    protected JumpyJumpMap map;

    public JumpyJumpListener(JumpyJumpMap map) {
        this.map = map;
    }
}
