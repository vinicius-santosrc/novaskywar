package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Minerador extends Kit {
    public Minerador() {
        super("Minerador", true, new ItemStack(Material.IRON_PICKAXE), false,
                new MenuOptions(
                        "§3Kit Minerador",
                        Arrays.asList(
                                "§fRecebe uma Picareta de Ferro"),
                        7, 1),
                1000);
        addItem(new ItemStack(Material.IRON_PICKAXE));
    }
}
