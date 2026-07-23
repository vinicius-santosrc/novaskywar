package br.dev.santos.skywar.kit.ability;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import br.dev.santos.skywar.arena.ArenaMessenger;
import br.dev.santos.skywar.kit.ability.abilities.AssassinoAbility;
import br.dev.santos.skywar.kit.ability.abilities.EndermanAbility;
import br.dev.santos.skywar.kit.ability.abilities.HomemAranhaAbility;
import br.dev.santos.skywar.kit.ability.abilities.HomemBombaAbility;
import br.dev.santos.skywar.kit.ability.abilities.SopaAbility;
import br.dev.santos.skywar.kit.ability.abilities.VampiroAbility;
import br.dev.santos.skywar.kit.ability.abilities.VidaExtraAbility;
import br.dev.santos.skywar.kit.ability.abilities.ZeusAbility;
import br.dev.santos.skywar.player.PlayerManager;
import br.dev.santos.skywar.warp.WarpManager;

public class AbilityManager {

    private final Plugin plugin;

    public AbilityManager(
            Plugin plugin,
            PlayerManager playerManager,
            WarpManager warpManager,
            ArenaMessenger arenaMessenger) {

        this.plugin = plugin;

        register(new VidaExtraAbility(playerManager, warpManager, arenaMessenger));
        register(new VampiroAbility());
        register(new AssassinoAbility());
        register(new HomemAranhaAbility());
        register(new HomemBombaAbility());
        register(new EndermanAbility());
        register(new SopaAbility());
        register(new ZeusAbility());
    }

    private void register(Ability ability) {
        Bukkit.getPluginManager().registerEvents(
                ability,
                plugin);
    }
}