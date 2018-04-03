package de.bergwerklabs.jumpyjump.core;

import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Represents the JumpyJump game.
 *
 * @author Yannic Rieger
 */
public class JumpyJump extends LabsGame<JumpyJumpPlayer> {

    public long getStartTime() {
        return startTime;
    }

    public JumpyJump(String name) {
        super(name);
    }

    private long startTime;

    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    public void stop() {

    }
}
