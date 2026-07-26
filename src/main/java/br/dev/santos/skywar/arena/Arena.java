/**
 * 
 * NovaSkyWar Kit
 * Criado por Vinicius Santos em 21/07/2026.
 * Copyright (c) 2026 Vinicius Santos. Todos os direitos reservados.
 * Obs: Este código é parte do projeto NovaSkyWar e não deve ser distribuído sem autorização.
 * 
 * Essa classe representa o modelo das Salas.
*/

package br.dev.santos.skywar.arena;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.tasks.StartCountdownTask;

public class Arena {
    private String name;
    private String nameOfTheWorld;
    private UUID id = UUID.randomUUID();
    private StatusArena status = StatusArena.OPEN;
    public boolean pvpOn;
    public int pvpOffTime = 5;
    public int maxPlayers = 12;
    public int minPlayers = 2;
    public ArrayList<PlayerData> alivePlayers = new ArrayList<PlayerData>();
    public ArrayList<PlayerData> spectators = new ArrayList<PlayerData>();
    private int timeToStart;
    public int timeOfTheMatch;
    private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
    public boolean firstBlood = false;
    public boolean feastReached = false;

    private FileConfiguration arenaConfig;
    private final ArenaMessenger arenaMessenger;

    private StartCountdownTask countdownTask;

    PlayerManager playerManager;

    public Arena(String name, String nameOfTheWorld, int maxPlayers, int minPlayers, int pvpOffTime,
            FileConfiguration config, ArenaMessenger arenaMessenger) {
        this.name = name;
        this.nameOfTheWorld = nameOfTheWorld;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;
        this.pvpOffTime = pvpOffTime;
        this.arenaConfig = config;
        this.arenaMessenger = arenaMessenger;
    }
    
    public ConfigurationSection getArenaConfig() {
        return this.arenaConfig.getConfigurationSection("arenas." + this.name);
    }

    public StartCountdownTask getCountdownTask() {
        return countdownTask;
    }

    public void setCountdownTask(StartCountdownTask countdownTask) {
        this.countdownTask = countdownTask;
    }

    public int getTimeToStart() {
        return this.timeToStart;
    };

    public String getName() {
        return this.name;
    };

    public String getNameOfTheWorld() {
        return this.nameOfTheWorld;
    }

    public void setNameOfTheWorld(String nameOfTheWorld) {
        this.nameOfTheWorld = nameOfTheWorld;
    }

    public UUID getId() {
        return id;
    };

    public StatusArena getStatus() {
        return this.status;
    };

    public ArrayList<PlayerData> getPlayers() {
        return this.players;
    };

    public void addPlayer(PlayerData player) {
        this.players.add(player);
    }

    public void removePlayer(PlayerData player) {
        this.players.remove(player);
    }

    public void setTimeToStart(int time) {
        this.timeToStart = time;
    };

    public void setStatus(StatusArena status) {
        this.status = status;
    };

    public PlayerData getWinner() {
        return this.alivePlayers.get(0);
    }

    public boolean isFull() {
        if (this.maxPlayers == this.players.size()) {
            return true;
        }
        return false;
    }

    public boolean canJoin(PlayerData player) {
        if (player.getPlayerEntity().hasPermission("vip_skywar_join")) {
            return this.status == StatusArena.VIP;
        }
        return this.status == StatusArena.OPEN;
    }

    public void sendMessageToArena(String message) {
        this.arenaMessenger.sendMessageToArena(this, message);
    }

    public static enum StatusArena {
        STARTED,
        FINISHING,
        RESETING,
        VIP,
        OPEN
    };
}
