package de.bergwerklabs.jumpyjump.api;

import de.bergwerklabs.framework.bedrock.api.LabsPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Class representing a JumpyJump playing Minecraft player
 *
 * @author Yannic Rieger
 */
public class JumpyJumpPlayer extends LabsPlayer {

    private Queue<Location> checkpoints = new LinkedList<Location>();

    public JumpyJumpPlayer(Player player) {
        super(player);
    }

    /**
     * Adds the checkpoints to the {@link Queue}.
     *
     * @param checkpoints {@link Queue} containing checkpoints.
     */
    public void addCheckpoints(Queue<Location> checkpoints) {
        this.checkpoints.addAll(checkpoints);
    }

    /**
     * Gets the current checkpoint the user is at.
     */
    public Location getCurrentCheckPoint() {
        return this.checkpoints.element();
    }

    /**
     * Gets the next checkpoint.
     */
    public Location getNextCheckpoint() {
        return this.checkpoints.peek();
    }

    /**
     * Gets a unmodifiable collection of all available checkpoints for this players jump course.
     */
    public Collection<Location> getCheckpoints() {
        return Collections.unmodifiableCollection(this.checkpoints);
    }

}
