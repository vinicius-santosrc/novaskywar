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

import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;

public class Arena {
    private String name;
    private String nameOfTheWorld;
    private UUID id = UUID.randomUUID();
    private StatusArena status = StatusArena.OPEN;
    public boolean pvpOff;
    public int pvpOffTime = 5;
    public int maxPlayers = 12;
    public int minPlayers = 2;
    public ArrayList<PlayerData> alivePlayers = new ArrayList<PlayerData>();
    public ArrayList<PlayerData> spectators = new ArrayList<PlayerData>();
    private int timeToStart;
    public int timeOfTheMatch;
    private ArrayList<PlayerData> players = new ArrayList<PlayerData>();
    
    PlayerManager playerManager;

    public Arena(String name, String nameOfTheWorld, int maxPlayers, int minPlayers) {
        this.name = name;
        this.nameOfTheWorld = nameOfTheWorld;
        this.maxPlayers = maxPlayers;
        this.minPlayers = minPlayers;

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
        if(player.getPlayerEntity().hasPermission("vip_skywar_join")) {
            return this.status == StatusArena.VIP;
        }
        return this.status == StatusArena.OPEN;
    }

    public static enum StatusArena {
        STARTED,
        FINISHING,
        RESETING,
        VIP,
        OPEN
    };
}
