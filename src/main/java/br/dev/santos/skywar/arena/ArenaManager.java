package br.dev.santos.skywar.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class ArenaManager {
        private final Map<String, Arena> arenas = new HashMap<>();

    public void addExistingArena(String name, String nameOfWorld, int maxPlayers, int minPlayers, int pvpOffTime,
            FileConfiguration arenaConfig) {
        Arena arena = new Arena(name, nameOfWorld, maxPlayers, minPlayers, pvpOffTime, arenaConfig, new ArenaMessenger());

        arenas.put(arena.getName(), arena);
    }

    public void createArena(
            Player player,
            String name,
            String worldName,
            int maxPlayers,
            FileConfiguration configuration) {
        String arenaPath = "arenas." + name;

        configuration.set(arenaPath + ".enabled", true);
        configuration.set(arenaPath + ".worldName", worldName);
        configuration.set(arenaPath + ".max-players", maxPlayers);
        configuration.set(arenaPath + ".min-players", maxPlayers / 2);
        configuration.set(arenaPath + ".pvp-off-time", 5);

        configuration.createSection(arenaPath + ".warps");
        configuration.createSection(arenaPath + ".warps.waiting");
        configuration.createSection(arenaPath + ".warps.winner");
        configuration.createSection(arenaPath + ".feast");
        configuration.createSection(arenaPath + ".feast.point1");
        configuration.createSection(arenaPath + ".feast.point2");
        configuration.createSection(arenaPath + ".islands");

        Arena arena = new Arena(
                name,
                worldName,
                maxPlayers,
                maxPlayers / 2,
                5,
                configuration,
                new ArenaMessenger());

        arenas.put(arena.getName(), arena);

        player.sendMessage("§aArena " + arena.getName() + "-" + arena.getId() + " criada.");
        player.sendMessage("§a  Utilize: /skywar set spectator");
        player.sendMessage("§a  Utilize: /skywar set island {numero}");
        player.sendMessage("§a  Utilize: /skywar set waitingLobby");
        player.sendMessage("§a  Utilize: /skywar set feast <pos1/pos2>");
        player.sendMessage("§aApós isso, a arena estará disponível para jogo.");
    }

    public void handleEditArena(
            Arena arena,
            Player player,
            String arg,
            String arg2,
            FileConfiguration configuration) {
        String arenaPath = "arenas." + arena.getName();

        switch (arg) {
            case "island":
                String islandPath = arenaPath + ".islands." + arg2;

                configuration.set(
                        islandPath + ".worldName",
                        player.getWorld().getName());
                configuration.set(
                        islandPath + ".x",
                        player.getLocation().getBlockX());
                configuration.set(
                        islandPath + ".y",
                        player.getLocation().getBlockY());
                configuration.set(
                        islandPath + ".z",
                        player.getLocation().getBlockZ());

                player.sendMessage("§aLocalização da Ilha " + arg2 + " definida na arena " + arena.getName() + ".");
                break;

            case "spectator":
                String winnerPath = arenaPath + ".warps.winner";

                configuration.set(
                        winnerPath + ".worldName",
                        player.getWorld().getName());
                configuration.set(
                        winnerPath + ".x",
                        player.getLocation().getBlockX());
                configuration.set(
                        winnerPath + ".y",
                        player.getLocation().getBlockY());
                configuration.set(
                        winnerPath + ".z",
                        player.getLocation().getBlockZ());

                player.sendMessage(
                        "§aLocalização da posição do espectador/vencedor definida na arena " + arena.getName() + ".");
                break;

            case "waitingLobby":
                String waitingPath = arenaPath + ".warps.waiting";

                configuration.set(
                        waitingPath + ".worldName",
                        player.getWorld().getName());
                configuration.set(
                        waitingPath + ".x",
                        player.getLocation().getBlockX());
                configuration.set(
                        waitingPath + ".y",
                        player.getLocation().getBlockY());
                configuration.set(
                        waitingPath + ".z",
                        player.getLocation().getBlockZ());

                player.sendMessage("§aLocalização da sala de espera definida na arena " + arena.getName() + ".");
                break;
             case "feast":
                if (!arg2.equalsIgnoreCase("pos1") && !arg2.equalsIgnoreCase("pos2")) {
                        player.sendMessage("§cUtilize pos1 ou pos2 como definição para o feast.");
                        break;

                }
                String feast = arenaPath + ".feast." + arg2;

                configuration.set(
                                feast + ".worldName",
                                player.getWorld().getName());
                configuration.set(
                                feast + ".x",
                                player.getLocation().getBlockX());
                configuration.set(
                                feast + ".y",
                                player.getLocation().getBlockY());
                configuration.set(
                                feast + ".z",
                                player.getLocation().getBlockZ());

                player.sendMessage("§aLocalização " + arg2 + " do feast definida na arena " + arena.getName() + ".");
                break;
            default:
                player.sendMessage("§cDefinição não encontrada.");
                break;
        }
    }

    public Arena getArena(String name, String numberRoom) {
        if (name == null) {
            return null;
        }

        return arenas.get(name);
    }

    public boolean exists(String name, String numberRoom) {
        return getArena(name, numberRoom) != null;

    }

    public boolean removeArena(Player player, String name, FileConfiguration config) {
        if (!this.exists(name, "1")) {

            player.sendMessage("§cArena " + name + "não existe.");
            return false;
        }
        config.set("arenas." + name, null);

        player.sendMessage("§aArena " + name + " removida com sucesso.");
        return arenas.remove(name) != null;
    }

    public List<Arena> getArenas() {
        return new ArrayList<>(arenas.values());
    }
}
