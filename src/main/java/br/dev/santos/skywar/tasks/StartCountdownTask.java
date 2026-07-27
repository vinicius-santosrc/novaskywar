package br.dev.santos.skywar.tasks;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.arena.GameManager;
import br.dev.santos.skywar.player.PlayerData;

public final class StartCountdownTask extends BukkitRunnable {

    private final GameManager gameManager;
    private final Arena arena;
    private final ArenaMessenger arenaMessenger;
    private int timeToStart;

    public StartCountdownTask(
            GameManager gameManager,
            Arena arena,
            ArenaMessenger arenaMessenger,
            int seconds) {

        this.gameManager = gameManager;
        this.arena = arena;
        this.arenaMessenger = arenaMessenger;
        this.timeToStart = seconds;
    }

    public int getTimeToStart() {
        return timeToStart;
    }

    public void setTimeToStart(int timeToStart) {
        this.timeToStart = timeToStart;
        this.arena.setTimeToStart(timeToStart);
    }

    // Loop a cada segundo
    @Override
    public void run() {

        if (timeToStart <= 0) {
            cancel();
            this.gameManager.startGame(arena);
            return;
        }

        arena.setTimeToStart(timeToStart);

        if (timeToStart <= 5
                || timeToStart == 10
                || timeToStart == 30
                || timeToStart == 60
                || timeToStart == 120
                || timeToStart == 180) {

            String message = gameManager.getPlugin()
                    .getConfig()
                    .getString("messages.seconds_to_start")
                    .replace("{seconds}", String.valueOf(timeToStart));

            arenaMessenger.sendMessageToArena(arena, message);
        }

        for (PlayerData data : arena.getPlayers()) {

            Player player = data.getPlayerEntity();

            player.setLevel(timeToStart);

            if (timeToStart == 1) {
                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1f, 1f);
            } else if (timeToStart <= 10
                    || timeToStart == 30
                    || timeToStart == 60
                    || timeToStart == 120
                    || timeToStart == 180) {

                player.playSound(player.getLocation(), Sound.CLICK, 1f, 1f);
            }
        }

        timeToStart--;
    }

    @Override
    public synchronized void cancel() throws IllegalStateException {
        arena.setTimeToStart(90);
        super.cancel();
    }
}