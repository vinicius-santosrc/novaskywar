package br.dev.santos.skywar.tasks;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;

public class PvPEnableTask extends BukkitRunnable {

    private final Arena arena;
    private final ArenaMessenger arenaMessenger;
    private final FileConfiguration config;

    public PvPEnableTask(
            Arena arena,
            ArenaMessenger arenaMessenger,
            FileConfiguration config) {

        this.arena = arena;
        this.arenaMessenger = arenaMessenger;
        this.config = config;
    }

    @Override
    public void run() {

        String message = config.getString("messages.pvpenabled");

        arenaMessenger.sendMessageToArena(arena, message);
    }
}