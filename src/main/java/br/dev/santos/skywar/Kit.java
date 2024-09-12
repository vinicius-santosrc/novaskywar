package br.dev.santos.skywar;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Kit implements Listener {

    // Array para armazenar os kits
    private static List<KitInterface> kits = new ArrayList<>();

    // Classe interna que define a interface do kit
    public static class KitInterface {
        String name;
        Boolean haveItems;
        ItemStack item;
        Boolean exclusiveVip;
        String hability;

        public KitInterface(String name, Boolean haveItems, ItemStack item, Boolean exclusiveVip, String hability) {
            this.name = name;
            this.haveItems = haveItems;
            this.item = item;
            this.exclusiveVip = exclusiveVip;
            this.hability = hability;
        }

        public String getName() {
            return name;
        }

        public ItemStack getItem() {
            return item;
        }

        public Boolean isExclusiveVip() {
            return exclusiveVip;
        }

        public String getHability() {
            return hability;
        }
    }

    public static void addKit(KitInterface kit) {
        kits.add(kit);
    }

    public static void giveItems(Player player, String kitName) {
        for (KitInterface kit : kits) {
            if (kit.getName().equalsIgnoreCase(kitName)) {
                ItemStack item = new ItemStack(kit.getItem());
                ItemMeta itemMeta = item.getItemMeta();

                item.setItemMeta(itemMeta);

                Inventory inventory = player.getInventory();
                inventory.addItem(item);

                break;
            }
        }
    }

    public static void initializeKits() {
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL);
        ItemStack ironchest = new ItemStack(Material.IRON_CHESTPLATE);

        addKit(new KitInterface("Enderman", true, enderpearl, true, ""));
        addKit(new KitInterface("Paladino", true, ironchest, false, ""));
        addKit(new KitInterface("Vida-Extra", false, enderpearl, true, "Vida-Extra"));
    }

}
