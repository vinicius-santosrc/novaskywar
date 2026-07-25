package br.dev.santos.skywar.kit.ability.abilities;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.kit.ability.Ability;
import br.dev.santos.skywar.kit.kits.Enderman;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.player.PlayerData.PlayerState;

public class EndermanAbility extends Ability {
    PlayerManager playerManager;
    public EndermanAbility(PlayerManager playerManager) {
        super("Enderman");
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onEnderPearl(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
            Skywar plugin = Skywar.getPlugin(Skywar.class);
            FileConfiguration config = plugin.getConfig();
            
            Player player = event.getPlayer();
            PlayerData playerData = this.playerManager.get(player);

            Location enderPearlLocation = event.getTo();

            // Verifica se o kit é o Enderman
            if (playerData.getKit() instanceof Enderman) {
                // Envia mensagem para o jogador será teleportado em 3 segundos
                String message = config.getString("messages.enderpearl_message");
                player.sendMessage(message);

                event.setCancelled(true);

                // Da play no som da bigorna onde a enderpearl foi jogada 
                enderPearlLocation.getWorld().playSound(enderPearlLocation, Sound.valueOf("ANVIL_LAND"), 1.0f, 1.0f);

                // Teleporta o jogador após 60L(3 segundos) 15 blocos acima
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Location newLocation = enderPearlLocation.clone().add(0, 15, 0);
                        player.teleport(newLocation);
                        player.setNoDamageTicks(60);
                    }
                }.runTaskLater(plugin, 60L);
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            PlayerData playerData = this.playerManager.get(player);

            if (playerData.getKit() instanceof Enderman && event.getCause() == EntityDamageEvent.DamageCause.FALL && playerData.getStatus().equals(PlayerState.PLAYING)) {
                event.setCancelled(true);
            }
        }
    }
}
