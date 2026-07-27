package br.dev.santos.skywar.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.menu.MenuManager;
import br.dev.santos.skywar.kit.menu.menus.KitMenu;
import br.dev.santos.skywar.kit.menu.menus.ShopMenu;

public class MenuListener implements Listener {

    private final MenuManager menuManager;

    public MenuListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getInventory();

        if (inventory == null) {
            return;
        }

        String title = event.getView().getTitle();

        if (KitMenu.TITLE.equals(title)) {
            handleKitMenu(event, player);
            return;
        }

        if (ShopMenu.TITLE.equals(title)) {
            handleShopMenu(event, player);
        }
    }

    private void handleKitMenu(
            InventoryClickEvent event,
            Player player) {
        event.setCancelled(true);

        if (event.getRawSlot() < 0
                || event.getRawSlot() >= event.getInventory().getSize()) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return;
        }

        int slot = event.getRawSlot();

        if (menuManager.getKitMenu().isRemoveKitSlot(slot)) {
            player.closeInventory();

            Bukkit.dispatchCommand(
                    player,
                    "skywar kit remove");

            return;
        }

        String kitName = menuManager
                .getKitMenu()
                .getKitNameBySlot(slot);

        if (kitName == null) {
            return;
        }

        player.closeInventory();

        Bukkit.dispatchCommand(
                player,
                "skywar kit " + kitName);
    }

    private void handleShopMenu(
            InventoryClickEvent event,
            Player player) {
        event.setCancelled(true);

        if (event.getRawSlot() < 0
                || event.getRawSlot() >= event.getInventory().getSize()) {
            return;
        }

        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) {
            return;
        }

        int slot = event.getRawSlot();

        if (menuManager.getShopMenu().isBalanceSlot(slot)) {
            return;
        }

        String kitName = menuManager
                .getShopMenu()
                .getKitNameBySlot(slot);

        if (kitName == null) {
            return;
        }

        player.closeInventory();

        Bukkit.dispatchCommand(
                player,
                "skywar comprar " + kitName);
    }
}