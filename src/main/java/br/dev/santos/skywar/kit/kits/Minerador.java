package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Minerador extends Kit{
    public Minerador() {
        super("Minerador", true, new ItemStack(Material.IRON_PICKAXE), false);
        addItem(new ItemStack(Material.IRON_PICKAXE));
    }
}
