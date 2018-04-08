package de.bergwerklabs.jumpyjump.lobby.command;

import de.bergwerklabs.framework.commons.misc.Tuple;
import de.bergwerklabs.framework.commons.spigot.item.ItemStackBuilder;
import de.bergwerklabs.jumpyjump.api.Difficulty;
import de.bergwerklabs.jumpyjump.lobby.Main;
import de.bergwerklabs.jumpyjump.lobby.MapSelectSession;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.security.acl.LastOwnerException;
import java.util.UUID;

/**
 * Created by Yannic Rieger on 08.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class ChallengeAcceptCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) return false;
        if (args.length == 0) return false;
        Player sender = (Player)commandSender;
        String playerName = args[0];
        Player player =  Bukkit.getPlayer(playerName);


        if (player == null) {
            Main.MESSENGER.message("§cDieser Spieler nicht in der Lobby.", sender);
            return false;
        }

        final Tuple<UUID, Long> requested = MapSelectSession.REQUESTS.get(player.getUniqueId());

        if  (requested != null && !requested.getValue1().equals(sender.getUniqueId())) {
            Main.MESSENGER.message("§cDieser Spieler muss dich erst herausfordern.", sender);
            return false;
        }

        MapSelectSession.REQUESTS.remove(player.getUniqueId());
        final MapSelectSession session = new MapSelectSession(player, sender);
        MapSelectSession.SESSIONS.put(player.getUniqueId(), session);
        final Inventory inventory = this.createInventory();
        session.setOpenInventory(inventory);
        player.openInventory(inventory);
        sender.playSound(sender.getEyeLocation(), Sound.CLICK, 100 , 1);
        Main.MESSENGER.message("Du hast die Herausforderung §aangenommen§7.", sender);
        return false;
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
