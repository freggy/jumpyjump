package de.bergwerklabs.jumpyjump.api;

import org.bukkit.ChatColor;

/**
 * Created by Yannic Rieger on 06.04.2018.
 *
 * <p>
 *
 * @author Yannic Rieger
 */
public enum Difficulty {
  EASY(ChatColor.GREEN),
  MEDIUM(ChatColor.YELLOW),
  HARD(ChatColor.RED);

  private ChatColor color;

  Difficulty(ChatColor color) {
    this.color = color;
  }

  public ChatColor getColor() {
    return color;
  }

  public String getDisplayName() {
    return this.color + this.name();
  }
}
