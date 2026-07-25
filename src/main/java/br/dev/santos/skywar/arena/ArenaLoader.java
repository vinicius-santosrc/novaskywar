package br.dev.santos.skywar.arena;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public final class ArenaLoader {

    private final FileConfiguration config;
    private final ArenaManager arenaManager;

    public ArenaLoader(
            FileConfiguration config,
            ArenaManager arenaManager) {

        this.config = config;
        this.arenaManager = arenaManager;
    }

    public void loadArenas() {
        ConfigurationSection section = config.getConfigurationSection("arenas");

        for (String arenaName : section.getKeys(false)) {
            String path = "arenas." + arenaName;

            boolean enabled = config.getBoolean(
                    path + ".enabled",
                    true
            );

            if (!enabled) {
                continue;
            }

            String worldName = config.getString(path + ".worldName");

            int maxPlayers = config.getInt(path + ".max-players", 12);
            int minPlayers = config.getInt(path + ".min-players", 2);

            int pvpOffTime = config.getInt(
                    path + ".pvp-off-time",
                    5
            );

            arenaManager.addExistingArena(
                arenaName,
                worldName,
                maxPlayers,
                minPlayers,
                pvpOffTime,
                config
            );
        }
    }
}