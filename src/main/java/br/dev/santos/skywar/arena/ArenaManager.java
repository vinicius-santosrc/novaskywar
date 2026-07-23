package br.dev.santos.skywar.arena;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArenaManager {
    private final Map<String, Arena> arenas = new HashMap<>();

    public void createArena(String name, String nameOfWorld, int maxPlayers, int minPlayers) {
        Arena arena = new Arena(name, nameOfWorld, maxPlayers, minPlayers);

        arenas.put(arena.getName(), arena);
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

    public boolean removeArena(String name) {
        return arenas.remove(name) != null;
    }

    public List<Arena> getArenas() {
        return new ArrayList<>(arenas.values());
    }
}
