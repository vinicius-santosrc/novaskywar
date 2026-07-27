package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class HomemDasCavernas extends Kit {
    public HomemDasCavernas() {
        super("Homem-das-Cavernas", true, new ItemStack(Material.FLINT_AND_STEEL), false,
                new MenuOptions(
                        "§2Kit Homem das Cavernas",
                        Arrays.asList(
                                "§fRecebe um isqueiro"),
                        3, 2),
                1000);
        addItem(getIcon());
    }
}
