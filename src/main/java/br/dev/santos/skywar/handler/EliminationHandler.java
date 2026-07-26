package br.dev.santos.skywar.handler;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.arena.Arena;
import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.kit.kits.Assassino;
import br.dev.santos.skywar.kit.kits.Vampiro;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;

public class EliminationHandler {
    private final ArenaMessenger arenaMessenger;
    private final PlayerManager playerManager;
    String deathMessage;

    Skywar plugin = Skywar.getPlugin(Skywar.class);
    FileConfiguration config = plugin.getConfig();

    public EliminationHandler(ArenaMessenger arenaMessenger, PlayerManager playerManager) {
        this.arenaMessenger = arenaMessenger;
        this.playerManager = playerManager;
    }

    public void handle(Arena arena, PlayerData playerDead) {
        Player playerDeadEntity = playerDead.getPlayerEntity();
        EntityDamageEvent lastDamageCause = playerDeadEntity.getLastDamageCause();

        if (lastDamageCause instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) lastDamageCause;
            if (entityDamageEvent.getDamager() instanceof Player) {
                Player killer = (Player) entityDamageEvent.getDamager();
                PlayerData killerData = this.playerManager.get(killer);
                killerData.addCredits("eliminar {X} jogador", 8);
                
                if (!killerData.getArena().firstBlood) {
                    killerData.getArena().firstBlood = true;
                    killerData.addCredits("dar o First Blood", 12);
                    deathMessage = config.getString("messages.first_blood_message")
                            .replace("{player}", playerDeadEntity.getDisplayName())
                            .replace("{killer}", killer.getDisplayName())
                            .replace("{X}", String.valueOf(arena.alivePlayers.size()))
                            .replace("{Y}", String.valueOf(arena.maxPlayers));
                }
                else {
                    this.deathMessage = config.getString("messages.player_killed_oponent")
                            .replace("{player}", playerDeadEntity.getDisplayName())
                            .replace("{killer}", killer.getDisplayName())
                            .replace("{X}", String.valueOf(arena.alivePlayers.size()))
                            .replace("{Y}", String.valueOf(arena.maxPlayers));
                }


                if (killerData.getKit() instanceof Assassino) {
                    killer.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 600, 1));
                }
                if (killerData.getKit() instanceof Vampiro) {
                    double health = killer.getHealth();
                    killer.setHealth(health + 5);
                }
            }
        } else if (lastDamageCause.getCause() == EntityDamageEvent.DamageCause.VOID) {
            this.deathMessage = config.getString("messages.death")
                    .replace("{player}", playerDeadEntity.getDisplayName())
                    .replace("{X}", String.valueOf(arena.alivePlayers.size()))
                    .replace("{Y}", String.valueOf(arena.maxPlayers));
        } 
        else {
            this.deathMessage = config.getString("messages.messageDeath")
                    .replace("{player}", playerDeadEntity.getDisplayName())
                    .replace("{X}", String.valueOf(arena.alivePlayers.size()))
                    .replace("{Y}", String.valueOf(arena.maxPlayers));
        }

        this.arenaMessenger.sendMessageToArena(arena, deathMessage);
    }

}
