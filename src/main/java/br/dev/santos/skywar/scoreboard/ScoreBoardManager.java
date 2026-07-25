package br.dev.santos.skywar.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.Arena.StatusArena;
import br.dev.santos.skywar.player.PlayerData;

public class ScoreBoardManager implements Listener {

    public void removeScoreBoard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public void updateScoreBoard(Arena arena) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("gameInfo", "dummy");
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "SkyWar");

        Score playersScore = objective.getScore(ChatColor.AQUA + "Jogadores: ");
        playersScore.setScore(arena.alivePlayers.size());

        if (arena.spectators.size() > 0) {
            Score specsScore = objective.getScore(ChatColor.AQUA + "Espectadores: ");
            specsScore.setScore(arena.spectators.size());
        }

        if (arena.pvpOffTime > 0 && arena.getStatus() == StatusArena.STARTED) {
            Score pvpoff = objective.getScore(ChatColor.AQUA + "PvPOff: ");
            pvpoff.setScore(arena.pvpOffTime);
        }

        for (PlayerData player : arena.getPlayers()) {
            Player playerEntity = player.getPlayerEntity();
            playerEntity.setScoreboard(scoreboard);
        }
    }
}