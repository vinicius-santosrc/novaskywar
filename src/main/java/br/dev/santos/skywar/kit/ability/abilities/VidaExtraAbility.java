package br.dev.santos.skywar.kit.ability.abilities;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.kit.ability.Ability;
import br.dev.santos.skywar.kit.kits.VidaExtra;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.warp.WarpManager;

public class VidaExtraAbility extends Ability {
    PlayerManager playerManager;
    WarpManager warpManager;
    ArenaMessenger arenaMessenger;

    public VidaExtraAbility(PlayerManager playerManager, WarpManager warpManager, ArenaMessenger arenaMessenger) {
        super("Vida-extra");
        this.playerManager = playerManager;
        this.warpManager = warpManager;
        this.arenaMessenger = arenaMessenger;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        Player player = event.getEntity();
        PlayerData playerData = this.playerManager.get(player);
        WarpManager warpManager;

        if (playerData.getArena() == null) {
            return;
        }

        if (playerData.getKit() instanceof VidaExtra) {
            if (!playerData.getHasUsedExtraLife()) {
                // Salva o inventário e a armadura do jogador
                    ItemStack[] savedInventory = player.getInventory().getContents();
                    ItemStack[] savedArmor = player.getInventory().getArmorContents();

                    // Limpa os drops de itens
                    event.getDrops().clear();

                    // Teleporta para a warp configurada
                    this.warpManager.teleportToIsland(player, playerData.getArena(), playerData.getIsland());
                    
                    // Adiciona um pequeno delay antes de restaurar o inventário e a armadura
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            // Restaura o inventário e a armadura do jogador
                            player.getInventory().setContents(savedInventory);
                            player.getInventory().setArmorContents(savedArmor);
                            player.updateInventory(); // Atualiza o inventário do jogador para garantir que seja
                                                      // refletido no cliente
                        }
                    }.runTaskLater(Skywar.getPlugin(Skywar.class), 5L); // Pequeno delay de 5 ticks (0.25 segundos)

                    // Envia mensagens
                    String extraLifeMessage = config.getString("messages.extralifekit_message")
                            .replace("{player}", player.getDisplayName());
                    this.arenaMessenger.sendMessageToArena(playerData.getArena(), extraLifeMessage);

                    String extraLifeMessageToPlayer = config.getString("messages.extralife_message_to_player");
                    player.sendMessage(extraLifeMessageToPlayer);

                    // Marca que o jogador usou a vida extra
                    playerData.setHasUsedExtraLife(true);
            }
        }
    }
}
