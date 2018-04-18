package de.bergwerklabs.jumpyjump.api;

import de.bergwerklabs.framework.bedrock.api.LabsPlayer;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 02.04.2018.
 *
 * <p>Class representing a JumpyJump playing Minecraft player
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

  public int getFails() {
    return fails;
  }

  public LabsScoreboard getScoreboard() {
    return scoreboard;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public void setCurrentCheckpoint(Location currentCheckpoint) {
    this.currentCheckpoint = currentCheckpoint;
  }

  public void setScoreboard(LabsScoreboard scoreboard) {
    this.scoreboard = scoreboard;
    scoreboard.apply(getPlayer());
  }

  private Course course;
  private Location currentCheckpoint;
  private int fails;
  private LabsScoreboard scoreboard;

  public JumpyJumpPlayer(Player player) {
    super(player.getUniqueId());
  }

  /** Teleports the player to the current checkpoint. */
  public void resetToCheckpoint() {
    fails++;
    final Player player = this.getPlayer();
    if (this.currentCheckpoint == null) {
      player.teleport(this.course.getSpawn().clone().add(0.5, 0.5, 0.5));
    } else player.teleport(this.currentCheckpoint.clone().add(0.5, 0.5, 0.5));
  }

  public void setCheckpointProgress(float percentage) {
    this.getPlayer().setExp(percentage);
  }

  public void setGoalProgress(float percentage) {
    final Player player = this.getPlayer();
    if (this.scoreboard == null) return;
    this.scoreboard
        .getRowsByContent()
        .values()
        .stream()
        .filter(row -> row.getText().contains("§7" + player.getDisplayName()))
        .findFirst()
        .ifPresent(
            row ->
                row.setText(
                    "§7" + player.getDisplayName() + ": §b" + Math.round(percentage) + "%"));
  }
}
