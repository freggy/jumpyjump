package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.LobbyPlayer;
import de.bergwerklabs.jumpyjump.lobby.Main;
import session.MapSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import java.util.UUID;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class PlayerInteractAtEntityListener extends LobbyListener {

    public PlayerInteractAtEntityListener(Config config, LobbyMapManager mapManager) {
        super(config, mapManager);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractAtEntityEvent event) {
        final Player player = event.getPlayer();
        if (player.getItemInHand().getType() != this.config.getChallengeItem().getType()) return;
        final Player other = (Player) event.getRightClicked();

        final LobbyPlayer self = Main.LOBBY_PLAYERS.get(player.getUniqueId());
        final LobbyPlayer otherLobbyPlayer = Main.LOBBY_PLAYERS.get(other.getUniqueId());

        if (otherLobbyPlayer.isQueued() || self.isQueued()) {
            Main.MESSENGER.message("§cDieser Spieler wird bereits herausgefordert.", player);
            return;
        }

        final Tuple<UUID, Long> requested = MapSession.REQUESTS.get(player.getUniqueId());

        if (requested != null) {
            Main.MESSENGER.message("Warte §b10 Sekunden §7bevor du erneut Herausforderst.", player);
            return;
        }

        if (otherLobbyPlayer.isQueued()) {
            Main.MESSENGER.message("§cDieser Spieler fordert dich bereits heraus.", player);
            return;
        }

        other.sendMessage("");
        // TODO: use Rankcolor
        // TODO: use UTF-8 "|" EVERYWHERE!!
        String firstMessage = "§b" + player.getDisplayName() + " §bfordert dich heraus!";
        String centered = StringUtils.center("", firstMessage.length() / 4);

        Main.MESSENGER.message(firstMessage, other);

        BaseComponent[] message = new ComponentBuilder(centered)
                .append("[ANNEHMEN]").color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cacpt " + player.getName()))
                .append(" | ").color(ChatColor.GOLD)
                .append("[ABLEHNEN]").color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cdny "+ player.getName()))
                .create();
        other.spigot().sendMessage(message);
        other.sendMessage("");

        other.playSound(other.getEyeLocation(), Sound.LEVEL_UP, 100 ,1);
        player.playSound(other.getEyeLocation(), Sound.CLICK, 100 ,1);

        Main.MESSENGER.message("Du hast §b" + other.getDisplayName() + " §7herausgefordert.", player);
        MapSession.REQUESTS.put(player.getUniqueId(), new Tuple<>(other.getUniqueId(), System.currentTimeMillis()));
    }
}
