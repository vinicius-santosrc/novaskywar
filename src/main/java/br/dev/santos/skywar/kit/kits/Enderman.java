package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.EndermanAbility;

public class Enderman extends Kit {

    public Enderman() {
        super("Enderman", true, new ItemStack(Material.ENDER_PEARL), true, new EndermanAbility());
        addItem(new ItemStack(Material.ENDER_PEARL));
    }
}