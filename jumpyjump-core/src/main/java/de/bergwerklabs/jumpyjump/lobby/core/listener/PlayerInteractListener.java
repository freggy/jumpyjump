package de.bergwerklabs.jumpyjump.lobby.core.listener;

import com.comphenix.protocol.wrappers.EnumWrappers;
import de.bergwerklabs.framework.bedrock.api.LabsPlayer;
import de.bergwerklabs.framework.commons.math.vector.Vector3F;
import de.bergwerklabs.framework.commons.spigot.chat.messenger.PluginMessenger;
import de.bergwerklabs.framework.commons.spigot.particle.ParticleUtil;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.api.WinResult;
import de.bergwerklabs.jumpyjump.api.event.CheckpointReachedEvent;
import de.bergwerklabs.jumpyjump.api.event.JumpyJumpWinEvent;
import de.bergwerklabs.jumpyjump.lobby.core.JumpyJumpSession;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

import java.util.Collection;


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
        final Location nextCheckpoint = jumpyJumpPlayer.getCourse().inspectNextCheckpoint();
        final Collection<JumpyJumpPlayer> players = JumpyJumpSession.getInstance().getRegistry().getPlayers().values();

        if (event.getAction() == Action.PHYSICAL) {

            // Return if its not a checkpoint-pressure-plate.
            if (!nextCheckpoint.toVector().equals(blockLocationVector)) {
                event.setCancelled(true);
                return;
            }

            if (this.map.getCheckpointMaterial().contains(material) && (currentCheckpoint == null || !currentCheckpoint.toVector().equals(blockLocationVector))) {
                final Location checkpoint = jumpyJumpPlayer.getCourse().pollNextCheckpoint();
                jumpyJumpPlayer.setCurrentCheckpoint(checkpoint);

                player.playSound(player.getEyeLocation(), Sound.LEVEL_UP, 100, 10);
                player.setLevel(player.getLevel() + 1);
                final int checkpointIndex = player.getLevel();
                final PluginMessenger messenger =  JumpyJumpSession.getInstance().getGame().getMessenger();

                // TODO: add rank colors.
                players.stream()
                        .filter(p -> !p.getUuid().equals(player.getUniqueId()))
                        .map(LabsPlayer::getPlayer)
                        .forEach(p -> {
                            String msg = player.getDisplayName() + " hat Checkpoint §b" + checkpointIndex + " §7erreicht.";
                            p.playSound(p.getEyeLocation(), Sound.ANVIL_LAND, 100, 10);
                            messenger.message(msg, p);
                        });

                messenger.message("Du hast Checkpoint §b" + checkpointIndex + " §7erreicht.", player);

                JumpyJumpSession.getInstance().getRegistry().getPlayers().values().forEach(jumpPlayer -> {
                    final Vector pos = checkpoint.clone().add(0.5, 0, 0.5).toVector();
                    ParticleUtil.spawnParticle(
                            player,
                            EnumWrappers.Particle.VILLAGER_HAPPY,
                            25,
                            new Vector3F((float) pos.getX(), (float) pos.getY(), (float) pos.getZ()),
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
            event.setCancelled(true);
        }
    }
}
