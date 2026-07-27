package br.dev.santos.skywar.player;

import java.util.ArrayList;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.economy.MoneyManager;
import br.dev.santos.skywar.kit.menu.KitUserManager;
import br.dev.santos.skywar.player.PlayerData.PlayerState;

public final class PlayerManager {

    private final List<PlayerData> players = new ArrayList<>();
    private final MoneyManager moneyManager;
    private final KitUserManager kitUserManager;

    public PlayerManager(MoneyManager moneyManager, KitUserManager kitUserManager) {
        this.moneyManager = moneyManager;
        this.kitUserManager = kitUserManager;
    }

    public PlayerData get(Player player) {
        for (PlayerData data : players) {
            if (data.getUniqueId().equals(player.getUniqueId())) {
                return data;
            }
        }

        return null;
    }

    public PlayerData getOrCreate(Player player) {
        PlayerData existing = get(player);

        if (existing != null) {
            return existing;
        }

        PlayerData created = new PlayerData(player.getDisplayName(), player.getUniqueId(), player, this.moneyManager, this.kitUserManager);
        players.add(created);
        return created;
    }

    public void remove(Player player) {
        players.removeIf(data -> data.getUniqueId().equals(player.getUniqueId()));
    }

    public List<Player> getAlivePlayers(Arena arena) {
        List<Player> result = new ArrayList<>();

        for (PlayerData data : players) {
            if (data.getArena() != arena) {
                continue;
            }

            if (data.getStatus() != PlayerState.PLAYING) {
                continue;
            }

            Player player = Bukkit.getPlayer(data.getUniqueId());

            if (player != null) {
                result.add(player);
            }
        }

        return result;
    }

    public List<Player> getSpectators(Arena arena) {
        List<Player> result = new ArrayList<>();

        for (PlayerData data : players) {
            if (data.getArena() != arena) {
                continue;
            }

            if (data.getStatus() != PlayerState.SPECTATOR) {
                continue;
            }

            Player player = Bukkit.getPlayer(data.getUniqueId());

            if (player != null) {
                result.add(player);
            }
        }

        return result;
    }
}