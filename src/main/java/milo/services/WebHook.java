package milo.services;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import milo.MiLogin;
import org.bukkit.configuration.file.FileConfiguration;

public class WebHook {

    FileConfiguration config;
    String WEBHOOK_ID;
    String WEBHOOK_TOKEN;
    String WEBHOOK_URL;

    public WebHook(MiLogin plugin) {
        this.config = plugin.getConfig();
        this.WEBHOOK_TOKEN = config.getString("discord.webhook-token");
        this.WEBHOOK_ID = config.getString("discord.webhook-id");
        this.WEBHOOK_URL = "https://discord.com/api/webhooks/" + WEBHOOK_ID + "/" + WEBHOOK_TOKEN;;
    }

    public void sendWebhook(String message) {
        try {
            String jsonPayload = String.format(
                    "{\"username\": \"%s\", \"avatar_url\": \"%s\", \"content\": \"%s\"}",
                    config.getString("discord.bot-name"), config.getString("discord.bot-image-url"), message
            );

            // Create the Request HTTP Post.
            HttpResponse<JsonNode> response = Unirest.post(WEBHOOK_URL)
                    .header("Content-Type", "application/json")
                    .body(jsonPayload) // JSON con el mensaje.
                    .asJson();

            // Print the response code if this is success.
            System.out.println("Response Code: " + response.getStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
