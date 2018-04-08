package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.MapSession;
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

        boolean selfSessioned = MapSession.SESSIONS
                .values()
                .stream()
                .anyMatch(session -> session.getChallenged().getUniqueId().equals(player.getUniqueId()));

        boolean otherSessioned = MapSession.SESSIONS
                .values()
                .stream()
                .anyMatch(session -> session.getChallenged().getUniqueId().equals(other.getUniqueId()));

        if (selfSessioned || otherSessioned) {
            Main.MESSENGER.message("§cHerausfordern ist nicht möglich.", player);
            return;
        }

        final Tuple<UUID, Long> requested = MapSession.REQUESTS.get(player.getUniqueId());

        if (requested != null) {
            Main.MESSENGER.message("Warte §b10 Sekunden §7bevor du erneut Herausforderst.", player);
            return;
        }

        final MapSession potenial = MapSession.SESSIONS.get(other.getUniqueId());

        if (potenial != null) {
            Main.MESSENGER.message("§cDieser Spieler fordert dich bereits heraus.", player);
            return;
        }

        other.sendMessage("");
        // TODO: use Rankcolor
        // TODO: use UTF-8 "|" EVERYWHERE!!
        String firstMessage = "§b" + player.getDisplayName() + " §bfordert dich heraus!";
        Main.MESSENGER.message(firstMessage, other);

        String centered = StringUtils.center("", firstMessage.length() / 4);

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
