package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.HomemBombaAbility;

public class HomemBomba extends Kit {
    public HomemBomba() {
        super("Homem-Bomba", true, new ItemStack(Material.TNT), false, new HomemBombaAbility());
        addItem(new ItemStack(Material.TNT, 3));
    }
}
