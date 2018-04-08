package de.bergwerklabs.jumpyjump.lobby.command;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class ChallengeDenyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        if (args.length == 0) return false;

        Player sender = (Player)commandSender;
        String playerName = args[0];
        Player player =  Bukkit.getPlayer(playerName);

        if (player == null) {
            Main.MESSENGER.message("§cDieser Spieler nicht in der Lobby", sender);
            return false;
        }

        final Tuple<UUID, Long> requested = MapSelectSession.REQUESTS.get(player.getUniqueId());

        if (requested == null) {
            Main.MESSENGER.message("§cDie Anfrage ist abgelaufen.", sender);
            return false;
        }

        if (!requested.getValue1().equals(sender.getUniqueId())) {
            Main.MESSENGER.message("§cDieser Spieler muss dich erst herausfordern.", sender);
            return false;
        }

        Main.MESSENGER.message("§b" + sender.getDisplayName() + " §7hat deine Herausforderung §cabgelehnt.", player);
        Main.MESSENGER.message("Du hast die Herausforderung §cabgelehnt§7.", sender);
        MapSelectSession.REQUESTS.remove(player.getUniqueId());
        player.playSound(player.getEyeLocation(), Sound.NOTE_BASS, 50, 50);
        sender.playSound(sender.getEyeLocation(), Sound.CLICK, 100 , 1);
        return false;
    }

}
