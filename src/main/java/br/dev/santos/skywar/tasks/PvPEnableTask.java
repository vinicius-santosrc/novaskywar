package br.dev.santos.skywar.tasks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;

public class PvPEnableTask extends BukkitRunnable {

    private final Arena arena;
    private final ArenaMessenger arenaMessenger;
    private final FileConfiguration config;
    private final ScoreBoardManager scoreBoardManager;

    public PvPEnableTask(
            Arena arena,
            ArenaMessenger arenaMessenger,
            ScoreBoardManager scoreBoardManager,
            FileConfiguration config) {

        this.arena = arena;
        this.arenaMessenger = arenaMessenger;
        this.scoreBoardManager = scoreBoardManager;
        this.config = config;
    }

    @Override
    public void run() {
        if (arena.pvpOffTime > 0) {
            arena.pvpOffTime--;
        }

        if (arena.pvpOffTime <= 0) {
            String message = config.getString("messages.pvpenabled");
            this.arenaMessenger.sendMessageToArena(arena, message);

            arena.pvpOn = true;

            cancel();
        }
    }
}