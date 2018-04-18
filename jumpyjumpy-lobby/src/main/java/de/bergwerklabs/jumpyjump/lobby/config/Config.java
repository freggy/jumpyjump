package de.bergwerklabs.jumpyjump.lobby.config;

import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.util.NPC;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Yannic Rieger on 06.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public class Config {

  public static final Config DEFAULT_CONFIG = new Config();

  private ItemStack challengeItem =
      new ItemStackBuilder(Material.NAME_TAG)
          .setName("§9§lHerausfordern§r §7<Rechtsklick>")
          .create();
  private ItemStack lobbyItem =
      new ItemStackBuilder(Material.WATCH)
          .setName("§e§lZurück zur Lobby§r §7<Rechtsklick>")
          .create();
  private ItemStack quickJoinItem =
      new ItemStackBuilder(Material.NETHER_STAR)
          .setName("§a§lQuickJoin§r §7<Rechtsklick>")
          .create();
  private ItemStack leaveQuickJoin =
      new ItemStackBuilder(Material.BARRIER)
          .setName("§c§lVerlasse §a§lQuickJoin§r §7<Rechtsklick>")
          .create();

  private NPC quickJoinNpc;
  private Location statsNpcLocation;

  private Config() {}

  Config(
      ItemStack challengeItem,
      ItemStack lobbyItem,
      ItemStack quickJoinItem,
      ItemStack leaveQuickJoin,
      Location statsNpcLocation,
      NPC quickJoinNpc) {
    this.challengeItem = challengeItem;
    this.lobbyItem = lobbyItem;
    this.quickJoinItem = quickJoinItem;
    this.leaveQuickJoin = leaveQuickJoin;
    this.quickJoinNpc = quickJoinNpc;
    this.statsNpcLocation = statsNpcLocation;
  }

  public ItemStack getChallengeItem() {
    return challengeItem;
  }

  public ItemStack getLobbyItem() {
    return lobbyItem;
  }

  public ItemStack getQuickJoinItem() {
    return quickJoinItem;
  }

  public ItemStack getLeaveQuickJoin() {
    return leaveQuickJoin;
  }

  public NPC getQuickJoinNpc() {
    return quickJoinNpc;
  }

  public Location getStatsNpcLocation() {
    return statsNpcLocation;
  }
}
