package de.bergwerklabs.jumpyjump.lobby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import de.bergwerklabs.jumpyjump.lobby.config.ConfigDeserializer;
import de.bergwerklabs.jumpyjump.lobby.config.ConfigSerializer;
import de.bergwerklabs.jumpyjump.lobby.listener.InventoryClickListener;
import de.bergwerklabs.jumpyjump.lobby.listener.InventoryCloseListener;
import de.bergwerklabs.jumpyjump.lobby.listener.PlayerInteractAtEntityListener;
import de.bergwerklabs.jumpyjump.lobby.listener.PlayerJoinListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.logging.Logger;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class Main extends JavaPlugin {

    private final Logger LOGGER = Bukkit.getLogger();
    private Config config = Config.DEFAULT_CONFIG;
    private LobbyMapManager mapManager;

    @Override
    public void onEnable() {
        this.setUpConfig();
        this.registerListeners();
        this.mapManager = new LobbyMapManager(null, null, null);
    }

    private void registerListeners() {
        final PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerInteractAtEntityListener(this.config, this.mapManager), this);
        manager.registerEvents(new PlayerJoinListener(this.config, this.mapManager), this);
        manager.registerEvents(new InventoryCloseListener(), this);
        manager.registerEvents(new InventoryClickListener(this.config, this.mapManager), this);
    }

    private void setUpConfig() {
        File configFile = new File(this.getDataFolder().getAbsolutePath() + "/config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting()
                                     .registerTypeAdapter(Config.class, new ConfigSerializer())
                                     .registerTypeAdapter(Config.class, new ConfigDeserializer())
                                     .create();

        try {
            if (!configFile.exists()) {
                LOGGER.info("Config file not present, creating it...");
                this.getDataFolder().mkdir();
                configFile.createNewFile();
                gson.toJson(this.config, Config.class, new JsonWriter(new FileWriter(configFile)));
            }
            else {
                LOGGER.info("Config found, reading it...");
                this.config = gson.fromJson(new FileReader(configFile), Config.class);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
