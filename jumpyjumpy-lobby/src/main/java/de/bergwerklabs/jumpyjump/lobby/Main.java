package de.bergwerklabs.jumpyjump.lobby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bergwerklabs.framework.commons.spigot.chat.messenger.PluginMessenger;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import de.bergwerklabs.jumpyjump.lobby.config.ConfigSerializer;
import de.bergwerklabs.jumpyjump.lobby.listener.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class Main extends JavaPlugin {

    public static Main getInstance() { return instance; }
    public static final PluginMessenger MESSENGER = new PluginMessenger("Lobby");

    private static Main instance;
    private final Logger LOGGER = Bukkit.getLogger();
    private Config config = Config.DEFAULT_CONFIG;
    private LobbyMapManager mapManager;

    @Override
    public void onEnable() {
        instance = this;
        this.setUpConfig();
        this.mapManager = new LobbyMapManager(null, null, null);
        this.registerListeners();
    }

    private void registerListeners() {
        final PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerInteractAtEntityListener(this.config, this.mapManager), this);
        manager.registerEvents(new PlayerJoinListener(this.config, this.mapManager), this);
        manager.registerEvents(new InventoryCloseListener(), this);
        manager.registerEvents(new InventoryClickListener(this.config, this.mapManager), this);
        manager.registerEvents(new CancelListener(), this);
        manager.registerEvents(new PlayerDropItemListener(), this);

    }

    private void setUpConfig() {
        File configFile = new File(this.getDataFolder().getAbsolutePath() + "/config.json");
        Gson gson = new GsonBuilder().setPrettyPrinting()
                                     .registerTypeAdapter(Config.class, new ConfigSerializer())
                                     //.registerTypeAdapter(Config.class, new ConfigDeserializer())
                                     .create();

        try {
            if (!configFile.exists()) {
                LOGGER.info("Config file not present, creating it...");
                this.getDataFolder().mkdir();
                configFile.createNewFile();

                try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8)) {
                    String json = gson.toJson(this.config, Config.class);
                    writer.write(json);
                    writer.flush();
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
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
