package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class HomemDasCavernas extends Kit {
    public HomemDasCavernas() {
        super("Homem-das-Cavernas", true, new ItemStack(Material.FLINT_AND_STEEL), false);
        addItem(getIcon());
    }
}
