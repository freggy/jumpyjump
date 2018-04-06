package de.bergwerklabs.jumpyjump.lobby.core.listener.timer;

import de.bergwerklabs.framework.commons.spigot.general.timer.event.LabsTimerStopEvent;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.lobby.core.JumpyJumpSession;
import de.bergwerklabs.jumpyjump.lobby.core.listener.JumpyJumpListener;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Yannic Rieger on 05.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class RoundTimerStopListener extends JumpyJumpListener implements Consumer<LabsTimerStopEvent> {

    private Collection<JumpyJumpPlayer> players;

    public RoundTimerStopListener(JumpyJumpSession session) {
        super(session);
        this.players = this.session.getRegistry().getPlayers().values();
    }

    @Override
    public void accept(LabsTimerStopEvent labsTimerStopEvent) {

    }
}
