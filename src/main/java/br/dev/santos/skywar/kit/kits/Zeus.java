package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.ZeusAbility;

public class Zeus extends Kit {
    public Zeus() {
        super("Zeus", true, new ItemStack(Material.BLAZE_ROD), false, new ZeusAbility());
        addItem(new ItemStack(Material.BLAZE_ROD));
    }
}