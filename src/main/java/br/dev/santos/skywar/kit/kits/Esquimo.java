package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Esquimo extends Kit{
    public Esquimo() {
        super("Esquimo", true, new ItemStack(Material.SNOW_BALL), false);
        addItem(new ItemStack(Material.SNOW_BALL, 10));
    }
}
