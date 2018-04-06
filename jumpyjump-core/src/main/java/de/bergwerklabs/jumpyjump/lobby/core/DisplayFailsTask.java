package de.bergwerklabs.jumpyjump.lobby.core;

import de.bergwerklabs.framework.commons.spigot.title.ActionbarTitle;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * Created by Yannic Rieger on 05.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class DisplayFailsTask implements Runnable {

    private JumpyJumpSession session;

    public DisplayFailsTask(JumpyJumpSession session) {
        this.session = session;
    }

    @Override
    public void run() {
        Collection<JumpyJumpPlayer> players = session.getRegistry().getPlayers().values()
                                                     .stream()
                                                     .filter(p -> p.getPlayer() != null)
                                                     .collect(Collectors.toList());
        Iterator<JumpyJumpPlayer> iterator = players.iterator();

        StringBuilder displayBuilder = new StringBuilder("§eFails §6» ");

        while (iterator.hasNext()) {
            JumpyJumpPlayer jumpyJumpPlayer = iterator.next();
            Player player = jumpyJumpPlayer.getPlayer();
            String name = "§e" + player.getDisplayName();
            String fails = String.valueOf(jumpyJumpPlayer.getFails());

            String failsDisplay = name + ":§b " + fails;

            if (!iterator.hasNext()) {
                displayBuilder.append(failsDisplay);
            }
            else displayBuilder.append(failsDisplay + " §7❘ ");
        }

        players.forEach(player -> ActionbarTitle.send(player.getPlayer(), displayBuilder.toString()));

    }
}
