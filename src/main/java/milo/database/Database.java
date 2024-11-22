package milo.database;

import milo.MiLogin;
import milo.services.WebHook;
import milo.utils.Password;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class Database {

    MiLogin plugin;
    WebHook webHook;
    private Connection connection;
    ZoneId zoneId = ZoneId.of("America/Bogota");
    ZonedDateTime now = LocalDateTime.now().atZone(zoneId);

    // Constructor
    public Database(MiLogin plugin) {
        if (plugin.getConfig().getBoolean("discord.enable")) {
            this.plugin = plugin;
            this.webHook = plugin.getWebhook();
        }
        try {
            // Create the connection with the database.
            File pluginFolder = new File("plugins/MiLogin");
            if (!pluginFolder.exists()) {
                pluginFolder.mkdirs();
            }

            // Route for the database file.
            String url = "jdbc:sqlite:" + pluginFolder + "/database.db";
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void initTables() {
        try (
            InputStream input = getClass().getResourceAsStream("/schema.sql");
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            Statement statement = connection.createStatement()
        ) {
            String line;
            StringBuilder sql = new StringBuilder();

            // Read the ".sql" file.
            while ((line = reader.readLine()) != null) {
                sql.append(line);
                if (line.endsWith(";")) {
                    statement.execute(sql.toString());
                    sql = new StringBuilder();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password) {
        String sql = "INSERT INTO users (username, pass, registrationDate) VALUES (?, ?, ?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy");
        String encryptedPassword = Password.encryptPassword(password);

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);
            pstmt.setString(3, now.format(formatter));
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? LIMIT 1";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    String hashedPassword = rs.getString("pass");
                    boolean isEquals = Password.checkPassword(password, hashedPassword);
                    if (isEquals) {
                        return true;
                    }
                }
                return false;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean registerLog(String username, String type) {
        String sql = "INSERT INTO log VALUES (null, ?, ?, ?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy h:mm a");

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, now.format(formatter));
            pstmt.setString(3, type);
            pstmt.executeUpdate();

            if (webHook != null) {
                String TYPE_MESSAGE = type.equals("JOIN") ? "join-webhook-message" : "exit-webhook-message"; // Cambié la comparación para usar equals
                String formattedDateText = now.format(formatter);
                webHook.sendWebhook(String.format(plugin.getMessage(TYPE_MESSAGE), username, formattedDateText));
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
