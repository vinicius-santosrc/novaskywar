package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Encantador extends Kit{
    public Encantador() {
        super("Encantador", true, new ItemStack(Material.ENCHANTMENT_TABLE), false);
        addItem(new ItemStack(Material.ENCHANTMENT_TABLE));
        addItem(new ItemStack(Material.EXP_BOTTLE, 5));
    }
}
