package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.AssassinoAbility;

public class Assassino extends Kit {
    public Assassino() {
        super("Assassino", true, new ItemStack(Material.DROPPER), false, new AssassinoAbility());
    }
}
