package br.dev.santos.skywar.tasks;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.Arena.StatusArena;
import br.dev.santos.skywar.commands.ResetWorldCommand;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerGameService;

public class EndGameTask extends BukkitRunnable {

    private static final int SECONDS_TO_RESET = 15;

    private final Arena arena;
    private final PlayerGameService playerGameService;
    private final ResetWorldCommand resetWorldCommand;
    private final PlayerData winner;
    private final FireworksTask fireworksTask;

    private int secondsRemaining;
    private boolean fireworksStarted;

    public EndGameTask(
            Arena arena,
            PlayerGameService playerGameService,
            ResetWorldCommand resetWorldCommand,
            PlayerData winner,
            FireworksTask fireworksTask) {

        this.arena = arena;
        this.playerGameService = playerGameService;
        this.resetWorldCommand = resetWorldCommand;
        this.winner = winner;
        this.fireworksTask = fireworksTask;

        this.secondsRemaining = SECONDS_TO_RESET;
        this.fireworksStarted = false;
    }

    @Override
    public void run() {
        startFireworks();

        if (secondsRemaining > 0) {
            secondsRemaining--;
            return;
        }

        cancel();
        finishGame();
    }

    private void startFireworks() {
        if (fireworksStarted || winner == null) {
            return;
        }

        fireworksTask.start(winner, arena);
        fireworksStarted = true;
    }

    private void finishGame() {
        playerGameService.resetPlayerAfterGame(arena);
        arena.setStatus(StatusArena.RESETING);

        try {
            this.resetWorldCommand.resetArena(arena);
        } catch (Exception exception) {
            Bukkit.getLogger().severe(
                    "Erro ao resetar a arena: " + exception.getMessage());

            exception.printStackTrace();
        }
        finally {
            arena.setStatus(StatusArena.OPEN);
        }
    }
}