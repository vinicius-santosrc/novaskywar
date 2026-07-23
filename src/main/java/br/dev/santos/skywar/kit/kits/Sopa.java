package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Sopa extends Kit {
    public Sopa() {
        super("Sopa", true, new ItemStack(Material.MUSHROOM_SOUP), false);

        addItem(new ItemStack(Material.MUSHROOM_SOUP, 3));
    }
    
}
