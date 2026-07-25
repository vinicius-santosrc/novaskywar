package br.dev.santos.skywar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.dev.santos.skywar.arena.Arena.StatusArena;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.utils.TeleportUtils;
import br.dev.santos.skywar.warp.WarpManager;
import br.dev.santos.skywar.warp.WarpManager.Coord;
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
            Coord waitingLobby = TeleportUtils.getWaitingLobby(playerData.getArena());
            this.warpManager.teleport(playerData.getPlayerEntity(), waitingLobby);
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            PlayerData playerData = this.playerManager.get(player);
            if (playerData.getStatus().equals(PlayerState.WAITING) || playerData.getArena().getStatus() == StatusArena.FINISHING)
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = (Player) event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);
        if (playerData.getStatus().equals(PlayerState.WAITING) || playerData.getArena().getStatus() == StatusArena.FINISHING)
            event.setCancelled(true);
    }


    @EventHandler
    public void onPlayerClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData == null || !playerData.getStatus().equals(PlayerState.WAITING)) {
            return;
        }

        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR)
            return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName())
            return;

        event.setCancelled(true);

        // Atribui comando para o báu
        // Atribui esmeralda para a loja
        if (meta.getDisplayName().equals("§6Seleção de KIT")) {
            Bukkit.dispatchCommand(player, "chestcommands open swkits " + player.getDisplayName());
        } else if (meta.getDisplayName().equals("§2Loja")) {
            Bukkit.dispatchCommand(player, "chestcommands open swloja " + player.getDisplayName());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData.getStatus().equals(PlayerState.WAITING)) {
            event.setCancelled(true);

            // Não deixa equipar armaduras
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

        if (playerData.getStatus().equals(PlayerState.WAITING)) {

            // Bloqueia mover items pelo inventário
            if (playerData.getStatus().equals(PlayerState.WAITING)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = this.playerManager.get(player);

        if (playerData.getStatus().equals(PlayerState.WAITING)) {
            // Bloqueia drop items pelo inventário
            if (playerData.getStatus().equals(PlayerState.WAITING)) {
                event.setCancelled(true);
            }
        }
    }
}