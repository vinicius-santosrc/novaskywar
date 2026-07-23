package br.dev.santos.skywar.player;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.kit.KitSelectionService;
import br.dev.santos.skywar.player.PlayerData.PlayerState;
import br.dev.santos.skywar.scoreboard.ScoreBoardManager;
import br.dev.santos.skywar.warp.WarpManager;

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
        playerData.setArena(arena);
        playerEntity.getInventory().clear();
        playerEntity.setGameMode(GameMode.ADVENTURE);

        // Teleporta para o waiting lobby
        this.warpManager.teleportToWaitingLobby(playerEntity);
        playerEntity.getInventory().clear();

        // Dá os items de seleção de kit
        this.kitSelectionService.giveSelectionItems(playerEntity);
    }

    public void prepareWinner(PlayerData playerData, Arena arena) {
        Player playerWinnerEntity = playerData.getPlayerEntity();
        this.warpManager.teleportToWinnerArea(playerWinnerEntity, arena);
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

            // Teleporta cada player a sua ilha
            int warpNumber = indexIsland + 1;
            playerData.setIsland(warpNumber);

            // Limpa inventário e levels de xp
            // Seta status da arena como STARTED

            playerEntity.getInventory().clear();
            playerEntity.setLevel(0);
            playerEntity.setExp(0);
            this.kitManager.giveItemsToPlayer(playerEntity, playerData.getKit());

            this.warpManager.teleportToIsland(playerEntity, arena, indexIsland);
        }
    }

    public void prepareForSpectator(PlayerData playerData) {
        Player player = playerData.getPlayerEntity();
        player.setGameMode(GameMode.SPECTATOR);
    }

    public void resetPlayerAfterGame(Arena arena) {
        for (PlayerData playerData : new java.util.ArrayList<PlayerData>(
                arena.getPlayers())) {

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
            playerEntity.performCommand("skywar leaveafterwin");
        }

        playerData.setArena(null);
        playerData.setKit(null);
        playerData.setStatus(PlayerState.LOBBY);
        playerData.setIsland(0);

        playerEntity.setFlying(false);
        playerEntity.getInventory().clear();
        playerEntity.getInventory().setArmorContents(
                new ItemStack[4]);
        playerEntity.setAllowFlight(false);

        this.warpManager.teleportToLobby(playerEntity);

        playerEntity.setPlayerListName(
                ChatColor.WHITE + playerEntity.getName());

        playerEntity.setCustomName(
                ChatColor.WHITE + playerEntity.getName());

        playerEntity.setCustomNameVisible(true);
    }
}
