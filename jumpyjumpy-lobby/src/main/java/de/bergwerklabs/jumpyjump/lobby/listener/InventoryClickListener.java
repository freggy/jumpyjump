package de.bergwerklabs.jumpyjump.lobby.listener;

import com.google.common.collect.Lists;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import de.bergwerklabs.jumpyjump.lobby.LobbyMapManager;
import de.bergwerklabs.jumpyjump.lobby.Main;
import session.MapSession;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        else if (itemStack.getType() != Material.STAINED_CLAY) {
            event.setCancelled(true);
            return;
        }

        final ItemMeta meta = itemStack.getItemMeta();
        final String displayName = meta.getDisplayName();
        final Player player = (Player)event.getWhoClicked();

        if (displayName.equals(Difficulty.EASY.getDisplayName())) {
            this.createAndShowMapInventory(player, this.mapManager.getEasyMaps());
        }
        else if (displayName.equals(Difficulty.MEDIUM.getDisplayName())) {
            this.createAndShowMapInventory(player, this.mapManager.getMediumMaps());
        }
        else if (displayName.equals(Difficulty.HARD.getDisplayName())) {
            this.createAndShowMapInventory(player, this.mapManager.getHardMaps());
        }
    }

    private void handleMapSelection(InventoryClickEvent event) {
        final HumanEntity player = event.getWhoClicked();
        final MapSession session = MapSession.SESSIONS.get(player.getUniqueId());
        final ItemStack item = event.getCurrentItem();

        if (item == null || item.getType() != Material.EMPTY_MAP) {
            event.setCancelled(true);
            return;
        }

        final ItemMeta meta = item.getItemMeta();
        final List<String> lore = meta.getLore();
        final String mapName = meta.getDisplayName().replace("§e", "");

        String id = lore.get(2).replace("§7", "");
        player.closeInventory();

        this.displaySelectedMap((Player)player, lore, mapName);
        this.displaySelectedMap(session.getChallenged().getPlayer(), lore, mapName);
        session.requestMapServer(id);
    }

    private void createAndShowMapInventory(Player player, Collection<JumpyJumpMap> maps) {
        Inventory inventory = this.createMapSelectionInventory(player, maps);
        player.playSound(player.getEyeLocation(), Sound.CLICK, 100 , 1);
        MapSession.SESSIONS.get(player.getUniqueId()).setOpenInventory(inventory);
        player.openInventory(inventory);
    }

    private Inventory createMapSelectionInventory(HumanEntity player, Collection<JumpyJumpMap> mapSet) {
        List<JumpyJumpMap> maps = new ArrayList<>(mapSet);
        int size = Lists.partition(maps, 9).size() * 9;
        Inventory inventory = Bukkit.createInventory(null, size, "§6>> §eJumpyJump §6| §bAuswahl");
        List<Integer> slots = Arrays.stream(IntStream.rangeClosed(0, maps.size() - 1).toArray()).boxed().collect(Collectors.toList());
        this.animateLoading((Player)player, maps, inventory, slots);
        return inventory;
    }

    private void animateLoading(Player player, List<JumpyJumpMap> maps, Inventory inventory, List<Integer> slots) {
        if (maps.size() == 0) return;
        Random random = new Random();

        int index = random.nextInt(maps.size());
        int slot = slots.get(index);
        JumpyJumpMap map = maps.get(index);

        maps.remove(index);
        slots.remove(index);

        final ItemStack mapItem = new ItemStackBuilder(Material.EMPTY_MAP).setName("§e" + map.getName()).create();
        final ItemMeta meta = mapItem.getItemMeta();
        final List<String> lore = Arrays.asList(
                "§7Builder: §b" + StringUtils.join(map.getBuilder(), ", "),
                "§7Difficulty: " + map.getDifficulty().getDisplayName(),
                "§7Id: §b" + map.getId()
        );

        meta.setLore(lore);
        mapItem.setItemMeta(meta);
        inventory.setItem(slot, mapItem);
        player.playSound(player.getEyeLocation(), Sound.ITEM_PICKUP, 100 ,1);

        Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
            this.animateLoading(player, maps, inventory, slots);
        }, 1L);
    }

    private void displaySelectedMap(Player player, List<String> metadata, String name) {
        player.sendMessage("");
        player.sendMessage("§6§m------§b Ausgewählte Map §6§m------");
        player.sendMessage("§7Name: §b" + name);
        metadata.forEach(player::sendMessage);
        player.sendMessage("§6§m---------------------------");
        player.sendMessage("");
        player.playSound(player.getEyeLocation(), Sound.ANVIL_LAND, 50, 50);
    }

}
