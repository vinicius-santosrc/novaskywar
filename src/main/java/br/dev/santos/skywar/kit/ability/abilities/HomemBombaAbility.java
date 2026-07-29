package br.dev.santos.skywar.kit.ability.abilities;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.kit.ability.Ability;
import br.dev.santos.skywar.kit.kits.Assassino;
import br.dev.santos.skywar.kit.kits.HomemBomba;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class HomemBombaAbility extends Ability {
    private final PlayerManager playerManager;
    public HomemBombaAbility(PlayerManager playerManager) {
        super("Homem-Bomba");
        this.playerManager = playerManager;
    }

    @EventHandler()
    public void onBlockPlace(BlockPlaceEvent blockEvent) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        Player playerDead = blockEvent.getPlayer();
        PlayerData playerData = this.playerManager.get(playerDead);

        Block block = blockEvent.getBlockPlaced();

        if(!(playerData.getKit() instanceof HomemBomba)) {
            return;
        }

        if (block.getType() == Material.TNT) {
            Location locationBlock = block.getLocation();
            block.setType(Material.AIR);
            locationBlock.getWorld().spawn(locationBlock, org.bukkit.entity.TNTPrimed.class);
        }
    }
}
