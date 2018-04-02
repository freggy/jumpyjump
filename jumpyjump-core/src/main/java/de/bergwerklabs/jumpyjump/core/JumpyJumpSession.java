package de.bergwerklabs.jumpyjump.core;

import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.framework.bedrock.api.session.MinigameSession;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class JumpyJumpSession extends MinigameSession<JumpyJumpPlayer> {

    public LabsGame getGame() {
        return new JumpyJump("JumpyJump");
    }

    public void prepare() {

    }
}
