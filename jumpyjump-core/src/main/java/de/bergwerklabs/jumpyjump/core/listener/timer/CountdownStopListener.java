package de.bergwerklabs.jumpyjump.core.listener.timer;

import de.bergwerklabs.framework.commons.spigot.general.BossBar;
import de.bergwerklabs.framework.commons.spigot.general.timer.LabsTimer;
import de.bergwerklabs.framework.commons.spigot.general.timer.event.LabsTimerStopEvent;
import de.bergwerklabs.jumpyjump.api.JumpyJumpPlayer;
import de.bergwerklabs.jumpyjump.core.Common;
import de.bergwerklabs.jumpyjump.core.DisplayFailsTask;
import de.bergwerklabs.jumpyjump.core.JumpyJumpSession;
import de.bergwerklabs.jumpyjump.core.listener.JumpyJumpListener;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * Created by Yannic Rieger on 05.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class CountdownStopListener extends JumpyJumpListener implements Consumer<LabsTimerStopEvent> {

    private DisplayFailsTask displayFailsTask;
    private LabsTimer timer;
    private Collection<JumpyJumpPlayer> players;

    public CountdownStopListener(JumpyJumpSession session) {
        super(session);
        this.players = this.session.getRegistry().getPlayers().values();
        this.displayFailsTask = new DisplayFailsTask(JumpyJumpSession.getInstance());

        int duration = 60 * 20; // TODO: read from config
        final BossBar bossBar = new BossBar(String.format("§7Verbleibende Zeit: §b%02d:%02d", duration / 60, duration % 60));
        this.timer = new LabsTimer(duration, timeLeft -> {
            String timeString = String.format("§b%02d:%02d", timeLeft / 60, timeLeft % 60);
            players.forEach(player -> {
                final Player spigotPlayer = player.getPlayer();
                bossBar.sendBar(spigotPlayer);
                final float timePercantage = ((float)timeLeft / (float)duration);
                final float healthPercantage = 200  * timePercantage;
                bossBar.updateBar(spigotPlayer, "§eVerbleibende Zeit: " + timeString, healthPercantage);

                if (((float)timeLeft / 60F) % 10 == 0) {
                    this.game.getMessenger().message("Noch " + timeString + " §7Minuten.", spigotPlayer);
                    spigotPlayer.playSound(spigotPlayer.getEyeLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
                else if (timeLeft <= 5 && timeLeft != 1) {
                    this.game.getMessenger().message("§b" + String.valueOf(timeLeft), spigotPlayer);
                    spigotPlayer.playSound(spigotPlayer.getEyeLocation(), Sound.NOTE_BASS, 1.0F, 1.0F);
                }
                else if (timeLeft == 1) bossBar.remove(spigotPlayer);
            });
        });
        this.timer.addStopListener(new RoundTimerStopListener(this.session));
    }

    @Override
    public void accept(LabsTimerStopEvent labsTimerStopEvent) {
        players.forEach(player -> {
            final Player playerObject = player.getPlayer();
            player.unfreeze();
            playerObject.playSound(playerObject.getEyeLocation(), Sound.COW_HURT, 100, 1);
            Common.createAndSendTitle(playerObject, "§bLOS!", "");
            this.timer.start();
        });
        Bukkit.getScheduler().runTaskTimerAsynchronously(JumpyJumpSession.getInstance(), this.displayFailsTask, 0L, 20L);
    }
}
