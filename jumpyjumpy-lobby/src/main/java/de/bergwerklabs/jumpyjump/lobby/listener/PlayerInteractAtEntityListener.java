package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

        final MapSelectSession potenial = MapSelectSession.SESSIONS.get(other.getUniqueId());
        if (potenial != null) {
            Main.MESSENGER.message("§cDieser Spieler fordert dich bereits heraus.", player);
            return;
        }

        final Inventory inventory = this.createInventory();
        final MapSelectSession session = new MapSelectSession(player, other);
        session.setOpenInventory(inventory);
        player.openInventory(inventory);
    }

    private Inventory createInventory() {
        // TODO: get rank color
        final Inventory inventory = Bukkit.createInventory(null, 9 * 3,  "§6>> §eJumpyJump §6| " +
                "§bSchwierigkeit");
        ItemStack easy = new ItemStackBuilder(Material.STAINED_CLAY)
                .setName(Difficulty.EASY.getDisplayName())
                .setData((byte)5)
                .create();

        ItemStack medium = new ItemStackBuilder(Material.STAINED_CLAY)
                .setName(Difficulty.MEDIUM.getDisplayName())
                .setData((byte)4)
                .create();

        ItemStack hard = new ItemStackBuilder(Material.STAINED_CLAY)
                .setName(Difficulty.HARD.getDisplayName())
                .setData((byte)14)
                .create();

        ItemStack surround = new ItemStackBuilder(Material.STAINED_GLASS_PANE).setData((byte)15).create();

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            if (slot == 10) inventory.setItem(slot, easy);
            else if (slot == 13) inventory.setItem(slot, medium);
            else if (slot == 16) inventory.setItem(slot, hard);
            else inventory.setItem(slot, surround);
        }
        return inventory;
    }

}
