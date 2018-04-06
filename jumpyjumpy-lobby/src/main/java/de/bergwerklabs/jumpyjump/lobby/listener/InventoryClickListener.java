package de.bergwerklabs.jumpyjump.lobby.listener;

import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class InventoryClickListener extends LobbyListener {

    public InventoryClickListener(Config config, LobbyMapManager mapManager) {
        super(config, mapManager);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        final Inventory inventory = event.getClickedInventory();
        if (inventory.getTitle().contains("Schwierigkeit")) {
            this.handleDifficultySelection(event);
        }
        else if (inventory.getTitle().contains("Auswahl")) {
            this.handleMapSelection(event);
        }
    }

    private void handleDifficultySelection(InventoryClickEvent event) {
        final ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null) {
            event.setCancelled(true);
            return;
        }
        else if (itemStack.getType() != Material.STAINED_GLASS) {
            event.setCancelled(true);
            return;
        }

        final ItemMeta meta = itemStack.getItemMeta();
        final String displayName = meta.getDisplayName();

        if (displayName.equals(Difficulty.EASY.getDisplayName())) {
            this.createAndShowMapInventory(event.getWhoClicked());
        }
        else if (displayName.equals(Difficulty.MEDIUM.getDisplayName())) {
            this.createAndShowMapInventory(event.getWhoClicked());
        }
        else if (displayName.equals(Difficulty.HARD.getDisplayName())) {
            this.createAndShowMapInventory(event.getWhoClicked());
        }
    }

    private void handleMapSelection(InventoryClickEvent event) {
        final HumanEntity player = event.getWhoClicked();
        final MapSelectSession session = MapSelectSession.SESSIONS.get(player.getUniqueId());
        final ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() != Material.EMPTY_MAP) {
            event.setCancelled(true);
            return;
        }
        final ItemMeta meta = item.getItemMeta();
        String id = meta.getLore().get(0); // TODO: use id index
        session.requestMapServer(id);
        // TODO: player output
    }

    private Inventory createMapSelectionInventory(Set<JumpyJumpMap> mapSet) {
        int size = Math.round(mapSet.size() / 9F);
        Inventory inventory = Bukkit.createInventory(null, size, "§eAuswahl");
        AtomicInteger slot = new AtomicInteger();

        // TODO: add map info to lore -> MAP ID -> MD5(MAP_NAME + CREATOR + DIFFICULTY)

        mapSet.forEach(map -> {
            ItemStack mapItem = new ItemStackBuilder(Material.EMPTY_MAP).setName("§b" + map.getName()).create();
            inventory.setItem(slot.get(), mapItem);
            slot.getAndIncrement();
        });
        return inventory;
    }

    private void createAndShowMapInventory(HumanEntity player) {
        Inventory inventory = this.createMapSelectionInventory(this.mapManager.getHardMaps());
        // TODO: play sound
        player.openInventory(inventory);
    }
}
