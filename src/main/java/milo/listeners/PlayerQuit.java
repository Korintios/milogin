package milo.listeners;

import milo.MiLogin;
import milo.database.Database;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final MiLogin plugin;
    Database database;

    public PlayerQuit(MiLogin plugin) {
        this.plugin = plugin;
        this.database = plugin.getDatabase();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        this.database.registerLog(player.getName(), "QUIT");
    }
}