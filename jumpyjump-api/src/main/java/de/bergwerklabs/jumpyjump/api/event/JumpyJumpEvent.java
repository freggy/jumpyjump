package de.bergwerklabs.jumpyjump.api.event;

import de.bergwerklabs.framework.commons.spigot.general.LabsEvent;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 * Base class for all JumpyJump event.
 *
 * @author Yannic Rieger
 */
public class JumpyJumpEvent extends LabsEvent {

    protected JumpyJumpPlayer player;
    protected JumpyJumpMap map;

    JumpyJumpEvent(JumpyJumpPlayer player, JumpyJumpMap map) {
        this.player = player;
        this.map = map;
    }

}
