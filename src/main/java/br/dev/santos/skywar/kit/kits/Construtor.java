package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Construtor extends Kit{
    public Construtor() {
        super("Construtor", true, new ItemStack(Material.STONE), false);
        addItem(new ItemStack(Material.STONE, 32));
    }
}
