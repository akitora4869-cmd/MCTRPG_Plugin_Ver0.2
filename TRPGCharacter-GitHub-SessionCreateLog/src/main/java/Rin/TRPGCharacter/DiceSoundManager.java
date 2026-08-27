package Rin.TRPGCharacter;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class DiceSoundManager {

    private final Plugin plugin;

    public DiceSoundManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void playRollSequence(Player player, Runnable resultAction) {
        // 約1秒間のサイコロ転がり音
        playNearby(player, Sound.BLOCK_STONE_BUTTON_CLICK_ON, 0.65f, 0.8f);

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> playNearby(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_ON, 0.65f, 0.95f),
                4L
        );

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> playNearby(player, Sound.BLOCK_STONE_BUTTON_CLICK_OFF, 0.70f, 1.05f),
                8L
        );

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> playNearby(player, Sound.BLOCK_WOODEN_BUTTON_CLICK_OFF, 0.75f, 1.15f),
                12L
        );

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> playNearby(player, Sound.BLOCK_NOTE_BLOCK_HAT, 0.55f, 0.7f),
                16L
        );

        // 最後の決定音 + 結果表示
        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> {
                    playNearby(player, Sound.BLOCK_NOTE_BLOCK_PLING, 0.9f, 1.25f);
                    resultAction.run();
                },
                20L
        );
    }

    private void playNearby(Player source, Sound sound, float volume, float pitch) {
        double radius = 12.0;

        for (Player target : source.getWorld().getPlayers()) {
            if (target.getLocation().distanceSquared(source.getLocation()) <= radius * radius) {
                target.playSound(source.getLocation(), sound, volume, pitch);
            }
        }
    }
}
