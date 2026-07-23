package br.dev.santos.skywar.warp;

import org.bukkit.entity.Player;

import br.dev.santos.skywar.arena.Arena;

public final class WarpManager {

    public void teleport(Player player, String warpName) {
        player.performCommand("warp " + warpName);
    }

    public void teleportToIsland(Player player, Arena arena, int island) {
        teleport(player, arena.getName() + "-" + island);
    }

    public void teleportToWinnerArea(Player player, Arena arena) {
        teleport(player, arena.getName() + "-winner");
    }

    public void teleportToLobby(Player player) {
        teleport(player, "skywar");
    }

    public void teleportToWaitingLobby(Player player) {
        teleport(player, "WaitingSW");
    }
}