package de.bergwerklabs.jumpyjump.lobby.core;

import de.bergwerklabs.framework.commons.spigot.title.Title;
import org.bukkit.entity.Player;

/**
 * Created by Yannic Rieger on 05.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class Common {

    public static void createAndSendTitle(Player player, String title, String subtitle) {
        new Title(title, subtitle, 4, 4, 40).display(player);
    }

}
