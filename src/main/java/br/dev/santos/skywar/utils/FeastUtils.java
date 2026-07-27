package br.dev.santos.skywar.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerData.PlayerState;

public abstract class FeastUtils {

    public static boolean isPlayerInFeast(Player player, Arena arena) {
        ConfigurationSection fileConfig = arena.getArenaConfig();
        String path = ".feast.";

        String worldName = fileConfig.getString(path + "pos1.worldName");

        int xPos1 = fileConfig.getInt(path + "pos1.x");
        int yPos1 = fileConfig.getInt(path + "pos1.y");
        int zPos1 = fileConfig.getInt(path + "pos1.z");

        int xPos2 = fileConfig.getInt(path + "pos2.x");
        int yPos2 = fileConfig.getInt(path + "pos2.y");
        int zPos2 = fileConfig.getInt(path + "pos2.z");

        if (!player.getWorld().getName().equalsIgnoreCase(worldName)) {
            return false;
        }

        int minX = Math.min(xPos1, xPos2);
        int maxX = Math.max(xPos1, xPos2);

        int minY = Math.min(yPos1, yPos2);
        int maxY = Math.max(yPos1, yPos2);

        int minZ = Math.min(zPos1, zPos2);
        int maxZ = Math.max(zPos1, zPos2);

        int playerX = player.getLocation().getBlockX();
        int playerY = player.getLocation().getBlockY();
        int playerZ = player.getLocation().getBlockZ();

        return playerX >= minX
                && playerX <= maxX
                && playerY >= minY
                && playerY <= maxY
                && playerZ >= minZ
                && playerZ <= maxZ;
    }

    public static boolean blockEnderPearlInsideFeast(Location location, Arena arena) {
        ConfigurationSection fileConfig = arena.getArenaConfig();
        String path = ".feast.";

        String worldName = fileConfig.getString(path + "pos1.worldName");

        if (location == null
                || location.getWorld() == null
                || !location.getWorld().getName().equalsIgnoreCase(worldName)) {
            return false;
        }

        int xPos1 = fileConfig.getInt(path + "pos1.x");
        int yPos1 = fileConfig.getInt(path + "pos1.y");
        int zPos1 = fileConfig.getInt(path + "pos1.z");

        int xPos2 = fileConfig.getInt(path + "pos2.x");
        int yPos2 = fileConfig.getInt(path + "pos2.y");
        int zPos2 = fileConfig.getInt(path + "pos2.z");

        int minX = Math.min(xPos1, xPos2);
        int maxX = Math.max(xPos1, xPos2);

        int minY = Math.min(yPos1, yPos2);
        int maxY = Math.max(yPos1, yPos2);

        int minZ = Math.min(zPos1, zPos2);
        int maxZ = Math.max(zPos1, zPos2);

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        return x >= minX
                && x <= maxX
                && y >= minY
                && y <= maxY
                && z >= minZ
                && z <= maxZ;
    }

    public static boolean handlePlayerEnteringFeast(
            PlayerData playerData,
            Arena arena) {

        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        if (arena.feastReached) {
            return false;
        }

        Player player = playerData.getPlayerEntity();

        if (!isPlayerInFeast(player, arena) || playerData.getStatus().equals(PlayerState.SPECTATOR)) {
            return false;
        }

        arena.feastReached = true;

        strikeLightningAtWinner(arena, player);

        arena.sendMessageToArena(config.getString("messages.playerReachedFeast").replace("{player}", player.getDisplayName()));

        playerData.addCredits(
                "chegar primeiro ao feast",
                12);

        return true;
    }

    public static void strikeLightningAtWinner(
            Arena arena,
            Player player) {

        ConfigurationSection fileConfig = arena.getArenaConfig();
        String path = ".warps.winner.";

        String worldName = fileConfig.getString(path + "worldName");

        World world = player.getServer().getWorld(worldName);

        if (world == null) {
            return;
        }

        Location winnerLocation = new Location(
                world,
                fileConfig.getDouble(path + "x"),
                fileConfig.getDouble(path + "y"),
                fileConfig.getDouble(path + "z"));

        world.strikeLightningEffect(winnerLocation);
    }
}