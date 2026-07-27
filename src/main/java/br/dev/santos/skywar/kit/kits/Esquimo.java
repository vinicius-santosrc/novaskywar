package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Esquimo extends Kit {
    public Esquimo() {
        super("Esquimo", true, new ItemStack(Material.SNOW_BALL), false,
                new MenuOptions(
                        "§3Kit Esquimo",
                        Arrays.asList("§fRecebe 10 bolas de neve"),
                        2, 2),
                1000);
        addItem(new ItemStack(Material.SNOW_BALL, 10));
    }
}
