package br.dev.santos.skywar.player;

import java.util.UUID;

import org.bukkit.entity.Player;

import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.kit.Kit;

public class PlayerData {
    private String name;
    private final UUID id;
    private Kit kit = null;
    private Arena arena;
    private int island;
    private PlayerState status;
    private Player playerEntity;
    private boolean isDead = false;

    private Boolean hasUsedExtraLife = false;

    public PlayerData(String name, UUID uniqueId, Player playerEntity) {
        this.name = name;
        this.id = uniqueId;
        this.status = PlayerState.LOBBY;
        this.arena = null;
        this.playerEntity = playerEntity;
    }

    public UUID getUniqueId() {
        return id;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getDead() {
        return this.isDead;
    }

    public Player getPlayerEntity() {
        return playerEntity;
    }

    public Kit getKit() {
        return kit;
    }

    public Boolean getHasUsedExtraLife() {
        return hasUsedExtraLife;
    }

    public void setHasUsedExtraLife(Boolean hasUsedExtraLife) {
        this.hasUsedExtraLife = hasUsedExtraLife;
    }

    public String getName() {
        return name;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void setIsland(int island) {
        this.island = island;
    }

    public int getIsland() {
        return island;
    }

    public Arena getArena() {
        return arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public PlayerState getStatus() {
        return status;
    }

    public void setStatus(PlayerState status) {
        this.status = status;
    }

    public static enum PlayerState {
        LOBBY,
        WAITING,
        PLAYING,
        SPECTATOR,
        DEAD
    }

}
