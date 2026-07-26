package br.dev.santos.skywar.utils;

import java.util.ArrayList;

import org.bukkit.configuration.ConfigurationSection;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.warp.WarpManager.Coord;

public abstract class TeleportUtils {
    public static Coord getWaitingLobby(Arena arena) {
        ConfigurationSection waitingLobbyConfiguration = arena.getArenaConfig()
        .getConfigurationSection(".warps.waiting");
        String waitingLobbyWorld = waitingLobbyConfiguration.getString("worldName");
        int waitingX = waitingLobbyConfiguration.getInt("x");
        int waitingY = waitingLobbyConfiguration.getInt("y");
        int waitingZ = waitingLobbyConfiguration.getInt("z");

        return new Coord(waitingLobbyWorld, waitingX, waitingY, waitingZ);
    }

    public static ArrayList<Coord> getIslandsArena(Arena arena) {

        ArrayList<Coord> allIslandsCoords = new ArrayList<>();
        for (int i = 1; i < arena.maxPlayers; i++) {
            ConfigurationSection island = arena.getArenaConfig().getConfigurationSection(".warps.islands." + i);
            String islandWorld = island.getString("worldName");
            int islandX = island.getInt("x");
            int islandY = island.getInt("y");
            int islandZ = island.getInt("z");

            allIslandsCoords.add(new Coord(islandWorld, islandX, islandY, islandZ));
        }

        return allIslandsCoords;
    }

    public static Coord getCoordIslandArenaByIndex(Arena arena, int index) {
        ConfigurationSection island = arena.getArenaConfig()
                .getConfigurationSection(".islands." + index);
        String islandWorld = island.getString("worldName");
        int islandX = island.getInt("x");
        int islandY = island.getInt("y");
        int islandZ = island.getInt("z");

        return new Coord(islandWorld, islandX, islandY, islandZ);
    }

    public static Coord getWinnerPlace(Arena arena) {
        ConfigurationSection winnerPlace = arena.getArenaConfig()
                .getConfigurationSection(".warps.winner");
        String winnerWorld = winnerPlace.getString("worldName");
        int winnerX = winnerPlace.getInt("x");
        int winnerY = winnerPlace.getInt("y");
        int winnerZ = winnerPlace.getInt("z");
        return new Coord(winnerWorld, winnerX, winnerY, winnerZ);
    }
}
