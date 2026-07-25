package br.dev.santos.skywar.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.handler.EliminationHandler;
import br.dev.santos.skywar.kit.ability.AbilityManager;
import br.dev.santos.skywar.kit.kits.VidaExtra;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerGameService;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.utils.TeleportUtils;
import br.dev.santos.skywar.player.PlayerData.PlayerState;
import br.dev.santos.skywar.warp.WarpManager;
import br.dev.santos.skywar.warp.WarpManager.Coord;

public final class PlayerDeathListener implements Listener {

    private final PlayerManager playerManager;
    private final EliminationHandler eliminationHandler;
    private final AbilityManager abilityManager;
    private final WarpManager warpManager;
    private final PlayerGameService playerGameService;
    private final ArenaMessenger arenaMessenger;

    public PlayerDeathListener(PlayerManager playerManager, EliminationHandler eliminationHandler,
            AbilityManager abilityManager, WarpManager warpManager, PlayerGameService playerGameService,
            ArenaMessenger arenaMessenger) {
        this.playerManager = playerManager;
        this.eliminationHandler = eliminationHandler;
        this.abilityManager = abilityManager;
        this.warpManager = warpManager;
        this.playerGameService = playerGameService;
        this.arenaMessenger = arenaMessenger;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        Player player = event.getEntity().getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        // Removendo mensagem de morte padrão do minecraft e respawnando o player
        event.setDeathMessage(null);
        player.spigot().respawn();

        if (playerData != null && playerData.getKit() instanceof VidaExtra && !playerData.getHasUsedExtraLife()) {
            return;
        }

        // Enviando mensagem de morte (acesse o inventário para teleporte...)
        String message = config.getString("messages.deathmessage");
        player.sendMessage(message);

        // Setando nome para vermelho
        player.setPlayerListName(ChatColor.RED + player.getName());
        player.setCustomName(ChatColor.RED + player.getName());
        player.setCustomNameVisible(true);

        if (playerData != null) {
            playerData.setDead(true);
            playerData.setKit(null);
            playerData.setHasUsedExtraLife(false);
            playerData.setStatus(PlayerState.SPECTATOR);

            Arena arena = playerData.getArena();

            // Adiciona player como espectador
            arena.alivePlayers.remove(playerData);
            arena.spectators.add(playerData);

            Coord winnerPlace = TeleportUtils.getWinnerPlace(arena);
            this.warpManager.teleport(playerData.getPlayerEntity(), winnerPlace);
            this.playerGameService.prepareForSpectator(playerData);
        }

        this.eliminationHandler.handle(playerData.getArena(), playerData);
    }
}
