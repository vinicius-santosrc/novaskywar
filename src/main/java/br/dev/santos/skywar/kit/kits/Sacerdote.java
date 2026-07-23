package br.dev.santos.skywar.kit.kits;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import br.dev.santos.skywar.kit.Kit;

public class Sacerdote extends Kit{
    public Sacerdote() {
        super("Sacerdote", true, new ItemStack(Material.POTION), false);

        ItemStack lifePotion = new ItemStack(Material.POTION, 3, (short) 1);
        ItemStack regenerationPotion = new ItemStack(Material.POTION, 3, (short) 1);

        addItem(lifePotion);
        addItem(regenerationPotion);
    }
}
