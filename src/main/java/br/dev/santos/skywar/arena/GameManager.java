/**
 * 
 * NovaSkyWar Kit
 * Criado por Vinicius Santos em 22/07/2026.
 * Copyright (c) 2026 Vinicius Santos. Todos os direitos reservados.
 * Obs: Este código é parte do projeto NovaSkyWar e não deve ser distribuído sem autorização.
 * 
 * Essa classe representa GameManager, setando joinGame, leaveGame, startGame e endGame.
*/

package br.dev.santos.skywar.arena;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena.StatusArena;
import br.dev.santos.skywar.commands.ResetWorldCommand;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerGameService;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;
import br.dev.santos.skywar.tasks.EndGameTask;
import br.dev.santos.skywar.tasks.FireworksTask;
import br.dev.santos.skywar.tasks.MatchMonitorTask;
import br.dev.santos.skywar.tasks.PvPEnableTask;
import br.dev.santos.skywar.tasks.StartCountdownTask;

import org.bukkit.plugin.java.JavaPlugin;


public class GameManager {

    private final Skywar plugin;

    private final PlayerManager playerManager;
    private ArenaMessenger arenaMessenger;
    private ScoreBoardManager scoreBoardManager;
    private ResetWorldCommand resetWorldCommand;
    private FireworksTask fireWorksTask;
    private PlayerGameService playerGameService;

    public GameManager(Skywar plugin, PlayerManager playerManager, ScoreBoardManager scoreBoardManager,
            ArenaMessenger arenaMessenger,
            ResetWorldCommand resetWorldCommand, FireworksTask fireWorksTask, PlayerGameService playerGameService) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.scoreBoardManager = scoreBoardManager;
        this.arenaMessenger = arenaMessenger;
        this.resetWorldCommand = resetWorldCommand;
        this.fireWorksTask = fireWorksTask;
        this.playerGameService = playerGameService;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }

    public void joinGame(Player player, Arena arena, String roomName) {
        PlayerData playerData = this.playerManager.getOrCreate(player);

        // Adiciona jogador a arena
        arena.addPlayer(playerData);
        arena.alivePlayers.add(playerData);

        if (playerData.getArena() == null) {
            this.playerGameService.preparePlayerForWaiting(playerData, arena);

            // Envia mensagem para todos da arena
            String joinMessage = this.getConfig().getString("messages.player_joined_arena")
                    .replace("{player}", player.getDisplayName())
                    .replace("{X}", String.valueOf(arena.getPlayers().size()))
                    .replace("{Y}", String.valueOf(arena.maxPlayers));

            this.arenaMessenger.sendMessageToArena(arena, joinMessage);

            // Atualiza scoreboard
            this.scoreBoardManager.updateScoreBoard(arena);

        } else {
            String message = this.getConfig().getString("messages.error_arena");
            player.sendMessage(message);
        }
    }

    public void leaveGame(Player player) {
        PlayerData playerData = this.playerManager.get(player);

        if (playerData == null || playerData.getArena() == null) {
            String message = this.getConfig().getString(
                    "messages.not_in_arena");

            player.sendMessage(message);
            return;
        }

        Arena arena = playerData.getArena();

        arena.removePlayer(playerData);
        arena.alivePlayers.remove(playerData);
        arena.spectators.remove(playerData);

        this.scoreBoardManager.updateScoreBoard(arena);
        this.scoreBoardManager.removeScoreBoard(player);

        this.playerGameService.preparePlayerForLobby(
                playerData,
                false);

        String playerLeftMessage = this.getConfig()
                .getString("messages.player_leave_arena")
                .replace(
                        "{player}",
                        player.getDisplayName())
                .replace(
                        "{X}",
                        String.valueOf(arena.getPlayers().size()))
                .replace(
                        "{Y}",
                        String.valueOf(arena.maxPlayers));

        this.arenaMessenger.sendMessageToArena(
                arena,
                playerLeftMessage);
    }

    public void forceStart(Arena arena) {
        int timeToStart = 10;
        arena.setTimeToStart(timeToStart);

        new StartCountdownTask(
                this,
                arena,
                this.arenaMessenger,
                timeToStart).runTaskTimer(plugin, 0L, 20L);
    }

    public void sendStartGameMessages(Arena arena) {
        String message = this.getConfig().getString("messages.game_start");
        this.arenaMessenger.sendMessageToArena(arena, message);

        // Mensagem para a arena do PvP Off
        String messagePvP = this.getConfig().getString("messages.pvp_message")
                .replace("{seconds}", String.valueOf(arena.pvpOffTime));
        this.arenaMessenger.sendMessageToArena(arena, messagePvP);

    }

    public void startGame(Arena arena) {
        arena.setStatus(StatusArena.STARTED);

        // Mensagem para a arena que indica que o Jogo começou
        this.sendStartGameMessages(arena);
        this.playerGameService.preparePlayersForGame(arena);

        // Task para monitorar o pvp
        new PvPEnableTask(arena, arenaMessenger, getConfig())
                .runTaskLater(plugin, 100L);

        // Task para monitorar partida
        new MatchMonitorTask(this, arena, scoreBoardManager)
                .runTaskTimer(plugin, 20L, 20L);

    };

    public void endGame(Arena arena) {

        PlayerData playerWinner = arena.getWinner();
        Player playerWinnerEntity = playerWinner.getPlayerEntity();

        // Mensagem de congratulações para o vencedor
        String message = this.getConfig().getString("messages.winning_message");

        playerWinnerEntity.sendMessage(message);
        this.arenaMessenger.sendMessageToArena(arena, message);

        // Mensagem do vencedor
        String messageFinished = this.getConfig()
                .getString("messages.game_finished")
                .replace("{player}", playerWinnerEntity.getDisplayName());

        this.arenaMessenger.sendMessageToArena(arena, messageFinished);

        // Mensagem de que todos serão retornados ao lobby
        String messageLobby = this.getConfig().getString("messages.lobby_message");
        this.arenaMessenger.sendMessageToArena(arena, messageLobby);

        // TODO enviar 1000 créditos para o vencedor
        this.playerGameService.prepareWinner(playerWinner, arena);

        new EndGameTask(
                arena,
                this.playerGameService,
                this.resetWorldCommand,
                playerWinner,
                this.fireWorksTask).runTaskTimer(this.plugin, 0L, 20L);
    }

}
