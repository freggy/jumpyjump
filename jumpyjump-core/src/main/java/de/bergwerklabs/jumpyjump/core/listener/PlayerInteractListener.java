package de.bergwerklabs.jumpyjump.core.listener;

import com.comphenix.protocol.wrappers.EnumWrappers;
import de.bergwerklabs.framework.commons.math.vector.Vector3F;
import de.bergwerklabs.framework.commons.spigot.particle.ParticleUtil;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.api.WinResult;
import de.bergwerklabs.jumpyjump.api.event.CheckpointReachedEvent;
import de.bergwerklabs.jumpyjump.api.event.JumpyJumpWinEvent;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import static de.bergwerklabs.jumpyjump.core.JumpyJumpSession.getInstance;


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
        final Block block = event.getClickedBlock();
        if (block == null) return;
        final Material material = block.getType();

        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Vector blockLocationVector = block.getLocation().toVector();
        final Location currentCheckpoint = jumpyJumpPlayer.getCurrentCheckpoint();

        if (this.map.getCheckpointMaterial().contains(material) && (currentCheckpoint == null || !currentCheckpoint.toVector().equals(blockLocationVector))) {
            Location checkpoint = jumpyJumpPlayer.getCourse().getCheckpoint(blockLocationVector);
            jumpyJumpPlayer.setCurrentCheckpoint(checkpoint);

            player.playSound(player.getEyeLocation(), Sound.LEVEL_UP, 100, 10);
            getInstance().getGame().getMessenger().message("Du hast einen Checkpoint erreicht!", player);

            JumpyJumpSession.getInstance().getRegistry().getPlayers().values().forEach(jumpPlayer -> {
                final Vector pos = checkpoint.clone().add(0.5, 0, 0.5).toVector();
                ParticleUtil.spawnParticle(
                        player,
                        EnumWrappers.Particle.VILLAGER_HAPPY,
                        25,
                        new Vector3F((float)pos.getX(), (float)pos.getY(), (float)pos.getZ()),
                        new Vector3F(.5F, .4F, .5F),
                        2F,
                        false
                );
            });
            Bukkit.getPluginManager().callEvent(new CheckpointReachedEvent(jumpyJumpPlayer, this.map, checkpoint));
        }
        else if (material == Material.GOLD_PLATE) {
            WinResult result = new WinResult(System.currentTimeMillis() - this.game.getStartTime());
            Bukkit.getPluginManager().callEvent(new JumpyJumpWinEvent(jumpyJumpPlayer, this.map, result));
            this.game.stop();
        }
    }
}
