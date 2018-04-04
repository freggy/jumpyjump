package de.bergwerklabs.jumpyjump.core;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 * Takes care of map selection and loading. The map that will be loaded is defined
 * in the environment variable {@code MAP}. It can be retrieved by using {@link System#getenv(String)}.
 *
 * @author Yannic Rieger
 */
public class MapManager {

    public JumpyJumpMap getMap() {
        return map;
    }

    private String mapName;
    private JumpyJumpMap map;
    private Plugin plugin;

    /**
     * @param plugin the {@link JumpyJumpSession}
     */
    MapManager(Plugin plugin) {
        this.mapName = "sj-1";//System.getenv("WORLD");
        this.plugin = plugin;
    }

    /**
     * Loads the map and applies the following settings:
     * <ul>
     *     <li>AutoSave = true
     *     <li>PVP = false
     *     <li>Difficulty = PEACEFUL
     *     <li>doDaylightCycle = false
     *     <li>mobGriefing = false
     *     <li>doMobSpawning = false
     *     <li>doFireTick = false
     *     <li>keepInventory = true
     *     <li>commandBlockOutput = false
     *     <li>setSpawnFlags = (false, false)
     * </ul>
     */
    public void loadMap() {

        try {
            new File(this.mapName).mkdir();
            FileUtils.copyDirectory(new File(this.plugin.getDataFolder() + "/maps/" + this.mapName), new File(this.mapName));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        WorldCreator wc = new WorldCreator(this.mapName);
        World world = wc.createWorld();
        world.setAutoSave(false);
        world.setPVP(false);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("commandBlockOutput", "false");
        world.setSpawnFlags(false, false);

        try {
            final JsonParser parser = new JsonParser();
            final FileReader reader = new FileReader(this.plugin.getDataFolder() + "/maps/" + this.mapName + "/config.json");
            final JsonElement element = parser.parse(reader);
            this.map = JumpyJumpMap.fromJson(element.getAsJsonObject());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
