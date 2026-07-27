package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Paladino extends Kit {
    public Paladino() {
        super("Paladino", true, new ItemStack(Material.IRON_CHESTPLATE), false,
                new MenuOptions(
                        "§2Kit Paladino",
                        Arrays.asList(
                                "§fRecebe um Peitoral de Ferro"),
                        5, 1),
                1000);
        addItem(new ItemStack(Material.IRON_CHESTPLATE));
    }
}
