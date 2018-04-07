package de.bergwerklabs.jumpyjump.lobby.listener;

import com.google.common.collect.Lists;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        if (inventory == null) return;
        if (inventory.getTitle() == null) return;

        if (inventory.getTitle().contains("§bSchwierigkeit")) {
            this.handleDifficultySelection(event);
        }
        else if (inventory.getTitle().contains("§bAuswahl")) {
            this.handleMapSelection(event);
        }
        event.setCancelled(true);
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
            this.createAndShowMapInventory(event.getWhoClicked(), this.mapManager.getEasyMaps());
        }
        else if (displayName.equals(Difficulty.MEDIUM.getDisplayName())) {
            this.createAndShowMapInventory(event.getWhoClicked(), this.mapManager.getMediumMaps());
        }
        else if (displayName.equals(Difficulty.HARD.getDisplayName())) {
            this.createAndShowMapInventory(event.getWhoClicked(), this.mapManager.getHardMaps());
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
        String id = meta.getLore().get(2).replace("§7", "");
        session.requestMapServer(id);
        player.closeInventory();
        Bukkit.broadcastMessage("YEY");
    }

    private Inventory createMapSelectionInventory(Set<JumpyJumpMap> mapSet) {
        int size = Lists.partition(new ArrayList<>(mapSet), 9).size() * 9;
        Inventory inventory = Bukkit.createInventory(null, size, "§6>> §eJumpyJump §6| §bAuswahl");
        AtomicInteger slot = new AtomicInteger();

        mapSet.forEach(map -> {
            final ItemStack mapItem = new ItemStackBuilder(Material.EMPTY_MAP).setName("§e" + map.getName()).create();
            final ItemMeta meta = mapItem.getItemMeta();
            final List<String> lore = Arrays.asList(
                    "§7Builder: §b" + StringUtils.join(map.getBuilder(), ","),
                    "§7Difficulty: " + map.getDifficulty().getDisplayName(),
                    "§7Id: §b" + map.getId()
            );
            meta.setLore(lore);
            mapItem.setItemMeta(meta);
            inventory.setItem(slot.get(), mapItem);
            slot.getAndIncrement();
        });
        return inventory;
    }

    private void createAndShowMapInventory(HumanEntity player, Set<JumpyJumpMap> maps) {
        System.out.println(this.mapManager);
        System.out.println(this.mapManager.getHardMaps());

        Inventory inventory = this.createMapSelectionInventory(maps);
        // TODO: play sound
        MapSelectSession.SESSIONS.get(player.getUniqueId()).setOpenInventory(inventory);
        player.openInventory(inventory);
    }
}
