package br.dev.santos.skywar.tasks;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.player.PlayerData;

public final class FireworksTask {
    Skywar plugin = Skywar.getPlugin(Skywar.class);

    public void start(PlayerData playerData, Arena arena) {
        Player playerEntity = playerData.getPlayerEntity();
        Firework firework = playerEntity.getWorld().spawn(playerEntity.getLocation(), Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.addEffect(FireworkEffect.builder()
                .withColor(Color.RED, Color.BLUE)
                .withFlicker()
                .withTrail()
                .with(FireworkEffect.Type.BALL_LARGE)
                .build());
        meta.setPower(1);
        firework.setFireworkMeta(meta);

        new BukkitRunnable() {
            int fireworksLaunched = 0;

            @Override
            public void run() {
                if (fireworksLaunched >= 24) {
                    cancel(); // Para após lançar 12 fogos
                    return;
                }

                // Calcula a direção para cada fogo de artifício
                double angle = Math.toRadians((360.0 / 12) * fireworksLaunched);
                double xOffset = Math.cos(angle) * 30;
                double zOffset = Math.sin(angle) * 30;

                Location fireworkLocation = playerEntity.getLocation().add(xOffset, 0, zOffset);

                Firework distantFirework = playerEntity.getWorld().spawn(fireworkLocation, Firework.class);
                FireworkMeta distantMeta = distantFirework.getFireworkMeta();
                distantMeta.addEffect(FireworkEffect.builder()
                        .withColor(Color.RED, Color.BLUE)
                        .withFlicker()
                        .withTrail()
                        .with(FireworkEffect.Type.BALL_LARGE)
                        .build());
                distantMeta.setPower(1);
                distantFirework.setFireworkMeta(distantMeta);

                fireworksLaunched++;
            }
        }.runTaskTimer(plugin, 0L, 1L); //

    }
}
