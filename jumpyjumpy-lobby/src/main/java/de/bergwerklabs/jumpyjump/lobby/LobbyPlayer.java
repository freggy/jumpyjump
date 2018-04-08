package de.bergwerklabs.jumpyjump.lobby;

import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import session.QuickJoinSession;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class LobbyPlayer  {

    public Player getPlayer() {
        return player;
    }

    public int getPriority() {
        return priority;
    }

    private Player player;
    private QuickJoinSession quickJoinSession;
    private int priority;

    public LobbyPlayer(Player player) {
        this.player = player;
        this.priority = 0; // Get group-order metadata from luckperms.
    }

    public void joinQuickJoinQueue() {
        this.quickJoinSession = new QuickJoinSession(this);
        Main.SERVER_MANAGER.addToQueue(this.quickJoinSession);
        final Inventory inventory = this.player.getInventory();
        inventory.clear();
        inventory.setItem(4, Main.getInstance().getLobbyConfig().getLeaveQuickJoin());
    }

    public void leaveQuickJoinQueue() {
        final Inventory inventory = this.player.getInventory();
        final Config config = Main.getInstance().getLobbyConfig();
        inventory.setItem(2, config.getChallengeItem());
        inventory.setItem(4, config.getQuickJoinItem());
        inventory.setItem(6, config.getLobbyItem());
        Main.SERVER_MANAGER.removeFromQueue(this.quickJoinSession);
    }
}
