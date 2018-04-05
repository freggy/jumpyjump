package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.framework.commons.math.SQRT;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerMoveListener extends JumpyJumpListener {

    public PlayerMoveListener(JumpyJumpSession session) {
        super(session);
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Material ground = event.getTo().getBlock().getRelative(BlockFace.DOWN).getType();

        final Location nextCheckpoint = jumpyJumpPlayer.getCourse().inspectNextCheckpoint().clone().add(0.5, 0, 0.5);
        final Location spawnPoint = jumpyJumpPlayer.getCurrentCheckpoint().clone().add(0.5, 0, 0.5);

        float totalDistance = (float)this.calculateDistanceFast(spawnPoint, nextCheckpoint);
        float currentDistance = (float)this.calculateDistanceFast(player.getLocation(), nextCheckpoint);
        float wayPercentage = (currentDistance / totalDistance) * 100;

        player.setExp(1 - (wayPercentage / 100));
        if (!this.map.getAllowedBlocks().contains(ground)) {
            jumpyJumpPlayer.resetToCheckpoint();
        }
    }

    private double calculateDistanceFast(Location location1, Location location2) {
        return location1.distance(location2);
        /*return SQRT.fast(
                (location1.getX() - location2.getX()) +
                (location1.getY() - location2.getY()) +
                (location1.getZ() - location2.getZ())
        );*/
    }

}
