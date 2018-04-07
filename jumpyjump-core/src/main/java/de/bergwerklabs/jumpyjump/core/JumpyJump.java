package de.bergwerklabs.jumpyjump.core;

import com.google.common.collect.Iterators;
import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.framework.bedrock.api.PlayerRegistry;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.scoreboard.LabsScoreboard;
import de.bergwerklabs.framework.commons.spigot.scoreboard.Row;
import de.bergwerklabs.jumpyjump.api.Course;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.listener.CancelListener;
import de.bergwerklabs.jumpyjump.core.listener.PlayerInteractListener;
import de.bergwerklabs.jumpyjump.core.listener.PlayerMoveListener;
import de.bergwerklabs.jumpyjump.core.listener.timer.CountdownStopListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Represents the JumpyJump game.
 *s
 * @author Yannic Rieger
 */
public class JumpyJump extends LabsGame<JumpyJumpPlayer> {

    public boolean isFinished() {
        return finished;
    }

    public long getStartTime() {
        return this.startTime;
    }

    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public JumpyJump() {
        super("JumpyJump");
    }

    private LabsScoreboard scoreboard;
    private boolean finished = false;
    private long startTime;

    public void start(PlayerRegistry<JumpyJumpPlayer> registry) {
        JumpyJumpSession.getInstance().setRegistry(registry);
        this.playerRegistry = registry;
        final JumpyJumpMap map = JumpyJumpSession.getInstance().getMapManager().getMap();
        final Iterator<Course> courses = JumpyJumpSession.getInstance().getMapManager().getMap().getCourses().iterator();
        final Collection<JumpyJumpPlayer> players = registry.getPlayers().values();
        this.registerListeners();
        this.setUpScoreboard(players, 60 * 20);

        players.forEach(player -> {
            new PotionEffect(PotionEffectType.BLINDNESS, 21, 10, false, false).apply(player.getPlayer());
            Bukkit.getOnlinePlayers().forEach(p -> {
                if (!p.getUniqueId().equals(player.getUuid())) {
                    player.getPlayer().hidePlayer(p);
                }
            });

            if (courses.hasNext()) {
                final Course course = courses.next();
                player.setCourse(course);
                player.getPlayer().teleport(course.getSpawn().clone().add(0.5F, 0F, 0.5F));
                player.freeze();
            }
        });

        Bukkit.getScheduler().runTaskLater(JumpyJumpSession.getInstance(), () -> {
            players.forEach(jumpPlayer -> {
                Common.createAndSendTitle(
                        jumpPlayer.getPlayer(),
                        map.getDifficulty().getColor() + map.getName(),
                        "§b" + StringUtils.join(map.getBuilder(), ", ")
                );
            });
        }, 10);

        Bukkit.getScheduler().runTaskLater(JumpyJumpSession.getInstance(), () -> {
            Iterator<String> iterator = Iterators.cycle("§6Auf die Plätze...", "§eFertig...");
            LabsTimer timer = new LabsTimer(2, (timeLeft) -> {
                String text = iterator.next();
                players.forEach(player -> {
                    final Player playerObject = player.getPlayer();
                    Common.createAndSendTitle(playerObject, text, "");
                    playerObject.playSound(playerObject.getEyeLocation(), Sound.ORB_PICKUP, 100, 1);
                });
            });
            timer.addStopListener(new CountdownStopListener(JumpyJumpSession.getInstance(), 60 * 20, this.scoreboard));
            timer.start();
        }, 20 * 2);
    }

    public void stop() {
        this.finished = true;
        Bukkit.getScheduler().runTaskLater(JumpyJumpSession.getInstance(), () -> {
            this.playerRegistry.getPlayers()
                               .values()
                               .stream()
                               .filter(p -> p.getPlayer() != null)
                               .forEach(jumpPlayer -> {
                this.getMessenger().messageSome("Der Server startet in §b10 §7Sekunden neu.", jumpPlayer.getPlayer());
            });
        }, 20 * 2);

        Bukkit.getScheduler().runTaskLaterAsynchronously(JumpyJumpSession.getInstance(), () -> {
            this.getMessenger().messageAll("TSCHAU");
        }, 20 * 12);
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new CancelListener(), JumpyJumpSession.getInstance());
    }

    private void setUpScoreboard(Collection<JumpyJumpPlayer> players, int duration) {
        this.scoreboard = new LabsScoreboard(String.format("§6>> §eJumpyJump §6❘ §b%02d:%02d", duration / 60, duration % 60), "distance");
        scoreboard.addRow(players.size() + 2, new Row(this.scoreboard, "§a§a§a"));
        scoreboard.addRow(2, new Row(scoreboard, "§a§a"));
        scoreboard.addRow(1, new Row(scoreboard, "§6§m-------------"));
        scoreboard.addRow(0, new Row(scoreboard, "§ebergwerkLABS.de"));
        AtomicInteger count = new AtomicInteger(2);
        players.forEach(jumpPlayer -> {
            scoreboard.addRow(count.getAndIncrement(), new Row(scoreboard, "§7" + jumpPlayer.getPlayer().getDisplayName() + ": §b0%"));
        });
    }
}
