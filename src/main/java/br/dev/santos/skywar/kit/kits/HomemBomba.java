package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;
import br.dev.santos.skywar.kit.ability.abilities.HomemBombaAbility;

public class HomemBomba extends Kit {
    public HomemBomba(HomemBombaAbility homemBombaAbility) {
        super("Homem-Bomba", true, new ItemStack(Material.TNT), false,
                new MenuOptions(
                        "§4Kit Homem-Bomba",
                        Arrays.asList(
                                "§fRecebe 3 TNTs"),
                        6, 1),
                1000,
                homemBombaAbility);
        addItem(new ItemStack(Material.TNT, 3));
    }
}
