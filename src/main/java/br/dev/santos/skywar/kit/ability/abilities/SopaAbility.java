package br.dev.santos.skywar.kit.ability.abilities;

import br.dev.santos.skywar.kit.ability.Ability;
import br.dev.santos.skywar.kit.kits.Sopa;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class SopaAbility extends Ability{
    private final PlayerManager playerManager;

    public SopaAbility(PlayerManager playerManager) {
        super("Sopa");
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        PlayerData playerData = this.playerManager.get(player);

        if(!(playerData.getKit() instanceof Sopa)) {
            return;
        }

        Inventory inventory = event.getInventory();

        if (inventory == null) {
            return;
        }

        boolean isSoup = event.getCurrentItem().getType().equals(Material.MUSHROOM_SOUP);

        if(isSoup) {
            // Saúde maxima, cancela
            if(player.getHealth() >= 20) {
                event.setCancelled(true);
                return;
            }

            // Adiciona mais 3 corações para o jogador
            player.setHealth(player.getHealth() + 7);
            player.setFoodLevel(20);

            player.getInventory().setItemInHand(new ItemStack(Material.BOWL));
        }
    }
}
