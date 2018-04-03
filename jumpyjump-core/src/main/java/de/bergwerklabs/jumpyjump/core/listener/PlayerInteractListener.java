package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.api.WinResult;
import de.bergwerklabs.jumpyjump.api.event.CheckpointReachedEvent;
import de.bergwerklabs.jumpyjump.api.event.JumpyJumpWinEvent;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerInteractListener extends JumpyJumpListener {

    PlayerInteractListener(JumpyJumpSession session) {
        super(session);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Material clicked = event.getClickedBlock().getType();

        if (this.map.getCheckpointMaterial().contains(clicked)) {
            jumpyJumpPlayer.next();
            Bukkit.getPluginManager().callEvent(
                    new CheckpointReachedEvent(
                            jumpyJumpPlayer,
                            this.map,
                            jumpyJumpPlayer.getCurrentCheckPoint()
                    )
            );
        }
        else if (clicked == Material.GOLD_PLATE) {
            WinResult result = new WinResult(System.currentTimeMillis() - this.game.getStartTime());
            Bukkit.getPluginManager().callEvent(new JumpyJumpWinEvent(jumpyJumpPlayer, this.map, result));
            this.game.stop();
        }
    }
}
