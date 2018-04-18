package de.bergwerklabs.jumpyjump.lobby.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Created by Yannic Rieger on 07.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class CancelListener implements Listener {

  @EventHandler
  private void onPlayerDamage(EntityDamageEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  private void onPlayerDamage(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onLeaveDecay(LeavesDecayEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  private void onPlayerDropItemListener(PlayerDropItemEvent event) {
    event.setCancelled(true);
  }

  @EventHandler
  public void onBlockFade(BlockFadeEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onBlockBurn(BlockBurnEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onWeatherChange(WeatherChangeEvent e) {
    e.setCancelled(true);
  }

  @EventHandler
  public void onExplode(EntityExplodeEvent e) {
    e.blockList().clear();
  }

  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent e) {
    e.setCancelled(true);
  }
}
