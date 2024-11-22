package milo.commands;

import milo.MiLogin;
import milo.database.Database;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Register implements CommandExecutor {

    private final MiLogin plugin;
    FileConfiguration config;
    Database database;

    public Register(MiLogin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.database = plugin.getDatabase();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length < 2) {
                player.sendMessage(config.getString("register-auth"));
                return false;
            }

            // Get the values.
            String pass = args[0];
            String confirmPass = args[1];

            // Validate the passwords is equals.
            if (pass.equals(confirmPass)) {
                // Save the player in database.
                this.database.registerUser(player.getName(), pass);
                player.sendMessage(plugin.getMessage("register-auth-success"));
            } else {
                player.sendMessage(plugin.getMessage("register-auth-fail"));
            }
            return true;
        }
        return false;
    }
}