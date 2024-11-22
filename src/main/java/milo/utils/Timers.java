package milo.utils;

import milo.MiLogin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class Timers {

    public static void initTimers(MiLogin plugin, Map<Player, Integer> playerMoveCount, Player player, Map<Player, BukkitRunnable> playerTimers) {

        FileConfiguration config = plugin.getConfig();

        // Start the timer for x time seconds (in the config)
        BukkitRunnable timer = new BukkitRunnable() {
            @Override
            public void run() {
                // If the player dont move in the x seconds (in the config) remove from server.
                if (playerMoveCount.containsKey(player)) {
                    player.kickPlayer(config.getString("messages.dont-move-auth"));
                    playerMoveCount.remove(player); // Eliminar jugador del mapa
                }
            }
        };

        // Save the timer.
        playerTimers.put(player, timer);

        // Execute the timer.
        timer.runTaskLater(plugin, config.getInt("auth-timeout") * 20); // 60 segundos * 20 ticks por segundo = 1200 ticks
    }
}
