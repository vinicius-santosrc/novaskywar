package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.VidaExtraAbility;

public class VidaExtra extends Kit {

    public VidaExtra(VidaExtraAbility vidaExtraAbility) {
        super("Vida-extra", true, new ItemStack(Material.GOLDEN_APPLE), true, vidaExtraAbility);
    }
}