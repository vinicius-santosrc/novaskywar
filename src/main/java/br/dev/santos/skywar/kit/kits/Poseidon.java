package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Poseidon extends Kit {
    public Poseidon() {
        super("Poseidon", true, new ItemStack(Material.WATER_BUCKET), false);
        addItem(new ItemStack(Material.WATER_BUCKET, 5));
    }
}
