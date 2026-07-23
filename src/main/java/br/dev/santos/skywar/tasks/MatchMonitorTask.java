package br.dev.santos.skywar.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.GameManager;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;

public class MatchMonitorTask extends BukkitRunnable {

    private final GameManager gameManager;
    private final Arena arena;
    private final ScoreBoardManager scoreBoardManager;

    public MatchMonitorTask(
            GameManager gameManager,
            Arena arena,
            ScoreBoardManager scoreBoardManager) {

        this.gameManager = gameManager;
        this.arena = arena;
        this.scoreBoardManager = scoreBoardManager;
    }

    @Override
    public void run() {

        arena.timeOfTheMatch++;

        scoreBoardManager.updateScoreBoard(arena);

        if (arena.alivePlayers.size() <= 1) {
            cancel();
            gameManager.endGame(arena);
        }
    }
}