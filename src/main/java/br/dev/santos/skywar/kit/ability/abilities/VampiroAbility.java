package br.dev.santos.skywar.kit.ability.abilities;

import br.dev.santos.skywar.Skywar;
import br.dev.santos.skywar.kit.ability.Ability;
import br.dev.santos.skywar.kit.kits.Sopa;
import br.dev.santos.skywar.kit.kits.Vampiro;
import br.dev.santos.skywar.player.PlayerData;
import br.dev.santos.skywar.player.PlayerManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampiroAbility extends Ability {
    private final PlayerManager playerManager;
    public VampiroAbility(PlayerManager playerManager) {
        super("Vampiro");
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Skywar plugin = Skywar.getPlugin(Skywar.class);
        FileConfiguration config = plugin.getConfig();

        Player playerDead = event.getEntity().getPlayer();
        PlayerData playerData = this.playerManager.get(playerDead);

        if(!(playerData.getKit() instanceof Vampiro)) {
            return;
        }

        EntityDamageEvent lastDamageCause = playerDead.getLastDamageCause();

        if (lastDamageCause instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent entityDamageEvent = (EntityDamageByEntityEvent) lastDamageCause;
            if (entityDamageEvent.getDamager() instanceof Player) {
                Player killer = (Player) entityDamageEvent.getDamager();
                killer.setHealth(killer.getHealth() + 10);
            }
        }
    }
}
