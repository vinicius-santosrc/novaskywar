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
        ConfigurationSection section =
                config.getConfigurationSection("arenas");

        if (section == null) {
            Bukkit.getLogger().severe(
                    "[SkyWar] A seção 'arenas' não existe "
                            + "no config.yml carregado pelo servidor."
            );
            return;
        }

        Bukkit.getLogger().info(
                "[SkyWar] Iniciando carregamento das arenas."
        );

        for (String arenaName : section.getKeys(false)) {
            String path = "arenas." + arenaName;

            boolean enabled = config.getBoolean(
                    path + ".enabled",
                    true
            );

            Bukkit.getLogger().info(
                    "[SkyWar] Arena encontrada no config: "
                            + arenaName
                            + " | enabled="
                            + enabled
            );

            if (!enabled) {
                continue;
            }

            String worldName = config.getString(
                    path + ".worldName"
            );

            int maxPlayers = config.getInt(
                    path + ".max-players",
                    12
            );

            int pvpOffTime = config.getInt(
                    path + ".pvp-off-time",
                    5
            );

            Bukkit.getLogger().info(
                    "[SkyWar] Criando arena: "
                            + arenaName
                            + " | mundo="
                            + worldName
                            + " | maxPlayers="
                            + maxPlayers
                            + " | pvpOffTime="
                            + pvpOffTime
            );

            arenaManager.createArena(
                    arenaName,
                    worldName,
                    maxPlayers,
                    pvpOffTime
            );

            Arena loadedArena =
                    arenaManager.getArena(arenaName, "1");

            if (loadedArena == null) {
                Bukkit.getLogger().severe(
                        "[SkyWar] createArena não registrou a arena: "
                                + arenaName
                );
            } else {
                Bukkit.getLogger().info(
                        "[SkyWar] Arena registrada com sucesso: "
                                + arenaName
                );
            }
        }
    }
}