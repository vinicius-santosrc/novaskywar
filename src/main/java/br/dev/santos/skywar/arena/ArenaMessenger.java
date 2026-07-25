package br.dev.santos.skywar.arena;

import org.bukkit.entity.Player;

import br.dev.santos.skywar.player.PlayerData;

public class ArenaMessenger {
    public void sendMessageToArena(Arena arena, String message) {
        for (PlayerData playerData : arena.getPlayers()) {
            Player playerEntity = playerData.getPlayerEntity();
            playerEntity.sendMessage(message);
        }
    }
}
