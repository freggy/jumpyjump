package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

        boolean selfSessioned = MapSelectSession.SESSIONS
                .values()
                .stream()
                .anyMatch(session -> session.getChallenged().getUniqueId().equals(player.getUniqueId()));

        boolean otherSessioned = MapSelectSession.SESSIONS
                .values()
                .stream()
                .anyMatch(session -> session.getChallenged().getUniqueId().equals(other.getUniqueId()));

        if (selfSessioned || otherSessioned) {
            Main.MESSENGER.message("§cHerausfordern ist nicht möglich.", player);
            return;
        }

        final Tuple<UUID, Long> requested = MapSelectSession.REQUESTS.get(player.getUniqueId());

        if (requested != null) {
            Main.MESSENGER.message("§cDu hast bereits jemanden herausgefordert. Warte §b10 §cSekunden.", player);
            return;
        }

        final MapSelectSession potenial = MapSelectSession.SESSIONS.get(other.getUniqueId());

        if (potenial != null) {
            Main.MESSENGER.message("§cDieser Spieler fordert dich bereits heraus.", player);
            return;
        }

        other.sendMessage("");
        // TODO: use Rankcolor
        // TODO: use UTF-8 "|" EVERYWHERE!!
        Main.MESSENGER.message("§b" + player.getDisplayName() + " §bfordert dich heraus!", other);
        BaseComponent[] message = new ComponentBuilder("        ")
                .append("[ANNEHMEN]").color(ChatColor.GREEN).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cacpt " + player.getName()))
                .append(" | ").color(ChatColor.GOLD)
                .append("[ABLEHNEN]").color(ChatColor.RED).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/cdny "+ player.getName()))
                .create();
        other.spigot().sendMessage(message);
        other.sendMessage("");
        other.playSound(other.getEyeLocation(), Sound.LEVEL_UP, 100 ,1);
        MapSelectSession.REQUESTS.putIfAbsent(player.getUniqueId(), new Tuple<>(other.getUniqueId(), System.currentTimeMillis()));
    }

}
