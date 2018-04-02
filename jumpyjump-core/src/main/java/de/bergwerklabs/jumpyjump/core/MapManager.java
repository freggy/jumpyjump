package de.bergwerklabs.jumpyjump.core;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import de.bergwerklabs.jumpyjump.api.JumpyJumpMap;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yannic Rieger on 03.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class MapManager {

    public JumpyJumpMap getMap() {
        return map;
    }

    private String mapName;
    private JumpyJumpMap map;

    public MapManager(Plugin plugin) {
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

    public void loadMap() {
        WorldCreator creator = new WorldCreator(this.mapName);
        World world = creator.createWorld();
        world.setAutoSave(false);
        world.setPVP(true);
        world.setDifficulty(Difficulty.NORMAL);
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("keepInventory", "false");
        world.setGameRuleValue("commandBlockOutput", "false");
        world.setSpawnFlags(false, false);
    }
}
