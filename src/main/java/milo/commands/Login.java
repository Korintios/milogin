package milo.commands;

import milo.MiLogin;
import milo.database.Database;
import milo.utils.PlayerEffects;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Map;

public class Login implements CommandExecutor {

    private final MiLogin plugin;
    Database database;
    Map<Player, BukkitRunnable> playerTimers;
    Map<Player, Integer> playerMoveCount;
    FileConfiguration config;

    public Login(MiLogin plugin, Map<Player, BukkitRunnable> playerTimers, Map<Player, Integer> playerMoveCount) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.playerTimers = playerTimers;
        this.playerMoveCount = playerMoveCount;
        this.database = plugin.getDatabase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            // Get the player.
            Player player = (Player) sender;
            PlayerEffects playerEffects = new PlayerEffects(player);

            //Verificamos los argumentos recibidos.
            if (args.length < 1) {
                player.sendMessage(plugin.getMessage("login-auth"));
                return false;
            }

            // Get the values.
            String pass = args[0];

            boolean loginStatus = database.authenticateUser(player.getName(), pass);

            if (!loginStatus) {
                player.sendMessage(plugin.getMessage("login-auth-fail"));
                return false;
            }

            if (playerTimers.containsKey(player)) {
                plugin.authenticatePlayer(player);
                playerTimers.get(player).cancel();
                playerTimers.remove(player);
                playerMoveCount.remove(player);
                playerEffects.removePlayerEffects();
                player.sendMessage(plugin.getMessage("messages.login-auth-success"));
                return true;
            }
        }
        return false;
    }
}