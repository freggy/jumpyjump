package de.bergwerklabs.jumpyjump.core;

import com.google.common.collect.Iterators;
import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.framework.bedrock.api.PlayerRegistry;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.jumpyjump.api.Course;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.listener.PlayerDamageListener;
import de.bergwerklabs.jumpyjump.core.listener.PlayerInteractListener;
import de.bergwerklabs.jumpyjump.core.listener.PlayerMoveListener;
import de.bergwerklabs.jumpyjump.core.listener.timer.CountdownStopListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Represents the JumpyJump game.
 *s
 * @author Yannic Rieger
 */
public class JumpyJump extends LabsGame<JumpyJumpPlayer> {

    public long getStartTime() {
        return startTime;
    }

    public JumpyJump() {
        super("JumpyJump");
    }

    private long startTime;
    private final Scoreboard SCOREBOARD = Bukkit.getScoreboardManager().getNewScoreboard();

    public void start(PlayerRegistry<JumpyJumpPlayer> registry) {
        JumpyJumpSession.getInstance().setRegistry(registry);
        this.startTime = System.currentTimeMillis();
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
                        "§e" + map.getName(),
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
            timer.addStopListener(new CountdownStopListener(JumpyJumpSession.getInstance(), 60 * 20, this.SCOREBOARD));
            timer.start();
        }, 20 * 2);
    }

    public void stop() {
        this.messenger.messageAll("WIN!");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerDamageListener(), JumpyJumpSession.getInstance());
    }

    private void setUpScoreboard(Collection<JumpyJumpPlayer> players, int duration) {
        Objective objective = this.SCOREBOARD.registerNewObjective("distance", "dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(String.format("§6>> §eJumpyJump §6❘ §b%02d:%02d", duration / 60, duration % 60));
        players.forEach(jumpPlayer -> {
            objective.getScore("§/" + jumpPlayer.getPlayer().getDisplayName()).setScore(0);
        });
    }
}
