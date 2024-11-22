package milo.listeners;

import milo.MiLogin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import java.util.Map;

public class PlayerMove implements Listener {

    private final MiLogin plugin;
    FileConfiguration config;
    private final Map<Player, Integer> playerMoveCount;

    public PlayerMove(MiLogin plugin, Map<Player, Integer> playerMoveCount) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.playerMoveCount = playerMoveCount;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        // Block movement in the player if this dont autheticante.
        if (!plugin.isAuthenticated(event.getPlayer())) {
            Player player = event.getPlayer();

            // Verify if the player is in movement counter.
            if (playerMoveCount.containsKey(player)) {
                // Increase the counter.
                int moveCount = playerMoveCount.get(player) + 1;
                playerMoveCount.put(player, moveCount);

                // If the player move more the 3 times, this remove from server.
                if (moveCount > 3) {
                    player.kickPlayer(plugin.getMessage("move"));
                    playerMoveCount.remove(player);  // Eliminar el jugador del mapa
                }
            }

            event.setCancelled(true);
            event.getPlayer().sendMessage(plugin.getMessage("move-warn"));
        }
    }
}
