package milo.listeners;

import milo.MiLogin;
import milo.database.Database;
import milo.utils.PlayerEffects;
import milo.utils.Timers;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class PlayerJoin implements Listener {

    private final MiLogin plugin;
    FileConfiguration config;
    Database database;
    private final Map<Player, Integer> playerMoveCount;
    private final Map<Player, BukkitRunnable> playerTimers;

    public PlayerJoin(MiLogin plugin, Map<Player, BukkitRunnable> playerTimers, Map<Player, Integer> playerMoveCount) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.database = plugin.getDatabase();
        this.playerMoveCount = playerMoveCount;
        this.playerTimers = playerTimers;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        playerMoveCount.put(player, 0);

        Timers.initTimers(plugin, playerMoveCount, player, playerTimers);

        this.database.registerLog(player.getName(), "JOIN");
        PlayerEffects playerEffects = new PlayerEffects(player);
        String message = plugin.getMessage("welcome");
        player.sendMessage(message);
        playerEffects.addPlayerEffects();
    }
}
