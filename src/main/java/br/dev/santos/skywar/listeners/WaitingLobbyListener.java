package br.dev.santos.skywar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.dev.santos.skywar.arena.Arena.StatusArena;
import br.dev.santos.skywar.kit.menu.MenuManager;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.utils.TeleportUtils;
import br.dev.santos.skywar.warp.WarpManager;
import br.dev.santos.skywar.warp.WarpManager.Coord;
import br.dev.santos.skywar.player.PlayerData.PlayerState;

public final class WaitingLobbyListener implements Listener {

    private final PlayerManager playerManager;
    private final WarpManager warpManager;
    private final MenuManager menuManager;

    public WaitingLobbyListener(
            PlayerManager playerManager,
            WarpManager warpManager,
            MenuManager menuManager) {

        this.playerManager = playerManager;
        this.warpManager = warpManager;
        this.menuManager = menuManager;
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
            if (playerData.getStatus().equals(PlayerState.WAITING)
                    || playerData.getArena().getStatus() == StatusArena.FINISHING)
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

        if (event.getAction() == Action.PHYSICAL) {
            event.setCancelled(true);
            return;
        }

        if (event.getClickedBlock() != null
                && isBlockedInteraction(event.getClickedBlock().getType())) {
            event.setCancelled(true);
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
            this.menuManager.openKitMenu(this.playerManager.get(player));
        } else if (meta.getDisplayName().equals("§2Loja")) {
            this.menuManager.openShopMenu(this.playerManager.get(player));
        }
    }

    private boolean isBlockedInteraction(Material material) {
        switch (material) {
            case CHEST:
            case TRAPPED_CHEST:
            case ENDER_CHEST:

            case WOODEN_DOOR:
            case IRON_DOOR_BLOCK:

            case TRAP_DOOR:

            case FENCE_GATE:

            case WOOD_PLATE:
            case STONE_PLATE:
            case IRON_PLATE:
            case GOLD_PLATE:

            case LEVER:
            case STONE_BUTTON:
            case WOOD_BUTTON:

            case DIODE_BLOCK_OFF:
            case DIODE_BLOCK_ON:

            case REDSTONE_COMPARATOR_OFF:
            case REDSTONE_COMPARATOR_ON:
                return true;

            default:
                return false;
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

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        PlayerData playerData = this.playerManager.get(event.getPlayer());

        if (playerData == null) {
            return;
        }

        if (playerData.getStatus() != PlayerState.LOBBY) {
            updateWaitingLobbyVisibility(playerData);
            return;
        }

        restorePlayerVisibility(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        restorePlayerVisibility(event.getPlayer());
    }

    private void updateWaitingLobbyVisibility(PlayerData playerData) {
        Player player = playerData.getPlayerEntity();

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer.equals(player)) {
                continue;
            }

            PlayerData otherPlayerData = this.playerManager.get(otherPlayer);

            boolean isSameMatch = otherPlayerData != null
                    && otherPlayerData.getStatus() != PlayerState.LOBBY
                    && otherPlayerData.getArena() == playerData.getArena();

            if (isSameMatch) {
                player.showPlayer(otherPlayer);
                otherPlayer.showPlayer(player);
            } else {
                player.hidePlayer(otherPlayer);
                otherPlayer.hidePlayer(player);
            }
        }
    }

    private void restorePlayerVisibility(Player player) {
        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer.equals(player)) {
                continue;
            }

            player.showPlayer(otherPlayer);
            otherPlayer.showPlayer(player);
        }
    }
}