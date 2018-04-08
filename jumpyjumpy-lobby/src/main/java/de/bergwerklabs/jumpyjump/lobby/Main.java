package de.bergwerklabs.jumpyjump.lobby;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.bergwerklabs.framework.commons.spigot.chat.messenger.PluginMessenger;
import de.bergwerklabs.jumpyjump.lobby.command.ChallengeAcceptCommand;
import de.bergwerklabs.jumpyjump.lobby.command.ChallengeDenyCommand;
import de.bergwerklabs.jumpyjump.lobby.config.Config;
import de.bergwerklabs.jumpyjump.lobby.config.ConfigDeserializer;
import de.bergwerklabs.jumpyjump.lobby.config.ConfigSerializer;
import de.bergwerklabs.jumpyjump.lobby.listener.*;
import de.bergwerklabs.jumpyjump.lobby.net.ServerManager;
import de.bergwerklabs.util.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import session.MapSession;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by Yannic Rieger on 06.04.2018.
 * <p>
 *
 * @author Yannic Rieger
 */
public class Main extends JavaPlugin {

    public static final PluginMessenger MESSENGER = new PluginMessenger("Lobby");
    public static final ServerManager SERVER_MANAGER = new ServerManager();
    public static final Map<UUID, LobbyPlayer> LOBBY_PLAYERS = new HashMap<>();

    public static Main getInstance() { return instance; }

    public Config getLobbyConfig() { return config; }

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
        Bukkit.getScheduler().runTaskTimerAsynchronously(this, () -> {
            MapSession.REQUESTS.forEach((key, value) -> {
                Long requested = value.getValue2();
                if (requested != null) { // can occur if the players are in a session
                    if ((System.currentTimeMillis() - requested) <= TimeUnit.SECONDS.toMillis(10)) {
                        Bukkit.getLogger().info("Remove entry");
                        MapSession.REQUESTS.remove(key);
                    }
                }
            });
        }, 20, 20 * 10); // TODO: make configurable

        this.getCommand("cacpt").setExecutor(new ChallengeAcceptCommand());
        this.getCommand("cdny").setExecutor(new ChallengeDenyCommand());
    }

    private void registerListeners() {
        final PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new PlayerInteractAtEntityListener(this.config, this.mapManager), this);
        manager.registerEvents(new PlayerJoinListener(this.config, this.mapManager), this);
        manager.registerEvents(new InventoryCloseListener(), this);
        manager.registerEvents(new InventoryClickListener(this.config, this.mapManager), this);
        manager.registerEvents(new PlayerInteractListener(this.config, this.mapManager), this);
        manager.registerEvents(new CancelListener(), this);
        manager.registerEvents(new PlayerQuitListener(), this);
        manager.registerEvents(new NpcInteractListener(), this);
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
                try (InputStreamReader reader = new InputStreamReader(new FileInputStream(configFile), StandardCharsets.UTF_8)) {
                    this.config = gson.fromJson(reader, Config.class);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
