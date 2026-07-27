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
import br.dev.santos.skywar.player.PlayerManager;

public class ShopMenu {

    public static final String TITLE = ChatColor.translateAlternateColorCodes('&', "&8&lLoja");

    private static final int INVENTORY_SIZE = 45;
    private static final int BALANCE_SLOT = 40;

    private final KitManager kitManager;
    private final PlayerManager playerManager;
    private final Map<Integer, String> kitNamesBySlot;
    private final Map<String, Integer> prices;

    public ShopMenu(KitManager kitManager, PlayerManager playerManager) {
        this.kitManager = kitManager;
        this.kitNamesBySlot = new HashMap<Integer, String>();
        this.prices = new HashMap<String, Integer>();
        this.playerManager = playerManager;

        registerPrices();
    }

    public void open(Player player, PlayerData playerData) {
        Inventory inventory = Bukkit.createInventory(null, INVENTORY_SIZE, TITLE);

        kitNamesBySlot.clear();

        Set<Integer> occupiedSlots = new HashSet<Integer>();
        int nextDynamicSlot = getLastConfiguredSlot() + 1;

        for (Kit kit : kitManager.getKits()) {
            if (kit == null || kit.getName() == null) {
                continue;
            }

            if (playerData != null
                    && playerData.getKits().containsKey(kit.getName())) {

                continue;
            }

            int slot;

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
                    && configuredSlot != BALANCE_SLOT
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

            inventory.setItem(
                    slot,
                    createShopIcon(kit));

            occupiedSlots.add(slot);
            kitNamesBySlot.put(slot, kit.getName());
        }

        inventory.setItem(
                BALANCE_SLOT,
                createBalanceItem(player));

        player.openInventory(inventory);
    }

    public String getKitNameBySlot(int slot) {
        return kitNamesBySlot.get(slot);
    }

    public boolean isBalanceSlot(int slot) {
        return slot == BALANCE_SLOT;
    }

    public int getPrice(String kitName) {
        Integer price = prices.get(normalize(kitName));

        if (price == null) {
            return 1000;
        }

        return price;
    }

    private ItemStack createShopIcon(Kit kit) {
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

            List<String> lore = new ArrayList<String>();
            List<String> description = menuOptions.getDescription();

            if (description != null) {
                for (String line : description) {
                    if (line == null) {
                        continue;
                    }

                    lore.add(
                            ChatColor.translateAlternateColorCodes(
                                    '&',
                                    line));
                }
            }

            lore.add("");
            lore.add(
                    ChatColor.WHITE
                            + "Preço: "
                            + ChatColor.DARK_GREEN
                            + getPrice(kit.getName()));

            meta.setLore(lore);

        } else {
            if (!meta.hasDisplayName()) {
                meta.setDisplayName(kit.getName());
            }

            List<String> lore;

            if (meta.hasLore()) {
                lore = new ArrayList<String>(meta.getLore());
            } else {
                lore = new ArrayList<String>();
            }

            lore.add("");
            lore.add(
                    ChatColor.WHITE
                            + "Preço: "
                            + getPrice(kit.getName()));

            meta.setLore(lore);
        }

        icon.setItemMeta(meta);

        return icon;
    }

    private ItemStack createBalanceItem(Player player) {
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) {
            return item;
        }

        meta.setDisplayName(
                ChatColor.DARK_AQUA + "Seu saldo:");

        List<String> lore = new ArrayList<String>();

        lore.add(
                ChatColor.WHITE
                        + "Créditos: "
                        + ChatColor.DARK_GREEN
                        + this.playerManager.get(player).getAllCredits());

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    private int findNextAvailableSlot(
            int startSlot,
            Set<Integer> occupiedSlots) {

        int firstSlot = Math.max(startSlot, 0);

        for (int slot = firstSlot; slot < INVENTORY_SIZE; slot++) {
            if (slot == BALANCE_SLOT) {
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
                    && slot != BALANCE_SLOT
                    && slot > lastSlot) {

                lastSlot = slot;
            }
        }

        return lastSlot;
    }

    private void registerPrices() {
        registerPrice("Arqueiro", 1000);
        registerPrice("Homem-fogo", 1000);
        registerPrice("Sacerdote", 1000);
        registerPrice("Homem-bomba", 1000);
        registerPrice("Vampiro", 1000);
        registerPrice("Encantador", 1000);
        registerPrice("Minerador", 1000);
        registerPrice("Construtor", 1000);
        registerPrice("Zeus", 1000);
        registerPrice("Peso-pena", 1000);
        registerPrice("Esquimo", 1000);
        registerPrice("Poseidon", 1000);
        registerPrice("Homem-das-Cavernas", 1000);
        registerPrice("Apple", 1000);
        registerPrice("Grandpa", 1000);
        registerPrice("Ferramentas", 1000);
        registerPrice("Homem-aranha", 1000);
        registerPrice("The-flash", 1000);
        registerPrice("Ninja", 4500);
        registerPrice("Sopa", 1000);
        registerPrice("Vida-extra", 1000);
        registerPrice("Assassino", 1000);
        registerPrice("Enderman", 5500);
        registerPrice("Paladino", 1000);
    }

    private void registerPrice(
            String kitName,
            int price) {

        prices.put(normalize(kitName),price);
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

    private String normalize(String text) {
        return text
                .replace("-", "")
                .replace("_", "")
                .replace(" ", "")
                .replace("á", "a")
                .replace("à", "a")
                .replace("ã", "a")
                .replace("â", "a")
                .replace("é", "e")
                .replace("ê", "e")
                .replace("í", "i")
                .replace("ó", "o")
                .replace("ô", "o")
                .replace("õ", "o")
                .replace("ú", "u")
                .replace("ç", "c");
    }
}