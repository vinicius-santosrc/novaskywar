package br.dev.santos.skywar.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.arena.GameManager;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerGameService;
import br.dev.santos.skywar.player.PlayerManager;

public final class PlayerConnectionListener implements Listener {

    private final PlayerManager playerManager;
    private final GameManager gameManager;
    private final ArenaMessenger arenaMessenger;
    private final PlayerGameService playerGameService;

    public PlayerConnectionListener(PlayerManager playerManager, GameManager gameManager, ArenaMessenger arenaMessenger, PlayerGameService playerGameService) {
        this.playerManager = playerManager;
        this.gameManager = gameManager;
        this.arenaMessenger = arenaMessenger;
        this.playerGameService = playerGameService;
    }

    public static enum LeaveReason {
        COMMAND,
        QUIT,
        GAME_FINISHED
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        this.playerGameService.preparePlayerForLobby(playerData, false);

        if (playerData != null) {
            this.gameManager.leaveGame(player);
        }
    }

}