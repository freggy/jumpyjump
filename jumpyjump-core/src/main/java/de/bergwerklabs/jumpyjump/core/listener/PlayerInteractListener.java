package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.api.WinResult;
import de.bergwerklabs.jumpyjump.api.event.CheckpointReachedEvent;
import de.bergwerklabs.jumpyjump.api.event.JumpyJumpWinEvent;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerInteractListener extends JumpyJumpListener {

    public PlayerInteractListener(JumpyJumpSession session) {
        super(session);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Block block = event.getClickedBlock();
        final Material material = block.getType();

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            this.game.getMessenger().message(event.getClickedBlock().getType().toString(), player);
        }


        if (this.map.getCheckpointMaterial().contains(material)) {

            Vector vector = block.getLocation().subtract(0, 1, 0).toVector();

            this.game.getMessenger().message("Vec(-1): " + vector, player);

            jumpyJumpPlayer.getCourse().getCheckpoints().forEach((key, val) -> {
                this.game.getMessenger().message("Vec(CHECK): " + val.toVector(), player);
            });
            Location checkpoint = jumpyJumpPlayer.getCourse().getCheckpoints().get(block.getLocation().toVector());
            jumpyJumpPlayer.setCurrentCheckpoint(checkpoint);
            Bukkit.getPluginManager().callEvent(
                    new CheckpointReachedEvent(
                            jumpyJumpPlayer,
                            this.map,
                            jumpyJumpPlayer.getCurrentCheckpoint()
                    )
            );
        }
        else if (material == Material.GOLD_PLATE) {
            WinResult result = new WinResult(System.currentTimeMillis() - this.game.getStartTime());
            Bukkit.getPluginManager().callEvent(new JumpyJumpWinEvent(jumpyJumpPlayer, this.map, result));
            this.game.stop();
        }
    }
}
