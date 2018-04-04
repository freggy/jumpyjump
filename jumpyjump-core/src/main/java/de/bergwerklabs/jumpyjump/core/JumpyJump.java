package de.bergwerklabs.jumpyjump.core;

import com.comphenix.protocol.wrappers.EnumWrappers;
import de.bergwerklabs.framework.bedrock.api.LabsGame;
import de.bergwerklabs.framework.bedrock.api.PlayerRegistry;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.nms.packet.v1_8.WrapperPlayServerCustomSoundEffect;
import de.bergwerklabs.framework.commons.spigot.nms.packet.v1_8.WrapperPlayServerNamedSoundEffect;
import de.bergwerklabs.framework.commons.spigot.title.Title;
import de.bergwerklabs.jumpyjump.api.Course;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.listener.PlayerInteractListener;
import de.bergwerklabs.jumpyjump.core.listener.PlayerMoveListener;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by Yannic Rieger on 02.04.2018.
 * <p>
 * Represents the JumpyJump game.
 *
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

    public void start(PlayerRegistry<JumpyJumpPlayer> registry) {
        JumpyJumpSession.getInstance().setRegistry(registry);
        this.startTime = System.currentTimeMillis();
        final JumpyJumpMap map = JumpyJumpSession.getInstance().getMapManager().getMap();
        final Iterator<Course> courses = JumpyJumpSession.getInstance().getMapManager().getMap().getCourses().iterator();
        this.registerListeners();

        registry.getPlayers().values().forEach(player -> {
            if (courses.hasNext()) {
                final Course course = courses.next();
                player.setCourse(course);
                player.getPlayer().teleport(course.getSpawn().clone().add(0.5F, 0F, 0.5F));
                player.freeze();
            }
        });


        Bukkit.getScheduler().runTaskLater(JumpyJumpSession.getInstance(), () -> {
            registry.getPlayers().values().forEach(jumpPlayer -> {
                this.createAndSendTitle(
                        jumpPlayer.getPlayer(),
                        "§e" + map.getName(),
                        "§b" + StringUtils.join(map.getBuilder(), ", ")
                );
            });
        }, 10);

        Bukkit.getScheduler().runTaskLater(JumpyJumpSession.getInstance(), () -> {
            LabsTimer timer = new LabsTimer(3, (timeLeft) -> {
                registry.getPlayers().values().forEach(player -> {
                    final Player playerObject = player.getPlayer();
                    this.createAndSendTitle(playerObject, "§a" + String.valueOf(timeLeft), "");
                    playerObject.playSound(playerObject.getEyeLocation(), Sound.CLICK, 100, 1);
                });
            });

            timer.addStopListener(event -> {
                registry.getPlayers().values().forEach(player -> {
                    final Player playerObject = player.getPlayer();
                    player.unfreeze();
                    playerObject.playSound(playerObject.getEyeLocation(), Sound.COW_HURT, 100, 1);
                });
                this.messenger.messageAll("§bLOS!");
            });

            timer.start();
        }, 20 * 2);
    }

    public void stop() {
        this.messenger.messageAll("WIN!");
    }

    private void registerListeners() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(JumpyJumpSession.getInstance()), JumpyJumpSession.getInstance());
    }

    private void createAndSendTitle(Player player, String title, String subtitle) {
        new Title(title, subtitle, 4, 4, 40).display(player);
    }
}
