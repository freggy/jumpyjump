package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
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

    PlayerMoveListener(JumpyJumpSession session) {
        super(session);
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Material ground = event.getTo().getBlock().getRelative(BlockFace.DOWN).getType();
        if (!this.map.getAllowedBlocks().contains(ground)) {
            jumpyJumpPlayer.resetToCheckpoint();
        }
    }
}
