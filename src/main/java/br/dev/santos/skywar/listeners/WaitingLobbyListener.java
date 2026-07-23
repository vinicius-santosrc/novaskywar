package br.dev.santos.skywar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.warp.WarpManager;
import br.dev.santos.skywar.player.PlayerData.PlayerState;

public final class WaitingLobbyListener implements Listener {

    private final PlayerManager playerManager;
    private final WarpManager warpManager;

    public WaitingLobbyListener(
            PlayerManager playerManager,
            WarpManager warpManager) {

        this.playerManager = playerManager;
        this.warpManager = warpManager;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        if (player.getLocation().getY() < 0 && playerData.getStatus() == PlayerState.WAITING) {
            this.warpManager.teleportToWaitingLobby(player);
        }
    }

    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData == null || !"WaitingLobby".equals(playerData.getStatus())) {
            return;
        }

        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) return;

        event.setCancelled(true);

        if (meta.getDisplayName().equals("§6Seleção de KIT")) {
            Bukkit.dispatchCommand(player, "chestcommands open swkits " + player.getDisplayName());
        } else if (item.getType() == Material.EMERALD) {
            Bukkit.dispatchCommand(player, "chestcommands open swloja " + player.getDisplayName());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData != null && "WaitingLobby".equals(playerData.getStatus())) {
            event.setCancelled(true);

            // Prevent armor equip via shift-click or number keys
            if (event.getCurrentItem() != null && event.getCurrentItem().getType().name().contains("CHESTPLATE")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData != null && "WaitingLobby".equals(playerData.getStatus())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData != null && "WaitingLobby".equals(playerData.getStatus())) {
            event.setCancelled(true);
        }
    }
}