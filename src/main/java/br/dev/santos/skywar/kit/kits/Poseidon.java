package br.dev.santos.skywar.kit.kits;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Poseidon extends Kit {
    public Poseidon() {
        super("Poseidon", true, new ItemStack(Material.WATER_BUCKET), false,
                new MenuOptions(
                        "§9Kit Poseidon",
                        Arrays.asList(
                                "§fRecebe 5 Baldes de água"),
                        2, 1),
                1000);
        addItem(new ItemStack(Material.WATER_BUCKET, 5));
    }
}
