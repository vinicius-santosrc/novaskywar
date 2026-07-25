package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Paladino extends Kit {
    public Paladino() {
        super("Paladino", true, new ItemStack(Material.IRON_CHESTPLATE), false);
        addItem(new ItemStack(Material.IRON_CHESTPLATE));
    }
}
