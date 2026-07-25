package br.dev.santos.skywar.warp;

import org.bukkit.Bukkit;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public final class WarpManager {
    FileConfiguration config;
    public WarpManager(FileConfiguration config) {
        this.config = config;
    }

    public void teleport(Player player, Coord coord) {
        Location location = new Location(Bukkit.getWorld(coord.worldName), coord.x, coord.y, coord.z);
        player.teleport(location);
    }

    public void teleportToLoobySw(Player player) {
        ConfigurationSection configSec = this.config.getConfigurationSection("lobby");
        String lobbyWorld = configSec.getString("worldName");
        int lobbyX = configSec.getInt("x");
        int lobbyY = configSec.getInt("y");
        int lobbyZ = configSec.getInt("z");

        Location location = new Location(Bukkit.getWorld(lobbyWorld), lobbyX, lobbyY, lobbyZ);
        player.teleport(location);
    }

    public static class Coord {
        public String worldName = "";
        public int x = 0;
        public int y = 0;
        public int z = 0;

        public Coord(String worldName, int x, int y, int z) {
            this.worldName = worldName;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
