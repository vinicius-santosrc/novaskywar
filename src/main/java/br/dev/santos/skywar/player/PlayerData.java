package br.dev.santos.skywar.player;

import java.util.HashMap;
import java.util.Map;
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
    private int creditsEarn = 0;
    private Map<String, Integer> creditsEarnList = new HashMap<>();

    private Boolean hasUsedExtraLife = false;

    public PlayerData(String name, UUID uniqueId, Player playerEntity) {
        this.name = name;
        this.id = uniqueId;
        this.status = PlayerState.LOBBY;
        this.arena = null;
        this.playerEntity = playerEntity;
    }

    public Map<String, Integer> getCreditsEarnList() {
        return this.creditsEarnList;
    }

    public void addCredits(String name, Integer quantity) {
        this.creditsEarnList.put(name, quantity);
        this.creditsEarn += quantity;
        this.playerEntity.sendMessage("§6+" + quantity);
    }

    public UUID getUniqueId() {
        return this.id;
    }

    public int getCreditsEarn() {
        return this.creditsEarn;
    }

    public void setDead(boolean isDead) {
        this.isDead = isDead;
    }

    public boolean getDead() {
        return this.isDead;
    }

    public Player getPlayerEntity() {
        return this.playerEntity;
    }

    public Kit getKit() {
        return this.kit;
    }

    public Boolean getHasUsedExtraLife() {
        return this.hasUsedExtraLife;
    }

    public void setHasUsedExtraLife(Boolean hasUsedExtraLife) {
        this.hasUsedExtraLife = hasUsedExtraLife;
    }

    public String getName() {
        return this.name;
    }

    public void setKit(Kit kit) {
        this.kit = kit;
    }

    public void setIsland(int island) {
        this.island = island;
    }

    public int getIsland() {
        return this.island;
    }

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena arena) {
        this.arena = arena;
    }

    public PlayerState getStatus() {
        return this.status;
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
