package milo.utils;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerEffects {

    private Player player;
    private int DURATION_POTION_EFFECT = Integer.MAX_VALUE;
    private int AMPLIFIER_POTION_EFFECT = Integer.MAX_VALUE;

    public PlayerEffects(Player player) {
        this.player = player;
    }

    public void addPlayerEffects() {
        // Apply invencible effects in the player.
        PotionEffect blindness = new PotionEffect(PotionEffectType.BLINDNESS, DURATION_POTION_EFFECT, AMPLIFIER_POTION_EFFECT, true, false);
        PotionEffect resistence = new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, DURATION_POTION_EFFECT, AMPLIFIER_POTION_EFFECT, true, false);
        this.player.addPotionEffect(blindness);
        this.player.addPotionEffect(resistence);

        // Freeze the player.
        this.player.setWalkSpeed(0); // Establecer velocidad de caminar a 0
        this.player.setFlySpeed(0);  // Establecer velocidad de vuelo a 0
    }

    public void removePlayerEffects() {
        // Remove effects in the player.
        this.player.removePotionEffect(PotionEffectType.BLINDNESS);
        this.player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        this.player.setWalkSpeed(0.2f); // Restaurar velocidad de movimiento predeterminada.
        this.player.setFlySpeed(0.1f); // Restaurar velocidad de vuelo predeterminada.
    }
}
