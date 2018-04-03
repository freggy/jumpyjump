package de.bergwerklabs.jumpyjump.core;

import de.bergwerklabs.framework.bedrock.api.PlayerFactory;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Factory for creating {@link JumpyJumpPlayer} objects.
 *
 * @author Yannic Rieger
 */
public class JumpyJumpPlayerFactory implements PlayerFactory<JumpyJumpPlayer> {

    public JumpyJumpPlayer createPlayer(Player player) {
        return new JumpyJumpPlayer(player);
    }
}
