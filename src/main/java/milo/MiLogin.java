package milo;

import milo.commands.Login;
import milo.commands.Register;
import milo.database.Database;
import milo.listeners.PlayerJoin;
import milo.listeners.PlayerMove;
import milo.listeners.PlayerQuit;
import milo.services.WebHook;
import milo.utils.PlayerEffects;
import milo.utils.Timers;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MiLogin extends JavaPlugin {

    // Services
    private WebHook webhook;
    private Database database;

    // Players
    private final Set<Player> authenticatedPlayers = new HashSet<>();
    private final Map<Player, Integer> playerMoveCount = new HashMap<>();
    private final Map<Player, BukkitRunnable> playerTimers = new HashMap<>();

    // Language
    private YamlConfiguration langFile;

    @Override
    public void onEnable() {
        saveDefaultConfig(); // Si no existe config.yml, lo crea automáticamente.

        if (getConfig().getString("lang") == null) {
            getLogger().severe("Failed to load config.yml.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        } else {
            getLogger().info("Config loaded successfully.");

            File langFolder = new File(this.getDataFolder(), "lang");
            if (!langFolder.exists()) {
                langFolder.mkdirs(); // Crea la carpeta si no existe
            }

            File languageFile = new File("plugins/MiLogin/lang/" + getConfig().getString("lang") + ".yml");
            YamlConfiguration langConfig;
            if (languageFile.exists()) {
                langConfig = YamlConfiguration.loadConfiguration(languageFile);
            } else {
                this.saveResource("lang/" + getConfig().getString("lang") + ".yml", false);
                langConfig = YamlConfiguration.loadConfiguration(languageFile);
            }
            langFile = langConfig;
            getLogger().info("Languages loaded successfully.");
        }

        if (getConfig().contains("discord.enable") && getConfig().getBoolean("discord.enable")) {
            getLogger().info("Discord Webhook enabled.");
            webhook = new WebHook(this);
        }

        database = new Database(this);

        this.database.initTables();

        if (this.database.getConnection() != null) {
            getLogger().info("Database connection established");
        } else {
            getLogger().info("The database could not connect to the plugin");
        }

        getServer().getPluginManager().registerEvents(new PlayerJoin(this, playerTimers, playerMoveCount), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this, playerMoveCount), this);
        getServer().getPluginManager().registerEvents(new PlayerQuit(this), this);
        this.getCommand("register").setExecutor(new Register(this));
        this.getCommand("login").setExecutor(new Login(this, playerTimers, playerMoveCount));

        getLogger().info("¡MiLogin Loaded Successfully!");

        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerEffects playerEffects = new PlayerEffects(player);
            playerEffects.addPlayerEffects();
            Timers.initTimers(this, playerMoveCount, player, playerTimers);
        }
    }

    @Override
    public void onDisable() {
        // Este código se ejecutará cuando el plugin se desactive.
        if (database != null) {
            this.database.close();
        }
        getLogger().info("¡Plugin Off!");
    }

    public void authenticatePlayer(Player player) {
        authenticatedPlayers.add(player);
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    public boolean isAuthenticated(Player player) {
        return authenticatedPlayers.contains(player);
    }

    public String getMessage(String message) {
        return langFile.getString(message);
    }

    public Database getDatabase() {
        return database;
    }

    public WebHook getWebhook() {
        return webhook;
    }
}
