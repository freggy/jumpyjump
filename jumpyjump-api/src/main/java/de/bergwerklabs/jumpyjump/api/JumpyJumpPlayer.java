package de.bergwerklabs.jumpyjump.api;

import de.bergwerklabs.framework.bedrock.api.LabsPlayer;
import de.bergwerklabs.framework.commons.spigot.particle.ParticleUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
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
        return this.currentCheckpoint == null ? this.course.getSpawn() : this.currentCheckpoint;
    }

    public Course getCourse() {
        return course;
    }

    public int getFails() { return fails; }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setCurrentCheckpoint(Location currentCheckpoint) {
        this.currentCheckpoint = currentCheckpoint;
    }

    private Course course;
    private Location currentCheckpoint;
    private int fails;

    public JumpyJumpPlayer(Player player) {
        super(player.getUniqueId());
    }

    /**
     * Teleports the player to the current checkpoint.
     */
    public void resetToCheckpoint() {
        fails++;
        final Player player = this.getPlayer();
        if (this.currentCheckpoint == null) {
            player.teleport(this.course.getSpawn().clone().add(0.5, 0.5, 0.5));
        }
        else player.teleport(this.currentCheckpoint.clone().add(0.5, 0.5, 0.5));
    }
}
