package de.bergwerklabs.jumpyjump.api;

import de.bergwerklabs.framework.bedrock.api.LabsPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Class representing a JumpyJump playing Minecraft player
 *
 * @author Yannic Rieger
 */
public class JumpyJumpPlayer extends LabsPlayer {


    public Location getCurrentCheckpoint() {
        return currentCheckpoint;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCurrentCheckpoint(Location currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    private Course course;
    private Location currentCheckpoint;

    public JumpyJumpPlayer(Player player) {
        super(player.getUniqueId());
    }

    /**
     * Teleports the player to the current checkpoint.
     */
    public void resetToCheckpoint() {
        // TODO: play cool sound
        if (this.currentCheckpoint == null) {
            this.getPlayer().teleport(this.course.getSpawn().clone().add(0.5, 0.5, 0.5));
        }
        else this.getPlayer().teleport(this.currentCheckpoint.clone().add(0.5, 0.5, 0.5));
    }
}
