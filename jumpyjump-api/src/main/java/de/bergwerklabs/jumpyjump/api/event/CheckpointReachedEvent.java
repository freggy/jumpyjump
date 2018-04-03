package de.bergwerklabs.jumpyjump.api.event;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import org.bukkit.Location;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class CheckpointReachedEvent extends JumpyJumpEvent {

    public Location getLocation() {
        return location;
    }

    private Location location;

    public CheckpointReachedEvent(JumpyJumpPlayer player, JumpyJumpMap map, Location location) {
        super(player, map);
        this.location = location;
    }

}
