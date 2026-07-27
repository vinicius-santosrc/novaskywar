package br.dev.santos.skywar.kit.menu.menus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.KitManager;
import br.dev.santos.skywar.player.PlayerData;

public class KitMenu {

    public static final String TITLE = ChatColor.translateAlternateColorCodes('&', "&8&lSeus kits");

    private static final int INVENTORY_SIZE = 45;
    private static final int REMOVE_KIT_SLOT = 40;

    private final KitManager kitManager;
    private final Map<Integer, String> kitNamesBySlot;

    public KitMenu(KitManager kitManager) {
        this.kitManager = kitManager;
        this.kitNamesBySlot = new HashMap<Integer, String>();
    }

    public void open(Player player, PlayerData playerData) {
        // Abre o inventário de acordo com o tamanho e titulo
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, TITLE);

        kitNamesBySlot.clear();

        // Seta slots dinamicos para futuros kits
        Set<Integer> occupiedSlots = new HashSet<Integer>();
        int nextDynamicSlot = getLastConfiguredSlot() + 1;

        for (Kit kit : kitManager.getKits()) {
            if (kit == null || kit.getName() == null) {
                continue;
            }

            if (playerData == null || !playerData.getKits().containsKey(kit.getName())) {
                continue;
            }

            int slot;

            // De acordo com todos os kits pega o menuOptions com suas posições e atribui.

            Kit.MenuOptions menuOptions = kit.getMenuOptions();
            Integer configuredSlot = null;

            if (hasValidPosition(menuOptions)) {
                configuredSlot = toSlot(
                        menuOptions.getPositionX(),
                        menuOptions.getPositionY());
            }

            if (configuredSlot != null
                    && configuredSlot >= 0
                    && configuredSlot < INVENTORY_SIZE
                    && configuredSlot != REMOVE_KIT_SLOT
                    && !occupiedSlots.contains(configuredSlot)) {

                slot = configuredSlot;

            } else {
                slot = findNextAvailableSlot(
                        nextDynamicSlot,
                        occupiedSlots);

                if (slot == -1) {
                    continue;
                }

                nextDynamicSlot = slot + 1;
            }


            // Seta o item no menu de kits
            inventory.setItem(
                    slot,
                    createKitIcon(kit));

            occupiedSlots.add(slot);
            kitNamesBySlot.put(slot, kit.getName());
        }

        // Seta o item para remover kit
        inventory.setItem(
                REMOVE_KIT_SLOT,
                createRemoveKitItem());

        player.openInventory(inventory);
    }

    public String getKitNameBySlot(int slot) {
        return kitNamesBySlot.get(slot);
    }

    public boolean isRemoveKitSlot(int slot) {
        return slot == REMOVE_KIT_SLOT;
    }

    private ItemStack createKitIcon(Kit kit) {
        ItemStack icon = kit.getIcon();

        if (icon == null) {
            icon = new ItemStack(Material.CHEST);
        } else {
            icon = icon.clone();
        }

        ItemMeta meta = icon.getItemMeta();

        if (meta == null) {
            return icon;
        }

        Kit.MenuOptions menuOptions = kit.getMenuOptions();

        if (menuOptions != null) {
            String displayName = menuOptions.getName();

            if (displayName == null || displayName.isEmpty()) {
                displayName = kit.getName();
            }

            if (Boolean.TRUE.equals(kit.getExclusiveVip())) {
                displayName = "&6[VIP] " + displayName;
            }

            meta.setDisplayName(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            displayName));

            List<String> description = menuOptions.getDescription();

            if (description != null) {
                List<String> lore = new ArrayList<String>();

                for (String line : description) {
                    if (line == null) {
                        continue;
                    }

                    String[] brokenLines = line.split("<br>");

                    for (String brokenLine : brokenLines) {
                        lore.add(
                                ChatColor.translateAlternateColorCodes(
                                        '&',
                                        brokenLine));
                    }
                }

                meta.setLore(lore);
            }

        } else if (!meta.hasDisplayName()) {
            meta.setDisplayName(
                    ChatColor.translateAlternateColorCodes(
                            '&',
                            kit.getName()));
        }

        icon.setItemMeta(meta);

        return icon;
    }

    private ItemStack createRemoveKitItem() {
        ItemStack item = new ItemStack(Material.MILK_BUCKET);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(
                ChatColor.GREEN + "Remover kit");

        List<String> lore = new ArrayList<String>();

        lore.add(
                ChatColor.WHITE
                        + "Clique para ficar sem nenhum KIT");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private int findNextAvailableSlot(
            int startSlot,
            Set<Integer> occupiedSlots) {

        int firstSlot = Math.max(startSlot, 0);

        for (int slot = firstSlot; slot < INVENTORY_SIZE; slot++) {
            if (slot == REMOVE_KIT_SLOT) {
                continue;
            }

            if (!occupiedSlots.contains(slot)) {
                return slot;
            }
        }

        return -1;
    }

    private int getLastConfiguredSlot() {
        int lastSlot = -1;

        for (Kit kit : kitManager.getKits()) {
            if (kit == null) {
                continue;
            }

            Kit.MenuOptions menuOptions = kit.getMenuOptions();

            if (!hasValidPosition(menuOptions)) {
                continue;
            }

            int slot = toSlot(
                    menuOptions.getPositionX(),
                    menuOptions.getPositionY());

            if (slot >= 0
                    && slot < INVENTORY_SIZE
                    && slot != REMOVE_KIT_SLOT
                    && slot > lastSlot) {

                lastSlot = slot;
            }
        }

        return lastSlot;
    }

    private boolean hasValidPosition(
            Kit.MenuOptions menuOptions) {

        return menuOptions != null
                && menuOptions.getPositionX() >= 1
                && menuOptions.getPositionX() <= 9
                && menuOptions.getPositionY() >= 1
                && menuOptions.getPositionY() <= 5;
    }

    private int toSlot(
            int positionX,
            int positionY) {

        return ((positionY - 1) * 9)
                + (positionX - 1);
    }
}