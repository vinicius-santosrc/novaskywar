package br.dev.santos.skywar;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoard implements Listener {

    public static void removeScoreBoard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    public static void setScoreBoard(Player player, Integer playersAlive, Integer specs, String arena, int Time) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("gameInfo", "dummy");
        objective.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
        objective.setDisplayName(ChatColor.GOLD + "SkyWar");

        Score playersScore = objective.getScore(ChatColor.AQUA + "Jogadores: ");
        playersScore.setScore(playersAlive);

        if(specs > 0) {
            Score specsScore = objective.getScore(ChatColor.AQUA + "Espectadores: ");
            specsScore.setScore(specs);
        }

        if(Time > 0) {
            Score pvpoff = objective.getScore(ChatColor.AQUA + "PvPOff: ");
            pvpoff.setScore(Time);
        }

        player.setScoreboard(scoreboard);
    }
}
