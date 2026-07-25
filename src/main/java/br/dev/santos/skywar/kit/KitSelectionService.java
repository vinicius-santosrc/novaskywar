package br.dev.santos.skywar.kit;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;

public class KitSelectionService {
    private final PlayerManager playerManager;
    private final KitManager kitManager;

    public KitSelectionService(PlayerManager playerManager, KitManager kitManager) {
        this.playerManager = playerManager;
        this.kitManager = kitManager;
    }

    public void chooseKit(Player player, Kit kit) {
        ItemStack kitIconTest = this.kitManager.getKitIcon(kit);
        if (kitIconTest == null) {
            player.sendMessage("§cEsse kit não existe ou está indisponível.");
            return;
        }
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = this.playerManager.getOrCreate(player);
        playerData.setKit(kit);

        if (kit == null) {
            ItemStack chest = new ItemStack(Material.CHEST);
            ItemMeta metaChest = chest.getItemMeta();
            if (metaChest != null) {
                metaChest.setDisplayName("§6Seleção de KIT");
                chest.setItemMeta(metaChest);
            }
            player.getInventory().setItem(3, chest);
            return;
        }

        String message = config.getString("messages.kit_selected").replace("{kit}", kit.getName());
        player.sendMessage(message);
        ItemStack kitIcon = kitIconTest.clone();
        ItemMeta meta = kitIcon.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§6Seleção de KIT");
            kitIcon.setItemMeta(meta);
        }
        player.getInventory().setItem(3, kitIcon);
    }

    public void removeKit(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = this.playerManager.get(player);
        if (playerData != null) {
            playerData.setKit(null);
        }

        String message = config.getString("messages.kit_removed");
        player.sendMessage(message);
    }

    public void giveSelectionItems(Player player) {
        //Define bau de seleção de Kits

        ItemStack chest = new ItemStack(Material.CHEST);
        ItemMeta chestMeta = chest.getItemMeta();
        if (chestMeta != null) {
            chestMeta.setDisplayName("§6Seleção de KIT");
            chest.setItemMeta(chestMeta);
        }

        // Define esmeralda de loja de Kits

        ItemStack emerald = new ItemStack(Material.EMERALD);
        ItemMeta emeraldMeta = emerald.getItemMeta();
        if (emeraldMeta != null) {
            emeraldMeta.setDisplayName("§2Loja");
            emerald.setItemMeta(emeraldMeta);
        }

        // Envia pro inventário

        Inventory inventory = player.getInventory();
        inventory.setItem(3, chest);
        inventory.setItem(5, emerald);
    }

    public void checkKit(Player player) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        PlayerData playerData = this.playerManager.get(player);
        if (playerData != null && playerData.getKit() != null) {
            String message = config.getString("messages.actual_kit").replace("{kit}", playerData.getKit().getName());
            player.sendMessage(message);
        } else {
            String message = config.getString("messages.noactual_kit");
            player.sendMessage(message);
        }
    }

}
