package br.dev.santos.skywar.kit.menu;

import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.kit.menu.menus.KitMenu;
import br.dev.santos.skywar.kit.menu.menus.ShopMenu;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;

public class MenuManager {

    private final KitMenu kitMenu;
    private final ShopMenu shopMenu;

    public MenuManager(KitManager kitManager, PlayerManager playerManager) {
        this.kitMenu = new KitMenu(kitManager);
        this.shopMenu = new ShopMenu(kitManager, playerManager);
    }

    public void openKitMenu(PlayerData player) {
        kitMenu.open(player.getPlayerEntity(), player );
    }

    public void openShopMenu(PlayerData player) {
        shopMenu.open(player.getPlayerEntity(), player);
    }

    public KitMenu getKitMenu() {
        return kitMenu;
    }

    public ShopMenu getShopMenu() {
        return shopMenu;
    }
}