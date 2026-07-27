package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Apple extends Kit {
    public Apple() {
        super("Apple", true, new ItemStack(Material.GOLDEN_APPLE), false,
                new MenuOptions(
                        "§5Apple",
                        Arrays.asList("§fRecebe 3 maçãs douradas"),
                        5, 2),
                1000);

        addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
    }
}
