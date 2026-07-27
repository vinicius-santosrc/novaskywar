package br.dev.santos.skywar.arena;

import javax.security.auth.login.Configuration;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import br.dev.santos.skywar.player.PlayerData;

public class ArenaMessenger {
    Plugin plugin;
    FileConfiguration config;
    public ArenaMessenger(Plugin plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
    }
    public void sendMessageToArena(Arena arena, String message) {
        for (PlayerData playerData : arena.getPlayers()) {
            Player playerEntity = playerData.getPlayerEntity();
            playerEntity.sendMessage(message);
        }
    }

    public void sendMessageToPlayer(
            Player player,
            String path) {

        String message = this.config.getString(path);

        player.sendMessage(message);
    }

    public void sendMessageToPlayer(
            Player player,
            String path,
            String[] replaceKey,
            String[] replaceValue) {

        String message = this.config.getString(path);

        if (replaceKey != null && replaceValue != null) {
            int i = 0;
            for (String value : replaceKey) {
                message = message.replace(value, replaceValue[i]);
                i++;
            }
        }

        player.sendMessage(message);
    }
}
