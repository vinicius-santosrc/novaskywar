package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Ferramentas extends Kit{
    public Ferramentas() {
        super("Ferramentas", true, new ItemStack(Material.WORKBENCH), false);
        addItem(new ItemStack(Material.WORKBENCH));
        addItem(new ItemStack(Material.FURNACE));
        addItem(new ItemStack(Material.STONE_AXE));
        addItem(new ItemStack(Material.STONE_PICKAXE));
        addItem(new ItemStack(Material.WOOD_SPADE));
    }
}
