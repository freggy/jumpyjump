package de.bergwerklabs.jumpyjump.core.listener;

import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerInteractListener extends JumpyJumpListener {

    public PlayerInteractListener(JumpyJumpMap map) {
        super(map);
    }

    private void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        final JumpyJumpPlayer jumpyJumpPlayer = JumpyJumpSession.getInstance().getPlayer(player.getUniqueId());
        final Material clicked = event.getClickedBlock().getType();

        if (this.map.getCheckpointMaterial().contains(clicked)) {
        }



    }


}
