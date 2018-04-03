package de.bergwerklabs.jumpyjump.core;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.io.FileReader;

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

    /**
     * @param plugin the {@link JumpyJumpSession}
     */
    MapManager(Plugin plugin) {
        this.mapName = System.getenv("WORLD");

        try {
            final JsonParser parser = new JsonParser();
            final FileReader reader = new FileReader(plugin.getDataFolder() + "/maps/" + this.mapName + "/config.json");
            final JsonElement element = parser.parse(reader);
            this.map = JumpyJumpMap.fromJson(element.getAsJsonObject());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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
        WorldCreator creator = new WorldCreator(this.mapName);
        World world = creator.createWorld();
        world.setAutoSave(false);
        world.setPVP(false);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("commandBlockOutput", "false");
        world.setSpawnFlags(false, false);
    }
}
