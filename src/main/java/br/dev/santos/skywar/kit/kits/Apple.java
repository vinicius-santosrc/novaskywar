package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Apple extends Kit {
    public Apple() {
        super("Apple", true, new ItemStack(Material.GOLDEN_APPLE), false);
        addItem(new ItemStack(Material.GOLDEN_APPLE, 3));
    }
}
