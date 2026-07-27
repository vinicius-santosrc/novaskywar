package br.dev.santos.skywar.player;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.kit.KitSelectionService;
import br.dev.santos.skywar.player.PlayerData.PlayerState;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;
import br.dev.santos.skywar.utils.TeleportUtils;
import br.dev.santos.skywar.warp.WarpManager;
import br.dev.santos.skywar.warp.WarpManager.Coord;

public class PlayerGameService {

    private ScoreBoardManager scoreBoardManager;
    private KitManager kitManager;
    private WarpManager warpManager;
    private KitSelectionService kitSelectionService;

    public PlayerGameService(ScoreBoardManager scoreBoardManager, KitManager kitManager, WarpManager warpManager, KitSelectionService kitSelectionService) {
        this.scoreBoardManager = scoreBoardManager;
        this.kitManager = kitManager;
        this.warpManager = warpManager;
        this.kitSelectionService = kitSelectionService;
    }

    public void preparePlayerForWaiting(PlayerData playerData, Arena arena) {
        Player playerEntity = playerData.getPlayerEntity();
        playerData.setStatus(PlayerState.WAITING);

        playerData.setArena(arena);
        playerEntity.getInventory().clear();
        playerEntity.setGameMode(GameMode.ADVENTURE);
        playerEntity.setLevel(0);
        playerEntity.setTotalExperience(0);
        playerEntity.setExp(0);

        // Teleporta para o waiting lobby
        Coord waitingLobbyCoord = TeleportUtils.getWaitingLobby(arena);
        this.warpManager.teleport(playerEntity, waitingLobbyCoord);
        playerEntity.getInventory().clear();

        // Dá os items de seleção de kit
        this.kitSelectionService.giveSelectionItems(playerEntity);
    }

    public void prepareWinner(PlayerData playerData, Arena arena) {
        Player playerWinnerEntity = playerData.getPlayerEntity();

        // Teleporta para winnerPlace
        Coord winnerPlace = TeleportUtils.getWinnerPlace(arena);
        this.warpManager.teleport(playerWinnerEntity, winnerPlace);

        playerWinnerEntity.getLocation();
        playerWinnerEntity.setAllowFlight(true);
        playerWinnerEntity.setFlying(true);
    }

    public void preparePlayersForGame(Arena arena) {
        // Definição default para setar survival mode
        // Definição para setar nome verde para players vivos
        int indexIsland = 1;
        for (PlayerData playerData : arena.getPlayers()) {
            Player playerEntity = playerData.getPlayerEntity();

            this.scoreBoardManager.updateScoreBoard(arena);

            playerEntity.setGameMode(GameMode.SURVIVAL);
            playerEntity.setPlayerListName(ChatColor.GREEN + playerEntity.getName());
            playerEntity.setCustomName(ChatColor.GREEN + playerEntity.getName());
            playerEntity.setCustomNameVisible(true);

            playerData.setStatus(PlayerState.PLAYING);

            // Limpa inventário e levels de xp
            // Seta status da arena como STARTED

            playerEntity.getInventory().clear();
            playerEntity.setLevel(0);
            playerEntity.setTotalExperience(0);
            playerEntity.setExp(0);
            this.kitManager.giveItemsToPlayer(playerEntity, playerData.getKit());

            // Teleporta cada player a sua ilha
            playerData.setIsland(indexIsland);
            Coord islandCoord = TeleportUtils.getCoordIslandArenaByIndex(arena, indexIsland);
            this.warpManager.teleport(playerEntity, islandCoord);
            indexIsland++;

        }
    }

    public void prepareForSpectator(PlayerData playerData) {
        Player player = playerData.getPlayerEntity();
        player.setGameMode(GameMode.SPECTATOR);
        playerData.setStatus(PlayerState.DEAD);

        player.setLevel(0);
        player.setTotalExperience(0);
        player.setExp(0);
    }

    public void resetPlayerAfterGame(Arena arena) {
        arena.firstBlood = false;
        for (PlayerData playerData : new ArrayList<PlayerData>(arena.getPlayers())) {
            this.preparePlayerForLobby(
                    playerData,
                    true);
        }
    }

    public void preparePlayerForLobby(
            PlayerData playerData,
            Boolean finishedGame) {

        Player playerEntity = playerData.getPlayerEntity();

        if (finishedGame) {
            playerEntity.performCommand("skywar sair");
        }

        this.scoreBoardManager.removeScoreBoard(playerEntity);

        playerData.setArena(null);
        playerData.setKit(null);
        playerData.setStatus(PlayerState.LOBBY);
        playerData.setIsland(0);

        playerEntity.setLevel(0);
        playerEntity.setTotalExperience(0);
        playerEntity.setExp(0);

        playerEntity.setFlying(false);
        playerEntity.getInventory().clear();
        playerEntity.getInventory().setArmorContents(
                new ItemStack[4]);
        playerEntity.setAllowFlight(false);

        // Teleporta ao lobby
        this.warpManager.teleportToLoobySw(playerEntity);

        playerEntity.setPlayerListName(
                ChatColor.WHITE + playerEntity.getName());

        playerEntity.setCustomName(
                ChatColor.WHITE + playerEntity.getName());

        playerEntity.setCustomNameVisible(true);

        playerEntity.setGameMode(GameMode.ADVENTURE);
    }
}
