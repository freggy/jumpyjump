package de.bergwerklabs.jumpyjump.api.event;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.api.WinResult;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 * This event will be fired if a player wins the game by reaching the golden pressure plate.
 *
 * @author Yannic Rieger
 */
public class JumpyJumpWinEvent extends JumpyJumpEvent {

    public WinResult getResult() {
        return result;
    }

    private WinResult result;

    public JumpyJumpWinEvent(JumpyJumpPlayer player, JumpyJumpMap map, WinResult result) {
        super(player, map);
        this.result = result;
    }
}
