package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerMoveListener implements Listener {

    private void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpMap map = JumpyJumpSession.getInstance().getMapManager().getMap();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Material ground = event.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
        if (!map.getAllowedBlocks().contains(ground)) {
            jumpyJumpPlayer.resetToCheckpoint();
        }
    }
}
